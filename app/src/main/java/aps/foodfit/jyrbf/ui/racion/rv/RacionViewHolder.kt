package aps.foodfit.jyrbf.ui.racion.rv

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import aps.foodfit.jyrbf.R

class RacionViewHolder(itemView:View):ViewHolder(itemView) {
    val ivLogo = itemView.findViewById<ImageView>(R.id.ivRacionLogo)
    val tvName = itemView.findViewById<TextView>(R.id.tvRacionName)
    val tvKcalValue = itemView.findViewById<TextView>(R.id.tvKcalValue)
    val tvProteinValue = itemView.findViewById<TextView>(R.id.tvProteinValue)
    val tvFatValue = itemView.findViewById<TextView>(R.id.tvFatValue)
    val tvCarbValue = itemView.findViewById<TextView>(R.id.tvCarbValue)
    val tvTotalTime = itemView.findViewById<TextView>(R.id.tvTotalTime)
    val tvTotalWeight = itemView.findViewById<TextView>(R.id.tvTotalWeight)
}