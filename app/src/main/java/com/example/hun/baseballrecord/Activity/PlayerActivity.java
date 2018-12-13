package com.example.hun.baseballrecord.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.hun.baseballrecord.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private String TAG = "PlayerActivity";

    private YouTubePlayerView ytpv;
    private YouTubePlayer ytp;
    final String serverKey="AIzaSyCuAjoENl1G_hs-B2EnVaBG5ZIdddiqPFM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_activity);
        ytpv = findViewById(R.id.youtubeplayer);
        ytpv.initialize(serverKey, this);

    }


    @Override
    public void onInitializationFailure(YouTubePlayer.Provider arg0,
                                        YouTubeInitializationResult arg1) {
        Toast.makeText(this, "Initialization Fail", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasrestored) {
        ytp = player;

        Intent gt =getIntent();
        ytp.loadVideo(gt.getStringExtra("id"));
    }


}
