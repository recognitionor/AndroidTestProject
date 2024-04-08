package com.nhnace.imasdktest;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.google.ads.interactivemedia.v3.api.AdDisplayContainer;
import com.google.ads.interactivemedia.v3.api.AdEvent;
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
import java.util.stream.Collectors;

public class VideoAdsActivity extends Activity implements View.OnClickListener {

    static long LOAD_TIME;
    static long SHOW_TIME;

    private VideoView videoPlayer;
    private ViewGroup videoPlayerContainer;
    AudioManager audioManager;
    AdsManagerLoadedEvent adsManagerLoadedEvent;
    AdsManager adsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_test);
        findViewById(R.id.test_load_btn).setOnClickListener(this);
        findViewById(R.id.test_load2_btn).setOnClickListener(this);
        findViewById(R.id.test_load3_btn).setOnClickListener(this);

        videoPlayer = findViewById(R.id.videoView);
        videoPlayerContainer = findViewById(R.id.videoPlayerContainer);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_load_btn:
                LOAD_TIME = System.currentTimeMillis();

                String vastString = "";
                try {
                    vastString = new BufferedReader(new InputStreamReader(this.getAssets().open("vast.xml"))).lines().collect(Collectors.joining("\n"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("jhlee", "url : " + vastString);
                ImaSdkFactory imaSdkFactory = ImaSdkFactory.getInstance();
                ImaSdkSettings settings = imaSdkFactory.createImaSdkSettings();
                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

                VideoAdPlayerAdapter videoAdPlayerAdapter = new VideoAdPlayerAdapter(videoPlayer, audioManager);
                AdDisplayContainer adDisplayContainer = ImaSdkFactory.createAdDisplayContainer(videoPlayerContainer, videoAdPlayerAdapter);
                AdsLoader adsLoader = ImaSdkFactory.getInstance().createAdsLoader(this, settings, adDisplayContainer);
                adsLoader.addAdsLoadedListener(adsManagerLoadedEvent -> {
                    adsManager = adsManagerLoadedEvent.getAdsManager();
                    AdsRenderingSettings renderingSettings = ImaSdkFactory.getInstance().createAdsRenderingSettings();
                    adsManager.addAdEventListener(new AdEvent.AdEventListener() {
                        @Override
                        public void onAdEvent(AdEvent adEvent) {
                            if (adEvent.getType() == AdEvent.AdEventType.STARTED) {
                                Log.d("jhlee", "STARTED");
                                videoPlayerContainer.setVisibility(View.GONE);
                                videoPlayer.pause();
                            }

                            if (adEvent.getType() != AdEvent.AdEventType.AD_PROGRESS) {
                                Log.d("jhlee", "event : " + adEvent.getType() + " : " + (System.currentTimeMillis() - LOAD_TIME) + "- " + (System.currentTimeMillis() - SHOW_TIME));
                            }
                        }
                    });

                    adsManager.init(renderingSettings);
                    adsManager.start();
                });
                AdsRequest request = imaSdkFactory.createAdsRequest();
                request.setAdsResponse(vastString);
                adsLoader.requestAds(request);
//                videoPlayerContainer.setVisibility(View.GONE);
                break;

            case R.id.test_load2_btn:
                SHOW_TIME = System.currentTimeMillis();
                videoPlayer.pause();
                Log.d("jhlee", "SHOW");
                break;

            case R.id.test_load3_btn:
                Log.d("jhlee", "3");
                videoPlayerContainer.setVisibility(View.VISIBLE);
                videoPlayer.start();
                break;
        }
    }
}
