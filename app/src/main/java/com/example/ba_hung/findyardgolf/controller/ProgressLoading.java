package com.example.ba_hung.findyardgolf.controller;

import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by ba-hung on 11/12/2017.
 **/

public class ProgressLoading {
    private View mView;
    private ProgressBar spinner;

    public ProgressLoading(View mView) {
        this.mView = mView;
    }


    public void xuLyLoading(View mView, int a, int k) {
        spinner = mView.findViewById(k);
        Runnable spinnerCyCle = new Runnable() {
            @Override
            public void run() {
                spinner.setVisibility(View.GONE);
            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(spinnerCyCle, a);

    }
    public void xuLyLoadingNotGone(View mView, int a, int k) {
        spinner = mView.findViewById(k);
        Runnable spinnerCyCle = new Runnable() {
            @Override
            public void run() {
                spinner.setVisibility(View.INVISIBLE);
            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(spinnerCyCle, a);
    }

    public void hienProgress(int k){
        spinner = mView.findViewById(k);
        spinner.setVisibility(View.VISIBLE);
    }
}
