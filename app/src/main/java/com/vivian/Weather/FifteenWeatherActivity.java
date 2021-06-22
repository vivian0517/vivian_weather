package com.vivian.Weather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextDirectionHeuristic;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.vivian.Weather.GSON.CityManagerList;
import com.vivian.Weather.GSON.DailyDTO;
import com.vivian.Weather.GSON.SevenDayWeather;
import com.vivian.Weather.GSON.ThreeDayWeather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.vivian.Weather.myMethod.setStatusBarHeight;

public class FifteenWeatherActivity extends AppCompatActivity {
    private String id;
    private RecyclerView recyclerView;
    private List<Integer> integers;
    private List<Integer> integers2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifteen_weather);
        //状态栏黑色字符
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        Intent intent = getIntent();
        id = intent.getStringExtra("prosition_id");
        request7DayWeather(id);
        TextView textView = findViewById(R.id.toolbar_textview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        AppBarLayout appBarLayout = findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if( state == State.EXPANDED ) {
                    //展开状态
                    textView.setVisibility(View.GONE);

                }else if(state == State.COLLAPSED){
                    //折叠状态
                    textView.setVisibility(View.VISIBLE);
                }else {
                    //中间状态

                }
            }
        });
        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.nested);
        OverScrollDecoratorHelper.setUpStaticOverScroll(scrollView, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
    }


    private void request7DayWeather(String id) {
        String address = "https://devapi.qweather.com/v7/weather/7d?" + "location=" + id + Key.getKey();

        HttpUtil.sendOKHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                SevenDayWeather sevenDayWeather = Utility.responseOfJson(responseText, SevenDayWeather.class);

                if (sevenDayWeather != null) {runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void run() {

                        SevenDayViewInit(getApplicationContext(), sevenDayWeather);

                    }
                });
                }
            }
        });
    }

    private void SevenDayViewInit(Context applicationContext, SevenDayWeather sevenDayWeather) {
        recyclerView = (RecyclerView) findViewById(R.id.recyc);
        List<DailyDTO> dailyDTOList = sevenDayWeather.getDaily();

        integers = new ArrayList<>();
        integers2 = new ArrayList<>();
        if (dailyDTOList != null) {
            for (DailyDTO dailyDTO : dailyDTOList) {
                integers.add(Integer.valueOf(dailyDTO.getTempMax()));
                integers2.add(Integer.valueOf(dailyDTO.getTempMin()));
            }

            FifteenWeatherAdapter fifteenWeatherAdapter = new FifteenWeatherAdapter(integers, integers2,dailyDTOList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            recyclerView.setAdapter(fifteenWeatherAdapter);


        }
    }
}