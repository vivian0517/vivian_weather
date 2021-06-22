package com.vivian.Weather;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.vivian.Weather.GSON.HourlyDTO;
import com.vivian.Weather.GSON.IdAndName;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class TwentyFourAdapter extends RecyclerView.Adapter<TwentyFourAdapter.ViewHolder> {
    private List<HourlyDTO> hourlyDTOS;
    public TwentyFourAdapter(List<HourlyDTO> list){
        hourlyDTOS = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView_time;
        TextView textView_tempe;
        TextView textView_wind;
        ImageView icon_day;
        public ViewHolder(View itemView) {
            super(itemView);
            textView_time = itemView.findViewById(R.id.time);
            textView_tempe = itemView.findViewById(R.id.tempe);
            textView_wind = itemView.findViewById(R.id.wind);
            icon_day = itemView.findViewById(R.id.iconDay);
        }
    }
    @NonNull
    @Override
    public TwentyFourAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_24hour_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);


        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       HourlyDTO mhourly = hourlyDTOS.get(position);
        String mtime = mhourly.getFxTime();

        holder.textView_time.setText(specTime(mtime));
        holder.textView_tempe.setText(mhourly.getTemp()+"°");
        holder.textView_wind.setText(mhourly.getWindScale()+"级");
        String[] images = null;
        try {
            images = LitePalApplication.getContext().getAssets().list("/weather_icon");
            //Log.i("TAG", Arrays.toString(images));
            InputStream input;
            Bitmap icon;
            input = LitePalApplication.getContext().getAssets().open("weather_icon/" + mhourly.getIcon() + ".png");
            icon = BitmapFactory.decodeStream(input);
            holder.icon_day.setImageBitmap(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return hourlyDTOS.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String specTime(String mtime) {
        Date time = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            time = simpleDateFormat.parse(mtime);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat simple = new SimpleDateFormat("HH:mm");
        //Log.i("tt",simple.format(time));
        return simple.format(time);

    }

}

