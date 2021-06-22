package com.vivian.Weather.GSON;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class IdAndName extends LitePalSupport implements Serializable {
    private String city_id;
    private String name;
    private String show_name;
    private int display_sort;

    public int getDisplay_sort() {
        return display_sort;
    }

    public void setDisplay_sort(int display_sort) {
        this.display_sort = display_sort;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShow_name() {
        return show_name;
    }

    public void setShow_name(String show_name) {
        this.show_name = show_name;
    }
}
