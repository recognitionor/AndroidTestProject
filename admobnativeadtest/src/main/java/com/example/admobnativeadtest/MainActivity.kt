package com.example.admobnativeadtest

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.VideoOptions
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView


const val ADMOB_AD_UNIT_ID = "ca-app-pub-4279642319532137/9493605777"

class MainActivity : AppCompatActivity() {

    private var currentNativeAd: NativeAd? = null

    //    private lateinit var googleMobileAdsConsentManager: GoogleMobileAdsConsentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thread {
            Log.d("jhlee", "thread start")
            val advertisingInfo = AdvertisingIdClient.getAdvertisingIdInfo(applicationContext)
            val adId = advertisingInfo.id
            Log.d("jhlee", "adId : $adId")
        }.start()


        val manualLoadBtn: Button = findViewById(R.id.ad_manual_load_btn)
        val templateLoadBtn: Button = findViewById(R.id.ad_template_load_btn)
        val templateAdFrame: TemplateView = findViewById(R.id.my_template)
        val adFrame: FrameLayout = findViewById(R.id.ad_frame)

        MobileAds.initialize(this) { initializationStatus ->
            Toast.makeText(this, "init done", Toast.LENGTH_SHORT).show()
        }

//        googleMobileAdsConsentManager = GoogleMobileAdsConsentManager.getInstaㄱce(applicationContext)

        templateLoadBtn.setOnClickListener {
            Log.d("jhlee", "templateLoad")
            Log.d("jhlee", "Google Mobile Ads SDK Version: " + MobileAds.getVersion())

            val builder = AdLoader.Builder(this, ADMOB_AD_UNIT_ID)
            builder.forNativeAd { nativeAd ->
                currentNativeAd = nativeAd
                val color = ColorDrawable(Color.WHITE)
                val style = NativeTemplateStyle.Builder().withMainBackgroundColor(color).build()
                templateAdFrame.setStyles(style)
                templateAdFrame.setNativeAd(nativeAd)
                Log.d("jhlee", "nativeAD nativeAd.body : ${nativeAd.body}")
                Log.d("jhlee", "nativeAD nativeAd.headline : ${nativeAd.headline}")
                Log.d("jhlee", "nativeAD nativeAd.advertiser : ${nativeAd.advertiser}")
                Log.d("jhlee", "nativeAD nativeAd.price : ${nativeAd.price}")
                Log.d("jhlee", "nativeAD nativeAd.mediaContent : ${nativeAd.mediaContent}")
            }

            val videoOptions = VideoOptions.Builder().setStartMuted(true).build()
            val adOptions = NativeAdOptions.Builder().setVideoOptions(videoOptions).build()
            builder.withNativeAdOptions(adOptions)

            val adLoader = builder.withAdListener(object : AdListener() {
                override fun onAdLoaded() {
                    super.onAdLoaded()
                    Log.d("jhlee", "onAdLoaded")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.d("jhlee", "onAdFailedToLoad : ${loadAdError.message}")
                }
            }).build()
            adLoader.loadAd(AdRequest.Builder().build())
        }

        manualLoadBtn.setOnClickListener {
            Log.d("jhlee", "Google Mobile Ads SDK Version: " + MobileAds.getVersion())

            val builder = AdLoader.Builder(this, ADMOB_AD_UNIT_ID)
            builder.forNativeAd { nativeAd ->
                currentNativeAd = nativeAd
                val adView = layoutInflater.inflate(R.layout.ad_layout, null) as NativeAdView

                populateNativeAdView(nativeAd, adView)
                adFrame.removeAllViews()
                adFrame.addView(adView)

            }

            val videoOptions = VideoOptions.Builder().setStartMuted(true).build()
            val adOptions = NativeAdOptions.Builder().setVideoOptions(videoOptions).build()
            builder.withNativeAdOptions(adOptions)

            val adLoader = builder.withAdListener(object : AdListener() {
                override fun onAdLoaded() {
                    super.onAdLoaded()
                    Log.d("jhlee", "onAdLoaded")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.d("jhlee", "onAdFailedToLoad : ${loadAdError.message}")
                }
            }).build()
            adLoader.loadAd(AdRequest.Builder().build())
        }
    }

    private fun populateTemplateNativeAdView(ad: NativeAd, templateView: TemplateView) {
        Log.d("jhlee", "populateTemplateNativeAdView")
    }

    private fun populateNativeAdView(ad: NativeAd, adView: NativeAdView) {
        val headlineView = adView.findViewById<TextView>(R.id.ad_headline)
//        val appIconImageView = adView.findViewById<ImageView>(R.id.ad_app_icon)
        val mediaView = adView.findViewById<MediaView>(R.id.ad_test_media)


        headlineView.text = ad.headline
        mediaView.mediaContent = ad.mediaContent

        adView.mediaView = mediaView
        adView.headlineView = headlineView


        adView.setNativeAd(ad)




    }
}