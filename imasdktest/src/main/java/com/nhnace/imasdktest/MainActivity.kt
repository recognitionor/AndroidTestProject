package com.nhnace.imasdktest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : Activity() {

    companion object {
        private const val LOGTAG = "IMABasicSample"
//        const val SAMPLE_VIDEO_URL =
//            "https://storage.googleapis.com/gvabox/media/samples/stock.mp4"

        const val SAMPLE_URL = "https://eyevinn.adtest.eyevinn.technology/api/v1/vast?dur=30"


        /**
         * IMA sample tag for a single skippable inline video ad. See more IMA sample tags at
         * https://developers.google.com/interactive-media-ads/docs/sdks/html5/client-side/tags
         */
//        const val SAMPLE_VAST_TAG_URL =
//            ("https://pubads.g.doubleclick.net/gampad/ads?iu=/21775744923/external/" + "single_preroll_skippable&sz=640x480&ciu_szs=300x250%2C728x90&gdfp_req=1&output=vast" + "&unviewed_position_start=1&env=vp&impl=s&correlator=")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.go_test).setOnClickListener {
            Log.d("jhlee", "go test")
            Intent(this@MainActivity, TestActivity::class.java).apply {
                startActivity(this)
            }
        }

        findViewById<Button>(R.id.go_test2).setOnClickListener {
            Log.d("jhlee", "go test2")

            Intent(this@MainActivity, TagTestActivity::class.java).apply {
                startActivity(this)
            }
        }

        findViewById<Button>(R.id.go_video).setOnClickListener {
            Intent(this@MainActivity, VideoAdsActivity::class.java).apply {
                startActivity(this)
            }
        }


        Log.d("jhlee", "onCreate")
    }
}