package com.vivian.Weather;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.vivian.Weather.GSON.LookUpCity;

import java.util.ArrayList;
import java.util.List;

public class Utility {
    //解析城市
    public static List handleResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            Gson gson = new Gson();
            List<String> list = new ArrayList<>();
            LookUpCity lookUpCity = gson.fromJson(response, LookUpCity.class);
            if (lookUpCity.getLocation() != null ) {
                for (LookUpCity.LocationDTO location : lookUpCity.getLocation()) {
                    list.add(location.getName()+"-"+location.getAdm2()+","+location.getAdm1()+","+location.getCountry());
                    //Log.i("TAG", location.getName());
                }
            }

            return list;
            /*for(LookUpCity.LocationDTO location: lookUpCity.getLocation()) {
                Log.i("TAG", location.getName());
            }*/
        } else
            return null;
    }
   
    //解析三日天气
    public static <T> T responseOfJson (String response,Class<T> classofT) {
        if (!TextUtils.isEmpty(response)) {
            Gson gson = new Gson();
           return gson.fromJson(response, classofT);

        }
        else
            return null;

    }


}
