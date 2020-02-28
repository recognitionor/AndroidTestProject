package com.example.recyclerviewtest.staggerd

import android.content.Context
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.view.View

class ListPaddingItemDecoration(var context: Context,
                                var topPadding: Int,
                                var bottomPadding: Int,
                                var itemTopPadding: Int = 0,
                                var hasHeader: Boolean = true) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition == RecyclerView.NO_POSITION) {
            return
        }

        var paddingPosition = 0
        if (hasHeader) {
            paddingPosition++
        }

        if (itemPosition == paddingPosition) {
            outRect.top = topPadding
        }

        val adapter = parent.adapter
        if (adapter != null && itemPosition > paddingPosition && itemPosition < adapter.itemCount - 1) {
            outRect.top = itemTopPadding
        }

        if (adapter != null && itemPosition == adapter.itemCount - 1) {
            outRect.bottom = bottomPadding
        }
    }
}