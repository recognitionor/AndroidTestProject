package com.example.instagrammoretextview.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagrammoretextview.MyRecyclerViewAdapter
import com.example.instagrammoretextview.R
import com.example.instagrammoretextview.ReadMoreTextView

class ReadMoreTestFragment : Fragment() {
    lateinit var textView: ReadMoreTextView
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("jhlee", "onCreateView")
        return inflater.inflate(R.layout.content_main, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //        editText = findViewById(R.id.editText)
        Log.d("jhlee", "onViewCreated")

        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = MyRecyclerViewAdapter()
    }
}
