package com.vivian.Weather.GSON;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ThreeDayWeather {
    /**
     * code : 200
     * updateTime : 2021-01-02T23:35+08:00
     * fxLink : http://hfx.link/2ax1
     * daily : [{"fxDate":"2021-01-02","sunrise":"07:36","sunset":"17:02","moonrise":"20:07","moonset":"10:00","moonPhase":"亏凸月","tempMax":"1","tempMin":"-9","iconDay":"100","textDay":"晴","iconNight":"101","textNight":"多云","wind360Day":"220","windDirDay":"西南风","windScaleDay":"1-2","windSpeedDay":"6","wind360Night":"45","windDirNight":"东北风","windScaleNight":"1-2","windSpeedNight":"3","humidity":"29","precip":"0.0","pressure":"1029","vis":"25","cloud":"0","uvIndex":"2"},{"fxDate":"2021-01-03","sunrise":"07:36","sunset":"17:03","moonrise":"21:16","moonset":"10:35","moonPhase":"亏凸月","tempMax":"0","tempMin":"-9","iconDay":"101","textDay":"多云","iconNight":"101","textNight":"多云","wind360Day":"225","windDirDay":"西南风","windScaleDay":"1-2","windSpeedDay":"3","wind360Night":"0","windDirNight":"北风","windScaleNight":"1-2","windSpeedNight":"3","humidity":"27","precip":"0.0","pressure":"1028","vis":"25","cloud":"0","uvIndex":"2"},{"fxDate":"2021-01-04","sunrise":"07:36","sunset":"17:03","moonrise":"22:25","moonset":"11:06","moonPhase":"亏凸月","tempMax":"0","tempMin":"-10","iconDay":"101","textDay":"多云","iconNight":"150","textNight":"晴","wind360Day":"0","windDirDay":"北风","windScaleDay":"3-4","windSpeedDay":"16","wind360Night":"0","windDirNight":"北风","windScaleNight":"1-2","windSpeedNight":"3","humidity":"22","precip":"0.0","pressure":"1033","vis":"25","cloud":"0","uvIndex":"2"}]
     * refer : {"sources":["Weather China"],"license":["no commercial use"]}
     */

    @SerializedName("code")
    private String code;
    @SerializedName("updateTime")
    private String updateTime;
    @SerializedName("fxLink")
    private String fxLink;
    @SerializedName("refer")
    private ReferDTO refer;
    @SerializedName("daily")
    public List<DailyDTO> daily ;

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

    public ReferDTO getRefer() {
        return refer;
    }

    public void setRefer(ReferDTO refer) {
        this.refer = refer;
    }

    public List<DailyDTO> getDaily() {
        return daily;
    }

    public void setDaily(List<DailyDTO> daily) {
        this.daily = daily;
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
