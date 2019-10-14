package com.example.mvppatterntest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvppatterntest.R
import com.example.mvppatterntest.adapter.TestAdapter
import com.example.mvppatterntest.presenter.TestPresenter
import org.jetbrains.anko.find

class TestFragment : Fragment() {

    private var mRecyclerView: RecyclerView? = null

    private var mPresenter: TestPresenter? = null

    private var mAdapter: TestAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = TestPresenter()
        mAdapter = context?.let { TestAdapter(it) }
        mPresenter?.adapterView = mAdapter
        mPresenter?.adapterModel = mAdapter
        mPresenter?.loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_content, container, false)
        mRecyclerView = view.find(R.id.recycler_view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView?.adapter = mAdapter
        mRecyclerView?.layoutManager = LinearLayoutManager(context)
    }
}