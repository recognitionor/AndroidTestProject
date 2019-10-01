package com.example.mvppatterntest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.mvppatterntest.R
import com.example.mvppatterntest.adapter.TestAdapter
import org.jetbrains.anko.find

class TestFragment : Fragment() {

    private var recyclerView: RecyclerView? = null

    private lateinit var imageAdapter: TestAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_content, container, false)
        recyclerView = view.find(R.id.recycler_view)
        return view
    }
}