package com.example.hun.baseballrecord.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hun.baseballrecord.R;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;

import com.google.android.youtube.player.YouTubePlayer;

import com.google.android.youtube.player.YouTubePlayerView;


public class VideoActivity extends YouTubeBaseActivity {
    private static String TAG = "VideoActivity";

    private YouTubePlayerView youTubeView;

    private Button button;

    private YouTubePlayer.OnInitializedListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);
        init();
    }

    /**
     * 레이아웃 초기화
     */
    private void init() {
        Log.d(TAG, "init()");
        button = findViewById(R.id.youtubebutton);
        youTubeView = findViewById(R.id.youtubeView);

        listener = new YouTubePlayer.OnInitializedListener(){

            //초기화 성공시
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("U4X-fjfkBR4");//url의 맨 뒷부분 ID값만 넣으면 됨

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //첫번째 인자는 API키값 두번째는 실행할 리스너객체를 넘겨줌
                youTubeView.initialize("AIzaSyCuAjoENl1G_hs-B2EnVaBG5ZIdddiqPFM", listener);
            }
        });

    }




}
