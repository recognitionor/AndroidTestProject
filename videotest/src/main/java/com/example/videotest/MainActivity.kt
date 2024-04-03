package com.example.videotest

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val videoPlayerView = findViewById<VideoPlayerView>(R.id.ad_view_frame)
        videoPlayerView.setOnPreparedListener { mp ->
            Log.d("jhlee", "onPrepared")
            mp?.setDataSource(this@MainActivity, SAMPLE_VAST_TAG_URL.toUri())
            Log.d("jhlee", "onPrepared post")
        }
        Log.d("jhlee", SAMPLE_VAST_TAG_URL)
        videoPlayerView.setVideoURI(SAMPLE_VAST_TAG_URL.toUri())
    }

    companion object {
        const val SAMPLE_VAST_TAG_URL =
            ("https://pubads.g.doubleclick.net/gampad/ads?iu=/21775744923/external/" + "single_preroll_skippable&sz=640x480&ciu_szs=300x250%2C728x90&gdfp_req=1&output=vast" + "&unviewed_position_start=1&env=vp&impl=s&correlator=")
    }
}