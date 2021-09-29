package com.example.android.trackmysleepquality.sleeptracker

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight

/*
No entanto, acontece que, com esse novo mecanismo de tratamento de cliques, agora é possível que
 os adaptadores de ligação sejam chamados com um valor null para item.
  Em particular, quando o aplicativo é iniciado, o LiveData inicia como null,
  portanto, você precisa adicionar verificações de nulos a cada um dos adaptadores.
 */
    @BindingAdapter("sleepDurationFormatted")
    fun TextView.setSleepDurationFormatted(item: SleepNight?){
        item?.let {
            text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, context.resources)
        }
    }

    @BindingAdapter("sleepQualityString")
    fun TextView.setSleepQualityString(item: SleepNight?) {
        item?.let {
            text = convertNumericQualityToString(item.sleepQuality, context.resources)
        }
    }

    @BindingAdapter("sleepImage")
    fun ImageView.setSleepImage(item: SleepNight?) {
        item?.let {
            setImageResource(when (item.sleepQuality) {
                0 -> R.drawable.ic_sleep_0
                1 -> R.drawable.ic_sleep_1
                2 -> R.drawable.ic_sleep_2
                3 -> R.drawable.ic_sleep_3
                4 -> R.drawable.ic_sleep_4
                5 -> R.drawable.ic_sleep_5
                else -> R.drawable.ic_sleep_active
            })
        }
    }
