package com.example.mvppatterntest.contract

import com.example.mvppatterntest.adapter.contract.TestAdapterContract

interface TestContract {

    interface View {
        fun onClick(callBack: (() -> Unit?))
    }

    interface Presenter {
        var view: View?

        var adapterView: TestAdapterContract.View?
        var adapterModel: TestAdapterContract.Model?

        fun loadData()

    }

}