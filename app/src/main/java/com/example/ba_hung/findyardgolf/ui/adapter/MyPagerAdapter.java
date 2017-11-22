package com.example.ba_hung.findyardgolf.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ba_hung.findyardgolf.bean.SanGolfModel;
import com.example.ba_hung.findyardgolf.ui.fragment.BanDoFragment;
import com.example.ba_hung.findyardgolf.ui.fragment.ChiTietFragment;
import com.example.ba_hung.findyardgolf.ui.fragment.DichVuFragment;

/**
 * Created by ba-hung on 09/10/2017.
 **/

public class MyPagerAdapter  extends FragmentPagerAdapter {
    private static final String[] tieuDe = {"Chi tiết", "Dịch vụ", "Bản đồ" };
    SanGolfModel sanGolfModel;
    public MyPagerAdapter(FragmentManager manager,SanGolfModel sanGolfModel ) {
        super(manager);
        this.sanGolfModel = sanGolfModel;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new ChiTietFragment(sanGolfModel);
        }
        else if (position == 1)
        {
            fragment = new DichVuFragment(sanGolfModel);
        }
        else if (position == 2)
        {
            fragment = new BanDoFragment(sanGolfModel);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tieuDe.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tieuDe[position];
    }

}
