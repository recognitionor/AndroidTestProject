package com.jhlee.webviewtest;

public class MRAIDUtils {
    static boolean isMRAIDUrl(String url) {
        return url.startsWith("mraid://");
    }
}
