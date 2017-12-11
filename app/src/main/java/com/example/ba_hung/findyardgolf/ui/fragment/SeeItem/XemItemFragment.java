package com.example.ba_hung.findyardgolf.ui.fragment.SeeItem;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ba_hung.findyardgolf.R;
import com.example.ba_hung.findyardgolf.bean.SanGolfModel;
import com.example.ba_hung.findyardgolf.ui.adapter.MyPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class XemItemFragment extends Fragment {
    private View mView;
    private SanGolfModel sanGolfModel;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    public XemItemFragment(SanGolfModel sanGolfModel) {
        this.sanGolfModel = sanGolfModel;
    }


    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_xem_item, container, false);
        viewPager = mView.findViewById(R.id.viewpager);
        tabLayout =  mView.findViewById(R.id.tabLayout);
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager(), sanGolfModel);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.details);
        tabLayout.getTabAt(1).setIcon(R.drawable.support);
        tabLayout.getTabAt(2).setIcon(R.drawable.map);
        tabLayout.getTabAt(3).setIcon(R.drawable.weather);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


}
