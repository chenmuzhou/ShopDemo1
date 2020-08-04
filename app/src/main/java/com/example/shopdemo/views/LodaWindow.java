package com.example.shopdemo.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.shopdemo.R;


/**
 *
 * 加载中弹窗
 */

public class LodaWindow extends PopupWindow{
    private View viewShow;
    private PopupWindow popupWindow;
    private RelativeLayout loadLayout;
    public LodaWindow(Context context, final Activity activity){
        viewShow = LayoutInflater.from(context).inflate(R.layout.loading, null);
        popupWindow = new PopupWindow(viewShow, LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ffffff")));//也可以直接把Color.TRANSPARENT换成0
       // popupWindow.setAnimationStyle(R.style.popWindow_animation);
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        //lp.alpha = 0.6f; //0.0-1.0
        activity.getWindow().setAttributes(lp);
        popupWindow.showAtLocation(viewShow, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1.0f; //0.0-1.0
                activity.getWindow().setAttributes(lp);
            }
        });
        loadLayout = viewShow.findViewById(R.id.load_layout);
    }
    private TextView textView;

   public void setMessage(String message){
       textView = viewShow.findViewById(R.id.tv_text);
       textView.setText(message);
   }
   public void dis(){
       if (popupWindow != null){
           popupWindow.dismiss();
       }
   }
   public void gone(){
       if (popupWindow != null){
          loadLayout.setVisibility(View.INVISIBLE);
       }
   }
}
