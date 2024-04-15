package aps.foodfit.jyrbf.ui.racion.rv

import androidx.recyclerview.widget.DiffUtil
import aps.foodfit.jyrbf.data.remote.RecipeItemShort
import aps.foodfit.jyrbf.domain.Racion

class RacionDiffCallBack:DiffUtil.ItemCallback<Racion>() {

    override fun areItemsTheSame(oldItem: Racion, newItem: Racion): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Racion, newItem: Racion): Boolean {
        return oldItem == newItem
    }
}