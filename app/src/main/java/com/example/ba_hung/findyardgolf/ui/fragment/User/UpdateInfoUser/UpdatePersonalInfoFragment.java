package com.example.ba_hung.findyardgolf.ui.fragment.User.UpdateInfoUser;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ba_hung.findyardgolf.R;
import com.example.ba_hung.findyardgolf.ui.activity.MainActivity;
import com.example.ba_hung.findyardgolf.ui.fragment.Home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdatePersonalInfoFragment extends Fragment implements View.OnClickListener {
    private static final int PICK_IMAGE = 100;
    private View mView;
    private TextView txtPhone;
    private Button btnLuu;
    private EditText edtHoVaTen, edtDiaChi;
    private DatabaseReference mData;
    private FirebaseAuth mAuth;
    private String phoneNumber;
    public UpdatePersonalInfoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_update_user_info, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        anhXa();
        phoneNumber = mAuth.getCurrentUser().getPhoneNumber().toString();
        txtPhone.setText(phoneNumber);
        btnLuu.setOnClickListener(this);
    }

    private void anhXa() {
        txtPhone = mView.findViewById(R.id.txtPhone);
        btnLuu = mView.findViewById(R.id.btnLuuThongTin);
        edtHoVaTen = mView.findViewById(R.id.txtHoVaTen);
        edtDiaChi = mView.findViewById(R.id.txtDiaChi);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnLuuThongTin){
            if(!(edtHoVaTen.getText().toString()).equals("")){
                mData.child("User").child(phoneNumber).child("name").setValue(edtHoVaTen.getText().toString());
            }

            if(!(edtDiaChi.getText().toString()).equals(""))
                mData.child("User").child(phoneNumber).child("diaChi").setValue(edtDiaChi.getText().toString());
            Toast.makeText(getActivity(), "Thay đổi thông tin thành công!", Toast.LENGTH_SHORT).show();
            edtDiaChi.setText("");
            edtHoVaTen.setText("");
            ((MainActivity)getActivity()).themVaChinhHeaderFragment(R.id.myLayout, new HomeFragment());
        }
    }


}
