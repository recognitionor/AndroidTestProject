package com.nhnace.imasdktest


import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.google.ads.interactivemedia.v3.api.AdEvent.AdEventType
import com.google.ads.interactivemedia.v3.api.AdsLoader
import com.google.ads.interactivemedia.v3.api.AdsManager
import com.google.ads.interactivemedia.v3.api.ImaSdkFactory
import com.google.ads.interactivemedia.v3.api.player.ContentProgressProvider
import com.google.ads.interactivemedia.v3.api.player.VideoProgressUpdate
import java.lang.StringBuilder
import java.util.Arrays

/** Main activity.  */
class MainActivity : AppCompatActivity() {
    // Factory class for creating SDK objects.
    private var sdkFactory: ImaSdkFactory? = null

    // The AdsLoader instance exposes the requestAds method.
    private var adsLoader: AdsLoader? = null

    // AdsManager exposes methods to control ad playback and listen to ad events.
    private var adsManager: AdsManager? = null

    // The saved content position, used to resumed content following an ad break.
    private var savedPosition = 0

    // This sample uses a VideoView for content and ad playback. For production
    // apps, Android's Exoplayer offers a more fully featured player compared to
    // the VideoView.
    private var videoPlayer: VideoView? = null
    private var mediaController: MediaController? = null
    private var playButton: View? = null
    private var videoAdPlayerAdapter: VideoAdPlayerAdapter? = null

    private var mDescription: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create the UI for controlling the video view.
        mediaController = MediaController(this)
        videoPlayer = findViewById(R.id.videoView)
        mediaController!!.setAnchorView(videoPlayer)
        videoPlayer?.setMediaController(mediaController)
        mDescription = findViewById(R.id.playerDescription)

        // Create an ad display container that uses a ViewGroup to listen to taps.
        val videoPlayerContainer = findViewById<ViewGroup>(R.id.videoPlayerContainer)
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        videoAdPlayerAdapter = VideoAdPlayerAdapter(videoPlayer!!, audioManager) {
            mDescription?.post {
                mDescription?.text = "${mDescription?.text}\n${"\n Video!! : $it"}"
            }
        }
        sdkFactory = ImaSdkFactory.getInstance()
        val adDisplayContainer =
            ImaSdkFactory.createAdDisplayContainer(videoPlayerContainer, videoAdPlayerAdapter!!)

        // Create an AdsLoader.
        val settings = sdkFactory!!.createImaSdkSettings()
        adsLoader = sdkFactory!!.createAdsLoader(this, settings, adDisplayContainer)

        // Add listeners for when ads are loaded and for errors.
        adsLoader!!.addAdErrorListener { adErrorEvent ->

            /** An event raised when there is an error loading or playing ads.  */
            Log.i(LOGTAG, "Ad Error: " + adErrorEvent.error.message)

            resumeContent()
        }
        adsLoader!!.addAdsLoadedListener { adsManagerLoadedEvent -> // Ads were successfully loaded, so get the AdsManager instance. AdsManager has
            // events for ad playback and errors.
            adsManager = adsManagerLoadedEvent.adsManager

            // Attach event and error event listeners.
            adsManager!!.addAdErrorListener { adErrorEvent ->

                /** An event raised when there is an error loading or playing ads.  */
                /** An event raised when there is an error loading or playing ads.  */
                Log.e(LOGTAG, "Ad Error: " + adErrorEvent.error.message)
                mDescription?.text =
                    "${mDescription?.text}\n${"\nAd Error: " + adErrorEvent.error.message}"
                val universalAdIds = adsManager!!.currentAd.universalAdIds.contentToString()
                Log.i(
                    LOGTAG,
                    "Discarding the current ad break with universal " + "ad Ids: " + universalAdIds
                )
                mDescription?.text =
                    "${mDescription?.text}\n${"Discarding the current ad break with universal ad Ids: $universalAdIds"}"
                adsManager!!.discardAdBreak()
            }
            adsManager!!.addAdEventListener { adEvent ->

                /** Responds to AdEvents.  */
                /** Responds to AdEvents.  */
                if (adEvent.type != AdEventType.AD_PROGRESS) {
                    Log.i(LOGTAG, "Event: " + adEvent.type)
                    mDescription?.text = "${mDescription?.text}\n${"Event: " + adEvent.type}"
                }
                when (adEvent.type) {
                    AdEventType.LOADED ->                         // AdEventType.LOADED is fired when ads are ready to play.

                        // This sample app uses the sample tag
                        // single_preroll_skippable_ad_tag_url that requires calling
                        // AdsManager.start() to start ad playback.
                        // If you use a different ad tag URL that returns a VMAP or
                        // an ad rules playlist, the adsManager.init() function will
                        // trigger ad playback automatically and the IMA SDK will
                        // ignore the adsManager.start().
                        // It is safe to always call adsManager.start() in the
                        // LOADED event.
                        adsManager!!.start()

                    AdEventType.CONTENT_PAUSE_REQUESTED ->                         // AdEventType.CONTENT_PAUSE_REQUESTED is fired when you
                        // should pause your content and start playing an ad.
                        pauseContentForAds()

                    AdEventType.CONTENT_RESUME_REQUESTED ->                         // AdEventType.CONTENT_RESUME_REQUESTED is fired when the ad
                        // you should play your content.
                        resumeContent()

                    AdEventType.ALL_ADS_COMPLETED -> {
                        // Calling adsManager.destroy() triggers the function
                        // VideoAdPlayer.release().
                        adsManager!!.destroy()
                        adsManager = null
                    }

                    AdEventType.CLICKED -> {}
                    else -> {}
                }
            }
            val adsRenderingSettings = ImaSdkFactory.getInstance().createAdsRenderingSettings()
            adsManager!!.init(adsRenderingSettings)
        }

        // When the play button is clicked, request ads and hide the button.
        playButton = findViewById(R.id.playButton)
        playButton?.setOnClickListener(View.OnClickListener { view: View ->
//          videoPlayer.setVideoPath(SAMPLE_VIDEO_URL);

            requestAds(SAMPLE_VAST_TAG_URL)
            view.visibility = View.GONE
        })
        Log.d("jhlee", "")
    }

    private fun pauseContentForAds() {
        Log.i(LOGTAG, "pauseContentForAds")
        savedPosition = videoPlayer!!.currentPosition
        videoPlayer!!.stopPlayback()
        // Hide the buttons and seek bar controlling the video view.
        videoPlayer!!.setMediaController(null)
    }

    private fun resumeContent() {
        Log.i(LOGTAG, "resumeContent")

        // Show the buttons and seek bar controlling the video view.
        videoPlayer!!.setVideoPath(SAMPLE_VIDEO_URL)
        videoPlayer!!.setMediaController(mediaController)
        videoPlayer!!.setOnPreparedListener { mediaPlayer: MediaPlayer ->
            if (savedPosition > 0) {
                mediaPlayer.seekTo(savedPosition)
            }
            mediaPlayer.start()
        }
        videoPlayer!!.setOnCompletionListener { mediaPlayer: MediaPlayer? -> videoAdPlayerAdapter!!.notifyImaOnContentCompleted() }
    }

    private fun requestAds(adTagUrl: String) {
        // Create the ads request.
        val request = sdkFactory!!.createAdsRequest()
        request.adTagUrl = adTagUrl
        request.contentProgressProvider = ContentProgressProvider {
            if (videoPlayer!!.duration <= 0) {
                return@ContentProgressProvider VideoProgressUpdate.VIDEO_TIME_NOT_READY
            }
            VideoProgressUpdate(
                videoPlayer!!.currentPosition.toLong(), videoPlayer!!.duration.toLong()
            )
        }

        // Request the ad. After the ad is loaded, onAdsManagerLoaded() will be called.
        adsLoader!!.requestAds(request)
    }

    companion object {
        private const val LOGTAG = "IMABasicSample"
        const val SAMPLE_VIDEO_URL =
            "https://storage.googleapis.com/gvabox/media/samples/stock.mp4"

        /**
         * IMA sample tag for a single skippable inline video ad. See more IMA sample tags at
         * https://developers.google.com/interactive-media-ads/docs/sdks/html5/client-side/tags
         */
        const val SAMPLE_VAST_TAG_URL =
            ("https://pubads.g.doubleclick.net/gampad/ads?iu=/21775744923/external/" + "single_preroll_skippable&sz=640x480&ciu_szs=300x250%2C728x90&gdfp_req=1&output=vast" + "&unviewed_position_start=1&env=vp&impl=s&correlator=")
    }
}