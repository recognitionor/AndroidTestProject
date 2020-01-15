package com.example.recyclerviewtest.spannablegrid

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewtest.R

class SpanableTestAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SpanableViewHolder(
            parent.context,
            parent,
            View.inflate(parent.context, R.layout.recycler_item, null)
        )
    }

    override fun getItemCount(): Int {
        return 100
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as SpanableViewHolder
        holder.setText("test : $position")
    }
}