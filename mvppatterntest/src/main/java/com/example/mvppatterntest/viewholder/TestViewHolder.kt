package com.example.mvppatterntest.viewholder

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvppatterntest.R

class TestViewHolder(context: Context, parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.view_holder,
            parent,
            false
        )
    ) {

    fun onBind(position: Int) {
        itemView.findViewById<TextView>(R.id.text).text = "$position"
    }
}