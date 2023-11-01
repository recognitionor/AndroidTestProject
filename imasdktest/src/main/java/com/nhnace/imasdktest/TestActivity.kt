package com.nhnace.imasdktest

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.google.ads.interactivemedia.v3.api.AdEvent
import com.google.ads.interactivemedia.v3.api.ImaSdkFactory
import com.google.ads.interactivemedia.v3.api.player.ContentProgressProvider
import com.google.ads.interactivemedia.v3.api.player.VideoProgressUpdate

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("jhlee", "onCreate")
        setContentView(R.layout.activity_main)
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager

        val videoPlayer = findViewById<VideoView>(R.id.videoView)
        val videoPlayerContainer = findViewById<ViewGroup>(R.id.videoPlayerContainer)
        val playBtn = findViewById<ImageButton>(R.id.playButton)

        val videoAdPlayerAdapter = VideoAdPlayerAdapter(videoPlayer!!, audioManager) {

        }
        val imaSdkFactory = ImaSdkFactory.getInstance()
        val settings = imaSdkFactory.createImaSdkSettings()
        val adDisplayContainer =
            ImaSdkFactory.createAdDisplayContainer(videoPlayerContainer, videoAdPlayerAdapter)
        val adsLoader =
            ImaSdkFactory.getInstance().createAdsLoader(this, settings, adDisplayContainer)
        // Create the ads request.
        val request = imaSdkFactory.createAdsRequest()
        request.adTagUrl = MainActivity.SAMPLE_VAST_TAG_URL
        request.contentProgressProvider = ContentProgressProvider {
            if (videoPlayer!!.duration <= 0) {
                return@ContentProgressProvider VideoProgressUpdate.VIDEO_TIME_NOT_READY
            }
            VideoProgressUpdate(
                videoPlayer!!.currentPosition.toLong(), videoPlayer!!.duration.toLong()
            )
        }

        adsLoader.addAdsLoadedListener {
            Log.d("jhlee",  "adsLoader.addAdsLoadedListener")
            val adsRenderingSettings = ImaSdkFactory.getInstance().createAdsRenderingSettings()
            it.adsManager.addAdEventListener { it ->
                if (it.type == AdEvent.AdEventType.CONTENT_RESUME_REQUESTED) {
                    videoPlayer.setVideoPath(MainActivity.SAMPLE_VIDEO_URL)
                    videoPlayer.setMediaController(MediaController(this))
                    videoPlayer.setOnPreparedListener { mediaPlayer: MediaPlayer ->
                        mediaPlayer.start()
                    }
                    videoPlayer.setOnCompletionListener { mediaPlayer: MediaPlayer? -> videoAdPlayerAdapter!!.notifyImaOnContentCompleted() }
                }
                Log.d("jhlee", "it : ${it.type.name}")
            }

            it.adsManager.init(adsRenderingSettings)
            it.adsManager.start()

            playBtn.visibility = View.GONE
        }
        // Request the ad. After the ad is loaded, onAdsManagerLoaded() will be called.
        adsLoader.requestAds(request)
        videoPlayer.start()


    }

}