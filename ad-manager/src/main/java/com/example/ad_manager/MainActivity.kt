package com.example.ad_manager

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerAdView
import com.google.android.gms.ads.initialization.InitializationStatus
import com.ham.onettsix.R

class MainActivity : AppCompatActivity() {

    private lateinit var adViewContainer: FrameLayout
    private val adSize: AdSize
//        get() = AdSize.SMART_BANNER
        get() = AdSize(320, 480)
//        get() {
//            val display = windowManager.defaultDisplay
//            val outMetrics = DisplayMetrics()
//            display.getMetrics(outMetrics)
//
//            val density = outMetrics.density
//
//            var adWidthPixels: Float = adViewContainer.width.toFloat()
//            if (adWidthPixels == 0f) {
//                adWidthPixels = outMetrics.widthPixels.toFloat()
//            }

//            val adWidth = (adWidthPixels / density).toInt()
//            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
//        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adViewContainer = findViewById(R.id.adViewContainer)
        Thread {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(
                this
            ) { initializationStatus: InitializationStatus? ->
                initializationStatus?.adapterStatusMap?.keys?.forEach {
                    Log.d("jhlee", "loadBanner : $it-" + initializationStatus.adapterStatusMap[it]?.initializationState?.name)
                }
                loadBanner()
            }
        }.start()
    }

    private fun loadBanner() {

        // Create a new ad view.
        val adView = AdManagerAdView(this)
        adView.setAdSizes(adSize)
        adView.adUnitId = "/22889719886/com.ham.onettsix"

        // Create an ad request.
        val adRequest = AdManagerAdRequest.Builder().build()
        adView.setAppEventListener { s, s2 ->
            Log.d("jhlee", "test")
        }
        adView.adListener = object : AdListener() {
            override fun onAdClicked() {
                super.onAdClicked()
                Log.d("jhlee", "onAdClicked")
            }

            override fun onAdClosed() {
                super.onAdClosed()
                Log.d("jhlee", "onAdClosed")
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                Log.d("jhlee", "onAdFailedToLoad 1 : " + p0.message)
                Log.d("jhlee", "onAdFailedToLoad 2 : " + p0.responseInfo?.responseId)
                Log.d("jhlee", "onAdFailedToLoad 3 : " + p0.domain)
            }

            override fun onAdImpression() {
                super.onAdImpression()
                Log.d("jhlee", "onAdImpression")
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                Log.d("jhlee", "onAdLoaded")
            }

            override fun onAdOpened() {
                super.onAdOpened()
                Log.d("jhlee", "onAdOpened")
            }

            override fun onAdSwipeGestureClicked() {
                super.onAdSwipeGestureClicked()
                Log.d("jhlee", "onAdSwipeGestureClicked")
            }
        }

        adViewContainer.removeAllViews();
        adViewContainer.addView(adView);

        // Start loading the ad in the background.
        adView.loadAd(adRequest)
    }
}
