package com.udacity.asteroidradar.RV

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import kotlinx.android.synthetic.main.rv_card.view.*

class RV_Adapter(var arrayList: ArrayList<Asteroid>) :
    RecyclerView.Adapter<RV_Adapter.RV_ViewHolder>() {

    inner class RV_ViewHolder(item: View) : RecyclerView.ViewHolder(item) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RV_ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_card, parent, false)
        return RV_ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RV_ViewHolder, position: Int) {
        holder.itemView.apply {
           name.text=arrayList[position].codename
           date.text=arrayList[position].closeApproachDate
            if (arrayList[position].isPotentiallyHazardous)
            {
                //Todo:Set Image hazard
            }else
            {
              //todo: not hazardous
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}