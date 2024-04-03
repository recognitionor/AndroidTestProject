package com.example.videotest

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.util.AttributeSet
import android.widget.VideoView
import java.util.Timer
import java.util.TimerTask


class VideoPlayerView : VideoView {
    private var _videoUrl: String? = null
    private var _videoTimer: Timer? = null
    private var _prepareTimer: Timer? = null
    private var _progressEventInterval = 500
    private var _mediaPlayer: MediaPlayer? = null
    private var _volume: Float? = null
    private var _infoListenerEnabled = true
    private var _audioManager: AudioManager? = null

    constructor(context: Context) : super(context)

    // Context와 AttributeSet을 받는 생성자 - XML에서 사용될 때 필요함
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private fun startVideoProgressTimer() {
        _videoTimer = Timer()
        _videoTimer!!.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                var isPlaying = false
                try {
                    isPlaying = isPlaying()
                } catch (e: IllegalStateException) {
                }
            }
        }, _progressEventInterval.toLong(), _progressEventInterval.toLong())
    }

    fun stopVideoProgressTimer() {
        if (_videoTimer != null) {
            _videoTimer!!.cancel()
            _videoTimer!!.purge()
            _videoTimer = null
        }
    }

    private fun startPrepareTimer(delay: Long) {
        _prepareTimer = Timer()
        _prepareTimer!!.schedule(object : TimerTask() {
            override fun run() {
                var isPlaying = false
                try {
                    isPlaying = isPlaying()
                    if (!isPlaying) {

                    }
                } catch (e: IllegalStateException) {

                }
            }
        }, delay)
    }

    fun stopPrepareTimer() {
        if (_prepareTimer != null) {
            _prepareTimer!!.cancel()
            _prepareTimer!!.purge()
            _prepareTimer = null
        }
    }

    fun prepare(url: String?, initialVolume: Float, timeout: Int): Boolean {
        _videoUrl = url
        setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
            override fun onPrepared(mp: MediaPlayer) {
                stopPrepareTimer()
                if (mp != null) {
                    _mediaPlayer = mp
                }
                volume = initialVolume

            }
        })
        setOnErrorListener { mp, what, extra ->
            stopPrepareTimer()
            if (mp != null) {
                _mediaPlayer = mp
            }

            stopVideoProgressTimer()
            true
        }
        setInfoListenerEnabled(_infoListenerEnabled)
        if (timeout > 0) {
            startPrepareTimer(timeout.toLong())
        }
        try {
            // check api version
            // setAudioFocusRequest is available in API level 26 and above
            // requestAudioFocus(AudioManager.OnAudioFocusChangeListener l, int streamType, int durationHint) was deprecated in API level 26.
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                _audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                if (_audioManager != null) {
                    _audioManager!!.requestAudioFocus(
                        null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
                    )
                }
            } else {
                setAudioFocusRequest(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
            }
            setVideoPath(_videoUrl)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    fun play() {
        setOnCompletionListener { mp ->
            if (mp != null) {
                _mediaPlayer = mp
            }
            stopVideoProgressTimer()
        }
        try {
            start()
            stopVideoProgressTimer()
            startVideoProgressTimer()
        } catch (ex: IllegalStateException) {
        }
    }

    fun setInfoListenerEnabled(enabled: Boolean) {
        _infoListenerEnabled = enabled
        if (Build.VERSION.SDK_INT > 16) {
            if (_infoListenerEnabled) {
                setOnInfoListener { mp, what, extra ->
                    true
                }
            } else {
                setOnInfoListener(null)
            }
        }
    }

    override fun pause() {
        try {
            super.pause()

            // check api version
            // abandonAudioFocus was deprecated in API level 26
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                if (_audioManager != null) {
                    _audioManager!!.abandonAudioFocus(null)
                }
            } else {
                setAudioFocusRequest(AudioManager.AUDIOFOCUS_NONE)
            }
        } catch (e: Exception) {
            return
        }
        stopVideoProgressTimer()
    }

    override fun seekTo(msec: Int) {
        try {
            super.seekTo(msec)
        } catch (e: Exception) {
            return
        }
    }

    fun stop() {
        stopPlayback()
        stopVideoProgressTimer()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            if (_audioManager != null) {
                _audioManager!!.abandonAudioFocus(null)
            }
        } else {
            setAudioFocusRequest(AudioManager.AUDIOFOCUS_NONE)
        }
    }

    var volume: Float?
        get() = _volume
        set(volume) {
            _volume = try {
                _mediaPlayer!!.setVolume(volume!!, volume)
                volume
            } catch (e: Exception) {
                return
            }
        }
    var progressEventInterval: Int
        get() = _progressEventInterval
        set(ms) {
            _progressEventInterval = ms
            if (_videoTimer != null) {
                stopVideoProgressTimer()
                startVideoProgressTimer()
            }
        }
    val videoViewRectangle: IntArray
        get() {
            val xyPoint = IntArray(2)
            getLocationInWindow(xyPoint)
            return intArrayOf(xyPoint[0], xyPoint[1], this.measuredWidth, this.measuredHeight)
        }
}

