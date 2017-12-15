package com.example.ba_hung.findyardgolf.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.ba_hung.findyardgolf.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class HuongDanSuDung extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private static final String API_KEY_YOUTUBE = "AIzaSyCTJ2WEZJbswCDaPf1hlvrB_HfDH7JMiMs";
    private YouTubePlayerView youTubePlayerView;
    private static final int REQUEST_YOUTUBE = 256;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huong_dan_su_dung);
        youTubePlayerView = findViewById(R.id.myYoutube);
        youTubePlayerView.initialize(API_KEY_YOUTUBE,HuongDanSuDung.this);
    }
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.cueVideo("6fm4lH8D7sM");
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(HuongDanSuDung.this, REQUEST_YOUTUBE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_YOUTUBE){
            youTubePlayerView.initialize(API_KEY_YOUTUBE, HuongDanSuDung.this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        onDestroy();
        super.onBackPressed();
    }
}
