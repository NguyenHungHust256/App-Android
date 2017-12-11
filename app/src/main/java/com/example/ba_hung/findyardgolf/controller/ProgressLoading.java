package com.example.ba_hung.findyardgolf.controller;

import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by ba-hung on 11/12/2017.
 */

public class ProgressLoading {
    View mView;

    public ProgressLoading(View mView) {
        this.mView = mView;
    }


    public void xuLyLoading(View mView, int a, int k) {
        final ProgressBar spinner;
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
}
