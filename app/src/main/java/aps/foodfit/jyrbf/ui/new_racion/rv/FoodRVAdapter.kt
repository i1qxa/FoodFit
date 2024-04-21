package aps.foodfit.jyrbf.ui.new_racion.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import aps.foodfit.jyrbf.R
import aps.foodfit.jyrbf.data.remote.RecipeItemShort
import aps.foodfit.jyrbf.domain.firstCharToUpperCase

class FoodRVAdapter : ListAdapter<RecipeItemShort, FoodViewHolder>(FoodDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FoodViewHolder(
            layoutInflater.inflate(
                R.layout.recipe_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val item = getItem(position)
        val context = holder.itemView.context
        with(holder) {
            ivLogo.setImageBitmap(item.imgBitmap)
            tvName.text = item.label?.firstCharToUpperCase()
        }
        with(holder){
            tvKcal.text = context.getString(R.string.kcal_and_value, item.calories)
            tvProtein.text = context.getString(R.string.protein_and_value, item.protein)
            tvFat.text = context.getString(R.string.fat_and_value, item.fat)
            tvCarb.text = context.getString(R.string.carb_and_value, item.carbs)
            proteinProgress.setProgress(item.proteinPercent, true)
            fatProgress.setProgress(item.fatPercent, true)
            carbProgress.setProgress(item.carbPercent, true)
        }
    }
}
