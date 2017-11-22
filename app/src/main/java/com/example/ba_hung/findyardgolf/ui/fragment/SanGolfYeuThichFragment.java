package com.example.ba_hung.findyardgolf.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ba_hung.findyardgolf.R;
import com.example.ba_hung.findyardgolf.bean.SanGolfModel;
import com.example.ba_hung.findyardgolf.ui.adapter.SanGolfAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SanGolfYeuThichFragment extends Fragment {
    private RecyclerView rcvSanGolfYeuThich;
    private View mView;
    private SanGolfAdapter adapter;
    private FirebaseAuth mAuth;
    private DatabaseReference mData;
    private ArrayList nhungSanGolfDuocLike  = new ArrayList();
    private ArrayList<SanGolfModel> datas = new ArrayList<>();
    private static final String[] mien = {"Miền Bắc", "Miền Trung", "Miền Nam"};
    private String[] mienBac = {"Hà Nội", "Hải Dương", "Hải Phòng", "Vĩnh Phúc", "Bắc Giang", "Quảng Ninh", "Hòa Bình", "Ninh Bình"};
    private String[] mienTrung = {"Bình Thuận", "Bình Định", "Nghệ An", "Quảng Nam", "Thừa Thiên Huế", "Đà Nẵng"};
    private String[] mienNam = {"Bình Dương", "Khánh Hòa", "Kiên Giang", "Lâm Đồng", "TP Hồ Chí Minh", "Vũng Tàu", "Đồng Nai"};

    public SanGolfYeuThichFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_san_golf_yeu_thich, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        rcvSanGolfYeuThich = mView.findViewById(R.id.rcvSanGolfYeuThich);

        adapter = new SanGolfAdapter(getActivity(), datas);
        RecyclerView.LayoutManager giaoDien = new LinearLayoutManager(getActivity());
        rcvSanGolfYeuThich.setLayoutManager(giaoDien);
        ScaleInAnimationAdapter scale = new ScaleInAnimationAdapter(adapter);
        rcvSanGolfYeuThich.setAdapter(scale);
        XuLyDataDaLike();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                layDuLieuChoData();
            }
        }, 1000);
        Log.d("check xuLyDataDaLike", nhungSanGolfDuocLike.size()+"");

        Log.d("check LayduLieuChoData ", datas.size()+"");
    }

    private void XuLyDataDaLike() {
        String phone = mAuth.getCurrentUser().getPhoneNumber().toString();
        mData.child("User").child(phone).child("sanGolfDaLike").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sanGolfDaLike = dataSnapshot.getValue().toString();
                nhungSanGolfDuocLike =new ArrayList(Arrays.asList( sanGolfDaLike.split(";")));
                Log.d("check du lieu", nhungSanGolfDuocLike.size()+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void layDuLieuChoData() {
        String[] tinh = mienBac;
        if(datas.size()==0){
            for (int i = 0; i < mien.length; i++) {
                if (i == 1)
                    tinh = mienTrung;
                else if (i == 2)
                    tinh = mienNam;
                for(int j=0; j<tinh.length;j++){
                    mData.child("SanGolf").child(mien[i]).child(tinh[j]).addChildEventListener(new ChildEventListener() {
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
        Log.d("check du lieu trong them du lieu vao data ", nhungSanGolfDuocLike.size()+"");
        for(int i=0; i<nhungSanGolfDuocLike.size();i++){
            if(nhungSanGolfDuocLike.get(i).equals(name)){
                SanGolfModel sanGolf = new SanGolfModel(address, city, description, image, latitude, longtitude, like, name, phone, price, service, star, website, avatar);
                datas.add(sanGolf);
                adapter.notifyDataSetChanged();
            }
        }

    }
}
