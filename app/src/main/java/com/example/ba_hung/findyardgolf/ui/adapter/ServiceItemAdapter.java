package com.example.ba_hung.findyardgolf.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ba_hung.findyardgolf.R;

import java.util.ArrayList;

/**
 * Created by ba-hung on 25/10/2017.
 **/

public class ServiceItemAdapter extends RecyclerView.Adapter<ServiceItemAdapter.ViewHolder> {
    private Context context;
    ArrayList<String> tenDichVu = new ArrayList<>();

    public ServiceItemAdapter(Context context, ArrayList<String> tenDichVu) {
        this.context = context;
        this.tenDichVu = tenDichVu;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(this.context);
        view = inflater.inflate(R.layout.custom_service, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (tenDichVu.get(position).equals("Phòng thay đồ"))
            Glide.with(context).load("https://alegolf.com/sites/default/files/changing-room.png").into(holder.imgIconDichVu);
        else if (tenDichVu.get(position).equals("Sauna"))
            Glide.with(context).load("https://alegolf.com/sites/default/files/sauna.png").into(holder.imgIconDichVu);
        else if (tenDichVu.get(position).equals("Showers"))
            Glide.with(context).load("https://alegolf.com/sites/default/files/shower.png").into(holder.imgIconDichVu);
        else if (tenDichVu.get(position).equals("Xe kéo bao gậy"))
            Glide.with(context).load("https://alegolf.com/sites/default/files/trolleys.png").into(holder.imgIconDichVu);
        else if (tenDichVu.get(position).equals("Thuê gậy"))
            Glide.with(context).load("https://alegolf.com/sites/default/files/rental-club.png").into(holder.imgIconDichVu);
        else if (tenDichVu.get(position).equals("Học viện Golf"))
            Glide.with(context).load("https://alegolf.com/sites/default/files/academy.png").into(holder.imgIconDichVu);
        else if (tenDichVu.get(position).equals("Club house"))
            Glide.with(context).load("https://alegolf.com/sites/default/files/club-house.png").into(holder.imgIconDichVu);
        else if (tenDichVu.get(position).equals("Sân tập"))
            Glide.with(context).load("https://alegolf.com/sites/default/files/dring-range.png").into(holder.imgIconDichVu);
        else if (tenDichVu.get(position).equals("Xe điện"))
            Glide.with(context).load("https://alegolf.com/sites/default/files/rental-cart.png").into(holder.imgIconDichVu);
        else if (tenDichVu.get(position).equals("Cho thuê ô(dù)"))
            Glide.with(context).load("https://alegolf.com/sites/default/files/rental-umbrella.png").into(holder.imgIconDichVu);
        else if (tenDichVu.get(position).equals("Cho thuê ô (dù)"))
            Glide.with(context).load("https://alegolf.com/sites/default/files/rental-umbrella.png").into(holder.imgIconDichVu);
        else if (tenDichVu.get(position).equals("Massage"))
            Glide.with(context).load("https://alegolf.com/sites/default/files/spa.png").into(holder.imgIconDichVu);
        else if (tenDichVu.get(position).equals("Bể bơi"))
            Glide.with(context).load("https://alegolf.com/sites/default/files/swimming.png").into(holder.imgIconDichVu);
        else if (tenDichVu.get(position).equals("Phòng nghỉ"))
            Glide.with(context).load("https://alegolf.com/sites/default/files/accommodation.png").into(holder.imgIconDichVu);
        else if (tenDichVu.get(position).equals("Tennis"))
            Glide.with(context).load("https://alegolf.com/sites/default/files/tennis.png").into(holder.imgIconDichVu);
        else if (tenDichVu.get(position).equals("Cửa hàng dụng cụ"))
            Glide.with(context).load("https://alegolf.com/sites/default/files/proshop.png").into(holder.imgIconDichVu);
        else if (tenDichVu.get(position).equals("Nhà hàng"))
            Glide.with(context).load("https://alegolf.com/sites/default/files/ok_18.png").into(holder.imgIconDichVu);
        else
            Glide.with(context).load("https://alegolf.com/sites/default/files/ok_18.png").into(holder.imgIconDichVu);

        holder.txtTenDichVu.setText(tenDichVu.get(position));

        Animation translate;
        translate = AnimationUtils.loadAnimation(context, R.anim.scale);
        holder.imgIconDichVu.startAnimation(translate);
        holder.txtTenDichVu.startAnimation(translate);

    }


    @Override
    public int getItemCount() {
        return tenDichVu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenDichVu;
        ImageView imgIconDichVu;

        public ViewHolder(View v) {
            super(v);
            txtTenDichVu = v.findViewById(R.id.txtTenDichVu);
            imgIconDichVu = v.findViewById(R.id.imgIconDichVu);
        }


    }
}
