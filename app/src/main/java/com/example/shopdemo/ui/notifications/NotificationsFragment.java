package com.example.shopdemo.ui.notifications;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shopdemo.R;
import com.example.shopdemo.util.UrlUtil;
import com.example.shopdemo.util.WebViewUtil;
import com.example.shopdemo.views.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NotificationsFragment extends Fragment {


    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.chongxin_jiazai)
    Button chongxinJiazai;
    @BindView(R.id.zanwu_wangluo)
    LinearLayout zanwuWangluo;
    private Unbinder unbinder;
    private View view;
    private Context mContext;
    private LoadingDialog loadingDialog;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.web_view_page, container, false);
        mContext = getContext();
        unbinder = ButterKnife.bind(this, view);
        initview();
        return view;
    }

    private void initview() {
        loadingDialog = new LoadingDialog(mContext);
        WebViewUtil.setWebSetting(webView, UrlUtil.testUrl,mContext,zanwuWangluo,chongxinJiazai,loadingDialog);

    }
}