package com.vivian.Weather;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vivian.Weather.GSON.DailyDTO;
import com.vivian.Weather.GSON.HourlyDTO;
import com.vivian.Weather.GSON.IdAndName;
import com.vivian.Weather.GSON.NowDTO;
import com.vivian.Weather.GSON.NowWeather;
import com.vivian.Weather.GSON.ThreeDayWeather;
import com.vivian.Weather.GSON.TwentyFourHour;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainFragment extends Fragment {
    private String id = "101010100";

    public static final String ARG_CITY_ID = "city_id";
    public static final String ARG_CURRENT_POSITION_ID = "prosition_id";
    private TextView main_city_textview;
    private Button main_15_button;
    private StringBuilder currentPrositonId;

    public static MainFragment newInstance(String cityId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY_ID, cityId);
        //args.putSerializable(ARG_CUTTENT_POSITION_ID,currentPositionId);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = (String) getArguments().getSerializable(ARG_CITY_ID);
        //currentPrositonId = (StringBuilder) getArguments().getSerializable(ARG_CUTTENT_POSITION_ID);
        Log.i("tt", "fragment oncreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("tt", "fragment oncreate");
        View view = inflater.inflate(R.layout.main_activity, container, false);
        request3DayWeather(id);
        //获取实时温度
        requestNowWeather(id);
        request24hWeather(id);
        //设置显示的城市名字
        // main_city_textview = (TextView) view.findViewById(R.id.main_city);
        List<IdAndName> idAndName = LitePal.where("city_id = ?", String.valueOf(id)).find(IdAndName.class);
//        String show_name = idAndName.get(0).getShow_name();
        // main_city_textview.setText(show_name);
        /*
        Button open_search_city_button = (Button) view.findViewById(R.id.open_search_city);
       open_search_city_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CityManagerActivity.class);
                intent.putExtra("prosition_id",id);
                getActivity().startActivityForResult(intent, REQUEST_CODE_SEARCH);
            }
        });

 */


        main_15_button = (Button) view.findViewById(R.id.main_15_button);
        main_15_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FifteenWeatherActivity.class);
                intent.putExtra("prosition_id", id);
                startActivity(intent);
            }
        });
        return view;

    }

    private void request24hWeather(String id) {

        String address = "https://devapi.qweather.com/v7/weather/24h?" + "location=" + id + Key.getKey();
        HttpUtil.sendOKHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                TwentyFourHour twentyFourHour = Utility.responseOfJson(responseText, TwentyFourHour.class);
                if (twentyFourHour.getCode().equals("200")) {
                    if (twentyFourHour != null && getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void run() {

                                TwentyFourViewInit(getActivity().getApplicationContext(), twentyFourHour);

                            }
                        });
                    }
                }
            }
        });
    }

    private void TwentyFourViewInit(Context applicationContext, TwentyFourHour twentyFourHour) {


        List<HourlyDTO> hourlyDTOS = twentyFourHour.getHourly();
        boolean state = false;
        if (hourlyDTOS != null) {
            RecyclerView recyclerView = getView().findViewById(R.id.recyc);
            TwentyFourAdapter twentyFourAdapter = new TwentyFourAdapter(hourlyDTOS);
            recyclerView.setLayoutManager(new LinearLayoutManager(LitePalApplication.getContext(), recyclerView.HORIZONTAL, false));
            recyclerView.setAdapter(twentyFourAdapter);
        }
    }


    private void request3DayWeather(String id) {
        String address = "https://devapi.qweather.com/v7/weather/3d?" + "location=" + id + Key.getKey();
        HttpUtil.sendOKHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                ThreeDayWeather threeDayWeather = Utility.responseOfJson(responseText, ThreeDayWeather.class);
                if (threeDayWeather.getCode().equals("200")) {
                    if (threeDayWeather != null && getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void run() {

                                ThreeDayViewInit(getContext(), threeDayWeather);

                            }
                        });
                    }
                }
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void ThreeDayViewInit(Context context, ThreeDayWeather threeDayWeather) {

        List<DailyDTO> dailyDTOList = threeDayWeather.getDaily();
        boolean state = false;
        if (dailyDTOList != null) {
            for (DailyDTO dailyDTO : dailyDTOList) {
                state = dailyDTO.save();
            }
            //state = threeDayWeather.save();
            for (int i = 0; i < 3; i++) {
                LinearLayout layout = getView().findViewById(R.id.three);
                View view = LayoutInflater.from(context).inflate(R.layout.main_3day_layout, getView().findViewById(R.id.three), false);
                layout.addView(view);
                //初始化控件
                ImageView imageView = (ImageView) view.findViewById(R.id.buttom_img);
                TextView textView_week = (TextView) view.findViewById(R.id.buttom_week);
                //TextView textView_quality = (TextView) view.findViewById(R.id.buttom_quality);
                TextView textView_tempe = (TextView) view.findViewById(R.id.buttom_tempe);
                StringBuffer day_night = new StringBuffer("");
                String api_date = dailyDTOList.get(i).getFxDate();
                long daysBetween = 0L;
                try {
                    daysBetween = myMethod.betweenTime(api_date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String week_name = myMethod.weekName(api_date);
                //Log.i("TAG", String.valueOf(simpleDateFormat.parse(api_date)));
                if (daysBetween == -1) {
                    day_night.append("昨天·");
                } else if (daysBetween == 1) {
                    day_night.append("明天·");
                } else if (daysBetween == 0) {
                    day_night.append("今天·");
                } else {
                    //判断周几
                    day_night.append(week_name + "·");
                }
                //周几+天气字符串拼接
                if (dailyDTOList.get(i).getTextDay().equals(dailyDTOList.get(i).getTextNight())) {
                    day_night.append(dailyDTOList.get(i).getTextDay());
                } else {
                    day_night.append(dailyDTOList.get(i).getTextDay() + "转" + dailyDTOList.get(i).getTextNight());
                }
                textView_week.setText(day_night);
                //设置天气质量
                String tempe = dailyDTOList.get(i).getTempMax() + "°/" + dailyDTOList.get(i).getTempMin() + "°";
                //Log.i("TAG", String.valueOf(tempe));
                textView_tempe.setText(tempe);
                //设置icon图片
                //imageView.setImageBitmap(getAssets().open("100.png"));
                String iconDay = dailyDTOList.get(i).getIconDay();
                String iconNight = dailyDTOList.get(i).getIconNight();
                String Sunset = api_date + " " + dailyDTOList.get(i).getSunset();
                String imgset;
                if (i == 0) {
                    imgset = img_set(iconDay, iconNight, Sunset);
                } else {
                    imgset = iconDay;
                }

                String[] images = null;
                try {
                    images = context.getAssets().list("/weather_icon");
                    //Log.i("TAG", Arrays.toString(images));
                    InputStream input;
                    Bitmap icon;
                    input = context.getAssets().open("weather_icon/" + imgset + ".png");
                    icon = BitmapFactory.decodeStream(input);
                    imageView.setImageBitmap(icon);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String img_set(String iconDay, String iconNight, String sunset) {
        //Log.i("TAG", sunset);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        //获取当前时间
        Date sys = new Date(System.currentTimeMillis());
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date sun_set_date = new Date();
        try {
            sun_set_date = simple.parse(sunset);
            sun_set_date.setMinutes(sun_set_date.getMinutes() + 1);

        } catch (ParseException e) {
            e.printStackTrace();

        }
        //Log.i("TAG", String.valueOf(sys.getTime()));

        if (sys.getTime() <= sun_set_date.getTime()) {
            return iconDay;
        } else {
            return iconNight;
        }
    }


    private void requestNowWeather(String id) {
        String address = "https://devapi.qweather.com/v7/weather/now?" + "location=" + id + Key.getKey();
        HttpUtil.sendOKHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                NowWeather nowWeather = Utility.responseOfJson(responseText, NowWeather.class);
                if (nowWeather.getCode().equals("200")) {
                    if (nowWeather != null && getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void run() {
                                NowWeatherViewInit(nowWeather, id);
                            }
                        });
                    }
                }
            }
        });

    }

    private void NowWeatherViewInit(NowWeather nowWeather, String city_id) {
        TextView weather_tempe_textView = (TextView) getView().findViewById(R.id.main_temperature);
        TextView main_weather_textView = (TextView) getView().findViewById(R.id.main_weather);
        NowDTO nowDTO = nowWeather.getNow();
        nowDTO.setCity_id(city_id);
        //先删除再存储
        List<NowDTO> nowDTOS = LitePal.where("city_id = ?", city_id).find(NowDTO.class);
        if (nowDTOS.size() != 0) {
            LitePal.deleteAll(NowDTO.class, "city_id = ?", city_id);
        } else {
            nowDTO.save();
        }
        weather_tempe_textView.setText(nowDTO.getTemp());
        main_weather_textView.setText(nowDTO.getText());

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("tt", "fragment onresume");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("tt", "fragment ondestroy");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("tt", "fragment onpause");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("tt", "fragment onstart");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i("tt", "fragment onattach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("tt", "fragment ondetach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("tt", "fragment ondestroy view");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("tt", "fragment onActivityCreated");
    }
}


