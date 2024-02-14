package com.nhnace.umptest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentForm
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import java.util.concurrent.atomic.AtomicBoolean


class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "jhlee"
    }

    private lateinit var consentInformation: ConsentInformation

    // Use an atomic boolean to initialize the Google Mobile Ads SDK and load ads once.
    private var isMobileAdsInitializeCalled = AtomicBoolean(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Create a ConsentRequestParameters object.
//
        val debugSettings = ConsentDebugSettings.Builder(this)
            .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
            .addTestDeviceHashedId("1D022AE6BB947025DAAF43D5829AD35E")
            .build()

        val params = ConsentRequestParameters
            .Builder()
            .setConsentDebugSettings(debugSettings)
            .build()

        consentInformation = UserMessagingPlatform.getConsentInformation(this)
//        consentInformation.reset()
//        return
        consentInformation.requestConsentInfoUpdate(this, params, {
            UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                this@MainActivity
            ) { loadAndShowError ->
                Log.d("jhlee", "loadAndShowError")
                Log.w(
                    TAG, String.format(
                        "%s: %s", loadAndShowError?.errorCode, loadAndShowError?.message
                    )
                )

                // Consent has been gathered.
                if (consentInformation?.canRequestAds() == true) {
                    initializeMobileAdsSdk()
                }
            }
        }, { requestConsentError ->
            Log.d("jhlee", "requestConsentError")
            // Consent gathering failed.
            Log.w(
                TAG, String.format(
                    "%s: %s", requestConsentError.errorCode, requestConsentError.message
                )
            )
        })

        // Check if you can initialize the Google Mobile Ads SDK in parallel
        // while checking for new consent information. Consent obtained in
        // the previous session can be used to request ads.
        if (consentInformation.canRequestAds()) {
            initializeMobileAdsSdk()
        }
    }

    private fun initializeMobileAdsSdk() {
        if (isMobileAdsInitializeCalled.getAndSet(true)) {
            return
        }

        // Initialize the Google Mobile Ads SDK.
        MobileAds.initialize(this)

        // TODO: Request an ad.
        // InterstitialAd.load(...)
    }
}