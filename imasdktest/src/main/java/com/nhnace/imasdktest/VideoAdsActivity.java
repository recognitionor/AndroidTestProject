package com.nhnace.imasdktest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;

import com.google.ads.interactivemedia.v3.api.Ad;
import com.google.ads.interactivemedia.v3.api.AdDisplayContainer;
import com.google.ads.interactivemedia.v3.api.AdsLoader;
import com.google.ads.interactivemedia.v3.api.AdsManager;
import com.google.ads.interactivemedia.v3.api.AdsManagerLoadedEvent;
import com.google.ads.interactivemedia.v3.api.AdsRenderingSettings;
import com.google.ads.interactivemedia.v3.api.AdsRequest;
import com.google.ads.interactivemedia.v3.api.ImaSdkFactory;
import com.google.ads.interactivemedia.v3.api.ImaSdkSettings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class VideoAdsActivity extends Activity implements View.OnClickListener, AdsLoader.AdsLoadedListener {

    static long LOAD_TIME;
    static long SHOW_TIME;

    static String CLICK_THROUGH_URL = "clickThroughUrl";

    private AdsLoader adsLoader;

    private VideoView videoPlayer;
    private ViewGroup videoPlayerContainer;
    AdsManager adsManager;

    int position = 0;

    boolean isEnded = false;

    boolean isAdPlaying = false;

    String clickUrl;

    View closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adlib_activity_video_layout);
        closeBtn = findViewById(R.id.close_btn);
        closeBtn.setOnClickListener(this);
        findViewById(R.id.test_load_btn).setOnClickListener(this);
        findViewById(R.id.test_load2_btn).setOnClickListener(this);
        findViewById(R.id.test_load3_btn).setOnClickListener(this);

        initVideoPlayerView();
        initVideoLoader();
    }

    private void initVideoPlayerView() {
        videoPlayerContainer = findViewById(R.id.videoPlayerContainer);
        videoPlayer = findViewById(R.id.videoView);
    }

    private void initVideoLoader() {
        Log.d("jhlee", "initVideoLoader");
        String vastString = "";
        try {
            vastString = new BufferedReader(new InputStreamReader(this.getAssets().open("vast_test.xml"))).lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImaSdkFactory imaSdkFactory = ImaSdkFactory.getInstance();
        ImaSdkSettings settings = imaSdkFactory.createImaSdkSettings();
        settings.setAutoPlayAdBreaks(false);
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        VideoAdPlayerAdapter videoAdPlayerAdapter = new VideoAdPlayerAdapter(videoPlayer, audioManager);
        AdDisplayContainer adDisplayContainer = ImaSdkFactory.createAdDisplayContainer(videoPlayerContainer, videoAdPlayerAdapter);
        AdsLoader adsLoader = ImaSdkFactory.getInstance().createAdsLoader(this, settings, adDisplayContainer);

        adsLoader.addAdsLoadedListener(this);
        AdsRequest request = imaSdkFactory.createAdsRequest();
        request.setAdsResponse(vastString);
        request.setContentProgressProvider(() -> null);
        adsLoader.requestAds(request);
    }

    @Override
    protected void onPause() {
        super.onPause();
        position = videoPlayer.getCurrentPosition();
    }

    @Override
    protected void onStart() {
        super.onStart();
        videoPlayer.seekTo(position);
        if (isAdPlaying && !isEnded) {
            new Thread(() -> {
                while (true) {
                    if (videoPlayer.isPlaying()) {
                        videoPlayer.pause();
                        break;
                    }
                }
            }).start();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_btn: {
                Log.d("jhlee", "close_btn");
                finish();
            }
            case R.id.test_load_btn:
                LOAD_TIME = System.currentTimeMillis();
                initVideoLoader();
                break;

            case R.id.test_load2_btn:
                SHOW_TIME = System.currentTimeMillis();
                videoPlayer.pause();
                Log.d("jhlee", "PAUSE");
                break;

            case R.id.test_load3_btn:
                Log.d("jhlee", "SHOW");
                videoPlayer.start();
                break;
        }
    }

    @Override
    public void onAdsManagerLoaded(@NonNull AdsManagerLoadedEvent adsManagerLoadedEvent) {
        Log.d("jhlee", "onAdsManagerLoaded");
        adsManager = adsManagerLoadedEvent.getAdsManager();

        AdsRenderingSettings renderingSettings = ImaSdkFactory.getInstance().createAdsRenderingSettings();
        renderingSettings.setEnablePreloading(true);
        adsManager.addAdEventListener(adEvent -> {


            switch (adEvent.getType()) {
                case AD_PROGRESS: {
                    if (4000 < videoPlayer.getCurrentPosition()) {
                        closeBtn.setVisibility(View.VISIBLE);
                    }
                    break;
                }
                case LOADED: {
                    isAdPlaying = true;
                    clickUrl = getClickUrl(adsManager.getCurrentAd());
                    adsManager.start();
                    break;
                }
                case ALL_ADS_COMPLETED: {
                    videoPlayer.setOnClickListener(v -> {
                        goClickUrl();
                    });
                    isEnded = true;
                    break;
                }
                case CLICKED: {
                    Log.d("jhlee", "CLICKED");
                    break;
                }
                case TAPPED: {
                    Log.d("jhlee", "TAPPED");
                    if (!videoPlayer.isPlaying()) {
                        videoPlayer.start();
                    } else {
                        goClickUrl();
                    }
                    break;
                }
                case SKIPPED: {
                    adsManager.skip();
                    break;
                }
            }
        });
        adsManager.init();
    }

    private void goClickUrl() {
        try {
            Uri uri = Uri.parse(clickUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getClickUrl(Ad ad) {
        // Ad 에서 click시 이동하는 URL 을 제공하는 함수가 없어 직접 필드 접근해서 가져온다.
        Class<?> clazz = ad.getClass();
        Field nameField;
        String nameValue;
        try {
            nameField = clazz.getDeclaredField(CLICK_THROUGH_URL);
            nameField.setAccessible(true);
            nameValue = (String) nameField.get(ad);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return nameValue;
    }
}
