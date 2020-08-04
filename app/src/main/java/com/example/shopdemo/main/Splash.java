package com.example.shopdemo.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


import androidx.annotation.Nullable;

import com.example.shopdemo.MainActivity;
import com.example.shopdemo.R;
import com.example.shopdemo.base.BaseActivity;
import com.example.shopdemo.other.WebNoTitleActivity;
import com.example.shopdemo.util.UseTool;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 *
 * 初始化图片页面
 */

public class Splash extends BaseActivity {

    private final long SPLASH_DELAY_MILLONS = 3000;
    private final int TO_GUIDE = 0X111;
    private final int TO_MAIN = 0X123;
    private SharedPreferences sp;
    private Intent in;
    private Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        //实现全屏沉浸式
//        if (Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }

        mContext = this;
        sp = getSharedPreferences("splash", MODE_PRIVATE);
        if (sp.getBoolean("FirstIn", true)) {
            hd.sendEmptyMessageDelayed(TO_GUIDE,SPLASH_DELAY_MILLONS);
            SharedPreferences.Editor et = sp.edit();
            et.putBoolean("FirstIn", false);
            et.commit();
        } else {
            hd.sendEmptyMessageDelayed(TO_MAIN,SPLASH_DELAY_MILLONS);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler hd=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TO_GUIDE:
                case TO_MAIN:
                    UseTool.intentPage(mContext);
                    break;
            }
            if (in != null) {
                startActivity(in);
                finish();
            }
        }
    };
}
