package com.example.hun.baseballrecord.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.hun.baseballrecord.Adapter.VideoRecyclerAdapter;
import com.example.hun.baseballrecord.Model.YouTubeSearchModel;
import com.example.hun.baseballrecord.R;

import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;

import com.google.android.youtube.player.YouTubePlayer;

import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;


public class VideoActivity extends YouTubeBaseActivity {
    private static String TAG = "VideoActivity";

    private YouTubePlayerView youTubeView;

    private Button button;
    private EditText et;
    private RecyclerView youtubeRecyclerView;
    private List<YouTubeSearchModel> dataList = null;
    private VideoRecyclerAdapter VideoRecyclerAdapter = null;
    private String serverKey = "AIzaSyCuAjoENl1G_hs-B2EnVaBG5ZIdddiqPFM";

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
        button = findViewById(R.id.youtubebutton);
        youTubeView = findViewById(R.id.youtubeView);
        et =  findViewById(R.id.eturl);
        youtubeRecyclerView = findViewById(R.id.youtubeRecyclerView);
        dataList = new ArrayList<>();
        addMainMenuDummy();
        Button search = findViewById(R.id.search);


        Log.d("youtube Test",
                "사용가능여부:"+YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(this)); //SUCCSESS



        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTask = new searchTask().execute();

            }
        });

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
        dataList.add(new YouTubeSearchModel("videoId", "title", "url", "publishedAt"));
    }

    private void setRecyclerView() {
        Log.d(TAG, "setRecyclerView");
        VideoRecyclerAdapter = new VideoRecyclerAdapter(getApplicationContext(), R.layout.video_item, dataList);
        youtubeRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        youtubeRecyclerView.setHasFixedSize(true);

        VideoRecyclerAdapter.OnItemClickListener mOnItemClickListener = new VideoRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "position ==>  " + position);

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
//            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null){
                sb.append(line + "\n");
            }
            br.close();
            con.disconnect();
            Log.d("Aaaaaaaaaaaaaaaa", "" + sb.toString());

            paringJsonData(sb.toString());

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void paringJsonData(String jsonString) {
      //  sdata.clear();
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray contacts = jsonObject.getJSONArray("items");
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);
                String vodid = c.getJSONObject("id").getString("videoId");

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

                 dataList.add(new YouTubeSearchModel(vodid, changString, imgUrl, date));


//            ListView searchlist = (ListView) findViewById(R.id.searchlist);
//
//            StoreListAdapter mAdapter = new StoreListAdapter(
//                    StartActivity.this, R.layout.listview_start, sdata); //Json�Ľ��ؼ� ������ ��Ʃ�� �����͸� �̿��ؼ� ����Ʈ�� ������ݴϴ�.
//
//            searchlist.setAdapter(mAdapter);
            }

        } catch (JSONException e){
            e.printStackTrace();
        }

    }





}
