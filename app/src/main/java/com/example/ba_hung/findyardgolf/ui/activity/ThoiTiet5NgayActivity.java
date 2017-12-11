package com.example.ba_hung.findyardgolf.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ba_hung.findyardgolf.R;
import com.example.ba_hung.findyardgolf.bean.SanGolfModel;
import com.example.ba_hung.findyardgolf.bean.ThoiTietModel;
import com.example.ba_hung.findyardgolf.controller.ProgressLoading;
import com.example.ba_hung.findyardgolf.ui.adapter.ThoiTietAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ThoiTiet5NgayActivity extends AppCompatActivity {

    private ImageView imgBack;
    private TextView txtName;
    private ListView lv;
    private ThoiTietAdapter thoiTietAdapter;
    private ArrayList<ThoiTietModel> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thoi_tiet5_ngay);
        ProgressLoading progressLoading = new ProgressLoading(getWindow().getDecorView());
        progressLoading.xuLyLoading(getWindow().getDecorView(), 800, R.id.progressBar_loading);
        anhXa();
        khoiTaoDuLieuThoiTiet();
        Intent intent = getIntent();
       SanGolfModel sanGolfModel = (SanGolfModel) intent.getSerializableExtra("name");

        get5DayData(sanGolfModel);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void khoiTaoDuLieuThoiTiet() {
        datas = new ArrayList<>();
        thoiTietAdapter = new ThoiTietAdapter(ThoiTiet5NgayActivity.this, datas);
        lv.setAdapter(thoiTietAdapter);

    }

    private void anhXa() {
        imgBack = findViewById(R.id.imgBack);
        txtName = findViewById(R.id.txtThanhPho);
        lv = findViewById(R.id.listview);
    }

    public void get5DayData(final SanGolfModel data) {
        String url = "http://api.openweathermap.org/data/2.5/forecast?lat="+data.getLatitude()+"&lon="+data.getLongtitude()+"&units=metric&appid=1fbe106423cd558f25e74141f0ad4778";
        RequestQueue requestQueue = Volley.newRequestQueue(ThoiTiet5NgayActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ketqua", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");

//                            String nameThanhPho = jsonObjectCity.getString("name");
                            txtName.setText(data.getName());

                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");

                            for(int k=0; k < jsonArrayList.length(); k++) {
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(k);

                                String ngay = jsonObjectList.getString("dt");
                                Long l = Long.valueOf(ngay);
                                Date date = new Date(l*1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String Day = simpleDateFormat.format(date);


                                JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("main");
                                String temp = jsonObjectTemp.getString("temp");

                                Double a = Double.valueOf(temp);

                                String NhietDo = String.valueOf(a.intValue());

                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String status = jsonObjectWeather.getString("description");
                                String icon = jsonObjectWeather.getString("icon");
                                datas.add(new ThoiTietModel(Day,status,icon,NhietDo));
                            }
                            thoiTietAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
}
