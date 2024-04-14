package aps.foodfit.jyrbf.ui.new_racion.rv

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import aps.foodfit.jyrbf.R

class FoodViewHolder(itemView:View):ViewHolder(itemView) {
    val ivLogo = itemView.findViewById<ImageView>(R.id.ivRecipeLogo)
    val tvName = itemView.findViewById<TextView>(R.id.tvRecipeName)
    val tvKcalValue = itemView.findViewById<TextView>(R.id.tvKcalValue)
    val tvProteinValue = itemView.findViewById<TextView>(R.id.tvProteinValue)
    val tvFatValue = itemView.findViewById<TextView>(R.id.tvFatValue)
    val tvCarbValue = itemView.findViewById<TextView>(R.id.tvCarbValue)
}