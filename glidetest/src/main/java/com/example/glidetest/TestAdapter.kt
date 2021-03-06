package com.example.glidetest

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import android.view.LayoutInflater


class TestAdapter(private var glide: RequestManager) :
    RecyclerView.Adapter<TestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val context = parent.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return TestViewHolder(view, glide)
    }

    override fun getItemCount(): Int = 5

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.onBind(TestConstants.URL_LIST[position])
    }
}