// Copyright 2022 Google LLC
package com.nhnace.imasdktest

import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.widget.VideoView
import com.google.ads.interactivemedia.v3.api.AdPodInfo
import com.google.ads.interactivemedia.v3.api.player.AdMediaInfo
import com.google.ads.interactivemedia.v3.api.player.VideoAdPlayer
import com.google.ads.interactivemedia.v3.api.player.VideoAdPlayer.VideoAdPlayerCallback
import com.google.ads.interactivemedia.v3.api.player.VideoProgressUpdate
import java.util.Timer
import java.util.TimerTask
import kotlin.math.log

/** Example implementation of IMA's VideoAdPlayer interface.  */
class VideoAdPlayerAdapter(
    private val videoPlayer: VideoView,
    audioManager: AudioManager,
    private val logListener: (msg: String) -> Unit
) : VideoAdPlayer {
    private val audioManager: AudioManager
    private val videoAdPlayerCallbacks: MutableList<VideoAdPlayerCallback> = ArrayList()
    private var timer: Timer? = null
    private var adDuration = 0

    // The saved ad position, used to resumed ad playback following an ad click-through.
    private var savedAdPosition = 0
    private var loadedAdMediaInfo: AdMediaInfo? = null

    init {
        videoPlayer.setOnCompletionListener { mediaPlayer: MediaPlayer? -> notifyImaOnContentCompleted() }
        this.audioManager = audioManager
    }

    override fun addCallback(videoAdPlayerCallback: VideoAdPlayerCallback) {
        videoAdPlayerCallbacks.add(videoAdPlayerCallback)
    }

    override fun loadAd(adMediaInfo: AdMediaInfo, adPodInfo: AdPodInfo) {
        // This simple ad loading logic works because preloading is disabled. To support
        // preloading ads your app must maintain state for the currently playing ad
        // while handling upcoming ad downloading and buffering at the same time.
        // See the IMA Android preloading guide for more info:
        // https://developers.google.com/interactive-media-ads/docs/sdks/android/client-side/preload
        loadedAdMediaInfo = adMediaInfo
    }

    override fun pauseAd(adMediaInfo: AdMediaInfo) {
        Log.i(LOGTAG, "pauseAd")
        logListener.invoke("pauseAd")
        savedAdPosition = videoPlayer.currentPosition
        stopAdTracking()
    }

    override fun playAd(adMediaInfo: AdMediaInfo) {
        logListener.invoke("playAD")
        videoPlayer.setVideoURI(Uri.parse(adMediaInfo.url))
        videoPlayer.setOnPreparedListener { mediaPlayer: MediaPlayer ->
            adDuration = mediaPlayer.duration
            if (savedAdPosition > 0) {
                mediaPlayer.seekTo(savedAdPosition)
            }

            mediaPlayer.start()
            startAdTracking()
        }
        videoPlayer.setOnErrorListener { mediaPlayer: MediaPlayer?, errorType: Int, extra: Int ->
            logListener.invoke("setOnErrorListener")

            notifyImaSdkAboutAdError(
                errorType
            )
        }
        videoPlayer.setOnCompletionListener { mediaPlayer: MediaPlayer? ->
            logListener.invoke("setOnCompletionListener")

            savedAdPosition = 0
            notifyImaSdkAboutAdEnded()
        }
    }

    override fun release() {
        // any clean up that needs to be done.
        logListener.invoke("release")
    }

    override fun removeCallback(videoAdPlayerCallback: VideoAdPlayerCallback) {
        videoAdPlayerCallbacks.remove(videoAdPlayerCallback)
    }

    override fun stopAd(adMediaInfo: AdMediaInfo) {
        Log.i(LOGTAG, "stopAd")
        logListener.invoke("stopAd")
        stopAdTracking()
    }

    /** Returns current volume as a percent of max volume.  */
    override fun getVolume(): Int {
        return (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) / audioManager.getStreamMaxVolume(
            AudioManager.STREAM_MUSIC
        ))
    }

    private fun startAdTracking() {
        Log.i(LOGTAG, "startAdTracking")
        logListener.invoke("startAdTracking")
        if (timer != null) {
            return
        }
        timer = Timer()
        val updateTimerTask: TimerTask = object : TimerTask() {
            override fun run() {
                val progressUpdate = adProgress
                notifyImaSdkAboutAdProgress(progressUpdate)
            }
        }
        timer!!.schedule(updateTimerTask, POLLING_TIME_MS, INITIAL_DELAY_MS)
    }

    private fun notifyImaSdkAboutAdEnded() {
        Log.i(LOGTAG, "notifyImaSdkAboutAdEnded")
        logListener.invoke("notifyImaSdkAboutAdEnded")
        savedAdPosition = 0
        for (callback in videoAdPlayerCallbacks) {
            callback.onEnded(loadedAdMediaInfo!!)
        }
    }

    private fun notifyImaSdkAboutAdProgress(adProgress: VideoProgressUpdate) {
        logListener.invoke("notifyImaSdkAboutAdProgress")
        for (callback in videoAdPlayerCallbacks) {
            callback.onAdProgress(loadedAdMediaInfo!!, adProgress)
        }
    }

    /**
     * @param errorType Media player's error type as defined at
     * https://cs.android.com/android/platform/superproject/+/master:frameworks/base/media/java/android/media/MediaPlayer.java;l=4335
     * @return True to stop the current ad playback.
     */
    private fun notifyImaSdkAboutAdError(errorType: Int): Boolean {
        Log.i(LOGTAG, "notifyImaSdkAboutAdError")
        logListener.invoke("notifyImaSdkAboutAdError")
        when (errorType) {
            MediaPlayer.MEDIA_ERROR_UNSUPPORTED -> {
                Log.e(
                    LOGTAG, "notifyImaSdkAboutAdError: MEDIA_ERROR_UNSUPPORTED"
                )
                logListener.invoke("notifyImaSdkAboutAdError: MEDIA_ERROR_UNSUPPORTED")
            }

            MediaPlayer.MEDIA_ERROR_TIMED_OUT -> {
                Log.e(
                    LOGTAG, "notifyImaSdkAboutAdError: MEDIA_ERROR_TIMED_OUT"
                )
                logListener.invoke("notifyImaSdkAboutAdError: MEDIA_ERROR_TIMED_OUT")
            }

            else -> {}
        }
        for (callback in videoAdPlayerCallbacks) {
            callback.onError(loadedAdMediaInfo!!)
        }
        return true
    }

    fun notifyImaOnContentCompleted() {
        Log.i(LOGTAG, "notifyImaOnContentCompleted")
        logListener.invoke("notifyImaOnContentCompleted")
        for (callback in videoAdPlayerCallbacks) {
            callback.onContentComplete()
        }
    }

    private fun stopAdTracking() {
        Log.i(LOGTAG, "stopAdTracking")
        logListener.invoke("stopAdTracking")
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }

    override fun getAdProgress(): VideoProgressUpdate {
        logListener.invoke("getAdProgress")
        val adPosition = videoPlayer.currentPosition.toLong()
        return VideoProgressUpdate(adPosition, adDuration.toLong())
    }

    companion object {
        private const val LOGTAG = "IMABasicSample"
        private const val POLLING_TIME_MS: Long = 250
        private const val INITIAL_DELAY_MS: Long = 250
    }
}