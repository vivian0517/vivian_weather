package com.vivian.Weather;

import android.app.Activity;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.util.Date;

public class myMethod {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String weekName(String api_date) {
        Date week_time = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            week_time = simpleDateFormat.parse(api_date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat simple = new SimpleDateFormat("EE");
        return simple.format(week_time);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static long betweenTime(String api_date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        //获取当前时间
        Date sys = new Date(System.currentTimeMillis());
        String sys_date = simpleDateFormat.format(sys);
        Date d1 = simpleDateFormat.parse(sys_date);
        Date d2 = simpleDateFormat.parse(api_date);
        long daysBetween = ((d2.getTime() - d1.getTime()) / (60 * 60 * 24 * 1000));
        return daysBetween;
    }
    public static void setStatusBarHeight(Activity activity){
        //设置状态栏透明效果
        if (Build.VERSION.SDK_INT >= 21) {

            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //利用反射获取状态栏高度
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        RelativeLayout titleLayout = (RelativeLayout)activity.findViewById(R.id.title);
        //增加状态栏下移
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(titleLayout.getLayoutParams());
        lp.setMargins(0, result, 0, 0);
        titleLayout.setLayoutParams(lp);
    }
}
