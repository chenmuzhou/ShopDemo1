package com.example.shopdemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.shopdemo.base.ActivityController;
import com.example.shopdemo.base.BaseActivity;
import com.example.shopdemo.frag.index.IndexPageFragment;
import com.example.shopdemo.frag.person.PersonFragment;
import com.example.shopdemo.ui.home.HomeFragment;
import com.example.shopdemo.util.UseTool;
import com.example.shopdemo.views.NoScrollViewPager;
import com.hjq.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainNewActivity extends BaseActivity {

    @BindView(R.id.linear_bar)
    LinearLayout linearBar;
    @BindView(R.id.main_vp)
    NoScrollViewPager mainVp;
    @BindView(R.id.index_rb)
    RadioButton indexRb;
    @BindView(R.id.fenlei_rb)
    RadioButton fenleiRb;
    @BindView(R.id.kefu_rb)
    RadioButton kefuRb;
    @BindView(R.id.gouwu_car_rb)
    RadioButton gouwuCarRb;
    @BindView(R.id.wode_rb)
    RadioButton wodeRb;
    @BindView(R.id.index_rg)
    RadioGroup indexRg;
    @BindView(R.id.tv_yuandian)
    TextView tvYuandian;
    @BindView(R.id.main_ll)
    LinearLayout mainLl;
    private Context mContext;
    private List<Fragment> fraList = new ArrayList<>();
    private DisplayMetrics metrics = new DisplayMetrics();
    private long exitTime = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        setContentView(R.layout.activity_new_main);
        ButterKnife.bind(this);
        mContext = this;
       // setZhuangTaiLan();
        fragmentList();
        setview();
        viewListener();
    }

    private void setview(){
        mainVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fraList.get(position);
            }

            @Override
            public int getCount() {
                return fraList.size();
            }
        });
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(tvYuandian.getLayoutParams());
        params.setMargins(metrics.widthPixels / 8 * 5 + 15, 0, 0, 0);
        tvYuandian.setLayoutParams(params);

        if (null != getIntent().getExtras() && getIntent().getExtras().equals("3")) {
            mainVp.setCurrentItem(2);
        }
    }

    /**
     * 主页fragment
     */
    private void fragmentList(){
        fraList.add(new IndexPageFragment() );
        fraList.add(new HomeFragment());
        fraList.add(new HomeFragment());
        fraList.add(new HomeFragment());
        fraList.add(new PersonFragment());
    }

    /**
     * view监听
     */
    private void viewListener(){
        mainVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    RadioButton rb = (RadioButton) indexRg.getChildAt(position);
                    rb.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        indexRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                int x = 0;
                switch (checkedId) {

                    case R.id.index_rb:
                        x = 0;
                        break;
                    case R.id.fenlei_rb:
                        x = 1;
                        break;
                    case R.id.kefu_rb:
                        x = 2;
                        break;
                    case R.id.gouwu_car_rb:
                        x = 3;
                        break;
                    case R.id.wode_rb:
                        x = 4;
                        break;

                }
                mainVp.setCurrentItem(x);
                if (x == 3) {
                    // linear_bar.setBackgroundColor(Color.parseColor("#D27AA0"));
                    if (linearBar != null) {
                        linearBar.setVisibility(View.GONE);
                    }
                }else if (x == 2){
                    if (linearBar != null) {
                        linearBar.setVisibility(View.GONE);
                    }
                }
                else {
                    if (linearBar != null) {
                        linearBar.setVisibility(View.GONE);

                    }
                }

            }
        });


    }
    /**
     * 设置状态栏
     */
    private void setZhuangTaiLan(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏0
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            linearBar =  findViewById(R.id.linear_bar);
            linearBar.setVisibility(View.GONE);
            linearBar.setBackgroundColor(Color.parseColor("#ffffff"));
            int statusHeight = UseTool.getStatusBarHeight(this);
            android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) linearBar.getLayoutParams();
            params.height = statusHeight;
            linearBar.setLayoutParams(params);
        }


        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //设置导航栏为透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
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
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 102;
            String[] permissions = {Manifest.permission.READ_PHONE_STATE};
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.show("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                ActivityController.finishAll();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
