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
import com.google.ads.interactivemedia.v3.api.CompanionAdSlot;
import com.google.ads.interactivemedia.v3.api.ImaSdkFactory;
import com.google.ads.interactivemedia.v3.api.ImaSdkSettings;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
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

    static public long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("jhlee", "onCreate");
        setContentView(R.layout.adlib_activity_video_layout);
        closeBtn = findViewById(R.id.close_btn);
        closeBtn.setOnClickListener(this);
        findViewById(R.id.test_load_btn).setOnClickListener(this);
        findViewById(R.id.test_load2_btn).setOnClickListener(this);
        findViewById(R.id.test_load3_btn).setOnClickListener(this);
        time = System.currentTimeMillis();
        Log.d("jhlee", "onCreate time1 : " + (System.currentTimeMillis() - time));
        initVideoPlayerView();
        Log.d("jhlee", "onCreate time2 : " + (System.currentTimeMillis() - time));
        initVideoLoader();
        Log.d("jhlee", "onCreate time3 : " + (System.currentTimeMillis() - time));
    }

    private void initVideoPlayerView() {
        videoPlayerContainer = findViewById(R.id.videoPlayerContainer);
        videoPlayer = findViewById(R.id.videoView);
        ImaSdkFactory sdkFactory = ImaSdkFactory.getInstance();
    }

    private void initVideoLoader() {

        Log.d("jhlee", "initVideoLoader time1 : " + (System.currentTimeMillis() - time));
        String vastString = "";
        try {
            vastString = new BufferedReader(new InputStreamReader(this.getAssets().open("vast4.xml"))).lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("jhlee", "initVideoLoader time2 : " + (System.currentTimeMillis() - time));
        ImaSdkFactory imaSdkFactory = ImaSdkFactory.getInstance();
        ImaSdkSettings settings = imaSdkFactory.createImaSdkSettings();
        settings.setAutoPlayAdBreaks(false);

        // Companion View Group 및 Companion Ad Slot 설정
//        ViewGroup companionViewGroup = findViewById(R.id.companionAdSlot);
//        CompanionAdSlot companionAdSlot = imaSdkFactory.createCompanionAdSlot();
//        companionAdSlot.setContainer(companionViewGroup);
//        companionAdSlot.setSize(300, 250);  // 고정된 크기 설정 (테스트용)


        ArrayList<CompanionAdSlot> companionAdSlots = new ArrayList<>();
//        companionAdSlots.add(companionAdSlot);
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        AdDisplayContainer adDisplayContainer = ImaSdkFactory.createAdDisplayContainer(videoPlayerContainer, new VideoAdPlayerAdapter(videoPlayer, audioManager));
//        adDisplayContainer.setCompanionSlots(companionAdSlots);

        adsLoader = imaSdkFactory.createAdsLoader(this, settings, adDisplayContainer);
        adsLoader.addAdsLoadedListener(this);

        AdsRequest request = imaSdkFactory.createAdsRequest();
        request.setAdsResponse(vastString);
        request.setContentProgressProvider(() -> null);
        Log.d("jhlee", "initVideoLoader time3 : " + (System.currentTimeMillis() - time));
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
        Log.d("jhlee", "onAdsManagerLoaded time1 : " + (System.currentTimeMillis() - time));
        adsManager = adsManagerLoadedEvent.getAdsManager();

//        AdsRenderingSettings renderingSettings = ImaSdkFactory.getInstance().createAdsRenderingSettings();
//        renderingSettings.setEnablePreloading(true);
        adsManager.addAdEventListener(adEvent -> {

            Log.d("jhlee", "onAdsManagerLoaded adEvent : " + (System.currentTimeMillis() - time) + "-" +adEvent.getType().name());

            switch (adEvent.getType()) {
                case CONTENT_PAUSE_REQUESTED:{
                    Log.d("jhlee", "onAdsManagerLoaded CONTENT_PAUSE_REQUESTED : " + (System.currentTimeMillis() - time));
                    adsManager.resume();
                    videoPlayer.start();
                    break;
                }
                case RESUMED: {
                    Log.d("jhlee", "onAdsManagerLoaded RESUMED : " + (System.currentTimeMillis() - time));
                    break;
                }
                case STARTED: {
                    Log.d("jhlee", "onAdsManagerLoaded STARTED : " + (System.currentTimeMillis() - time));
                    break;
                }
                case AD_BUFFERING: {
                    Log.d("jhlee", "onAdsManagerLoaded AD_BUFFERING : " + (System.currentTimeMillis() - time));
                    break;
                }
                case CONTENT_RESUME_REQUESTED: {
                    Log.d("jhlee", "onAdsManagerLoaded CONTENT_RESUME_REQUESTED : " + (System.currentTimeMillis() - time));
                    break;
                }
                case AD_PROGRESS: {
                    Log.d("jhlee", "onAdsManagerLoaded AD_PROGRESS : " + (System.currentTimeMillis() - time) + "-" + videoPlayer.getCurrentPosition());
                    if (4000 < videoPlayer.getCurrentPosition()) {
                        closeBtn.setVisibility(View.VISIBLE);
                    }
                    break;
                }
                case LOADED: {
                    Log.d("jhlee", "onAdsManagerLoaded LOADED 1: " + (System.currentTimeMillis() - time));
                    isAdPlaying = true;
                    clickUrl = getClickUrl(adsManager.getCurrentAd());
                    adsManager.start();
                    videoPlayer.start();
                    Log.d("jhlee", "onAdsManagerLoaded LOADED 2: " + (System.currentTimeMillis() - time));
                    break;
                }
                case ALL_ADS_COMPLETED: {
//                    findViewById(R.id.companionAdSlot).setVisibility(View.VISIBLE);
                    Log.d("jhlee", "ALL_ADS_COMPLETED");
                    videoPlayer.setOnClickListener(v -> {
                        goClickUrl();
                    });
                    isEnded = true;
                    break;
                }
                case COMPLETED: {
                    Log.d("jhlee", "COMPLETED");
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
