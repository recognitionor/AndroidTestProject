package com.example.recyclerviewtest.staggerd

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.recyclerviewtest.R
import org.jetbrains.anko.dip
import org.jetbrains.anko.support.v4.dip

class StaggeredTestFragment : Fragment() {
    private lateinit var mRecyclerView: RecyclerView

    private lateinit var mAdapter: StaggeredTestAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_layout, container, false).apply {
            mRecyclerView = findViewById(R.id.recycler_view)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        mAdapter = activity?.let { StaggeredTestAdapter(it) }!!
        mRecyclerView.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        mRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {

            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                context?.resources?.getDimensionPixelSize(R.dimen.tag_main_image_margin)?.let {
                    outRect.bottom = it
                }

            }
        })
        mRecyclerView.adapter = mAdapter

    }
}