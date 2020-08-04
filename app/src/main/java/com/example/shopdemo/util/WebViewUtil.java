package com.example.shopdemo.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.shopdemo.views.LoadingDialog;

/**
 * webview设置  工具类
 */
public class WebViewUtil {

    @SuppressLint("SetJavaScriptEnabled")
    public static void setWebSetting(WebView webView, String url, Context context, LinearLayout zanwuWangluo, Button chongxinJiazai,LoadingDialog loadD){
        WebSettings webSettings = webView.getSettings();
        webView.requestFocusFromTouch();
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小

        webSettings.setSupportZoom(true);//支持缩放，默认为true。是下面那个的前提
        webSettings.setBuiltInZoomControls(true);//设置内置的缩放控件
        //设为true会有泄露错误
        webSettings.setDisplayZoomControls(false);//隐藏原生的缩放控件
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不适用缓存


        webSettings.setAllowFileAccess(true);//设置可以访问文件
//        webSettings.setNeedInitialFocus(true);//当webview调用requestFocus时为webview设置节点
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true);//支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webView.addJavascriptInterface(new JavaScriptInterface(context), "ncp");

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                loadD.setMessage("正在加载中");
//                loadD.show();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //在这里让加载动画结束
                if (null != loadD) {
                    loadD.dismiss();
                }
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                webView.setVisibility(View.GONE);
                zanwuWangluo.setVisibility(View.VISIBLE);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
        chongxinJiazai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl(url);
                webView.setVisibility(View.VISIBLE);
                zanwuWangluo.setVisibility(View.GONE);
            }
        });
         webView.loadUrl(url);
    }
}
