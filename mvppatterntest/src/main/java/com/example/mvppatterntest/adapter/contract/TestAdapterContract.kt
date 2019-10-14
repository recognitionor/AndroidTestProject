package com.example.mvppatterntest.adapter.contract

interface TestAdapterContract {

    interface View {

        fun notifyAdapter()

    }

    interface Model {

        fun addItem(items: ArrayList<String>)

        fun clearItems()

        fun getItems(positions: Int) :String

    }
}