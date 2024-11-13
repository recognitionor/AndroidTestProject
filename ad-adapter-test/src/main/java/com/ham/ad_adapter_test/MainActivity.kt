package com.ham.ad_adapter_test

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import com.applovin.mediation.ads.MaxAdView
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkConfiguration
import com.applovin.sdk.AppLovinSdkInitializationConfiguration
import com.applovin.sdk.AppLovinSdkSettings
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.interstitial.InterstitialAd


class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //뷰 Layout 을 가져와 엑티비티의 기본 화면으로 설정
        val view =
            findViewById<FrameLayout>(R.id.ad_view_container) // 기본 화면에 구현 되어 있는 광고 컨테이너를 객체를 가져옴
        val config = AppLovinSdkInitializationConfiguration.builder("«SDK_KEY»", this).build()
        AppLovinSdk.getInstance(this).initialize(config) {
            val test: AppLovinSdkConfiguration = it
            // 이니셜라이즈 결과에 따른 처리
        }

        val adView = MaxAdView("«ad-unit-ID»", this) // AdView 작성 즉 광고영역 생성 Param 으로 AD Unit ID 전달
        view.addView(adView) // 위에서 만든 Adview 를 광고 컨테이너에 Add 시킴 이것은 광고로드가 완료 되고 해도됨
        // Load the ad
        adView.loadAd()
    }


//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            val interstitialAd = MaxInterstitialAd("«ad-unit-ID»", applicationContext)
//
//            MobileAds.setRequestConfiguration(RequestConfiguration())
//            setContentView(R.layout.activity_main) //뷰 Layout 을 가져와 엑티비티의 기본 화면으로 설정
//            val view =
//                findViewById<FrameLayout>(R.id.ad_view_container) // 기본 화면에 구현 되어 있는 광고 컨테이너를 객체를 가져옴
//
//            MobileAds.initialize(this) // Init 호출 이고 콜백을 받을수 있으나 생략
//            val adView = AdView(this) // AdView 작성 즉 광고영역 생성
//            view.addView(adView)  // 위에서 만든 Adview 를 광고 컨테이너에 Add 시킴 이것은 광고로드가 완료 되고 해도됨
//            val adRequest = AdRequest.Builder().build() // 광고 로드를 하는 Request 생성
//            adView.adUnitId = "ca-app-pub-4279642319532137/4782382021" // AdUnit ID 지정
//            adView.adListener = object : AdListener() {
//                override fun onAdClicked() {
//                    Log.d("jhlee", "onAdClicked")
//                    // Code to be executed when the user clicks on an ad.
//                }
//
//                override fun onAdClosed() {
//                    Log.d("jhlee", "onAdClosed")
//                    // Code to be executed when the user is about to return
//                    // to the app after tapping on an ad.f
//                }
//
//                override fun onAdFailedToLoad(adError: LoadAdError) {
//                    Log.d("jhlee", "onAdFailedToLoad : " + adError.message)
//                    Log.d(
//                        "jhlee",
//                        "onAdFailedToLoad : ${adView.responseInfo?.mediationAdapterClassName}"
//                    )
//                    // Code to be executed when an ad request fails.
//                }
//
//                override fun onAdImpression() {
//                    // Code to be executed when an impression is recorded
//                    // for an ad.
//                    Log.d("jhlee", "onAdImpression : ")
//                }
//
//                override fun onAdLoaded() {
//
//                    Log.d("jhlee", "onAdLoaded : ")
//                    // Code to be executed when an ad finishes loading.
//                    Log.d("jhlee", "onAdLoaded : ${adView.responseInfo?.mediationAdapterClassName}")
//
//                }
//
//                override fun onAdOpened() {
//                    Log.d("jhlee", "onAdOpened : ")
//                    // Code to be executed when an ad opens an overlay that
//                    // covers the screen.
//                }
//            } // 콜백 리스너 set 구현은 생략 onAdLoaded, onAdFailedToLoad, onAdOpened, onAdClosed, 등 있음
//            adView.loadAd(adRequest) // 광고 로드
//        }
}