package com.example.ba_hung.findyardgolf.controller;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.ba_hung.findyardgolf.bean.SanGolfModel;

/**
 * Created by ba-hung on 11/10/2017.
 **/

@SuppressLint("ParcelCreator")
public class Suggestion implements SearchSuggestion{

    private String mName;
    private SanGolfModel sanGolf;

    public Suggestion(String suggestion, SanGolfModel sanGolf) {
        mName= suggestion.toLowerCase();
        this.sanGolf = sanGolf;
    }


    public String getName() {
        return mName;
    }
    public SanGolfModel getSanGolf(){
        return sanGolf;
    }

    @Override
    public String getBody() {
        return mName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
