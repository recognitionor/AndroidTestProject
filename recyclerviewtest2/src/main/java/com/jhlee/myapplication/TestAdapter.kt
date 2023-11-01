package com.jhlee.myapplication

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView

class TestAdapter : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {

    class TestViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(position: Int) {
            Log.d("jhlee", "bind")
            view.findViewById<AppCompatTextView>(R.id.test_tv).text = position.toString()
            view.findViewById<AppCompatTextView>(R.id.test_tv2).text =
                view.findViewById<AppCompatTextView>(R.id.test_tv2).id.toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val context = parent.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.rv_item, parent, false)
        return TestViewHolder(view)
    }

    override fun getItemCount(): Int = 1000

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.bind(position)
    }
}