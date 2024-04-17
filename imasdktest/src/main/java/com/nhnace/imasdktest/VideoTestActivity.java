package com.nhnace.imasdktest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

public class VideoTestActivity extends Activity implements View.OnClickListener {
    private VideoView videoView;

    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("jhlee", "onCreate");
        setContentView(R.layout.activity_video_layout);
        videoView = findViewById(R.id.video_test_view);
        videoView.setOnClickListener(v -> {
            if (videoView.isPlaying()) {
                videoView.pause();
            } else {
                videoView.start();
            }
            Log.d("jhlee", "click : " + videoView.isPlaying());
        });
        videoView.setOnCompletionListener(mp -> {
            Log.d("jhlee", "END");
            videoView.seekTo(0);
            videoView.setVisibility(View.GONE);
        });

        videoView.setOnPreparedListener(mp -> {
            Log.d("jhlee", "onPrepared : ");
            videoView.setVisibility(View.VISIBLE);
        });
//        videoView.setHorizontalFadingEdgeEnabled();
//        videoView.setMediaController(new MediaController(this) {
//            @Override
//            public void onFinishInflate() {
//                super.onFinishInflate();
//                Log.d("jhlee", "onFinishInflate");
//            }
//
//            @Override
//            public void setMediaPlayer(MediaPlayerControl player) {
//                super.setMediaPlayer(player);
//                Log.d("jhlee", "setMediaPlayer");
//            }
//
//            @Override
//            public void setAnchorView(View view) {
//                super.setAnchorView(view);
//                Log.d("jhlee", "setAnchorView");
//            }
//
//            @Override
//            public void show() {
//                super.show();
//                Log.d("jhlee", "show");
//            }
//
//            @Override
//            public void show(int timeout) {
//                super.show(timeout);
//                Log.d("jhlee", "show : " + timeout);
//            }
//
//            @Override
//            public boolean isShowing() {
//                Log.d("jhlee", "isShowing");
//                return super.isShowing();
//
//            }
//
//            @Override
//            public void hide() {
//                super.hide();
//                Log.d("jhlee", "onCreate");
//            }
//
//            @Override
//            public boolean onTouchEvent(MotionEvent event) {
//                Log.d("jhlee", "onTouchEvent");
//                return super.onTouchEvent(event);
//            }
//
//            @Override
//            public boolean onTrackballEvent(MotionEvent ev) {
//                Log.d("jhlee", "onTrackballEvent");
//                return super.onTrackballEvent(ev);
//            }
//
//            @Override
//            public boolean dispatchKeyEvent(KeyEvent event) {
//                Log.d("jhlee", "dispatchKeyEvent");
//                return super.dispatchKeyEvent(event);
//            }
//
//            @Override
//            public void setEnabled(boolean enabled) {
//                Log.d("jhlee", "setEnabled");
//                super.setEnabled(enabled);
//            }
//
//            @Override
//            public CharSequence getAccessibilityClassName() {
//                Log.d("jhlee", "getAccessibilityClassName");
//                return super.getAccessibilityClassName();
//            }
//
//            @Override
//            public void setPrevNextListeners(OnClickListener next, OnClickListener prev) {
//                super.setPrevNextListeners(next, prev);
//                Log.d("jhlee", "setPrevNextListeners");
//            }
//        });

//        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
//
//            @Override
//            public boolean onInfo(MediaPlayer mp, int what, int extra) {
//                Log.d("jhlee", "onInfo");
//                return false;
//            }
//        });
//
        findViewById(R.id.test_load_btn).setOnClickListener(this);
        findViewById(R.id.test_load2_btn).setOnClickListener(this);
        findViewById(R.id.test_load3_btn).setOnClickListener(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
        position = videoView.getCurrentPosition();
        Log.d("jhlee", "onPause : " + videoView.getCurrentPosition());
    }

    @Override
    protected void onStart() {
        super.onStart();
        videoView.seekTo(position);
//        videoView.start();
        videoView.pause();
        Log.d("jhlee", "onStart : " + position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_load_btn: {
                //"https://cdn-f.adsmoloco.com/JfgNcLu0Y3dhgzkB/creative/l3b8io1a_.m5v_lgeyhls36wdzqpsx.mp4"
                videoView.setVideoPath("https://cdn-f.adsmoloco.com/JfgNcLu0Y3dhgzkB/creative/l3b8io1a_.m5v_lgeyhls36wdzqpsx.mp4");
                videoView.setVisibility(View.VISIBLE);
//                videoView.start();
                Log.d("jhlee", "1");
                break;
            }
            case R.id.test_load2_btn: {
                videoView.pause();
                Log.d("jhlee", "2");
                break;

            }
            case R.id.test_load3_btn: {
                videoView.start();
                Log.d("jhlee", "3");
                break;
            }
        }
    }
}
