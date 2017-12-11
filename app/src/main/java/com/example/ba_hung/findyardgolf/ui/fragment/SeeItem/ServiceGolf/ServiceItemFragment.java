package com.example.ba_hung.findyardgolf.ui.fragment.SeeItem.ServiceGolf;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ba_hung.findyardgolf.R;
import com.example.ba_hung.findyardgolf.bean.SanGolfModel;
import com.example.ba_hung.findyardgolf.ui.adapter.ServiceItemAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceItemFragment extends Fragment {
    private View mView;
    private SanGolfModel sanGolf;
    private RecyclerView rcvDichVu;
    private ArrayList<String> tenDichVuGia, tenDichVuThat ;
    public ServiceItemFragment() {
        // Required empty public constructor
    }

    public ServiceItemFragment(SanGolfModel sanGolf) {
        // Required empty public constructor

        this.sanGolf = sanGolf;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_service, container, false);
        return  mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXa();
        layTenDichVu();
        suDungReCyClerView();
    }

    private void layTenDichVu() {
        tenDichVuGia = new ArrayList<>(Arrays.asList(sanGolf.getService().split(";")));
        tenDichVuThat = new ArrayList<>();

        for(int i=0; i<tenDichVuGia.size(); i++){
            tenDichVuThat.add(trimspace(tenDichVuGia.get(i)));
        }

    }

    private void suDungReCyClerView() {
        ServiceItemAdapter adapter = new ServiceItemAdapter(getActivity(), tenDichVuThat);
        RecyclerView.LayoutManager giaoDien = new LinearLayoutManager(getActivity());
        rcvDichVu.setLayoutManager(giaoDien);
        ScaleInAnimationAdapter scale = new ScaleInAnimationAdapter(adapter);
        rcvDichVu.setAdapter(scale);
    }

    private void anhXa() {
        rcvDichVu = mView.findViewById(R.id.rcvDichVu);
        
    }

    public String trimspace(String str)
    {
        str = str.replaceAll("(^\\s+|\\s+$)", "");
        return str;
    }


}
