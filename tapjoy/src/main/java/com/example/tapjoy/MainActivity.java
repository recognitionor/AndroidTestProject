package com.example.tapjoy;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tapjoy.TJActionRequest;
import com.tapjoy.TJConnectListener;
import com.tapjoy.TJEarnedCurrencyListener;
import com.tapjoy.TJError;
import com.tapjoy.TJPlacement;
import com.tapjoy.TJPlacementListener;
import com.tapjoy.TJPlacementVideoListener;
import com.tapjoy.Tapjoy;
import com.tapjoy.TapjoyConnectFlag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.util.Hashtable;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);
        // OPTIONAL: For custom startup flags.
        Hashtable<String, Object> connectFlags = new Hashtable<String, Object>();
        connectFlags.put(TapjoyConnectFlag.ENABLE_LOGGING, "true");

        // If you are not using Tapjoy Managed currency, you would set your own user ID here.
        //	connectFlags.put(TapjoyConnectFlag.USER_ID, "A_UNIQUE_USER_ID");

        // Connect with the Tapjoy server.  Call this when the application first starts.
        // REPLACE THE SDK KEY WITH YOUR TAPJOY SDK Key.
        String tapjoySDKKey = "01iUt9nmR2qqaBNXoOTUWAEC1zdDHEbrnpa6rG3hjM4eNaoFZVP5WMyTs6aI";
        Tapjoy.connect(this, tapjoySDKKey, connectFlags, new TJConnectListener() {
            @Override
            public void onConnectSuccess() {
                Log.d("jhlee", "onConnectSuccess");
                TJPlacement directPlayPlacement = Tapjoy.getPlacement("DEV_TEST", new TJPlacementListener() {
                    @Override
                    public void onRequestSuccess(TJPlacement tjPlacement) {
                        Log.d("jhlee", "onRequestSuccess tjPlacement.isContentAvailable() : " +tjPlacement.isContentAvailable());
                        Log.d("jhlee", "onRequestSuccess tjPlacement.isContentReady() : " +tjPlacement.isContentReady());
                    }

                    @Override
                    public void onRequestFailure(TJPlacement tjPlacement, TJError tjError) {
                        Log.d("jhlee", "onRequestFailure");
                    }

                    @Override
                    public void onContentReady(TJPlacement tjPlacement) {
                        Log.d("jhlee", "onContentReady");
                        tjPlacement.showContent();
                    }

                    @Override
                    public void onContentShow(TJPlacement tjPlacement) {
                        Log.d("jhlee", "onContentShow");
                    }

                    @Override
                    public void onContentDismiss(TJPlacement tjPlacement) {
                        Log.d("jhlee", "onContentDismiss");
                    }

                    @Override
                    public void onPurchaseRequest(TJPlacement tjPlacement, TJActionRequest tjActionRequest, String s) {
                        Log.d("jhlee", "onPurchaseRequest");
                    }

                    @Override
                    public void onRewardRequest(TJPlacement tjPlacement, TJActionRequest tjActionRequest, String s, int i) {
                        Log.d("jhlee", "onRewardRequest");
                    }

                    @Override
                    public void onClick(TJPlacement tjPlacement) {
                        Log.d("jhlee", "onClick");
                    }
                });

                directPlayPlacement.setVideoListener(new TJPlacementVideoListener() {
                    @Override
                    public void onVideoStart(TJPlacement tjPlacement) {
                        Log.d("jhlee", "onVideoStart");
                    }

                    @Override
                    public void onVideoError(TJPlacement tjPlacement, String s) {
                        Log.d("jhlee", "onVideoError");
                    }

                    @Override
                    public void onVideoComplete(TJPlacement tjPlacement) {
                        Log.d("jhlee", "onVideoComplete");
                    }
                });
                directPlayPlacement.requestContent();
                Tapjoy.setEarnedCurrencyListener((currencyName, amount) -> Log.d("jhlee", "onEarnedCurrency"));
            }

            @Override
            public void onConnectFailure() {
                Log.d("jhlee", "onConnectFailure");
            }
        });
    }
}