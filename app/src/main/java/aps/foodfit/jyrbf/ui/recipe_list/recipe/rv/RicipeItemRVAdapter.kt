package aps.foodfit.jyrbf.ui.recipe_list.recipe.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import aps.foodfit.jyrbf.R
import aps.foodfit.jyrbf.data.remote.IngredientItem
import coil.load
import coil.transform.RoundedCornersTransformation

class RicipeItemRVAdapter : ListAdapter<IngredientItem, RecipeItemViewHolder>(RecipeItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RecipeItemViewHolder(
            layoutInflater.inflate(
                R.layout.ingredient_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecipeItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder) {
            ivLogo.load(item.image) {
                transformations(RoundedCornersTransformation(20.0f))
            }
            tvText.text = item.text
        }

    }

}
