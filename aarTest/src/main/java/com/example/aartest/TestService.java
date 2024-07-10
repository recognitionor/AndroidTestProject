package com.example.aartest;

import android.util.Log;

public class TestService {

    public String testVal1;

    protected String testVal2;

    private String testVal3;

    String testVal4;

    String getTestStr() {
        String test = null;
        Log.d("jhlee", test);
        return test;
    }

    String testCall() {
        return "test";
    }
}
