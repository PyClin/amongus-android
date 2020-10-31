package com.minosai.typingdnahack.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<DATA, VH : BaseViewHolder<DATA>> : RecyclerView.Adapter<VH>() {

    var dataList: List<DATA> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return LayoutInflater.from(parent.context)
            .inflate(getLayoutRes(), parent, false)
            .let {
                getViewHolder(it)
            }
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(dataList[position])
        holder.currentData = dataList[position]
    }

    abstract fun getLayoutRes(): Int

    abstract fun getViewHolder(itemView: View): VH

}
