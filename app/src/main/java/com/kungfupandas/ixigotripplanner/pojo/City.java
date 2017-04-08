package com.kungfupandas.ixigotripplanner.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tushar on 08/04/17.
 */

public class City {
    @SerializedName("text")
    private String cityName;
    @SerializedName("_id")
    private String cityId;
    @SerializedName("url")
    private String url;
    @SerializedName("lat")
    private Double latitude;
    @SerializedName("lon")
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
