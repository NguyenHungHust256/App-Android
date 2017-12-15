package com.example.ba_hung.findyardgolf.bean;

import java.io.Serializable;

/**
 * Created by ba-hung on 22/10/2017.
 **/

public class SanGolfModel implements Serializable {
    private String address;
    private String city;
    private String description;
    private String image;
    private double latitude;
    private double longtitude;
    private int like;
    private String name;
    private String phone;
    private String price;
    private String service;
    private float star;
    private String website;
    private String avatar;
    private String mien;
    private String tinh;
    private int key;

    public SanGolfModel() {
    }

    public SanGolfModel(String address, String city, String description, String image, double latitude, double longtitude, int like, String name, String phone, String price, String service, float star, String website, String avatar, String mien, String tinh, int key) {
        this.address = address;
        this.description = description;
        this.image = image;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.like = like;
        this.name = name;
        this.phone = phone;
        this.price = price;
        this.service = service;
        this.star = star;
        this.website = website;
        this.avatar = avatar;
        this.city = city;
        this.mien = mien;
        this.tinh = tinh;
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public String getMien() {
        return mien;
    }

    public String getTinh() {
        return tinh;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public int getLike() {
        return like;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPrice() {
        return price;
    }

    public String getService() {
        return service;
    }

    public float getStar() {
        return star;
    }

    public String getWebsite() {
        return website;
    }

    public String getAvatar() {
        return avatar;
    }
}
