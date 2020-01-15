package com.example.recyclerviewtest.spannablegrid

import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewtest.R
import com.example.recyclerviewtest.SpannedGridLayoutManager

class SpanableTestAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_FULL = 0
    private val TYPE_HALF = 1
    private val TYPE_QUARTER = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = View.inflate(parent.context, R.layout.recycler_item, null)
        itemView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                val lp: ViewGroup.LayoutParams = itemView.layoutParams
                if (lp is SpannedGridLayoutManager.LayoutParams) {
                    lp.width = SpannedGridLayoutManager.LayoutParams.MATCH_PARENT
                    lp.height = SpannedGridLayoutManager.LayoutParams.MATCH_PARENT
                    itemView.layoutParams = lp
                }
                itemView.viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }
        })


        return SpanableViewHolder(
            parent.context,
            parent,
            itemView
        )
    }

    override fun getItemCount(): Int {
        return 30
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as SpanableViewHolder
        holder.setText("test : $position")
    }
}