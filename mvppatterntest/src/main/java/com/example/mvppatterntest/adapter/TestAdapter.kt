package com.example.mvppatterntest.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvppatterntest.adapter.contract.TestAdapterContract

class TestAdapter(val context: Context) : RecyclerView.Adapter<TestViewHolder>(),
    TestAdapterContract.View, TestAdapterContract.Model {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override var onClick: (() -> Unit)?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
}
