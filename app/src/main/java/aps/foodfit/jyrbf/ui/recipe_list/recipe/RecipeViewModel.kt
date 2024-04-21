package aps.foodfit.jyrbf.ui.recipe_list.recipe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aps.foodfit.jyrbf.data.remote.APP_ID
import aps.foodfit.jyrbf.data.remote.APP_KEY
import aps.foodfit.jyrbf.data.remote.FIELDS_SPLITTER
import aps.foodfit.jyrbf.data.remote.IngredientItem
import aps.foodfit.jyrbf.data.remote.RecipeByUriService
import aps.foodfit.jyrbf.data.remote.RecipeItem
import aps.foodfit.jyrbf.data.remote.VALUE_SPLITTER
import aps.foodfit.jyrbf.ui.new_racion.recipe_search.STATE_ERROR
import aps.foodfit.jyrbf.ui.new_racion.recipe_search.STATE_LOADING
import aps.foodfit.jyrbf.ui.new_racion.recipe_search.STATE_SUCCESS
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {

    private val retrofit = RecipeByUriService.getInstanceByUri()
    val recipeItemLD = MutableLiveData<RecipeItem?>()
    var recipeItem: RecipeItem? = null
    val state = MutableLiveData<Int>(STATE_LOADING)

    fun getRecipeList(uri: String) {

        viewModelScope.launch {
            recipeItemLD.postValue(null)
            val response = retrofit.getRecipeByUri(
                "public", uri, APP_ID, APP_KEY
            )
            if (response.isSuccessful) {
                if (response.body()?.count == 0) {
                    state.postValue(STATE_ERROR)
                } else {
                    response.body()?.hits?.map {
                        recipeItem = it.recipe
                    }
                    val recipeItemGetted = recipeItem
                    if (recipeItemGetted != null) {
                        val encoded = StringBuilder()
                        encoded.append(recipeItemGetted.label)
                        encoded.append(FIELDS_SPLITTER)
                        recipeItemGetted.ingredients?.forEach {
                            encoded.append(it.text)
                            encoded.append(VALUE_SPLITTER)
                        }
                        val options =
                            TranslatorOptions.Builder().setSourceLanguage(TranslateLanguage.ENGLISH)
                                .setTargetLanguage(TranslateLanguage.RUSSIAN).build()
                        val englishGermanTranslator = Translation.getClient(options)
                        val conditions = DownloadConditions.Builder().build()
                        englishGermanTranslator.downloadModelIfNeeded(conditions)
                            .addOnSuccessListener {
                                val translator = Translation.getClient(options)
                                translator.translate(encoded.toString())
                                    .addOnSuccessListener { translatedCollection ->
                                        val resTranslate =
                                            translatedCollection.split(FIELDS_SPLITTER)
                                        if (resTranslate.isNotEmpty()) recipeItem =
                                            recipeItemGetted.copy(label = resTranslate[0].trim())
                                        if (resTranslate.size > 1) {
                                            val listTranslatedIngredients = resTranslate[1].split(
                                                VALUE_SPLITTER
                                            ).map { it.trim() }
                                            val tmpIngredients = mutableListOf<IngredientItem>()
                                            val ingredientsEnglish = recipeItemGetted.ingredients
                                            var counter = 0
                                            while (counter < ingredientsEnglish?.size ?: 0) {
                                                ingredientsEnglish?.elementAt(counter)
                                                    ?.let { ingredientItem ->
                                                        tmpIngredients.add(
                                                            ingredientItem.copy(
                                                                text = listTranslatedIngredients.elementAt(
                                                                    counter
                                                                )
                                                            )
                                                        )
                                                    }
                                                counter++
                                            }
                                            recipeItem =
                                                recipeItem?.copy(ingredients = tmpIngredients)
                                        }
                                        recipeItemLD.postValue(recipeItem)
                                        state.postValue(STATE_SUCCESS)
                                    }
                            }.addOnFailureListener { exception ->
                                state.postValue(STATE_ERROR)
                            }
                    }
                }
            }
        }

    }


}