package com.example.ba_hung.findyardgolf.ui.fragment.User.InfomationUser;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ba_hung.findyardgolf.R;
import com.example.ba_hung.findyardgolf.controller.ProgressLoading;
import com.example.ba_hung.findyardgolf.ui.activity.MainActivity;
import com.example.ba_hung.findyardgolf.ui.fragment.Home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalUserFragment extends Fragment {
    private  View mView;
    private TextView txtName, txtPhone, txtDiaChi;
    private Button btnOk;
    private DatabaseReference mData;
    private FirebaseAuth mAuth;
    private String phone, name, diachi, linkAvatar;
    private CircleImageView imgAvatar;
    public PersonalUserFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_info_user, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        AnhXa();
        ProgressLoading progress = new ProgressLoading(mView);
        progress.xuLyLoading(mView, 1000, R.id.progressBar_loading);
        chinhHeader();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).themFragment(R.id.myLayout, new HomeFragment());
            }
        });
    }


    private void AnhXa() {
        txtName = mView.findViewById(R.id.txtHoVaTen);
        txtDiaChi = mView.findViewById(R.id.txtDiaChi);
        txtPhone = mView.findViewById(R.id.txtPhone);
        btnOk = mView.findViewById(R.id.btnOk);
        imgAvatar = mView.findViewById(R.id.imgAvatar);
    }
    private void chinhHeader() {
        phone = mAuth.getCurrentUser().getPhoneNumber().toString();
        txtPhone.setText(phone);
        mData.child("User").child(phone).child("avatar").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                linkAvatar = dataSnapshot.getValue().toString();
                Glide.with(getActivity())
                        .load(linkAvatar)
                        .into(imgAvatar);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mData.child("User").child(phone).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.getValue().toString();
                txtName.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mData.child("User").child(phone).child("diaChi").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                diachi = dataSnapshot.getValue().toString();
                txtDiaChi.setText(diachi);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}