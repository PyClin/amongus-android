package com.minosai.typingdnahack.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<DATA>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var currentData: DATA? = null

    abstract fun bind(data: DATA)

}