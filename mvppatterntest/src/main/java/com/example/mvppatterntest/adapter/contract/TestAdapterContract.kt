package com.example.mvppatterntest.adapter.contract

interface TestAdapterContract {

    interface View {

        var onClick: (() -> Unit)?

    }

    interface Model {

    }
}