package com.ham.ad_adapter_test;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.VersionInfo;
import com.google.android.gms.ads.mediation.Adapter;
import com.google.android.gms.ads.mediation.InitializationCompleteCallback;
import com.google.android.gms.ads.mediation.MediationConfiguration;

import java.util.List;

public class Test2Adapter extends Adapter {


    @NonNull
    @Override
    public VersionInfo getSDKVersionInfo() {
        Log.d("jhlee", "Test2Adapter getSDKVersionInfo()");
        return null;
    }

    @NonNull
    @Override
    public VersionInfo getVersionInfo() {
        Log.d("jhlee", "Test2Adapter getVersionInfo()");
        return null;
    }

    @Override
    public void initialize(@NonNull Context context, @NonNull InitializationCompleteCallback initializationCompleteCallback, @NonNull List<MediationConfiguration> list) {
        Log.d("jhlee", "Test2Adapter initialize()");
        initializationCompleteCallback.onInitializationSucceeded();
    }
}
