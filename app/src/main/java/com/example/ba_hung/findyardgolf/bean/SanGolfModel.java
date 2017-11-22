package com.example.ba_hung.findyardgolf.bean;

/**
 * Created by ba-hung on 22/10/2017.
 **/

public class SanGolfModel {
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
    private int star;
    private String website;
    private String avatar;

    public SanGolfModel() {
    }

    public SanGolfModel(String address, String city, String description, String image, double latitude, double longtitude, int like, String name, String phone, String price, String service, int star, String website, String avatar) {
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

    public int getStar() {
        return star;
    }

    public String getWebsite() {
        return website;
    }

    public String getAvatar() {
        return avatar;
    }
}