package com.example.mvppatterntest.adapter

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvppatterntest.adapter.contract.TestAdapterContract
import com.example.mvppatterntest.viewholder.TestViewHolder

class TestAdapter(var context: Context) : RecyclerView.Adapter<TestViewHolder>(),
    TestAdapterContract.Model, TestAdapterContract.View {

    private var mDataList = ArrayList<String>()


    override fun notifyAdapter() {
        notifyDataSetChanged()
    }

    override fun addItem(items: java.util.ArrayList<String>) {
        mDataList = items
    }

    override fun clearItems() {
        mDataList.clear()
    }

    override fun getItems(positions: Int): String {
        return mDataList[positions]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        return TestViewHolder(context, parent)
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.onBind(position)
    }
}