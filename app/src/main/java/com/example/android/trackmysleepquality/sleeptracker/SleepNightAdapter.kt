package com.example.android.trackmysleepquality.sleeptracker

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.TextItemViewHolder
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight

class SleepNightAdapter: RecyclerView.Adapter<SleepNightAdapter.ViewHolder>() {

    var data = listOf<SleepNight>()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(holder, item)
    }

    override fun getItemCount(): Int {
        Log.i("SleepNightAdapter","Entrando no getItemCount")
        Log.i("SleepNightAdapter","quantidade ${data.size}")
        return data.size
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sleepLenght = itemView.findViewById<TextView>(R.id.sleep_length)
        val quality = itemView.findViewById<TextView>(R.id.quality_string)
        val qualityImage = itemView.findViewById<ImageView>(R.id.quality_image)

        fun bind(
            holder: ViewHolder,
            item: SleepNight
        ) {
            val res = holder.itemView.context.resources
            holder.sleepLenght.text =
                convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
            holder.quality.text = convertNumericQualityToString(item.sleepQuality, res)
            holder.qualityImage.setImageResource(
                when (item.sleepQuality) {
                    0 -> R.drawable.ic_sleep_0
                    1 -> R.drawable.ic_sleep_1
                    2 -> R.drawable.ic_sleep_2
                    3 -> R.drawable.ic_sleep_3
                    4 -> R.drawable.ic_sleep_4
                    5 -> R.drawable.ic_sleep_5
                    else -> R.drawable.ic_sleep_active
                }
            )
        }
        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.list_item_sleep_night, parent, false)
                return ViewHolder(view)
            }
        }
    }
}