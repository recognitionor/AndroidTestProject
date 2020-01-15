package com.example.recyclerviewtest.staggerd

import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.recyclerviewtest.R


class StaggeredTestAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_FULL = 0
    private val TYPE_HALF = 1
    private val TYPE_QUARTER = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = View.inflate(parent.context, R.layout.recycler_item, null)
        itemView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                val lp: ViewGroup.LayoutParams = itemView.layoutParams
                if (lp is StaggeredGridLayoutManager.LayoutParams) {
                    when (viewType) {
                        TYPE_FULL -> lp.isFullSpan = true
                        TYPE_HALF -> {
                            lp.isFullSpan = false
                            lp.width = itemView.width / 2
                        }
                        TYPE_QUARTER -> {
                            lp.isFullSpan = false
                            lp.width = itemView.width / 2
                            lp.height = itemView.height / 2
                        }
                    }
                    itemView.layoutParams = lp
                    val lm =
                        (parent as RecyclerView).layoutManager as StaggeredGridLayoutManager?
                    lm!!.invalidateSpanAssignments()
                }
                itemView.viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }

        })
        return StaggeredViewHolder(parent.context, parent, itemView)
    }

    override fun getItemCount(): Int {
        return 100
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as StaggeredViewHolder
        holder.setText("test : $position")
    }

    override fun getItemViewType(position: Int): Int {
        when (position % 8) {
            0, 5 -> return TYPE_HALF
            1, 2, 4, 6 -> return TYPE_QUARTER
        }
        return TYPE_FULL
    }
}