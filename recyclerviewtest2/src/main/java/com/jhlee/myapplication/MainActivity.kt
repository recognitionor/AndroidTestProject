package com.jhlee.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layout = findViewById<FrameLayout>(R.id.frame_test)
        layout.addView(AppCompatTextView(this).apply {
            text = "~~"
        })
        findViewById<AppCompatTextView>(R.id.test).text =
            findViewById<AppCompatTextView>(R.id.test).id.toString()
        val rv = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = TestAdapter()
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
        adapter.notifyDataSetChanged()

    }
}