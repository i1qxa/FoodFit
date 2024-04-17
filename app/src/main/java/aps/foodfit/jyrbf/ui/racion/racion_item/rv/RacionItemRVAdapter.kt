package aps.foodfit.jyrbf.ui.racion.racion_item.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import aps.foodfit.jyrbf.R
import aps.foodfit.jyrbf.data.local.recipe.RecipeDB
import aps.foodfit.jyrbf.domain.Racion
import aps.foodfit.jyrbf.domain.firstCharToUpperCase
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AADataLabels

class RacionItemRVAdapter : ListAdapter<RecipeDB, RacionItemViewHolder>(RacionItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RacionItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RacionItemViewHolder(
            layoutInflater.inflate(
                R.layout.recipe_item_chart,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RacionItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder) {
            try {
                ivLogo.setImageBitmap(item.getSavedImg(holder.itemView.context))
            }catch (_:Exception){

            }
            tvName.text = item.name.firstCharToUpperCase()
        }
        with(holder){
            tvKcalValue.text = item.kcalTotal.toString()
            tvProteinValue.text = item.proteinTotal.toString()
            tvFatValue.text = item.fatTotal.toString()
            tvCarbValue.text= item.carbTotal.toString()
//            tvTotalTime.text=item.totalTimeInMinutes.toString()
//            tvTotalWeight.text=item.weightInGrams.toString()
        }
        drawChart(item,holder.chart)
    }

    private fun drawChart(recipe:RecipeDB, chart:AAChartView){
        val chartModel = AAChartModel()
        chartModel.apply {
            chartType(AAChartType.Pie)
            title("КБЖУ")
            backgroundColor("#FFFFFF")
            dataLabelsEnabled(true)
            series(
                arrayOf(
                    AASeriesElement()
                        .name("Белки")
                        .data(arrayOf(recipe.proteinTotal, recipe.fatTotal, recipe.carbTotal)),
//                    AASeriesElement()
//                        .name("Жиры")
//                        .data(arrayOf(recipe.fatTotal)),
//                    AASeriesElement()
//                        .name("Углеводы")
//                        .data(arrayOf(recipe.carbTotal)),
                )
            )
        }
        chart.aa_drawChartWithChartModel(chartModel)
    }

}
