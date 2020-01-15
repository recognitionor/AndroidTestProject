package com.example.recyclerviewtest.grid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewtest.R

class GridTestFragment : Fragment() {

    private lateinit var mRecyclerView: RecyclerView

    private lateinit var mAdapter: GridTestAdapter

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
        mAdapter = GridTestAdapter()
        mRecyclerView.layoutManager = GridLayoutManager(context, 1)
        mRecyclerView.adapter = mAdapter

    }
}