package com.example.mvppatterntest.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvppatterntest.R
import com.example.mvppatterntest.adapter.TestAdapter
import com.example.mvppatterntest.presenter.TestPresenter
import com.example.mvppatterntest.retrofit.RetrofitAPI
import org.jetbrains.anko.find
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TestFragment : Fragment() {

    private var mRecyclerView: RecyclerView? = null

    private var mPresenter: TestPresenter? = null

    private var mAdapter: TestAdapter? = null

    private var mRetrofit: Retrofit? = null

    private var mRetrofitAPI: RetrofitAPI? = null

    private var mRetrofitCallback: Callback<String> = object : Callback<String> {
        override fun onFailure(call: Call<String>?, t: Throwable?) {
            Log.d("jhlee", "onFailure $t")
        }

        override fun onResponse(call: Call<String>?, response: Response<String>?) {
            Log.d("jhlee", "response : $response")

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRetrofitInit()

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
        view.findViewById<Button>(R.id.btn).setOnClickListener {
            val test = mRetrofitAPI?.test()
            test?.enqueue(mRetrofitCallback)
        }
        return view
    }

    private fun setRetrofitInit() {
        mRetrofit = Retrofit.Builder()
            .baseUrl("https://4ca5e5e2.ngrok.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mRetrofitAPI = mRetrofit?.create(RetrofitAPI::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView?.adapter = mAdapter
        mRecyclerView?.layoutManager = LinearLayoutManager(context)
    }


}