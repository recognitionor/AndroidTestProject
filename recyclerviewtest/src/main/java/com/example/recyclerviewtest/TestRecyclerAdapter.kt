package com.example.recyclerviewtest

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TestRecyclerAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("jhlee", "onCreateViewHolder viewType $viewType,  ${viewType % 3}}")
        return when (viewType % 3) {
            0 -> {
                Log.d("jhlee", "TestSingleViewHolder")
                return TestSingleViewHolder(context, parent)
            }
            1 -> {
                Log.d("jhlee", "TestDoubleViewHolder")
                return TestDoubleViewHolder(context, parent)
            }
            2 -> {
                Log.d("jhlee", "TestTripleViewHolder")
                return TestTripleViewHolder(context, parent)
            }
            else -> TestSingleViewHolder(context, parent)
        }
    }

    override fun getItemCount(): Int {
        return 100
    }

    override fun getItemViewType(position: Int): Int {
        Log.d("jhlee", "getItemViewType ${position % 3}")
        return position % 3
    }
}