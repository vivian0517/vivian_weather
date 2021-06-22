package com.vivian.Weather.GSON;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SevenDayWeather {

    /**
     * code : 200
     * updateTime : 2021-02-10T13:35+08:00
     * fxLink : http://hfx.link/2ax1
     * daily : [{"fxDate":"2021-02-10","sunrise":"07:11","sunset":"17:46","moonrise":"06:24","moonset":"15:56","moonPhase":"残月","tempMax":"14","tempMin":"-3","iconDay":"100","textDay":"晴","iconNight":"150","textNight":"晴","wind360Day":"180","windDirDay":"南风","windScaleDay":"1-2","windSpeedDay":"3","wind360Night":"180","windDirNight":"南风","windScaleNight":"1-2","windSpeedNight":"3","humidity":"45","precip":"0.0","pressure":"1014","vis":"25","cloud":"0","uvIndex":"3"},{"fxDate":"2021-02-11","sunrise":"07:10","sunset":"17:47","moonrise":"07:09","moonset":"17:03","moonPhase":"残月","tempMax":"11","tempMin":"-1","iconDay":"101","textDay":"多云","iconNight":"150","textNight":"晴","wind360Day":"180","windDirDay":"南风","windScaleDay":"1-2","windSpeedDay":"3","wind360Night":"0","windDirNight":"北风","windScaleNight":"1-2","windSpeedNight":"3","humidity":"45","precip":"0.0","pressure":"1013","vis":"25","cloud":"1","uvIndex":"2"},{"fxDate":"2021-02-12","sunrise":"07:09","sunset":"17:48","moonrise":"07:45","moonset":"18:10","moonPhase":"新月","tempMax":"13","tempMin":"-1","iconDay":"101","textDay":"多云","iconNight":"101","textNight":"多云","wind360Day":"0","windDirDay":"北风","windScaleDay":"1-2","windSpeedDay":"3","wind360Night":"45","windDirNight":"东北风","windScaleNight":"1-2","windSpeedNight":"3","humidity":"58","precip":"0.0","pressure":"1015","vis":"25","cloud":"0","uvIndex":"3"},{"fxDate":"2021-02-13","sunrise":"07:08","sunset":"17:49","moonrise":"08:16","moonset":"19:15","moonPhase":"峨眉月","tempMax":"12","tempMin":"0","iconDay":"100","textDay":"晴","iconNight":"101","textNight":"多云","wind360Day":"225","windDirDay":"西南风","windScaleDay":"1-2","windSpeedDay":"3","wind360Night":"45","windDirNight":"东北风","windScaleNight":"1-2","windSpeedNight":"3","humidity":"59","precip":"0.0","pressure":"1016","vis":"18","cloud":"0","uvIndex":"3"},{"fxDate":"2021-02-14","sunrise":"07:06","sunset":"17:50","moonrise":"08:42","moonset":"20:17","moonPhase":"峨眉月","tempMax":"7","tempMin":"-3","iconDay":"101","textDay":"多云","iconNight":"400","textNight":"小雪","wind360Day":"180","windDirDay":"南风","windScaleDay":"1-2","windSpeedDay":"3","wind360Night":"180","windDirNight":"南风","windScaleNight":"1-2","windSpeedNight":"3","humidity":"66","precip":"0.0","pressure":"1015","vis":"24","cloud":"5","uvIndex":"3"},{"fxDate":"2021-02-15","sunrise":"07:05","sunset":"17:51","moonrise":"09:07","moonset":"21:18","moonPhase":"峨眉月","tempMax":"4","tempMin":"-6","iconDay":"100","textDay":"晴","iconNight":"150","textNight":"晴","wind360Day":"45","windDirDay":"东北风","windScaleDay":"1-2","windSpeedDay":"3","wind360Night":"315","windDirNight":"西北风","windScaleNight":"1-2","windSpeedNight":"3","humidity":"21","precip":"0.0","pressure":"1016","vis":"25","cloud":"1","uvIndex":"3"},{"fxDate":"2021-02-16","sunrise":"07:04","sunset":"17:53","moonrise":"09:31","moonset":"22:17","moonPhase":"峨眉月","tempMax":"5","tempMin":"-6","iconDay":"100","textDay":"晴","iconNight":"150","textNight":"晴","wind360Day":"0","windDirDay":"北风","windScaleDay":"1-2","windSpeedDay":"3","wind360Night":"45","windDirNight":"东北风","windScaleNight":"1-2","windSpeedNight":"3","humidity":"19","precip":"0.0","pressure":"1022","vis":"25","cloud":"3","uvIndex":"3"}]
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
    public List<DailyDTO> daily;

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
