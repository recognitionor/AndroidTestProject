package com.example.recyclerviewtest.staggerd

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewtest.R

abstract class AbsImageViewHolder(
    ctx: Context,
    parent: ViewGroup,
    targetView: View
) :
    RecyclerView.ViewHolder(targetView) {

    private var mLayout: LinearLayout = targetView.findViewById(R.id.layout)

    open fun setText(str: String) {
        with(mLayout) {
            createLayout(this)
        }
    }

    fun onBind() {
        with(mLayout) {
            createLayout(this)
        }
    }

    abstract fun createLayout(linearLayout: LinearLayout)

}