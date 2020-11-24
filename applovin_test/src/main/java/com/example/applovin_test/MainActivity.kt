package com.example.applovin_test

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.applovin.adview.AppLovinIncentivizedInterstitial
import com.applovin.sdk.AppLovinAd
import com.applovin.sdk.AppLovinAdLoadListener
import com.applovin.sdk.AppLovinAdRewardListener
import com.applovin.sdk.AppLovinSdk

class MainActivity : AppCompatActivity(), AppLovinAdLoadListener {

    private var incentivizedInterstitial: AppLovinIncentivizedInterstitial? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        incentivizedInterstitial = AppLovinIncentivizedInterstitial.create(
            "6c44b89baed6bd1e",
            AppLovinSdk.getInstance(this)
        );
        incentivizedInterstitial?.preload(this)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun adReceived(ad: AppLovinAd?) {
        Log.d("jhlee", "adReceived")
        incentivizedInterstitial?.show(this@MainActivity, object : AppLovinAdRewardListener {
            override fun userRewardVerified(
                ad: AppLovinAd?,
                response: MutableMap<String, String>?
            ) {
                Log.d("jhlee", "userRewardVerified")
                var test = response
                Log.d("jhlee", "userRewardVerified")
            }

            override fun userOverQuota(ad: AppLovinAd?, response: MutableMap<String, String>?) {
                Log.d("jhlee", "userOverQuota")
            }

            override fun validationRequestFailed(ad: AppLovinAd?, errorCode: Int) {
                Log.d("jhlee", "validationRequestFailed")
            }

            override fun userDeclinedToViewAd(ad: AppLovinAd?) {
                Log.d("jhlee", "userDeclinedToViewAd")
            }

            override fun userRewardRejected(
                ad: AppLovinAd?,
                response: MutableMap<String, String>?
            ) {
                Log.d("jhlee", "userRewardRejected")
            }
        })

    }

    override fun failedToReceiveAd(errorCode: Int) {
        Log.d("jhlee", "failedToReceiveAd");
    }
}