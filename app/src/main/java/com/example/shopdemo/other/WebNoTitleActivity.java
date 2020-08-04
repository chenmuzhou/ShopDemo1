package com.example.shopdemo.other;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.shopdemo.R;
import com.example.shopdemo.base.BaseActivity;
import com.example.shopdemo.base.BaseNewActivity;
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

public class WebNoTitleActivity extends BaseActivity {

    @BindView(R.id.linear_bar)
    LinearLayout linearBar;

    @BindView(R.id.chongxin_jiazai)
    Button chongxinJiazai;
    @BindView(R.id.zanwu_wangluo)
    LinearLayout zanwuWangluo;
    @BindView(R.id.wangluo_title)
    TextView wangluoTitle;
    @BindView(R.id.web_view)
    WebView webView;
    private WebView wb;
    private String url = "";
    private LoadingDialog ld;
    private LodaWindow lod;
    private static final int LOAD = 0x123;
    private boolean isload = true;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_page);
        ButterKnife.bind(this);
      //  setZhuangTaiLan();
        url = getIntent().getStringExtra("weburl");
//        if (url != null) {
//            if (url.indexOf("?") > 0) {
//                url = url + "&isapp=1";
//            } else {
//                url = url + "?isapp=1";
//            }
//        }

        wb = findViewById(R.id.web_view);
        wb.requestFocusFromTouch();//支持获取手势焦点，输入用户名、密码或其他
        setwebviewsetting();
        AndroidBug5497Workaround.assistActivity(this,0);

    }

    /**
     * 设置webview
     */
    private void setwebviewsetting(){
        WebSettings webSettings = wb.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小

        webSettings.setSupportZoom(true);//支持缩放，默认为true。是下面那个的前提
        webSettings.setBuiltInZoomControls(true);//设置内置的缩放控件
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//不适用缓存

        //设为true会有泄露错误
        webSettings.setDisplayZoomControls(false);//隐藏原生的缩放控件

        webSettings.setAllowFileAccess(true);//设置可以访问文件
        webSettings.setLoadsImagesAutomatically(true);//支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        /**
         * “ncp”是js调用的  “callOnJs”webView上跳转回Activity的方法
         * “ ncp.callOnJs("case", t + "," + itemid + "," + cyid);”  js上调用跳转的例句
         * Android容器设置桥连对象
         */
        wb.addJavascriptInterface(new JavaScriptInterface(this), "ncp"); ///  ncp.callOnJs("case", t + "," + itemid + "," + cyid);


        wb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                LhtTool.setCookieManager(url);
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                if (view != null) {
                    view.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
                }
                if (url.contains("tel:")) {

                    if (ActivityCompat.checkSelfPermission(WebNoTitleActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        return true;
                    }
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
                    startActivity(intent);
                    //这个超连接,java已经处理了，webview不要处理了
                    return true;
                }

//                if (getIntent().getStringExtra("title").equals("商标注册服务") || getIntent().getStringExtra("title").equals("版权登记服务")) {
//                    Log.e("notitle",url+"");
//                    view.loadUrl(url + "?dover=2");
//                }

                return true;
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //  if (isload) {
                handler.sendEmptyMessage(LOAD);
                // webTimeOut();
                //  }
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                if (null != lod) {
                    lod.dis();
                    lod = null;
                }
//                isload = false;
//                timer.cancel();
//                timer.purge();
                super.onPageCommitVisible(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                wb.setVisibility(View.GONE);
                zanwuWangluo.setVisibility(View.VISIBLE);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });


        wb.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });

        chongxinJiazai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // LhtTool.setCookieManager(url);
                wb.loadUrl(url);
                wb.setVisibility(View.VISIBLE);
                zanwuWangluo.setVisibility(View.GONE);
            }
        });
        //  LhtTool.setCookieManager(url);
        wb.loadUrl(url);
    }

    /**
     * 网页访问判断超时问题
     */
    private static final int LOADTIMEOUT = 0x101;
    private Timer timer;//计时器
    private long timeout = 5000;//超时时间

    private void webTimeOut() {
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (wb.canGoBack()) {
                wb.goBack();
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == 1) {
            UseTool.setCookieManager(url);
            wb.loadUrl(url);
        } else if (resultCode == 2) {
            wb.reload();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 设置状态栏
     */
    private LinearLayout linear_bar;

    private void setZhuangTaiLan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏0
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            linear_bar = findViewById(R.id.linear_bar);
            linear_bar.setVisibility(View.VISIBLE);
            linear_bar.setBackgroundColor(Color.parseColor("#ffffff"));
            int statusHeight = UseTool.getStatusBarHeight(this);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linear_bar.getLayoutParams();
            params.height = statusHeight;
            linear_bar.setLayoutParams(params);
        }


        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != lod) {
            lod.dis();
        }
        if (webView != null) {
            // 要首先移除

            // 清理缓存
            webView.stopLoading();
            webView.onPause();
            webView.clearHistory();
            webView.clearCache(true);
            webView.clearFormData();
            webView.clearSslPreferences();
            WebStorage.getInstance().deleteAllData();
            webView.destroyDrawingCache();
            webView.removeAllViews();

            // 最后再去webView.destroy();
            webView.destroy();
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
                        lod = new LodaWindow(WebNoTitleActivity.this, WebNoTitleActivity.this);
                        lod.setMessage("加载中....");
                    }
                    break;
                case LOADTIMEOUT:
                    if (lod != null) {
                        lod.dis();
                    }
                    Log.e("webload", "chaoshi222");
                    wb.setVisibility(View.GONE);
                    zanwuWangluo.setVisibility(View.VISIBLE);
                    wangluoTitle.setText("请求超时，请检查网络!");
                    break;
            }
        }
    };

}
