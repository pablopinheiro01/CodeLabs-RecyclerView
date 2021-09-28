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
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_sleep_night, parent, false)
        Log.i("SleepNightAdapter","Entrando no onCreateViewHolder")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources
        holder.sleepLenght.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
        holder.quality.text = convertNumericQualityToString(item.sleepQuality, res)
        holder.qualityImage.setImageResource(when (item.sleepQuality) {
            0 -> R.drawable.ic_sleep_0
            1 -> R.drawable.ic_sleep_1
            2 -> R.drawable.ic_sleep_2
            3 -> R.drawable.ic_sleep_3
            4 -> R.drawable.ic_sleep_4
            5 -> R.drawable.ic_sleep_5
            else -> R.drawable.ic_sleep_active
        })

        Log.i("SleepNightAdapter","Entrando no onBindViewHolder")
        Log.i("SleepNightAdapter","posicao da lista $position")
        Log.i("SleepNightAdapter","item da lista ${item.sleepQuality}")

    }

    override fun getItemCount(): Int {
        Log.i("SleepNightAdapter","Entrando no getItemCount")
        Log.i("SleepNightAdapter","quantidade ${data.size}")
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sleepLenght = itemView.findViewById<TextView>(R.id.sleep_length)
        val quality = itemView.findViewById<TextView>(R.id.quality_string)
        val qualityImage = itemView.findViewById<ImageView>(R.id.quality_image)

    }
}