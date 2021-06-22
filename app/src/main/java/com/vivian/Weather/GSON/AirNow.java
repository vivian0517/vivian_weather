package com.vivian.Weather.GSON;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AirNow {

    /**
     * code : 200
     * updateTime : 2021-01-15T20:58+08:00
     * fxLink : http://hfx.link/2ax4
     * now : {"pubTime":"2021-01-15T20:00+08:00","aqi":"30","level":"1","category":"优","primary":"NA","pm10":"30","pm2p5":"9","no2":"9","so2":"3","co":"0.3","o3":"66"}
     * station : [{"pubTime":"2021-01-15T20:00+08:00","name":"万寿西宫","id":"CNA1001","aqi":"36","level":"1","category":"优","primary":"NA","pm10":"36","pm2p5":"10","no2":"11","so2":"2","co":"0.3","o3":"61"},{"pubTime":"2021-01-15T20:00+08:00","name":"定陵","id":"CNA1002","aqi":"25","level":"1","category":"优","primary":"NA","pm10":"15","pm2p5":"4","no2":"2","so2":"3","co":"0.3","o3":"77"},{"pubTime":"2021-01-15T20:00+08:00","name":"东四","id":"CNA1003","aqi":"28","level":"1","category":"优","primary":"NA","pm10":"28","pm2p5":"12","no2":"12","so2":"1","co":"0.3","o3":"60"},{"pubTime":"2021-01-15T20:00+08:00","name":"天坛","id":"CNA1004","aqi":"32","level":"1","category":"优","primary":"NA","pm10":"32","pm2p5":"14","no2":"14","so2":"2","co":"0.3","o3":"62"},{"pubTime":"2021-01-15T20:00+08:00","name":"农展馆","id":"CNA1005","aqi":"29","level":"1","category":"优","primary":"NA","pm10":"29","pm2p5":"13","no2":"13","so2":"3","co":"0.3","o3":"71"},{"pubTime":"2021-01-15T20:00+08:00","name":"官园","id":"CNA1006","aqi":"31","level":"1","category":"优","primary":"NA","pm10":"31","pm2p5":"11","no2":"17","so2":"4","co":"0.4","o3":"54"},{"pubTime":"2021-01-15T20:00+08:00","name":"海淀区万柳","id":"CNA1007","aqi":"33","level":"1","category":"优","primary":"NA","pm10":"33","pm2p5":"7","no2":"19","so2":"3","co":"0.3","o3":"55"},{"pubTime":"2021-01-15T20:00+08:00","name":"顺义新城","id":"CNA1008","aqi":"29","level":"1","category":"优","primary":"NA","pm10":"29","pm2p5":"6","no2":"3","so2":"3","co":"0.3","o3":"68"},{"pubTime":"2021-01-15T20:00+08:00","name":"怀柔镇","id":"CNA1009","aqi":"33","level":"1","category":"优","primary":"NA","pm10":"33","pm2p5":"10","no2":"14","so2":"2","co":"0.2","o3":"60"},{"pubTime":"2021-01-15T20:00+08:00","name":"昌平镇","id":"CNA1010","aqi":"21","level":"1","category":"优","primary":"NA","pm10":"15","pm2p5":"3","no2":"7","so2":"3","co":"0.3","o3":"67"},{"pubTime":"2021-01-15T20:00+08:00","name":"奥体中心","id":"CNA1011","aqi":"32","level":"1","category":"优","primary":"NA","pm10":"32","pm2p5":"15","no2":"11","so2":"2","co":"0.2","o3":"62"},{"pubTime":"2021-01-15T20:00+08:00","name":"古城","id":"CNA1012","aqi":"28","level":"1","category":"优","primary":"NA","pm10":"28","pm2p5":"8","no2":"6","so2":"1","co":"0.3","o3":"66"},{"pubTime":"2021-01-15T20:00+08:00","name":"延庆夏都","id":"CNA3281","aqi":"23","level":"1","category":"优","primary":"NA","pm10":"15","pm2p5":"8","no2":"4","so2":"3","co":"0.1","o3":"71"},{"pubTime":"2021-01-15T20:00+08:00","name":"平谷新城","id":"CNA3417","aqi":"30","level":"1","category":"优","primary":"NA","pm10":"30","pm2p5":"7","no2":"1","so2":"2","co":"0.2","o3":"69"},{"pubTime":"2021-01-15T20:00+08:00","name":"密云新城","id":"CNA3418","aqi":"27","level":"1","category":"优","primary":"NA","pm10":"27","pm2p5":"7","no2":"3","so2":"2","co":"0.2","o3":"71"},{"pubTime":"2021-01-15T20:00+08:00","name":"门头沟三家店","id":"CNA3671","aqi":"28","level":"1","category":"优","primary":"NA","pm10":"28","pm2p5":"7","no2":"6","so2":"1","co":"0.2","o3":"73"},{"pubTime":"2021-01-15T20:00+08:00","name":"丰台云岗","id":"CNA3672","aqi":"41","level":"1","category":"优","primary":"NA","pm10":"41","pm2p5":"9","no2":"5","so2":"1","co":"0.2","o3":"70"},{"pubTime":"2021-01-15T20:00+08:00","name":"通州东关","id":"CNA3673","aqi":"40","level":"1","category":"优","primary":"NA","pm10":"40","pm2p5":"11","no2":"9","so2":"1","co":"0.2","o3":"54"},{"pubTime":"2021-01-15T20:00+08:00","name":"房山燕山","id":"CNA3674","aqi":"28","level":"1","category":"优","primary":"NA","pm10":"28","pm2p5":"7","no2":"2","so2":"1","co":"0.1","o3":"80"},{"pubTime":"2021-01-15T20:00+08:00","name":"大兴旧宫","id":"CNA3675","aqi":"38","level":"1","category":"优","primary":"NA","pm10":"38","pm2p5":"11","no2":"13","so2":"3","co":"0.3","o3":"60"},{"pubTime":"2021-01-15T20:00+08:00","name":"延庆石河营","id":"CNA3694","aqi":"23","level":"1","category":"优","primary":"NA","pm10":"13","pm2p5":"8","no2":"6","so2":"2","co":"0.2","o3":"73"},{"pubTime":"2021-01-15T20:00+08:00","name":"怀柔新城","id":"CNA3695","aqi":"38","level":"1","category":"优","primary":"NA","pm10":"38","pm2p5":"9","no2":"8","so2":"1","co":"0.2","o3":"66"},{"pubTime":"2021-01-15T20:00+08:00","name":"丰台小屯","id":"CNA3696","aqi":"39","level":"1","category":"优","primary":"NA","pm10":"39","pm2p5":"11","no2":"15","so2":"5","co":"0.4","o3":"58"},{"pubTime":"2021-01-15T20:00+08:00","name":"密云镇","id":"CNA3697","aqi":"32","level":"1","category":"优","primary":"NA","pm10":"32","pm2p5":"8","no2":"5","so2":"3","co":"0.2","o3":"67"}]
     * refer : {"sources":["cnemc"],"license":["no commercial use"]}
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
    @SerializedName("station")
    private List<StationDTO> station;

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

    public List<StationDTO> getStation() {
        return station;
    }

    public void setStation(List<StationDTO> station) {
        this.station = station;
    }

    public static class NowDTO {
        /**
         * pubTime : 2021-01-15T20:00+08:00
         * aqi : 30
         * level : 1
         * category : 优
         * primary : NA
         * pm10 : 30
         * pm2p5 : 9
         * no2 : 9
         * so2 : 3
         * co : 0.3
         * o3 : 66
         */

        @SerializedName("pubTime")
        private String pubTime;
        @SerializedName("aqi")
        private String aqi;
        @SerializedName("level")
        private String level;
        @SerializedName("category")
        private String category;
        @SerializedName("primary")
        private String primary;
        @SerializedName("pm10")
        private String pm10;
        @SerializedName("pm2p5")
        private String pm2p5;
        @SerializedName("no2")
        private String no2;
        @SerializedName("so2")
        private String so2;
        @SerializedName("co")
        private String co;
        @SerializedName("o3")
        private String o3;

        public String getPubTime() {
            return pubTime;
        }

        public void setPubTime(String pubTime) {
            this.pubTime = pubTime;
        }

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPrimary() {
            return primary;
        }

        public void setPrimary(String primary) {
            this.primary = primary;
        }

        public String getPm10() {
            return pm10;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public String getPm2p5() {
            return pm2p5;
        }

        public void setPm2p5(String pm2p5) {
            this.pm2p5 = pm2p5;
        }

        public String getNo2() {
            return no2;
        }

        public void setNo2(String no2) {
            this.no2 = no2;
        }

        public String getSo2() {
            return so2;
        }

        public void setSo2(String so2) {
            this.so2 = so2;
        }

        public String getCo() {
            return co;
        }

        public void setCo(String co) {
            this.co = co;
        }

        public String getO3() {
            return o3;
        }

        public void setO3(String o3) {
            this.o3 = o3;
        }
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

    public static class StationDTO {
        /**
         * pubTime : 2021-01-15T20:00+08:00
         * name : 万寿西宫
         * id : CNA1001
         * aqi : 36
         * level : 1
         * category : 优
         * primary : NA
         * pm10 : 36
         * pm2p5 : 10
         * no2 : 11
         * so2 : 2
         * co : 0.3
         * o3 : 61
         */

        @SerializedName("pubTime")
        private String pubTime;
        @SerializedName("name")
        private String name;
        @SerializedName("id")
        private String id;
        @SerializedName("aqi")
        private String aqi;
        @SerializedName("level")
        private String level;
        @SerializedName("category")
        private String category;
        @SerializedName("primary")
        private String primary;
        @SerializedName("pm10")
        private String pm10;
        @SerializedName("pm2p5")
        private String pm2p5;
        @SerializedName("no2")
        private String no2;
        @SerializedName("so2")
        private String so2;
        @SerializedName("co")
        private String co;
        @SerializedName("o3")
        private String o3;

        public String getPubTime() {
            return pubTime;
        }

        public void setPubTime(String pubTime) {
            this.pubTime = pubTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPrimary() {
            return primary;
        }

        public void setPrimary(String primary) {
            this.primary = primary;
        }

        public String getPm10() {
            return pm10;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public String getPm2p5() {
            return pm2p5;
        }

        public void setPm2p5(String pm2p5) {
            this.pm2p5 = pm2p5;
        }

        public String getNo2() {
            return no2;
        }

        public void setNo2(String no2) {
            this.no2 = no2;
        }

        public String getSo2() {
            return so2;
        }

        public void setSo2(String so2) {
            this.so2 = so2;
        }

        public String getCo() {
            return co;
        }

        public void setCo(String co) {
            this.co = co;
        }

        public String getO3() {
            return o3;
        }

        public void setO3(String o3) {
            this.o3 = o3;
        }
    }
}
