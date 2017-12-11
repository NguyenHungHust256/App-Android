package com.example.ba_hung.findyardgolf.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ba_hung.findyardgolf.R;
import com.example.ba_hung.findyardgolf.bean.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Phone";
    private EditText edtSDT, edtVertify;
    private Button btnLayMa, btnDangNhap;
    private String mVerificationId;
    private FirebaseAuth mAuth;
//    FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mData;
    private String SDT;
    private boolean check = true;

    public static final Pattern VALID_PHONE_REGEX =
            Pattern.compile("(\\+84)\\d{9,10}", Pattern.CASE_INSENSITIVE);

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Toast.makeText(LoginActivity.this, "Bạn đang đăng nhập với tài khoản " + currentUser.getPhoneNumber(), Toast.LENGTH_LONG).show();
            OpenMainActivity();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();
        khoiTaoQuyenTruyNhap();
        btnLayMa.setOnClickListener(this);
        btnDangNhap.setOnClickListener(this);
    }

    private void khoiTaoQuyenTruyNhap() {
        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    private void OpenMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void anhXa() {
        edtSDT = findViewById(R.id.edtSDT);
        edtVertify = findViewById(R.id.edtVertify);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnLayMa = findViewById(R.id.btnLayMa);
        edtSDT.setText("+84");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLayMa) {
            String phoneNumber = edtSDT.getText().toString();
            if (TextUtils.isEmpty(phoneNumber)) {
                Toast.makeText(this, "Mời bạn nhập số điện thoại.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (validate(phoneNumber)) {
                SDT = edtSDT.getText().toString();
                edtSDT.setEnabled(false);
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phoneNumber, 40, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                                Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);
                                signInWithCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(FirebaseException e) {
                                //incorrect phone number, verification code, emulator, etc.
                                Toast.makeText(LoginActivity.this, "Xác nhận thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                //now the code has been sent, save the verificationId we may need it
                                super.onCodeSent(verificationId, forceResendingToken);
                                Toast.makeText(LoginActivity.this, "Đã gửi tin nhắn", Toast.LENGTH_SHORT).show();
                                mVerificationId = verificationId;
                            }

                            @Override
                            public void onCodeAutoRetrievalTimeOut(String verificationId) {
                                //called after timeout if onVerificationCompleted has not been called
                                super.onCodeAutoRetrievalTimeOut(verificationId);
                                edtSDT.setEnabled(true);
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Bạn nhập số điện thoại không đúng", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.btnDangNhap) {
            if (validate(edtSDT.getText().toString()))
                signIn();
            else {
                Toast.makeText(LoginActivity.this, "Mời bạn nhập số điện thoại chính xác", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void signIn() {
        String code = edtVertify.getText().toString();
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(LoginActivity.this, "Mời bạn nhập mã xác nhận", Toast.LENGTH_SHORT).show();
        } else {
            signInWithCredential(PhoneAuthProvider.getCredential(mVerificationId, code));
        }
    }

    private void signInWithCredential(PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            kiemTraSuXuatHienCuaTaiKhoan();
                            themTaiKhoanVaoDataVaMoApp();

                        } else {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                            Log.d("thatbai","" + task.getException().getMessage());
                        }
                    }
                });

    }

    private void themTaiKhoanVaoDataVaMoApp() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (check) {
                    Log.d("check", check + "");
                    UserModel user = new UserModel(SDT);
                    mData.child("User").child(SDT).setValue(user);
                }
            }
        }, 1000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                OpenMainActivity();
            }
        }, 2000);
    }

    // comment
    private void kiemTraSuXuatHienCuaTaiKhoan() {
        mData.child("User").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if ((dataSnapshot.child("taiKhoan").getValue().toString()).equals(SDT)) {
                    check = false;
                }
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


    public static boolean validate(String phoneNumberStr) {
        Matcher matcher = VALID_PHONE_REGEX.matcher(phoneNumberStr);
        return matcher.find();
    }

}
