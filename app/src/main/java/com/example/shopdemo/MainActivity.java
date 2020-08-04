package com.example.shopdemo;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.shopdemo.base.ActivityController;
import com.example.shopdemo.base.BaseActivity;
import com.example.shopdemo.frag.index.IndexPageFragment;
import com.example.shopdemo.frag.person.PersonFragment;
import com.example.shopdemo.ui.home.HomeFragment;
import com.example.shopdemo.util.AndroidBug5497Workaround;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.hjq.toast.style.ToastWhiteStyle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import qiu.niorgai.StatusBarCompat;

public class MainActivity extends BaseActivity {

    @BindView(R.id.nav_view)
    BottomNavigationView navView;
    @BindView(R.id.container)
    RelativeLayout container;
    private List<Fragment> fragmentList;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //设置状态栏透明
        //StatusBarCompat.translucentStatusBar(this);

        // StatusBarCompat.cancelLightStatusBar(this);

//        AndroidBug5497Workaround.assistActivity(this);

        //初始化吐司工具类
        ToastUtils.init(getApplication(), new ToastWhiteStyle(getApplicationContext()));
        requestPermission();
        init();
    }


    private void init() {
        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        navView.measure(w,h);
        int height = navView.getMeasuredHeight();
        int wight = navView.getMeasuredWidth();
       AndroidBug5497Workaround.assistActivity(this,height);

        fragmentList = new ArrayList<>();
        fragmentList.add(new IndexPageFragment() );
        fragmentList.add(new HomeFragment());
        fragmentList.add(new HomeFragment());
        fragmentList.add(new HomeFragment());
        fragmentList.add(new PersonFragment());
        int position = 0;
        switchFragment(position);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tab_home_item:
                        switchFragment(0);
                        break;
                    case R.id.tab_fenlei_item:
                        switchFragment(1);
                        break;
                    case R.id.tab_notifications_item:
                        switchFragment(2);
                        break;
                    case R.id.tab_car_item:
                        switchFragment(3);
                        break;
                    case R.id.tab_person_item:
                        switchFragment(4);
                        break;
                }
                return true;
            }
        });
    }



    private void switchFragment(int position){
        FragmentManager fragmentManager =  getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container,fragmentList.get(position));
        transaction.commit();
    }
    /**
     * 获取权限
     */
    public void requestPermission() {
        XXPermissions.with(this)
                // 可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                //.constantRequest()
                // 支持请求6.0悬浮窗权限8.0请求安装权限
                //.permission(Permission.REQUEST_INSTALL_PACKAGES)
                // 不指定权限则自动获取清单中的危险权限
                .permission(Permission.Group.STORAGE, Permission.Group.CALENDAR)
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean all) {
                        if (all) {
                            //ToastUtils.show("获取权限成功");
                        } else {
                            ToastUtils.show("获取权限成功，部分权限未正常授予");
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            ToastUtils.show("被永久拒绝授权，请手动授予权限");
                            //如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(MainActivity.this);
                        } else {
                            ToastUtils.show("获取权限失败");
                        }
                    }
                });
    }

    public void hasPermission(View view) {
        if (XXPermissions.hasPermission(MainActivity.this, Permission.Group.STORAGE)) {
            ToastUtils.show("已经获取到权限，不需要再次申请了");
        } else {
            ToastUtils.show("还没有获取到权限或者部分权限未授予");
        }
    }

    public void startPermissionPage(View view) {
        XXPermissions.startPermissionActivity(MainActivity.this);
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