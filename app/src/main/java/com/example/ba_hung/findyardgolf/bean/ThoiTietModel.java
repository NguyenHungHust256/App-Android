package com.example.ba_hung.findyardgolf.bean;

/**
 * Created by ba-hung on 11/12/2017.
 **/

public class ThoiTietModel {
    private String day;
    private String status;
    private String image;
    private String temp;

    public ThoiTietModel(String day, String status, String image, String temp) {
        this.day = day;
        this.status = status;
        this.image = image;
        this.temp = temp;
    }

    public String getDay() {
        return day;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public String getTemp() {
        return temp;
    }
}
