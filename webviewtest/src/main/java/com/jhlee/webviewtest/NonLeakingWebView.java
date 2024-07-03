package com.jhlee.webviewtest;

import android.content.Context;
import android.os.Build;
import android.webkit.WebView;

import java.lang.reflect.Field;

public class NonLeakingWebView extends WebView {
    private static Field sConfigCallback;

    static {
        try {
            sConfigCallback = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
            sConfigCallback.setAccessible(true);
        } catch (Exception e) {
            // ignored
        }

    }

    public NonLeakingWebView(Context context) {
        super(context.getApplicationContext());
        setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setWebContentsDebuggingEnabled(true);
        }
    }

    @Override
    public void destroy() {
        super.destroy();

        try {
            if( sConfigCallback!=null )
                sConfigCallback.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
