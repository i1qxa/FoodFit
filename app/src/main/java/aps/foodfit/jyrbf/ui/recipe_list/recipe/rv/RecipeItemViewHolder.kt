package aps.foodfit.jyrbf.ui.recipe_list.recipe.rv

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import aps.foodfit.jyrbf.R

class RecipeItemViewHolder(itemView:View):ViewHolder(itemView) {
    val ivLogo = itemView.findViewById<ImageView>(R.id.ivLogo)
    val tvText = itemView.findViewById<TextView>(R.id.tvIngredient)

}