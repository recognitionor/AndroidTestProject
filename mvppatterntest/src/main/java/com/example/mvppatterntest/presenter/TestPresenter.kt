package com.example.mvppatterntest.presenter

import com.example.mvppatterntest.adapter.contract.TestAdapterContract
import com.example.mvppatterntest.contract.TestContract

class TestPresenter : TestContract.Presenter {

    override var view: TestContract.View? = null

    override var adapterView: TestAdapterContract.View? = null

    override var adapterModel: TestAdapterContract.Model? = null

    override fun loadData() {
        val list = ArrayList<String>().apply {
            this.add("1")
            this.add("2")
            this.add("3")
        }

        adapterModel?.addItem(list)
        adapterView?.notifyAdapter()


    }
}