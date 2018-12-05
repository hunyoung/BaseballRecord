package com.example.hun.baseballrecord.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hun.baseballrecord.Adapter.NewsRecyclerAdapter;
import com.example.hun.baseballrecord.Model.NewsModel;
import com.example.hun.baseballrecord.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    private static String TAG = "TeamDetailFragment";

    private View mRootView;
    private List<NewsModel> dataList = null;
    private NewsRecyclerAdapter newsFragmentRecyclerAdapter = null;
    private RecyclerView newsRecyclerView;
    public static StringBuilder sb;
    private String clientId = "Q9bW7CbasjpOQVO02uXm";
    private String clientSecret = "Wu_19W2CcU";

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        if (mRootView == null || !mRootView.isShown()) {
            if (mRootView == null) {
                mRootView = inflater.inflate(R.layout.news_fragment, container, false);
                init();
            }
        }
        return mRootView;
    }

    /**
     * 레이아웃 초기화
     */
    private void init() {
        Log.d(TAG, "init()");
        newsRecyclerView = mRootView.findViewById(R.id.one_team_detail_fragment_recyclerview);
        dataList = new ArrayList<>();
        addMainMenuDummy();
//        setRecyclerView();

        NewsFragment.JsoupAsyncTask jsoupAsyncTask = new NewsFragment.JsoupAsyncTask();
        jsoupAsyncTask.execute();

    }

    private void addMainMenuDummy() {
        Log.d(TAG, "addMainMenuDummy");
//        dataList.add(new NewsModel("제목", "설명", "날짜", "기사 링크"));
    }


    private void setRecyclerView() {
        Log.d(TAG, "setRecyclerView");
        newsFragmentRecyclerAdapter = new NewsRecyclerAdapter(getContext(), R.layout.news_item, dataList);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        newsRecyclerView.setHasFixedSize(true);

        NewsRecyclerAdapter.OnItemClickListener mOnItemClickListener = new NewsRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "position ==>  " + position);

            }
        };
        newsFragmentRecyclerAdapter.setOnItemClickListener(mOnItemClickListener);
        newsRecyclerView.setAdapter(newsFragmentRecyclerAdapter);

    }


    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
//            String clientId = "";// 애플리케이션 클라이언트 아이디값";
//            String clientSecret = "";// 애플리케이션 클라이언트 시크릿값";\
            int display = 50; // 검색결과갯수. 최대100개
            try {
                String text = URLEncoder.encode("프로야구", "utf-8");
                String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + text + "&display=" + display + "&";

                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if (responseCode == 200) {
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                sb = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                con.disconnect();
                System.out.println(sb);
                jsonParsing(sb.toString());
            } catch (Exception e) {
                System.out.println(e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            setRecyclerView();
        }
    }


    private void jsonParsing(String jsonString) {
        Log.d(TAG, "jsonParsing");
        StringBuffer sb1 = new StringBuffer();
        try {

            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray item = jsonObject.getJSONArray("items");

            Log.d(TAG, "jsonParsing  jObject  ====== \n" + item.toString());
            for(int i=0; i < item.length(); i++){
                JSONObject itemInfo = item.getJSONObject(i);  // JSONObject 추출
                String title = itemInfo.getString("title");
                String link = itemInfo.getString("link");
                String description = itemInfo.getString("description");
                String pubDate = itemInfo.getString("pubDate");

                dataList.add(new NewsModel(title, description, pubDate, link));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



}
