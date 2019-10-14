package com.example.recyclerviewtest

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TestRecyclerAdapter(private val context: Context) : RecyclerView.Adapter<TestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        return TestViewHolder(context, parent)
    }

    override fun getItemCount(): Int {

        return 100
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.setText("$position")
    }
}