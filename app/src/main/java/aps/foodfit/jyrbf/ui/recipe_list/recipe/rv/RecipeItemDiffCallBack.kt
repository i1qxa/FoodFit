package aps.foodfit.jyrbf.ui.recipe_list.recipe.rv

import androidx.recyclerview.widget.DiffUtil
import aps.foodfit.jyrbf.data.remote.IngredientItem

class RecipeItemDiffCallBack:DiffUtil.ItemCallback<IngredientItem>() {

    override fun areItemsTheSame(oldItem: IngredientItem, newItem: IngredientItem): Boolean {
        return oldItem.foodId == newItem.foodId
    }

    override fun areContentsTheSame(oldItem: IngredientItem, newItem: IngredientItem): Boolean {
        return oldItem == newItem
    }
}