package com.nhnace.imasdktest


import android.app.Activity
import android.media.AudioManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.VideoView
import com.google.ads.interactivemedia.v3.api.AdEvent
import com.google.ads.interactivemedia.v3.api.AdsManager
import com.google.ads.interactivemedia.v3.api.AdsManagerLoadedEvent
import com.google.ads.interactivemedia.v3.api.ImaSdkFactory


class TagTestActivity : Activity(), View.OnClickListener {

    companion object {
        var LOAD_TIME: Long = 0
        var SHOW_TIME: Long = 0
    }

    lateinit var videoPlayer: VideoView
    lateinit var videoPlayerContainer: ViewGroup
    lateinit var audioManager: AudioManager
    lateinit var adsManagerLoadedEvent: AdsManagerLoadedEvent
    lateinit var adsManager: AdsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("jhlee", "onCreate")

        setContentView(R.layout.activity_exo_test)

        findViewById<Button>(R.id.test_load_btn).setOnClickListener(this)
        findViewById<Button>(R.id.test_load2_btn).setOnClickListener(this)
        findViewById<Button>(R.id.test_load3_btn).setOnClickListener(this)

        videoPlayer = findViewById(R.id.videoView)
        videoPlayerContainer = findViewById(R.id.videoPlayerContainer)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.test_load_btn -> {
                LOAD_TIME = System.currentTimeMillis()
                Log.d("jhlee", "1")
                val vastString: String =
                    this.assets.open("vast.xml").bufferedReader().use { it.readText() }
                val imaSdkFactory = ImaSdkFactory.getInstance()
                val settings = imaSdkFactory.createImaSdkSettings()
                val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
                val videoAdPlayerAdapter = VideoAdPlayerAdapter(videoPlayer, audioManager)
                val adDisplayContainer = ImaSdkFactory.createAdDisplayContainer(
                    videoPlayerContainer, videoAdPlayerAdapter
                )
                val adsLoader =
                    ImaSdkFactory.getInstance().createAdsLoader(this, settings, adDisplayContainer)
                adsLoader.addAdsLoadedListener {
                    adsManager = it.adsManager
                    val renderingSettings = ImaSdkFactory.getInstance().createAdsRenderingSettings()
                    adsManager.addAdEventListener { event ->
                        if (event.type == AdEvent.AdEventType.STARTED) {
                            Log.d("jhlee", "STARTED")
                            videoPlayerContainer.visibility = View.GONE
                            videoPlayer.pause()
                        }

                        if (event.type != AdEvent.AdEventType.AD_PROGRESS) {
                            Log.d(
                                "jhlee",
                                "event : ${event.type} : ${System.currentTimeMillis() - LOAD_TIME}- ${System.currentTimeMillis() - SHOW_TIME}"
                            )
                        }

                    }

//                    adsManager.init(renderingSettings)
                    adsManager.init()
                    adsManager.start()


                }
                // Create the ads request.
                val request = imaSdkFactory.createAdsRequest()
                request.adsResponse = vastString
                adsLoader.requestAds(request)
                videoPlayerContainer.visibility = View.GONE
//                videoPlayer.visibility = View.GONE
            }

            R.id.test_load2_btn -> {
                SHOW_TIME = System.currentTimeMillis()
                videoPlayer.pause()
                Log.d("jhlee", "SHOW")
            }

            R.id.test_load3_btn -> {
                Log.d("jhlee", "3")
                videoPlayerContainer.visibility = View.VISIBLE
                videoPlayer.start()
            }
        }
    }
}