package com.example.shopdemo.frag.index;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;


import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.shopdemo.MainActivity;
import com.example.shopdemo.R;
import com.example.shopdemo.other.WebNoTitleActivity;
import com.example.shopdemo.util.AndroidBug5497Workaround;
import com.example.shopdemo.util.JavaScriptInterface;
import com.example.shopdemo.util.UseTool;
import com.example.shopdemo.views.LoadingDialog;
import com.example.shopdemo.views.LodaWindow;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */

public class IndexPageFragment extends Fragment {


    @BindView(R.id.faxian_web)
    WebView faxianWeb;
    @BindView(R.id.chongxin_jiazai)
    Button chongxinJiazai;
    @BindView(R.id.zanwu_wangluo)
    LinearLayout zanwuWangluo;
    private Context context;
    private WebSettings wSet;
    private String FAXIAN = "";
    private LodaWindow lod;
    private static final int LOAD = 0x123;
    private boolean isload = true;
    @SuppressLint("JavascriptInterface")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("webload", "加载完成chushihua");
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(context).inflate(R.layout.faxian_fragment_page, null);
        ButterKnife.bind(this, v);

        wSet = faxianWeb.getSettings();

            FAXIAN = "https://shop.mkrj.cn/app/index.php?i=4&c=entry&m=ewei_shopv2&do=mobile&r=user.suite";
          //  FAXIAN = "https://app.680.com/faxian/?isandrod=1&uid=0";
        setwebFaxian();
       // AndroidBug5497Workaround.assistActivity(getActivity());

        return v;
    }

    /**
     * webview设置
     */
    private void setwebFaxian() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wSet.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        wSet.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        wSet.setUseWideViewPort(true);//将图片调整到适合webview的大小
        wSet.setLoadWithOverviewMode(true);// 缩放至屏幕的大小

        wSet.setSupportZoom(true);//支持缩放，默认为true。是下面那个的前提
        wSet.setBuiltInZoomControls(true);//设置内置的缩放控件
        //设为true会有泄露错误
        wSet.setDisplayZoomControls(false);//隐藏原生的缩放控件
        wSet.setCacheMode(WebSettings.LOAD_NO_CACHE);//不适用缓存


        wSet.setAllowFileAccess(true);//设置可以访问文件
//        webSettings.setNeedInitialFocus(true);//当webview调用requestFocus时为webview设置节点
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        wSet.setLoadsImagesAutomatically(true);//支持自动加载图片
        wSet.setDefaultTextEncodingName("utf-8");//设置编码格式
        faxianWeb.addJavascriptInterface(new JavaScriptInterface(getActivity()), "ncp"); ///  ncp.callOnJs("case", t + "," + itemid + "," + cyid);
        faxianWeb.setWebViewClient(new WebViewClient() {


            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
              //  super.onReceivedSslError(view, handler, error);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {


//                UseTool.setCookieManager(url);
//                if (url.startsWith("http:") || url.startsWith("https:")) {
//                    return false;
//                }
//                view.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//                if (url.contains("tel:")) {
//
//                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//
//                        return true;
//                    }
//                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
//                    startActivity(intent);
//                    //这个超连接,java已经处理了，webview不要处理了
//                    return true;
//                }


                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    handler.sendEmptyMessage(LOAD);
                Log.e("webload", "开始加载");
                   webTimeOut();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                if (null != lod) {
                    lod.dis();
                    lod =null;
                }
                isload = false;
                if (timer != null) {
                    timer.cancel();
                    timer.purge();
                }
                Log.e("webload", "加载完成");
                super.onPageCommitVisible(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //在这里让加载动画结束
                if (null != lod) {
                    lod.dis();
                    lod = null;
                }
                Log.e("webload", "加载完成...");
                if (timer != null) {
                    timer.cancel();
                    timer.purge();
                }
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                faxianWeb.setVisibility(View.GONE);
                zanwuWangluo.setVisibility(View.VISIBLE);
                Log.e("webload", "加载完成sdasda");
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
        chongxinJiazai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                faxianWeb.loadUrl(FAXIAN);
                faxianWeb.setVisibility(View.VISIBLE);
                zanwuWangluo.setVisibility(View.GONE);
            }
        });
        faxianWeb.loadUrl(FAXIAN);
    }

    /**
     * 网页访问判断超时问题
     */
    private static final int LOADTIMEOUT = 0x101;
    private Timer timer;//计时器
    private long timeout = 5000;//超时时间

    private void webTimeOut() {
        if (timer == null) {
            timer = new Timer();
            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    /* * 超时后,首先判断页面加载是否小于100,就执行超时后的动作 */
                    Log.e("webload", "chaoshi");
                    handler.sendEmptyMessage(LOADTIMEOUT);
                }
            };
            timer.schedule(tt, timeout, timeout);
        }
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOAD:
                    if (lod == null) {
                        lod = new LodaWindow(context, getActivity());
                        Log.e("webload", "chaoshi222");
                        lod.setMessage("加载中....");
                    }
                    break;
                case LOADTIMEOUT:
                    if (null != lod) {
                        lod.dis();
                    }
                    timer.cancel();
                    timer.purge();
                    Log.e("webload", "chaoshi222");
                    Log.e("webload", "chaoshi11");
                    faxianWeb.setVisibility(View.GONE);
                    zanwuWangluo.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (lod != null) {
            lod.dismiss();
        }
    }
}
