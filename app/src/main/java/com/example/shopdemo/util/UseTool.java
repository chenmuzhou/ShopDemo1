package com.example.shopdemo.util;

import android.content.Context;
import android.content.Intent;
import android.webkit.CookieManager;

import com.example.shopdemo.other.WebNoTitleActivity;

import java.lang.reflect.Field;

/**
 * 一些使用的工具类
 */
public class UseTool {

    /**
     * 通过反射得到标题栏高度
     */

    public static int getStatusBarHeight(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 设置webview 的cookie
     * @param url
     */
    public static void setCookieManager(String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        StringBuffer sb = new StringBuffer();
    }

    public static void intentPage(Context context){
        Intent intent = new Intent(context, WebNoTitleActivity.class);
        intent.putExtra("weburl",UrlUtil.testUrl);
        context.startActivity(intent);

    }
}
