package com.example.ba_hung.findyardgolf.ui.fragment.ListItem;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.ba_hung.findyardgolf.R;
import com.example.ba_hung.findyardgolf.bean.SanGolfModel;
import com.example.ba_hung.findyardgolf.controller.ProgressLoading;
import com.example.ba_hung.findyardgolf.ui.adapter.YardGolfAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListItemFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private DatabaseReference mData;
    private View mView;
    RecyclerView rvcSanGolf;
    Spinner spnTinh;
    String danhDauMien;
    YardGolfAdapter adapter;
    ArrayList<SanGolfModel> datas = new ArrayList<>();
    ArrayList<String> dataTinh = new ArrayList<>();


    public ListItemFragment() {

    }

    public ListItemFragment(String danhDauMien) {
        this.danhDauMien = danhDauMien;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_danh_sach, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        mData = FirebaseDatabase.getInstance().getReference();
        ProgressLoading progressLoading = new ProgressLoading(mView);
        progressLoading.xuLyLoading(mView, 1500, R.id.progressBar_loading);
        AnhXa();
        InitDataTungTinhChoSpinner();
        RecyclerViewKhiAnButton(datas);
        setListenerForSpinner();
    }

    private void setListenerForSpinner() {
        final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, dataTinh);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnTinh.setAdapter(adapter);
        spnTinh.setOnItemSelectedListener(this);
    }


    private void AnhXa() {
        rvcSanGolf = mView.findViewById(R.id.rcvSanGolf);
        spnTinh = mView.findViewById(R.id.spnTinh);
    }

    private void RecyclerViewKhiAnButton(ArrayList<SanGolfModel> datas) {
        adapter= new YardGolfAdapter(getActivity(), datas);
        RecyclerView.LayoutManager giaoDien = new LinearLayoutManager(getActivity());
        rvcSanGolf.setLayoutManager(giaoDien);
        ScaleInAnimationAdapter scale = new ScaleInAnimationAdapter(adapter);
        rvcSanGolf.setAdapter(scale);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String tenTinh = spnTinh.getSelectedItem().toString();
        if (!tenTinh.equals(danhDauMien)) {
            datas.clear();
            mData.child("SanGolf").child(danhDauMien).child(tenTinh).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    themeDuLieuVaoData(dataSnapshot);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            datas.clear();

            for(int k = 1; k< dataTinh.size(); k++){
                mData.child("SanGolf").child(dataTinh.get(0)).child(dataTinh.get(k)).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        themeDuLieuVaoData(dataSnapshot);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    private void themeDuLieuVaoData(DataSnapshot dataSnapshot) {
        String address = dataSnapshot.child("Address").getValue().toString();
        String city = dataSnapshot.child("City").getValue().toString();
        String description = dataSnapshot.child("Description").getValue().toString();
        String image = dataSnapshot.child("Image").getValue().toString();
        double latitude = Double.valueOf(dataSnapshot.child("Latitude").getValue().toString());
        double longtitude = Double.valueOf(dataSnapshot.child("Longtitude").getValue().toString());
        int like = Integer.valueOf(dataSnapshot.child("Like").getValue().toString());
        String name = dataSnapshot.child("Name").getValue().toString();
        String phone = dataSnapshot.child("Phone").getValue().toString();
        String price = dataSnapshot.child("Price").getValue().toString();
        String service = dataSnapshot.child("Service").getValue().toString();
        int star = Integer.valueOf(dataSnapshot.child("Star").getValue().toString());
        String website = dataSnapshot.child("Website").getValue().toString();
        String avatar = dataSnapshot.child("avatar").getValue().toString();

        SanGolfModel sanGolf = new SanGolfModel(address, city, description, image, latitude, longtitude, like, name, phone, price, service, star, website, avatar);
        datas.add(sanGolf);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void InitDataTungTinhChoSpinner() {
        if (danhDauMien.equals("Miền Bắc")) {
            if (dataTinh.size() == 0) {
                dataTinh.add("Miền Bắc");
                dataTinh.add("Hà Nội");
                dataTinh.add("Vĩnh Phúc");
                dataTinh.add("Hải Phòng");
                dataTinh.add("Hải Dương");
                dataTinh.add("Bắc Giang");
                dataTinh.add("Hòa Bình");
                dataTinh.add("Ninh Bình");
                dataTinh.add("Quảng Ninh");
            } else return;

        } else if (danhDauMien.equals("Miền Trung")) {
            if (dataTinh.size() == 0) {
                dataTinh.add("Miền Trung");
                dataTinh.add("Bình Thuận");
                dataTinh.add("Bình Định");
                dataTinh.add("Nghệ An");
                dataTinh.add("Quảng Nam");
                dataTinh.add("Thừa Thiên Huế");
                dataTinh.add("Đà Nẵng");
            }
        } else if (danhDauMien.equals("Miền Nam")) {
            if (dataTinh.size() == 0) {
                dataTinh.add("Miền Nam");
                dataTinh.add("Bình Dương");
                dataTinh.add("Khánh Hòa");
                dataTinh.add("Kiên Giang");
                dataTinh.add("Lâm Đồng");
                dataTinh.add("TP Hồ Chí Minh");
                dataTinh.add("Vũng Tàu");
                dataTinh.add("Đồng Nai");
            }
        }
    }


}
