package com.example.ba_hung.findyardgolf.ui.fragment.Home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.ba_hung.findyardgolf.R;
import com.example.ba_hung.findyardgolf.bean.SanGolfModel;
import com.example.ba_hung.findyardgolf.controller.Suggestion;
import com.example.ba_hung.findyardgolf.ui.activity.MainActivity;
import com.example.ba_hung.findyardgolf.ui.fragment.ListItem.ListItemFragment;
import com.example.ba_hung.findyardgolf.ui.fragment.SeeItem.XemItemFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private View mView;
    Button btnMienBac, btnMienTrung, btnMienNam;
    TextView txtKhuVucSanGolf;
    FloatingSearchView searchView;
    private List<Suggestion> mSuggestions = new ArrayList<>();
    DatabaseReference mData;
    private static final String[] mien = {"Miền Bắc", "Miền Trung", "Miền Nam"};

    private String[] mienBac = {"Hà Nội", "Hải Dương", "Hải Phòng", "Vĩnh Phúc", "Bắc Giang", "Quảng Ninh", "Hòa Bình", "Ninh Bình"};
    private String[] mienTrung = {"Bình Thuận", "Bình Định", "Nghệ An", "Quảng Nam", "Thừa Thiên Huế", "Đà Nẵng"};
    private String[] mienNam = {"Bình Dương", "Khánh Hòa", "Kiên Giang", "Lâm Đồng", "TP Hồ Chí Minh", "Vũng Tàu", "Đồng Nai"};


    public HomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mData = FirebaseDatabase.getInstance().getReference();
        AnhXa();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
//        layDuLieuChoData();
        xuLySearchGolf();
        btnMienBac.setOnClickListener(this);
        btnMienTrung.setOnClickListener(this);
        btnMienNam.setOnClickListener(this);
    }



    private void layDuLieuChoData() {

        String[] tinh = mienBac;
        if(mSuggestions.size()==0){
            for (int i = 0; i < mien.length; i++) {
                if (i == 1)
                    tinh = mienTrung;
                else if (i == 2)
                    tinh = mienNam;
                for(int j=0; j<tinh.length;j++){
                    mData.child("SanGolf").child(mien[i]).child(tinh[j]).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
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
                            if(mSuggestions.size()<45){
                                mSuggestions.add(new Suggestion(sanGolf.getName(), sanGolf));
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
            }
        }
    }


    private void AnhXa() {
        txtKhuVucSanGolf = mView.findViewById(R.id.txtKhuVucSanGolf);
        searchView = mView.findViewById(R.id.floating_search_view);
        btnMienBac = mView.findViewById(R.id.btnMienBac);
        btnMienTrung = mView.findViewById(R.id.btnMienTrung);
        btnMienNam = mView.findViewById(R.id.btnMienNam);
    }



    private void xuLySearchGolf() {

        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    searchView.clearSuggestions();
                } else {
                    searchView.showProgress();
                    searchView.swapSuggestions(getSuggestion(newQuery));
                    searchView.hideProgress();
                }
            }
        });
        searchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                layDuLieuChoData();
                searchView.setLeftMenuOpen(true);
                searchView.openMenu(true);
                searchView.showProgress();
                searchView.swapSuggestions(getSuggestion(searchView.getQuery()));
                searchView.hideProgress();
            }


            @Override
            public void onFocusCleared() {
                mSuggestions.clear();


            }
        });
        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                Suggestion suggestion = (Suggestion) searchSuggestion;
                searchView.clearSearchFocus();
                searchView.clearQuery();
                ((MainActivity)getActivity()).themFragment(R.id.myLayout, new XemItemFragment(suggestion.getSanGolf()));

            }

            @Override
            public void onSearchAction(String currentQuery) {
            }
        });



    }

    private List<Suggestion> getSuggestion(String query) {
        List<Suggestion> suggestions = new ArrayList<>();
        for (Suggestion suggestion : mSuggestions) {
            if (suggestion.getName().toLowerCase().contains(query.toLowerCase())) {
                suggestions.add(suggestion);
            }
        }
        return suggestions;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnMienBac) {
            ((MainActivity) getActivity()).themFragment(R.id.myLayout, new ListItemFragment("Miền Bắc"));

        } else if (view.getId() == R.id.btnMienTrung)
        {
            ((MainActivity) getActivity()).themFragment(R.id.myLayout, new ListItemFragment("Miền Trung"));
        } else if (view.getId() == R.id.btnMienNam) {
            ((MainActivity) getActivity()).themFragment(R.id.myLayout, new ListItemFragment("Miền Nam"));
        }
    }

}

