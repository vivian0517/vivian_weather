package com.vivian.Weather.GSON;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NowWeather {

    /**
     * code : 200
     * updateTime : 2021-01-12T17:26+08:00
     * fxLink : http://hfx.link/2ax1
     * now : {"obsTime":"2021-01-12T17:04+08:00","temp":"3","feelsLike":"0","icon":"150","text":"晴","wind360":"135","windDir":"东南风","windScale":"1","windSpeed":"4","humidity":"22","precip":"0.0","pressure":"1005","vis":"10","cloud":"10","dew":"-18"}
     * refer : {"sources":["Weather China"],"license":["no commercial use"]}
     */

    @SerializedName("code")
    private String code;
    @SerializedName("updateTime")
    private String updateTime;
    @SerializedName("fxLink")
    private String fxLink;
    @SerializedName("now")
    private NowDTO now;
    @SerializedName("refer")
    private ReferDTO refer;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getFxLink() {
        return fxLink;
    }

    public void setFxLink(String fxLink) {
        this.fxLink = fxLink;
    }

    public NowDTO getNow() {
        return now;
    }

    public void setNow(NowDTO now) {
        this.now = now;
    }

    public ReferDTO getRefer() {
        return refer;
    }

    public void setRefer(ReferDTO refer) {
        this.refer = refer;
    }



    public static class ReferDTO {
        @SerializedName("sources")
        private List<String> sources;
        @SerializedName("license")
        private List<String> license;

        public List<String> getSources() {
            return sources;
        }

        public void setSources(List<String> sources) {
            this.sources = sources;
        }

        public List<String> getLicense() {
            return license;
        }

        public void setLicense(List<String> license) {
            this.license = license;
        }
    }
}
