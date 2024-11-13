package com.ham.ad_adapter_test

import android.app.Activity
import com.applovin.mediation.adapter.MaxAdapter
import com.applovin.mediation.adapter.parameters.MaxAdapterInitializationParameters
import com.applovin.mediation.adapters.MediationAdapterBase
import com.applovin.sdk.AppLovinSdk

class KotlinTest(p0: AppLovinSdk?) : MediationAdapterBase(p0) {
    override fun initialize(
        p0: MaxAdapterInitializationParameters?,
        p1: Activity?,
        p2: MaxAdapter.OnCompletionListener?
    ) {
    }

    override fun getSdkVersion(): String {
        return "1"
    }

    override fun getAdapterVersion(): String {
        return "1"
    }

    override fun onDestroy() {
    }


}