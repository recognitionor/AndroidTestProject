package com.example.recyclerviewtest

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TestTripleViewHolder(ctx: Context, parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(ctx).inflate(
        R.layout.recycler_item_3,
        parent,
        false
    )
) {

    private val textView: TextView by lazy {
        itemView.findViewById(R.id.text_view) as TextView
    }

    fun setText(str: String) {
        textView.text = str
    }


}