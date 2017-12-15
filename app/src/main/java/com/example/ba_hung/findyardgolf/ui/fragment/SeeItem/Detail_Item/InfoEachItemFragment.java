package com.example.ba_hung.findyardgolf.ui.fragment.SeeItem.Detail_Item;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.ba_hung.findyardgolf.R;
import com.example.ba_hung.findyardgolf.bean.SanGolfModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoEachItemFragment extends Fragment implements View.OnClickListener, OnLikeListener {
    private View mView;
    private ImageView img1, img2, img3, back, back1, back2, next, next1, next2;
    private ViewFlipper mViewFlipper;
    private Button btnBooking, btnOK;
    private LikeButton btnLike;
    //    private float xDown, xUp;
//    private float y;
    private TextView txtGia, txtMoTa, txtWeb, txtDiaChi, txtTenSanGolf;
    private RatingBar ratingBar, ratingForUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mData, mDataSanGolf;
    private String phone;
    private SanGolfModel sanGolf = new SanGolfModel();
    public float soSao;

    public InfoEachItemFragment() {

    }

    public InfoEachItemFragment(SanGolfModel sanGolf) {
        this.sanGolf = sanGolf;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_info_each_item, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mDataSanGolf = mData.child("SanGolf").child(sanGolf.getMien()).child(sanGolf.getTinh()).child(sanGolf.getKey()+"")
                .child("Star").getRef();
        phone = mAuth.getCurrentUser().getPhoneNumber().toString();

        AnhXa();
        loadAnh();
        mViewFlipper = view.findViewById(R.id.viewFlipper);
        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(1000);
        mViewFlipper.setInAnimation(getActivity(), android.R.anim.fade_in);
        mViewFlipper.setOutAnimation(getActivity(), android.R.anim.fade_out);
        LayDuLieu();
        xuLyNutLikeSangHayTat();
        btnBooking.setOnClickListener(this);
        btnLike.setOnLikeListener(this);
        btnOK.setOnClickListener(this);

        back.setClickable(true);
        back.setOnClickListener(this);

        back1.setClickable(true);
        back1.setOnClickListener(this);

        back2.setClickable(true);
        back2.setOnClickListener(this);

        next.setClickable(true);
        next.setOnClickListener(this);

        next1.setClickable(true);
        next1.setOnClickListener(this);

        next2.setClickable(true);
        next2.setOnClickListener(this);
    }

    private void xuLyNutLikeSangHayTat() {

        mData.child("User").child(phone).child("sanGolfDaLike").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sanGolfDaLike = dataSnapshot.getValue().toString();
                boolean check = true;  // Neu true thi chua duoc like
                String[] nhungSanGolfDuocLike = sanGolfDaLike.split(";");
                for (int i = 0; i < nhungSanGolfDuocLike.length; i++) {
                    if ((!(nhungSanGolfDuocLike[i].equals(""))) && (nhungSanGolfDuocLike[i].equals(sanGolf.getName()))) {
                        check = false;
                        break;
                    }
                }

                if (check) {
                    btnLike.setLiked(false);
                } else {
                    btnLike.setLiked(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void LayDuLieu() {
        txtDiaChi.setText(sanGolf.getAddress());
        txtWeb.setText(sanGolf.getWebsite());
        txtGia.setText(sanGolf.getPrice());
        txtMoTa.setText(sanGolf.getDescription() + "\n");
        txtTenSanGolf.setText(sanGolf.getName());
        ratingBar.setRating( sanGolf.getStar());

        layDuLieuChoRatingCuaPhone();

    }

    private void layDuLieuChoRatingCuaPhone() {

        mDataSanGolf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               if(dataSnapshot.hasChild(phone)){
                   float soSaoCuaPhone= Float.valueOf(dataSnapshot.child(phone).getValue().toString());
                   ratingForUser.setRating(soSaoCuaPhone);
                   Log.d("kiemtra", dataSnapshot.getValue().toString());
               }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void loadAnh() {
//
        String[] linkImage = sanGolf.getImage().split(";");
        Glide.with(getActivity()).load(trimspace(linkImage[0])).into(img1);
        Glide.with(getActivity()).load(trimspace(linkImage[1])).into(img2);
        Glide.with(getActivity()).load(trimspace(linkImage[2])).into(img3);
        Log.d("Link1", linkImage[0]);
        Log.d("Link2", linkImage[1]);
        Log.d("Link3", linkImage[2]);

    }

    public String trimspace(String str) {
        str = str.replaceAll("(^\\s+|\\s+$)", "");
        return str;
    }

    private void AnhXa() {
        btnBooking = mView.findViewById(R.id.btnBookingOneGolf);
        img1 = mView.findViewById(R.id.img1);
        img2 = mView.findViewById(R.id.img2);
        img3 = mView.findViewById(R.id.img3);
        txtGia = mView.findViewById(R.id.txtGia);
        txtMoTa = mView.findViewById(R.id.txtMoTa);
        txtDiaChi = mView.findViewById(R.id.txtDiaChiGolf);
        txtWeb = mView.findViewById(R.id.txtTrangWeb);
        txtTenSanGolf = mView.findViewById(R.id.txtTenSanGolf);
        btnLike = mView.findViewById(R.id.btnLike);
        ratingBar = mView.findViewById(R.id.ratingOneGolf);
        ratingForUser = mView.findViewById(R.id.ratingBarForUser);
        btnOK = mView.findViewById(R.id.btnOkRating);
        back = mView.findViewById(R.id.back);
        back1 = mView.findViewById(R.id.back1);
        back2 = mView.findViewById(R.id.back2);
        next = mView.findViewById(R.id.next);
        next1 = mView.findViewById(R.id.next1);
        next2 = mView.findViewById(R.id.next2);
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnBookingOneGolf) {
            String number = sanGolf.getPhone();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + number));
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            (getActivity()).startActivity(intent);
        } else if (view.getId() == R.id.back || view.getId() == R.id.back1 || view.getId() == R.id.back2) {
            mViewFlipper.showPrevious();
        } else if (view.getId() == R.id.next || view.getId() == R.id.next1 || view.getId() == R.id.next2) {
            mViewFlipper.showNext();
        } else if(view.getId() == R.id.btnOkRating){
            mDataSanGolf.child(phone).setValue(ratingForUser.getRating());
            Toast.makeText(getActivity(), "Số sao đánh giá của bạn là: "+ratingForUser.getRating()+"\nCảm ơn bạn đã đánh giá!", Toast.LENGTH_SHORT).show();
            soSao=0;

            mDataSanGolf.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("kiemtra", dataSnapshot.getChildrenCount()+"");
                    for(DataSnapshot child: dataSnapshot.getChildren()){
                        if(!child.getKey().equals("StarTB")){
                            soSao = soSao + Float.valueOf(child.getValue().toString());
                        }
                    }
                    float soTaiKhoanDanhGia = (float) (dataSnapshot.getChildrenCount()-1);
                    float soSaoTBNew = soSao/soTaiKhoanDanhGia;
                    mDataSanGolf.child("StarTB").setValue(soSaoTBNew+"");

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

  }
    }

    @Override
    public void liked(LikeButton likeButton) {
        mData.child("User").child(phone).child("sanGolfDaLike").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sanGolfDaLike = dataSnapshot.getValue().toString();
                Log.d("check sanGolfDaLike", sanGolfDaLike);
                boolean check = true;  // Neu true thi chua duoc like
                String[] nhungSanGolfDuocLike = sanGolfDaLike.split(";");
                Log.d("check Length nhungSanGolfDuocDat = ", String.valueOf(nhungSanGolfDuocLike.length));
                for (int i = 0; i < nhungSanGolfDuocLike.length; i++) {
                    if ((!(nhungSanGolfDuocLike[i].equals(""))) && (nhungSanGolfDuocLike[i].equals(sanGolf.getName()))) {
                        Log.d("check nhungsangolfduoclike", nhungSanGolfDuocLike[i]);
                        check = false;
                        break;
                    }
                }
                Log.d("check", check + "");
                if (check) {
                    if (sanGolfDaLike.equals("")) {
                        sanGolfDaLike = sanGolfDaLike + sanGolf.getName();
                    } else {
                        sanGolfDaLike = sanGolfDaLike + ";" + sanGolf.getName();
                    }
                    mData.child("User").child(phone).child("sanGolfDaLike").setValue(sanGolfDaLike);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void unLiked(LikeButton likeButton) {
        phone = mAuth.getCurrentUser().getPhoneNumber().toString();
        mData.child("User").child(phone).child("sanGolfDaLike").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean check = false;  // Neu false thi chua like
                int a = 0;
                ArrayList<String> nhungSanGolfDuocLike;
                String sanGolfDaLike = dataSnapshot.getValue().toString();
                Log.d("check sanGolfDaLike", sanGolfDaLike);

                nhungSanGolfDuocLike = new ArrayList<>(Arrays.asList(sanGolfDaLike.split(";")));
                for (int i = 0; i < nhungSanGolfDuocLike.size(); i++) {
                    if ((!(nhungSanGolfDuocLike.get(i).equals(""))) && (nhungSanGolfDuocLike.get(i).equals(sanGolf.getName()))) {
                        check = true;
                        a = i;
                        break;
                    }
                }

                if (check) {
                    sanGolfDaLike = "";

                    for (int i = 0; i < a; i++) {
                        if (sanGolfDaLike.equals("")) {
                            sanGolfDaLike = sanGolfDaLike + nhungSanGolfDuocLike.get(i);
                        } else {
                            sanGolfDaLike = sanGolfDaLike + ";" + nhungSanGolfDuocLike.get(i);
                        }
                    }
                    for (int i = a + 1; i < nhungSanGolfDuocLike.size(); i++) {
                        if (sanGolfDaLike.equals("")) {
                            sanGolfDaLike = sanGolfDaLike + nhungSanGolfDuocLike.get(i);
                        } else {
                            sanGolfDaLike = sanGolfDaLike + ";" + nhungSanGolfDuocLike.get(i);
                        }
                    }

                    mData.child("User").child(phone).child("sanGolfDaLike").setValue(sanGolfDaLike);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
