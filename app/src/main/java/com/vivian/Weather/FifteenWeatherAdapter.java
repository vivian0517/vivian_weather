package com.vivian.Weather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.vivian.Weather.GSON.DailyDTO;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FifteenWeatherAdapter extends RecyclerView.Adapter<FifteenWeatherAdapter.ViewHolder> {
    private int data_one_minValue;
    private int data_one_maxValue;
    private int data_two_minValue;
    private int data_two_maxValue;
    private List<Integer> data_one;
    private List<Integer> data_two;
    private List<DailyDTO> dailyDTOList;


    public FifteenWeatherAdapter(List<Integer> data_one, List<Integer> data_two, List<DailyDTO> dailyDTOList) {

        this.data_one_minValue = Collections.min(data_one);
        this.data_one_maxValue = Collections.max(data_one);
        this.data_one = data_one;
        this.data_two_minValue = Collections.min(data_two);
        this.data_two_maxValue = Collections.max(data_two);
        this.data_two = data_two;
        this.dailyDTOList = dailyDTOList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_15day_layout, parent, false);

        return new ViewHolder(layout);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //maxValue = findMaxValue(data);
        //minValue = findMinValue(data);
        if (position == 0) {
            holder.itemView.setBackgroundResource(R.drawable.fifteen_background);
        }
        if (position == 0) {
            holder.lineView.setDrawLeftLine(false);
        } else {
            holder.lineView.setDrawLeftLine(true);
            holder.lineView.setlastValue((data_one.get(position - 1)));
        }
        holder.lineView.setMaxValue(data_one_maxValue);
        holder.lineView.setMinValue(data_one_minValue);
        holder.lineView.setCurrentValue((data_one.get(position)));


        if (position == data_one.size() - 1) {
            holder.lineView.setDrawRightLine(false);
        } else {
            holder.lineView.setDrawRightLine(true);
            holder.lineView.setNextValue((data_one.get(position + 1)));
        }

        //another
        if (position == 0) {
            holder.lineView_buttom.setDrawLeftLine(false);
        } else {
            holder.lineView_buttom.setDrawLeftLine(true);
            holder.lineView_buttom.setlastValue((data_two.get(position - 1)));
        }
        holder.lineView_buttom.setMaxValue(data_two_maxValue);
        holder.lineView_buttom.setMinValue(data_two_minValue);
        holder.lineView_buttom.setCurrentValue((data_two.get(position)));


        if (position == data_one.size() - 1) {
            holder.lineView_buttom.setDrawRightLine(false);
        } else {
            holder.lineView_buttom.setDrawRightLine(true);
            holder.lineView_buttom.setNextValue((data_two.get(position + 1)));
        }
        //文字和图片部分
        //处理周几
        String api_date = dailyDTOList.get(position).getFxDate();
        long daysBetween = 0L;
        try {
            daysBetween = myMethod.betweenTime(api_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (daysBetween == -1) {
            holder.week.setText("昨天");
        } else if (daysBetween == 1) {
            holder.week.setText("明天");
        } else if (daysBetween == 0) {
            holder.week.setText("今天");
        } else {
            //判断周几
            holder.week.setText(myMethod.weekName(api_date));
        }
        //处理日期

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        //获取当前时间
        Date sys = new Date();
        try {
            sys = simpleDateFormat.parse(api_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat simple = new SimpleDateFormat("M月d日");
        String prefect = simple.format(sys);
        holder.date.setText(simple.format(sys));
        //处理天气 文字
        holder.textDay.setText(dailyDTOList.get(position).getTextDay());
        holder.textNight.setText(dailyDTOList.get(position).getTextNight());
        //处理天气 icon 🌤
        String iconDay_string = dailyDTOList.get(position).getIconDay();
        String iconNight_string = dailyDTOList.get(position).getIconNight();
        String[] images = null;
        try {
            //images = LitePalApplication.getContext().getAssets().list("/weather_icon");
            //Log.i("TAG", Arrays.toString(images));
            InputStream input,input2;
            Bitmap icon,icon2;
            input = LitePalApplication.getContext().getAssets().open("weather_icon/" + iconDay_string + ".png");
            icon = BitmapFactory.decodeStream(input);
            holder.iconDay.setImageBitmap(icon);
            input2 = LitePalApplication.getContext().getAssets().open("weather_icon/" + iconNight_string + ".png");
            icon2 = BitmapFactory.decodeStream(input2);
            holder.iconNight.setImageBitmap(icon2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //处理风速
        holder.windScaleDay.setText(dailyDTOList.get(position).getWindScaleDay()+"级");
    }

    @Override
    public int getItemCount() {
        return dailyDTOList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LineView lineView;
        LineView_buttom lineView_buttom;
        private TextView week;
        private TextView date;
        private ImageView iconDay;
        private TextView textDay;
        private ImageView iconNight;
        private TextView textNight;
        private TextView windScaleDay;

        public ViewHolder(View itemView) {
            super(itemView);
            this.lineView = itemView.findViewById(R.id.fifteen_day_item);
            this.lineView_buttom = itemView.findViewById(R.id.fifteen_day_item_buttom);
            week = (TextView) itemView.findViewById(R.id.week);
            date = (TextView) itemView.findViewById(R.id.date);
            textDay = (TextView) itemView.findViewById(R.id.textDay);
            textNight = (TextView) itemView.findViewById(R.id.textNight);
            iconDay = (ImageView) itemView.findViewById(R.id.iconDay);
            iconNight = (ImageView) itemView.findViewById(R.id.iconNight);
            windScaleDay = (TextView)itemView.findViewById(R.id.windScaleDay);

        }
    }
}
