package aps.foodfit.jyrbf.ui.racion.racion_item.rv

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import aps.foodfit.jyrbf.R

class RacionItemViewHolder(itemView:View):ViewHolder(itemView) {
    val ivLogo = itemView.findViewById<ImageView>(R.id.ivRecipeLogo)
    val tvName = itemView.findViewById<TextView>(R.id.tvRecipeName)
    val tvKcal = itemView.findViewById<TextView>(R.id.tvKcal)
    val tvProtein = itemView.findViewById<TextView>(R.id.tvProtein)
    val tvFat = itemView.findViewById<TextView>(R.id.tvFat)
    val tvCarb = itemView.findViewById<TextView>(R.id.tvCarb)
    val proteinProgress = itemView.findViewById<ProgressBar>(R.id.chartProtein)
    val fatProgress = itemView.findViewById<ProgressBar>(R.id.chartFat)
    val carbProgress = itemView.findViewById<ProgressBar>(R.id.chartCarb)
}