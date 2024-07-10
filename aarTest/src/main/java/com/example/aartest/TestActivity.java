package com.example.aartest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class TestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        findViewById(R.id.kill_btn).setOnClickListener(v -> {
            TestService ts = new TestService();
            String test = ts.getTestStr();
            String test2 = ts.testCall();
            test.toString();
            Log.d("jhlee", "test2: " + test2);
        });
    }
}
