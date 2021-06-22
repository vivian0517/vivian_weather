package com.vivian.Weather.GSON;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class CityManagerList extends LitePalSupport implements Serializable {
    private String city_id;

    private String show_name;
    private String weather_tempe;
    private String weather_now_tempe;
    private String weather_quaility;
    private int display_sort;

    public int getDisplay_sort() {
        return display_sort;
    }

    public void setDisplay_sort(int display_sort) {
        this.display_sort = display_sort;
    }

    public String getWeather_quaility() {
        return weather_quaility;
    }

    public void setWeather_quaility(String weather_quaility) {
        this.weather_quaility = weather_quaility;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }



    public String getShow_name() {
        return show_name;
    }

    public void setShow_name(String show_name) {
        this.show_name = show_name;
    }

    public String getWeather_tempe() {
        return weather_tempe;
    }

    public void setWeather_tempe(String weather_tempe) {
        this.weather_tempe = weather_tempe;
    }

    public String getWeather_now_tempe() {
        return weather_now_tempe;
    }

    public void setWeather_now_tempe(String weather_now_tempe) {
        this.weather_now_tempe = weather_now_tempe;
    }
}
