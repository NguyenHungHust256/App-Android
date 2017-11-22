package com.example.ba_hung.findyardgolf.ui.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ba_hung.findyardgolf.R;
import com.example.ba_hung.findyardgolf.bean.SanGolfModel;
import com.example.ba_hung.findyardgolf.ui.activity.MainActivity;
import com.example.ba_hung.findyardgolf.ui.fragment.XemItemFragment;

import java.util.ArrayList;

/**
 * Created by ba-hung on 22/10/2017.
 **/

public class SanGolfAdapter extends RecyclerView.Adapter<SanGolfAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SanGolfModel> datas;


    public SanGolfAdapter(Context context, ArrayList<SanGolfModel> datas) {
        this.context = context;
        this.datas = datas;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(this.context);
        view = inflater.inflate(R.layout.custom_item_golf, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Animation scale;
        holder.txtNameGolf.setText(datas.get(position).getName());
        holder.txtAddressGolf.setText(datas.get(position).getCity());
        Glide.with(context).load(datas.get(position).getAvatar()).into(holder.imgAvatarGolf);
        holder.txtPriceGolf.setText(datas.get(position).getPrice());
        holder.ratingGolf.setRating(Float.valueOf(datas.get(position).getStar()));
        scale = AnimationUtils.loadAnimation(context, R.anim.translate);
        holder.txtNameGolf.startAnimation(scale);
        holder.txtAddressGolf.startAnimation(scale);
        holder.imgAvatarGolf.startAnimation(scale);
        holder.txtPriceGolf.startAnimation(scale);
        holder.btnBookingGolf.startAnimation(scale);
        holder.ratingGolf.startAnimation(scale);

        holder.btnBookingGolf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = datas.get(position).getPhone();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + number));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)context).themFragment(R.id.myLayout, new XemItemFragment(datas.get(position)));
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAvatarGolf;
        TextView txtNameGolf;
        RatingBar ratingGolf;
        TextView txtAddressGolf;
        TextView txtPriceGolf;
        Button btnBookingGolf;

        public ViewHolder(View itemView) {
            super(itemView);
            imgAvatarGolf = itemView.findViewById(R.id.imgAvatarGolf);
            txtNameGolf =  itemView.findViewById(R.id.txtNameGolf);
            ratingGolf =  itemView.findViewById(R.id.ratingGolf);
            txtAddressGolf = itemView.findViewById(R.id.txtAddressGolf);
            txtPriceGolf = itemView.findViewById(R.id.txtPriceGolf);
            btnBookingGolf = itemView.findViewById(R.id.btnBooking);

        }

    }
}
