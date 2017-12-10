package com.example.ba_hung.findyardgolf.ui.fragment.User.UpdateInfoUser;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ba_hung.findyardgolf.R;
import com.example.ba_hung.findyardgolf.ui.activity.MainActivity;
import com.example.ba_hung.findyardgolf.ui.fragment.Home.HomeFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.example.ba_hung.findyardgolf.R.id.txtDiaChi;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdatePersonalInfoFragment extends Fragment implements View.OnClickListener {
    private static final int GALLERY_REQUEST = 1;
    private View mView;
    private TextView txtPhone;
    private Button btnLuu, btnChoose;
    private CircleImageView imgAvatar;
    private EditText edtHoVaTen, edtDiaChi;
    private DatabaseReference mData;
    private FirebaseAuth mAuth;
    private String phoneNumber, linkAvatar, name, diachi;
    private Uri uri = null;
    private StorageReference storage;

    public UpdatePersonalInfoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_update_user_info, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance().getReference();
        anhXa();
        layThongTinDienThoai();
        btnChoose.setOnClickListener(this);
        btnLuu.setOnClickListener(this);
    }

    private void layThongTinDienThoai() {
        phoneNumber = mAuth.getCurrentUser().getPhoneNumber().toString();
        txtPhone.setText(phoneNumber);
        mData.child("User").child(phoneNumber).child("avatar").addListenerForSingleValueEvent(new ValueEventListener() {
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

        mData.child("User").child(phoneNumber).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.getValue().toString();
                edtHoVaTen.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mData.child("User").child(phoneNumber).child("diaChi").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                diachi = dataSnapshot.getValue().toString();
                edtDiaChi.setText(diachi);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void anhXa() {
        txtPhone = mView.findViewById(R.id.txtPhone);
        btnLuu = mView.findViewById(R.id.btnLuuThongTin);
        edtHoVaTen = mView.findViewById(R.id.txtHoVaTen);
        edtDiaChi = mView.findViewById(txtDiaChi);
        btnChoose = mView.findViewById(R.id.btnChooseAvatar);
        imgAvatar = mView.findViewById(R.id.imgAvatarUser);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLuuThongTin) {

            mData.child("User").child(phoneNumber).child("name").setValue(edtHoVaTen.getText().toString());

            mData.child("User").child(phoneNumber).child("diaChi").setValue(edtDiaChi.getText().toString());

            capNhatAnhDaiDien();

            Toast.makeText(getActivity(), "Thay đổi thông tin thành công!", Toast.LENGTH_SHORT).show();
            ((MainActivity) getActivity()).themVaChinhHeaderFragment(R.id.myLayout, new HomeFragment());
        }
//        else if(view.getId() == R.id.btnChooseAvatar){
//            Intent intent = new Intent(Intent.ACTION_PICK);
//            intent.setType("image/*");
//            startActivityForResult(intent,GALLERY_REQUEST);
//        }
        else if (view.getId() == R.id.btnChooseAvatar) {

            new AlertDialog.Builder(getActivity())
                    .setTitle("Avatar")
                    .setMessage("Bạn có chắc chắn muốn thay đổi ảnh dại diện?")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(
                                    Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                            startActivityForResult(intent, GALLERY_REQUEST);
//                            Intent intent = new Intent();
//                            intent.setType("image/*");
//                            intent.setAction(Intent.ACTION_PICK);
//                            startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), GALLERY_REQUEST);
//                            startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GALLERY_REQUEST);
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
        }

    }


    private void capNhatAnhDaiDien() {
        StorageReference filePath = null;
        if (uri != null) {
            filePath = storage.child("Avatar User").child(uri.getLastPathSegment());
        }

        if (filePath != null) {
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadurl = taskSnapshot.getDownloadUrl();
                    mData.child("User").child(phoneNumber).child("avatar").setValue(downloadurl.toString());
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(getActivity(), "Vui lòng thử lại", Toast.LENGTH_SHORT).show();
            } else {
                uri = data.getData();
                imgAvatar.setImageURI(uri);
            }
        }

    }

//    @Override
//     public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//
//        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && null != data) {
//            Uri uri = data.getData();
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//            Cursor cursor = getActivity().getContentResolver().query(uri,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//
//            imgAvatar.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//
//        }
//    }

}
