package aps.foodfit.jyrbf.ui.racion.racion_item.rv

import androidx.recyclerview.widget.DiffUtil
import aps.foodfit.jyrbf.data.local.recipe.RecipeDB
import aps.foodfit.jyrbf.domain.Racion

class RacionItemDiffCallBack:DiffUtil.ItemCallback<RecipeDB>() {

    override fun areItemsTheSame(oldItem: RecipeDB, newItem: RecipeDB): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: RecipeDB, newItem: RecipeDB): Boolean {
        return oldItem == newItem
    }
}