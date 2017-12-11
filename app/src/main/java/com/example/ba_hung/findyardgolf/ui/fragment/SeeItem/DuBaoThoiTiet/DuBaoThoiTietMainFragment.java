package com.example.ba_hung.findyardgolf.ui.fragment.SeeItem.DuBaoThoiTiet;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.ba_hung.findyardgolf.R;
import com.example.ba_hung.findyardgolf.bean.SanGolfModel;
import com.example.ba_hung.findyardgolf.controller.ProgressLoading;
import com.example.ba_hung.findyardgolf.ui.activity.ThoiTiet5NgayActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class DuBaoThoiTietMainFragment extends Fragment implements View.OnClickListener {
    private TextView txtTenSanGolf, txtDiaDiem, txtDoAm, txtGio, txtMay, txtNgayCapNhat, txtNhietDo, txtTrangThai;
    private Button btnXemCacNgayTiepTheo;
    private ImageView imgIcon;
    private View mView;
    private SanGolfModel sanGolfModel;

    public DuBaoThoiTietMainFragment(SanGolfModel sanGolfModel) {
        this.sanGolfModel = sanGolfModel;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_du_bao_thoi_tiet_main, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProgressLoading progressLoading = new ProgressLoading(mView);
        progressLoading.xuLyLoading(mView, 600,R.id.progressBar_loading );
        anhXa(mView);
        getCurrentWeatherData(sanGolfModel);
        btnXemCacNgayTiepTheo.setOnClickListener(this);
    }

    private void anhXa(View v) {
        txtTenSanGolf = v.findViewById(R.id.txtTenSanGolf);
        txtDiaDiem = v.findViewById(R.id.txtDiaDiem);
        txtDoAm = v.findViewById(R.id.txtDoAm);
        txtMay = v.findViewById(R.id.txtMay);
        txtGio = v.findViewById(R.id.txtGio);
        txtNgayCapNhat = v.findViewById(R.id.txtNgay);
        txtNhietDo = v.findViewById(R.id.txtNhietDo);
        txtTrangThai = v.findViewById(R.id.txtTrangThai);
        btnXemCacNgayTiepTheo = v.findViewById(R.id.btnXemCacNgayKhac);
        imgIcon = v.findViewById(R.id.imgIcon);

    }

    public void getCurrentWeatherData(final SanGolfModel sanGolfModel) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = "http://api.openweathermap.org/data/2.5/weather?lat=" + sanGolfModel.getLatitude() + "&lon=" + sanGolfModel.getLongtitude() + "&units=metric&appid=1fbe106423cd558f25e74141f0ad4778";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
//                            String name = jsonObject.getString("name");
                            txtTenSanGolf.setText(sanGolfModel.getName());

                            long l = Long.valueOf(day);
                            Date date = new Date(l * 1000);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
                            String Day = simpleDateFormat.format(date);
                            txtNgayCapNhat.setText(Day);

                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObjectWeather.getString("main");
                            String icon = jsonObjectWeather.getString("icon");

                            Glide.with(getActivity()).load("http://openweathermap.org/img/w/" + icon + ".png").into(imgIcon);
                            txtTrangThai.setText(status);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietDo = jsonObjectMain.getString("temp");
                            String doAm = jsonObjectMain.getString("humidity");

                            Double a = Double.valueOf(nhietDo);
                            String NhietDo = String.valueOf(a.intValue());

                            txtNhietDo.setText(NhietDo+"\u00b0"+"C");
                            txtDoAm.setText(doAm + "%");

                            JSONObject jsonObjectGio = jsonObject.getJSONObject("wind");
                            String gio = jsonObjectGio.getString("speed");
                            txtGio.setText(gio + "m/s");

                            JSONObject jsonObjectClouds = jsonObject.getJSONObject("clouds");
                            String may = jsonObjectClouds.getString("all");
                            txtMay.setText(may + "%");

//                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
//                            String country = jsonObjectSys.getString("country");
                            txtDiaDiem.setText("Địa điểm: " + sanGolfModel.getCity());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        requestQueue.add(stringRequest);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnXemCacNgayKhac){

            Intent intent = new Intent(getActivity(), ThoiTiet5NgayActivity.class);
            intent.putExtra("name", sanGolfModel);
            startActivity(intent);
        }
    }
}
