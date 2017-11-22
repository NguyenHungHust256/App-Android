package com.example.ba_hung.findyardgolf.bean;

/**
 * Created by ba-hung on 22/10/2017.
 **/

public class UserModel {
    private String taiKhoan;
    private String name;
    private String avatar;
    private String sanGolfDaLike;
    private String diaChi;

    public UserModel(String taiKhoan) {
        this.taiKhoan = taiKhoan;
        this.name = "Khách Hàng";
        this.avatar = "https://cdn0.iconfinder.com/data/icons/golf-2/128/golf_3-23-512.png";
        this.sanGolfDaLike = "";
        this.diaChi = "";
    }

    public UserModel() {
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getSanGolfDaLike() {
        return sanGolfDaLike;
    }

    public String getDiaChi() {
        return diaChi;
    }
}
