package com.example.ad_manager

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerAdView
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.ham.onettsix.R


class MainActivity : AppCompatActivity() {

    private lateinit var adViewContainer: FrameLayout
    private var mInterstitialAd: InterstitialAd? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adViewContainer = findViewById(R.id.adViewContainer)
        findViewById<Button>(R.id.ad_manager_inter_load).setOnClickListener {
            admanagerLoadInterBanner()
        }
        findViewById<Button>(R.id.ad_manager_banner_load).setOnClickListener {
            admanagerLoadBanner()
        }
        findViewById<Button>(R.id.admob_load).setOnClickListener {
            loadAdmobLoad()
        }

        findViewById<Button>(R.id.adlib_load).setOnClickListener {
            loadAdlib()
        }

        Thread {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(
                this
            ) { initializationStatus: InitializationStatus? ->
                initializationStatus?.adapterStatusMap?.keys?.forEach {
                    Log.d(
                        "jhlee",
                        "loadBanner : $it-" + initializationStatus.adapterStatusMap[it]?.initializationState?.name
                    )
                }
            }
        }.start()
    }

    private fun loadAdlib() {

    }

    private fun loadAdmobLoad() {
        val adRequest: AdRequest = AdRequest.Builder().build()

        InterstitialAd.load(/*ca-app-pub-2339520391845779/3145779062*/

            this,
            "ca-app-pub-2339520391845779/3145779062",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d("jhlee", "onAdLoaded")
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    mInterstitialAd = interstitialAd
                    mInterstitialAd?.show(this@MainActivity)
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    Log.d("jhlee", "onAdFailedToLoad")
                    mInterstitialAd = null
                }
            })

    }

    private fun admanagerLoadBanner() {
        val adSize = AdSize(320, 50)
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

    private fun admanagerLoadInterBanner() {
        val adSize = AdSize(320, 480)
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
