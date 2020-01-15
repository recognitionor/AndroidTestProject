package com.example.recyclerviewtest.spannablegrid

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewtest.R
import com.example.recyclerviewtest.SpannedGridLayoutManagerTest


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
            SpannedGridLayoutManagerTest(SpannedGridLayoutManagerTest.GridSpanLookup { position ->
                Log.d("jhlee", "getSpanInfo : $position")
                // Conditions for 2x2 items
                // Conditions for 2x2 items
                return@GridSpanLookup SpannedGridLayoutManagerTest.SpanInfo(1, 1)
                return@GridSpanLookup if (position % 6 === 0 || position % 6 === 4) {
                    SpannedGridLayoutManagerTest.SpanInfo(1, 1)
                } else {
                    SpannedGridLayoutManagerTest.SpanInfo(2, 2)
                }

            }, 3, 1f)

        mRecyclerView.adapter = mAdapter

    }
}