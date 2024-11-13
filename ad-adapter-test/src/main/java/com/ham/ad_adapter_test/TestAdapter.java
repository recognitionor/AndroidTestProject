package com.ham.ad_adapter_test;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.VersionInfo;
import com.google.android.gms.ads.mediation.Adapter;
import com.google.android.gms.ads.mediation.InitializationCompleteCallback;
import com.google.android.gms.ads.mediation.MediationAdLoadCallback;
import com.google.android.gms.ads.mediation.MediationBannerAd;
import com.google.android.gms.ads.mediation.MediationBannerAdCallback;
import com.google.android.gms.ads.mediation.MediationBannerAdConfiguration;
import com.google.android.gms.ads.mediation.MediationConfiguration;

import java.util.List;

public class TestAdapter extends Adapter {


    @NonNull
    @Override
    public VersionInfo getSDKVersionInfo() {
        Log.d("jhlee", "TestAdapter getSDKVersionInfo()");
        return null;
    }

    @NonNull
    @Override
    public VersionInfo getVersionInfo() {
        Log.d("jhlee", "TestAdapter getVersionInfo()");
        return null;
    }

    @Override
    public void initialize(@NonNull Context context, @NonNull InitializationCompleteCallback initializationCompleteCallback, @NonNull List<MediationConfiguration> list) {
        Log.d("jhlee", "TestAdapter initialize()");
        initializationCompleteCallback.onInitializationSucceeded();
    }

    @Override
    public void loadBannerAd(@NonNull MediationBannerAdConfiguration mediationBannerAdConfiguration, @NonNull MediationAdLoadCallback<MediationBannerAd, MediationBannerAdCallback> callback) {
        super.loadBannerAd(mediationBannerAdConfiguration, callback);
        Log.d("jhlee", "TestAdapter loadBannerAd()");
    }
}
