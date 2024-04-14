package aps.foodfit.jyrbf.ui.new_racion.recipe_search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aps.foodfit.jyrbf.data.remote.APP_ID
import aps.foodfit.jyrbf.data.remote.APP_KEY
import aps.foodfit.jyrbf.data.remote.ITEM_SPLITTER
import aps.foodfit.jyrbf.data.remote.RecipeItemShort
import aps.foodfit.jyrbf.data.remote.RecipeTranslatedSubData
import aps.foodfit.jyrbf.data.remote.RetrofitService
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.launch

const val STATE_LOADING = 1
const val STATE_ERROR = 2
const val STATE_SUCCESS = 3
class RecipeSearchViewModel : ViewModel() {

    private val retrofit = RetrofitService.getInstance()
    val recipeList = MutableLiveData<List<RecipeItemShort?>?>()
    val state = MutableLiveData<Int>(0)

    private fun getRecipeList(query: String) {

        viewModelScope.launch {
            recipeList.postValue(null)
            val recipes = mutableListOf<RecipeItemShort?>()
            val recipesTranslated = mutableListOf<RecipeItemShort>()
            val response = retrofit.getRecipeResponse(
                "public", query, APP_ID, APP_KEY
            )
            if (response.isSuccessful) {
                if (response.body()?.count == 0) {
                    state.postValue(STATE_ERROR)
                } else {
                    state.postValue(STATE_SUCCESS)
                    var startId = 0
                    response.body()?.hits?.map {
                        recipes.add(it.recipe?.getRecipeShort(startId))
                        startId++
                    }
                    val encoded = StringBuilder()
                    recipes.forEach {
                        if (it != null) {
                            encoded.append(it.getDataForTranslate())
                            encoded.append(ITEM_SPLITTER)
                        }
                    }
                    val options =
                        TranslatorOptions.Builder().setSourceLanguage(TranslateLanguage.ENGLISH)
                            .setTargetLanguage(TranslateLanguage.RUSSIAN).build()
                    val englishGermanTranslator = Translation.getClient(options)
                    val conditions = DownloadConditions.Builder().build()
                    englishGermanTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener {
                        val translator = Translation.getClient(options)
                        translator.translate(encoded.toString())
                            .addOnSuccessListener { translatedCollection ->
                                translatedCollection.split(ITEM_SPLITTER).forEach {
                                    if (it.isNotEmpty()) {
                                        val tmpTranslatedItem =
                                            RecipeTranslatedSubData.decodeFromString(it)
                                        val recipeEnglishList =
                                            recipes.filter { it?.id == tmpTranslatedItem?.id }
                                        if (recipeEnglishList.isNotEmpty()) {
                                            val recipeEnglish = recipeEnglishList[0]
                                            if (recipeEnglish != null && tmpTranslatedItem != null) {
                                                recipesTranslated.add(
                                                    recipeEnglish.copy(
                                                        label = tmpTranslatedItem.name,
                                                        mealType = tmpTranslatedItem.diets,
                                                        ingredients = tmpTranslatedItem.ingredients
                                                    )
                                                )
                                            }
                                        }

                                    }

                                }
                                recipeList.postValue(recipesTranslated)
                            }
                    }.addOnFailureListener { exception ->
                        state.postValue(STATE_ERROR)
                    }
                }
            }
        }

    }

    fun translateQuery(queryRussian: String) {
        state.value = STATE_LOADING
        val options =
            TranslatorOptions.Builder().setSourceLanguage(TranslateLanguage.RUSSIAN)
                .setTargetLanguage(TranslateLanguage.ENGLISH).build()
        val russianEnglishTranslator = Translation.getClient(options)
        var conditions = DownloadConditions.Builder().build()
        russianEnglishTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener {
            val translator = Translation.getClient(options)
            translator.translate(queryRussian)
                .addOnSuccessListener {
                    getRecipeList(it)
                }
        }
            .addOnFailureListener { exception ->
                state.postValue(STATE_ERROR)
            }
    }



}