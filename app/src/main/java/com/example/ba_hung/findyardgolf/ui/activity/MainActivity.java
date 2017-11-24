package com.example.ba_hung.findyardgolf.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ba_hung.findyardgolf.R;
import com.example.ba_hung.findyardgolf.ui.fragment.CapNhatThongTinCaNhan;
import com.example.ba_hung.findyardgolf.ui.fragment.DanhSachFragment;
import com.example.ba_hung.findyardgolf.ui.fragment.HomeFragment;
import com.example.ba_hung.findyardgolf.ui.fragment.LienHeFragment;
import com.example.ba_hung.findyardgolf.ui.fragment.SanGolfYeuThichFragment;
import com.example.ba_hung.findyardgolf.ui.fragment.ThongTinUngDungFragment;
import com.example.ba_hung.findyardgolf.ui.fragment.ThongTinUserFragment;
import com.example.ba_hung.findyardgolf.ui.fragment.XemItemFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference mData;
    private FirebaseAuth mAuth;
    private ImageView imgAvatar;
    private TextView txtNameUser, txtPhoneNumber;
    String img , name ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xacDinhKetNoiMangHayChua();
        mData = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        AnhXa();
        navigationFunction();
        themFragment(R.id.myLayout, new HomeFragment());
        chinhHeader();
    }

    private void chinhHeader() {
        final String phone = mAuth.getCurrentUser().getPhoneNumber().toString();
        Log.d("phone", phone);
        mData.child("User").child(phone).child("avatar").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                img = dataSnapshot.getValue().toString();
                Log.d("check img ", img+"");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mData.child("User").child(phone).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.getValue().toString();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Glide.with(MainActivity.this).load(img).into(imgAvatar);
                txtPhoneNumber.setText(phone);
                txtNameUser.setText(name);
            }
        }, 1500);

    }

    private void AnhXa() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        txtNameUser = header.findViewById(R.id.nameUser);
        txtPhoneNumber = header.findViewById(R.id.phoneUser);
        imgAvatar = header.findViewById(R.id.imgAvatarUser);
    }

    private void navigationFunction() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void themFragment(int id, Fragment fragment) {
        xacDinhKetNoiMangHayChua();
        String name = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
        transaction.addToBackStack(name);
        transaction.commit();
    }
    public void themVaChinhHeaderFragment(int id, Fragment fragment) {
        xacDinhKetNoiMangHayChua();
        chinhHeader();
        String name = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
        transaction.addToBackStack(name);
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.myLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            themFragment(R.id.myLayout, new HomeFragment());
        } else if (getBaseContext() instanceof SplashView) {
            super.onBackPressed();
            System.exit(0);
        } else if (getBaseContext() instanceof LoginActivity) {
            super.onBackPressed();
            System.exit(0);
        } else if (fragment instanceof HomeFragment) {
            String name = fragment.getClass().getName();
            getSupportFragmentManager().popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            System.exit(0);
        } else if (fragment instanceof DanhSachFragment) {
            String name = fragment.getClass().getName();
            getSupportFragmentManager().popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else if (fragment instanceof XemItemFragment) {
            String name = fragment.getClass().getName();
            getSupportFragmentManager().popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }  else if (fragment instanceof SanGolfYeuThichFragment) {
            String name = fragment.getClass().getName();
            getSupportFragmentManager().popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            themFragment(R.id.myLayout, new HomeFragment());
        } else if (id == R.id.nav_dangXuat) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Đăng xuất thành công.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        } else if (id == R.id.nav_lienHe) {
            themFragment(R.id.myLayout, new LienHeFragment());
        } else if (id == R.id.nav_thongTinApp) {
            themFragment(R.id.myLayout, new ThongTinUngDungFragment());

        } else if (id == R.id.nav_golfLove) {
            themFragment(R.id.myLayout, new SanGolfYeuThichFragment());
        } else if (id == R.id.nav_capNhatThongTinCaNhan) {
            themFragment(R.id.myLayout, new CapNhatThongTinCaNhan());
        } else if(id==R.id.nav_infoUser){
            themFragment(R.id.myLayout, new ThongTinUserFragment());
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void xacDinhKetNoiMangHayChua() {
        ConnectivityManager cm =
                (ConnectivityManager) (MainActivity.this).getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            Toast.makeText(this, "Bạn chưa kết nối mạng.", Toast.LENGTH_SHORT).show();
        }
    }


}
