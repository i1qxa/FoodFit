package aps.foodfit.jyrbf.ui.racion.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import aps.foodfit.jyrbf.R
import aps.foodfit.jyrbf.domain.Racion
import aps.foodfit.jyrbf.domain.firstCharToUpperCase
import aps.foodfit.jyrbf.domain.getBitmapByName

class RacionRVAdapter : ListAdapter<Racion, RacionViewHolder>(RacionDiffCallBack()) {

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
        with(holder) {
            try {
                ivLogo.setImageBitmap(holder.itemView.context.getBitmapByName(item.imgLocal))
            }catch (_:Exception){

            }
            tvName.text = item.name.firstCharToUpperCase()
        }
        with(holder){
            tvKcalValue.text = item.totalKcal.toString()
            tvProteinValue.text = item.totalProtein.toString()
            tvFatValue.text = item.totalFat.toString()
            tvCarbValue.text= item.totalCarb.toString()
            tvTotalTime.text=item.totalTime.toString()
            tvTotalWeight.text=item.totalWeight.toString()
        }
    }
}
