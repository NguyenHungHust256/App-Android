package com.example.ba_hung.findyardgolf.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ba_hung.findyardgolf.R;
import com.example.ba_hung.findyardgolf.bean.ThoiTietModel;

import java.util.ArrayList;

/**
 * Created by ba-hung on 11/12/2017.
 **/

public class ThoiTietAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ThoiTietModel> datas;

    public ThoiTietAdapter(Context context, ArrayList<ThoiTietModel> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.custom_listview_item, null);

        ThoiTietModel thoiTiet = datas.get(position);

        TextView txtDay = view.findViewById(R.id.txtNgay);
        TextView txtTrangThai = view.findViewById(R.id.txtTrangThai);
        TextView txtNhietDo = view.findViewById(R.id.txtTemp);
        ImageView imgIcon = view.findViewById(R.id.imgIconMoiNgay);

        txtDay.setText(thoiTiet.getDay());
        txtTrangThai.setText(thoiTiet.getStatus());
        txtNhietDo.setText(thoiTiet.getTemp()+"\u00b0"+"C");
        Glide.with(context).load("http://openweathermap.org/img/w/"+thoiTiet.getImage()+".png").into(imgIcon);
        return view;
    }
}
