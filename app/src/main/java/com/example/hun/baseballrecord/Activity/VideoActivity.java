package com.example.hun.baseballrecord.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hun.baseballrecord.Adapter.VideoRecyclerAdapter;
import com.example.hun.baseballrecord.Model.YouTubeSearchModel;
import com.example.hun.baseballrecord.R;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class VideoActivity extends YouTubeBaseActivity {
    private static String TAG = "VideoActivity";

    private EditText et;
    private RecyclerView youtubeRecyclerView;
    private List<YouTubeSearchModel> dataList = null;
    private VideoRecyclerAdapter VideoRecyclerAdapter = null;
    private String serverKey = "AIzaSyCuAjoENl1G_hs-B2EnVaBG5ZIdddiqPFM";
    private TextView resultTxt;

    private YouTubePlayer.OnInitializedListener listener;
    AsyncTask<?, ?, ?> searchTask;


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

        et =  findViewById(R.id.eturl);
        youtubeRecyclerView = findViewById(R.id.youtubeRecyclerView);
        resultTxt = findViewById(R.id.result_search_text);
        dataList = new ArrayList<>();
        addMainMenuDummy();
        Button searchBtn = findViewById(R.id.search);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTask = new searchTask().execute();
            }
        });

    }


    private class searchTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            getUtube();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            setRecyclerView();
        }
    }

    private void addMainMenuDummy() {
        Log.d(TAG, "addMainMenuDummy");
//        dataList.add(new YouTubeSearchModel("videoId", "title", "url", "publishedAt"));
    }

    private void setRecyclerView() {
        Log.d(TAG, "setRecyclerView");
        VideoRecyclerAdapter = new VideoRecyclerAdapter(getApplicationContext(), R.layout.video_item, dataList);
        youtubeRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        youtubeRecyclerView.setHasFixedSize(true);

        VideoRecyclerAdapter.OnItemClickListener mOnItemClickListener = new VideoRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, String url) {
                Log.d(TAG, "position ==>  " + position);
                Log.d(TAG, "url ==>  " + url);
                resultTxt.setVisibility(View.VISIBLE);
                resultTxt.setText("검색어 : " + et.getText().toString());
                Intent intent = new Intent(VideoActivity.this, PlayerActivity.class);
                intent.putExtra("id", url);
                startActivity(intent);

            }
        };
        VideoRecyclerAdapter.setOnItemClickListener(mOnItemClickListener);
        youtubeRecyclerView.setAdapter(VideoRecyclerAdapter);

    }

    public void getUtube() {
        URL url = null;
        try {
            url = new URL("https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=20&q=" + et.getText().toString() + "&key=" + serverKey);
            Log.d(TAG, "video url ===> " + url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            BufferedReader br;
            Log.d(TAG, "resopnse code =====> " + responseCode);
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null){
                sb.append(line + "\n");
            }
            br.close();
            con.disconnect();
      //      Log.d("Aaaaaaaaaaaaaaaa", "" + sb.toString());

            paringJsonData(sb.toString());

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void paringJsonData(String jsonString) {
        dataList.clear();
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray contacts = jsonObject.getJSONArray("items");
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);
                String vodid = "";
                if(c.getJSONObject("id").has("videoId")){
                    if(c.getJSONObject("id").getString("videoId")==null){
                        vodid = c.getJSONObject("id").getString("channelId");
                    } else {
                        vodid = c.getJSONObject("id").getString("videoId");
                    }
                }

                String title = c.getJSONObject("snippet").getString("title");

                String changString = "";
                try {
                    changString = new String(title.getBytes("8859_1"), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                String date = c.getJSONObject("snippet").getString("publishedAt")
                        .substring(0, 10);
                String imgUrl = c.getJSONObject("snippet").getJSONObject("thumbnails")
                        .getJSONObject("default").getString("url");

                 dataList.add(new YouTubeSearchModel(vodid, title, imgUrl, date));

            }

        } catch (JSONException e){
            e.printStackTrace();
        }

    }


}
