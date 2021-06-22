package com.vivian.Weather.GSON;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LookUpCity {
    /**
     * code : 200
     * location : [{"name":"盐湖","id":"101100814","lat":"35.02564","lon":"111.00062","adm2":"运城","adm1":"山西省","country":"中国","tz":"Asia/Shanghai","utcOffset":"+08:00","isDst":"0","type":"city","rank":"35","fxLink":"http://hfx.link/1tpn1"},{"name":"盐湖城","id":"31715","lat":"40.76100","lon":"-111.89099","adm2":"盐湖城","adm1":"犹他州","country":"美国","tz":"America/Denver","utcOffset":"-07:00","isDst":"0","type":"city","rank":"41","fxLink":"http://hfx.link/20bh1"}]
     * refer : {"sources":["heweather.com"],"license":["commercial license"]}
     */

    @SerializedName("code")
    private String code;
    @SerializedName("refer")
    private ReferDTO refer;
    @SerializedName("location")
    private List<LocationDTO> location;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ReferDTO getRefer() {
        return refer;
    }

    public void setRefer(ReferDTO refer) {
        this.refer = refer;
    }

    public List<LocationDTO> getLocation() {
        return location;
    }

    public void setLocation(List<LocationDTO> location) {
        this.location = location;
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

    public static class LocationDTO {
        /**
         * name : 盐湖
         * id : 101100814
         * lat : 35.02564
         * lon : 111.00062
         * adm2 : 运城
         * adm1 : 山西省
         * country : 中国
         * tz : Asia/Shanghai
         * utcOffset : +08:00
         * isDst : 0
         * type : city
         * rank : 35
         * fxLink : http://hfx.link/1tpn1
         */

        @SerializedName("name")
        private String name;
        @SerializedName("id")
        private String id;
        @SerializedName("lat")
        private String lat;
        @SerializedName("lon")
        private String lon;
        @SerializedName("adm2")
        private String adm2;
        @SerializedName("adm1")
        private String adm1;
        @SerializedName("country")
        private String country;
        @SerializedName("tz")
        private String tz;
        @SerializedName("utcOffset")
        private String utcOffset;
        @SerializedName("isDst")
        private String isDst;
        @SerializedName("type")
        private String type;
        @SerializedName("rank")
        private String rank;
        @SerializedName("fxLink")
        private String fxLink;

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

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getAdm2() {
            return adm2;
        }

        public void setAdm2(String adm2) {
            this.adm2 = adm2;
        }

        public String getAdm1() {
            return adm1;
        }

        public void setAdm1(String adm1) {
            this.adm1 = adm1;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getTz() {
            return tz;
        }

        public void setTz(String tz) {
            this.tz = tz;
        }

        public String getUtcOffset() {
            return utcOffset;
        }

        public void setUtcOffset(String utcOffset) {
            this.utcOffset = utcOffset;
        }

        public String getIsDst() {
            return isDst;
        }

        public void setIsDst(String isDst) {
            this.isDst = isDst;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getFxLink() {
            return fxLink;
        }

        public void setFxLink(String fxLink) {
            this.fxLink = fxLink;
        }
    }
}
