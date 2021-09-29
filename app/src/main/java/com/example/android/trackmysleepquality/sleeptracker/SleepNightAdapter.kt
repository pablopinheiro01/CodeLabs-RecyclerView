package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException

//constantes de visualizaçao
private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class SleepNightAdapter( val clickListener: SleepNightListener)
    : ListAdapter<DataItem, RecyclerView.ViewHolder>(SleepNightDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            else -> throw ClassCastException("Nao foi possivel encontrar o tipo ${viewType}")
        }
    }

    fun addHeaderAndSubmitList(list: List<SleepNight>?){
        adapterScope.launch {
            val items = when (list){
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.SleepNightItem(it) }
            }
            withContext(Dispatchers.Main){
                submitList(items)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder -> {
                val nightItem = getItem(position) as DataItem.SleepNightItem
                holder.bind(nightItem.sleepNight, clickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.SleepNightItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    //esta classe vai fazer o inflate do header
    class TextViewHolder(view: View): RecyclerView.ViewHolder(view){
        companion object{
            fun from(parent: ViewGroup): TextViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header, parent, false)
                return TextViewHolder(view)
            }
        }
    }

    class ViewHolder private constructor(val binding:ListItemSleepNightBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(
            item: SleepNight,
            clickListener: SleepNightListener
        ) {
            binding.sleep = item
            binding.clickListener = clickListener
            //agiliza a execução da vinculação dos dados
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class SleepNightDiffCallback: DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

class SleepNightListener(val clickListener: (sleepId:Long) -> Unit){
    fun onClick(night:SleepNight){
        return clickListener(night.nightId)
    }
}

//sealed define um tipo de classe fechada close type, todas as subclasses de DataItem devem ser definidas no mesmo arquivo que foi enviado
//ninguem podera realizar um acesso externo a essa classe
sealed class DataItem {

    abstract val id: Long

    data class SleepNightItem(val sleepNight: SleepNight) : DataItem() {

        override val id = sleepNight.nightId
    }

    //o Header não vai possuir dados reais e vai existir apenas uma instancia no projeto, por este motivo usamos object
    object Header : DataItem() {
        //defino a variavel com o menor numero possivel existente em um Long, assim nunca teremos conflito com um id valido
        override val id: Long = Long.MIN_VALUE
    }

}