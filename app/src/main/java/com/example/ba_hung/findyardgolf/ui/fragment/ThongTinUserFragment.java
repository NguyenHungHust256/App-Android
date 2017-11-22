package com.example.ba_hung.findyardgolf.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ba_hung.findyardgolf.R;
import com.example.ba_hung.findyardgolf.ui.activity.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThongTinUserFragment extends Fragment {
    private  View mView;
    private TextView txtName, txtPhone, txtDiaChi;
    private Button btnOk;
    private DatabaseReference mData;
    private FirebaseAuth mAuth;
    private String phone, name, diachi;

    public ThongTinUserFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_thong_tin_user, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        AnhXa();
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
    }
    private void chinhHeader() {
        phone = mAuth.getCurrentUser().getPhoneNumber().toString();

        mData.child("User").child(phone).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mData.child("User").child(phone).child("diaChi").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                diachi = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                txtPhone.setText(phone);
                txtName.setText(name);
                txtDiaChi.setText(diachi);
            }
        }, 1500);

    }
}
