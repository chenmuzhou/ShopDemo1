package com.example.shopdemo.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

/**
 * 网页回调的方法
 */
public class JavaScriptInterface {
    private Context context;
    String TAG = "JavaScriptInterface";
    private Intent intent;

    public JavaScriptInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void callOnJs(String type,String s){
        String[] strings = s.split(",");
        switch (type){

        }
    }
}
