package com.example.recyclerviewtest.spannablegrid

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewtest.R

class SpanableViewHolder(ctx: Context, parent: ViewGroup, targetView: View) :
    RecyclerView.ViewHolder(targetView) {

    private val textView: TextView by lazy {
        itemView.findViewById(R.id.text_view) as TextView
    }

    fun setText(str: String) {
        textView.text = str
    }


}