package com.jhlee.webviewtest;

import android.content.Context;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import org.json.JSONException;
import org.json.JSONObject;

public class MRAIDImplementation {

    private final WebView webView;

    public MRAIDImplementation(WebView webView) {
        this.webView = webView;
    }

    // Mraid ENV 초기화
    void fireInitMraidEnv(Context ctx) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("version", "1.0.3");
            obj.put("sdk", "NHNACE_ADLIB");
            obj.put("sdkVersion", "1.0.3");
            obj.put("appId", ctx.getPackageName());
            obj.put("adid", "1111");
            obj.put("limitedAdTracking", true);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        webView.evaluateJavascript(String.format("javascript:window.mraid.initMraidEnv('%s')", obj), (ValueCallback) value -> Log.d("jhlee", "onReceiveValue : " + value));
    }
}
