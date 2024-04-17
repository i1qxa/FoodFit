package aps.foodfit.jyrbf.ui.racion.racion_item.rv

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import aps.foodfit.jyrbf.R
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartLayoutType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAChart

class RacionItemViewHolder(itemView:View):ViewHolder(itemView) {
    val ivLogo = itemView.findViewById<ImageView>(R.id.ivRacionLogo)
    val tvName = itemView.findViewById<TextView>(R.id.tvRecipeName)
    val tvKcalValue = itemView.findViewById<TextView>(R.id.tvKcalValue)
    val tvProteinValue = itemView.findViewById<TextView>(R.id.tvProteinValue)
    val tvFatValue = itemView.findViewById<TextView>(R.id.tvFatValue)
    val tvCarbValue = itemView.findViewById<TextView>(R.id.tvCarbValue)
    val tvTotalTime = itemView.findViewById<TextView>(R.id.tvTotalTime)
    val tvTotalWeight = itemView.findViewById<TextView>(R.id.tvTotalWeight)
    val chart = itemView.findViewById<AAChartView>(R.id.chart)
}