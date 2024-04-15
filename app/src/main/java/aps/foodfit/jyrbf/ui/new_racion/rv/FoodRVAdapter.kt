package aps.foodfit.jyrbf.ui.new_racion.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import aps.foodfit.jyrbf.R
import aps.foodfit.jyrbf.data.remote.RecipeItemShort
import aps.foodfit.jyrbf.domain.firstCharToUpperCase
import coil.load
import coil.transform.RoundedCornersTransformation

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
        with(holder) {
            ivLogo.load(item.imgSmall) {
                transformations(RoundedCornersTransformation(20.0f))
            }
            tvName.text = item.label?.firstCharToUpperCase()
        }
        with(holder){
            tvKcalValue.text = item.calories.toString()
            tvProteinValue.text = item.protein.toString()
            tvFatValue.text = item.fat.toString()
            tvCarbValue.text= item.carbs.toString()
        }
    }
}
