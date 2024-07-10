/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.os.Bundle
 *  android.util.Log
 *  com.example.aartest.R$id
 *  com.example.aartest.R$layout
 */
package com.example.newaar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.example.newaar.R;

public class TestActivity
extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.test_layout);
        this.findViewById(R.id.kill_btn).setOnClickListener(v -> {
            TestService ts = new TestService();
            String test = ts.getTestStr();
            String test2 = ts.testCall();
            test.toString();
            Log.d((String)"jhlee", (String)("test2: " + test2));
        });
    }
}
