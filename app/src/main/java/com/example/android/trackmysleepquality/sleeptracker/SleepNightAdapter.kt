package com.example.android.trackmysleepquality.sleeptracker

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.TextItemViewHolder
import com.example.android.trackmysleepquality.database.SleepNight

class SleepNightAdapter: RecyclerView.Adapter<TextItemViewHolder>() {

    var data = listOf<SleepNight>()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.text_item_view, parent, false) as TextView
        Log.i("SleepNightAdapter","Entrando no onCreateViewHolder")
        return TextItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        Log.i("SleepNightAdapter","Entrando no onBindViewHolder")
        Log.i("SleepNightAdapter","posicao da lista $position")
        Log.i("SleepNightAdapter","item da lista ${item.sleepQuality}")

        if(item.sleepQuality <= 1){
            holder.textView.setTextColor(Color.RED)
        }

        holder.textView.text = item.sleepQuality.toString()
    }

    override fun getItemCount(): Int {
        Log.i("SleepNightAdapter","Entrando no getItemCount")
        Log.i("SleepNightAdapter","quantidade ${data.size}")
        return data.size
    }
}