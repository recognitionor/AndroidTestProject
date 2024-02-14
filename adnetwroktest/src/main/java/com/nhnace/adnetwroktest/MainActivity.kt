package com.nhnace.adnetwroktest

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adView = findViewById<AdView>(R.id.adView)
        val btn = findViewById<Button>(R.id.btn)
        MobileAds.initialize(
            this
        ) { }

        btn.setOnClickListener {
            Log.d("jhlee", "click")
            adView.visibility = View.VISIBLE
        }
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
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
                Log.d("jhlee", "onAdFailedToLoad")
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

    }
}