package com.example.recyclerviewtest.spannablegrid

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewtest.R
import com.example.recyclerviewtest.SpannedGridLayoutManager


class SpanableTestFragment : Fragment() {

    private lateinit var mRecyclerView: RecyclerView

    private lateinit var mAdapter: SpanableTestAdapter

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
        mAdapter = SpanableTestAdapter()
        mRecyclerView.layoutManager =
            SpannedGridLayoutManager(SpannedGridLayoutManager.GridSpanLookup { position ->
                val tempSize: Int = when (position % 16) {
                    0, 1, 8, 9 -> 3
                    2, 11 -> 4
                    3, 4, 5, 6, 7, 10, 12, 13, 14, 15 -> 2

                    else -> 2
                }
                // Conditions for 2x2 items
                // Conditions for 2x2 items
                return@GridSpanLookup SpannedGridLayoutManager.SpanInfo(tempSize, tempSize)

            }, 6, 1f)

        mRecyclerView.adapter = mAdapter

    }
}