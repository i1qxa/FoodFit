package aps.foodfit.jyrbf.ui.racion.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import aps.foodfit.jyrbf.R
import aps.foodfit.jyrbf.domain.Racion
import aps.foodfit.jyrbf.domain.firstCharToUpperCase

class RacionRVAdapter : ListAdapter<Racion, RacionViewHolder>(RacionDiffCallBack()) {

    var onRacionClickListener: ((String) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RacionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RacionViewHolder(
            layoutInflater.inflate(
                R.layout.racion_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RacionViewHolder, position: Int) {
        val item = getItem(position)
        val context = holder.itemView.context
        with(holder) {
            try {
                ivLogo.setImageBitmap(item.getSavedImg(context))
            }catch (_:Exception){}
            tvName.text = item.name.firstCharToUpperCase()
        }
        with(holder) {
            tvKcalValue.text = item.totalKcal.toString()
            tvProteinValue.text = item.totalProtein.toString()
            tvFatValue.text = item.totalFat.toString()
            tvCarbValue.text = item.totalCarb.toString()
            tvTotalTime.text = item.totalTime.toString()
            tvTotalWeight.text = item.totalWeight.toString()
        }
        holder.itemView.setOnClickListener {
            onRacionClickListener?.invoke(item.name)
        }
    }
}
