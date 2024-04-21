package aps.foodfit.jyrbf.ui.racion.racion_item.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import aps.foodfit.jyrbf.R
import aps.foodfit.jyrbf.data.local.recipe.RecipeDB
import aps.foodfit.jyrbf.domain.firstCharToUpperCase

class RacionItemRVAdapter : ListAdapter<RecipeDB, RacionItemViewHolder>(RacionItemDiffCallBack()) {

    var onItemClickListener: ((String) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RacionItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RacionItemViewHolder(
            layoutInflater.inflate(
                R.layout.recipe_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RacionItemViewHolder, position: Int) {
        val item = getItem(position)
        val context = holder.itemView.context
        with(holder) {
            try {
                ivLogo.setImageBitmap(item.getSavedImg(context))
            }catch (_:Exception){
            }
            tvName.text = item.name.firstCharToUpperCase()
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(item.uri)
        }
        with(holder){
            tvKcal.text = context.getString(R.string.kcal_and_value, item.kcalTotal.toInt())
            tvProtein.text = context.getString(R.string.protein_and_value, item.proteinTotal.toInt())
            tvFat.text = context.getString(R.string.fat_and_value, item.fatTotal.toInt())
            tvCarb.text = context.getString(R.string.carb_and_value, item.carbTotal.toInt())
            proteinProgress.setProgress(item.proteinPercent, true)
            fatProgress.setProgress(item.fatPercent, true)
            carbProgress.setProgress(item.carbPercent, true)
        }
    }

}
