package com.example.recyclerviewtest.grid

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewtest.R

class GridTestAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GridTestViewHolder(
            parent.context,
            parent,
            View.inflate(parent.context, R.layout.recycler_item, null)
        )
    }

    override fun getItemCount(): Int {
        return 100
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as GridTestViewHolder
        holder.setText("test : $position")
    }
}