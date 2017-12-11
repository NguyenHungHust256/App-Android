package com.example.ba_hung.findyardgolf.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ba_hung.findyardgolf.bean.SanGolfModel;
import com.example.ba_hung.findyardgolf.ui.fragment.SeeItem.DuBaoThoiTiet.DuBaoThoiTietMainFragment;
import com.example.ba_hung.findyardgolf.ui.fragment.SeeItem.Map.MapItemFragment;
import com.example.ba_hung.findyardgolf.ui.fragment.SeeItem.Detail_Item.InfoEachItemFragment;
import com.example.ba_hung.findyardgolf.ui.fragment.SeeItem.ServiceGolf.ServiceItemFragment;

/**
 * Created by ba-hung on 09/10/2017.
 **/

public class MyPagerAdapter  extends FragmentPagerAdapter {
//    private static final String[] tieuDe = {"Chi tiết", "Dịch vụ", "Bản đồ","Thời tiết" };
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
            fragment = new InfoEachItemFragment(sanGolfModel);
        }
        else if (position == 1)
        {
            fragment = new ServiceItemFragment(sanGolfModel);
        }
        else if (position == 2)
        {
            fragment = new MapItemFragment(sanGolfModel);
        }
        else if(position == 3){
            fragment = new DuBaoThoiTietMainFragment(sanGolfModel);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }


//    @Override
//    public CharSequence getPageTitle(int position) {
//        return tieuDe[position];
//    }

}
