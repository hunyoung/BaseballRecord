package com.example.hun.baseballrecord.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hun.baseballrecord.Adapter.OneTeamDetailFrgmentRecyclerAdapter;
import com.example.hun.baseballrecord.Adapter.TeamDetailFrgmentRecyclerAdapter;
import com.example.hun.baseballrecord.Model.OneTeamDetailFragmentRecyclerModel;
import com.example.hun.baseballrecord.Model.RecyclerModel;
import com.example.hun.baseballrecord.Model.TeamDetailFragmentRecyclerModel;
import com.example.hun.baseballrecord.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OneTeamDetailFragment extends Fragment {
    private static String TAG = "TeamDetailFragment";

    private View mRootView;
    private List<OneTeamDetailFragmentRecyclerModel> dataList = null;
    private OneTeamDetailFrgmentRecyclerAdapter mOneTeamDetailFragmentRecyclerAdapter = null;
    private RecyclerView oneTeamDetailRecyclerView;
    private String accessUrl = "https://www.doosanbears.com/players/pitchers.do";


    public OneTeamDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        if (mRootView == null || !mRootView.isShown()) {
            if (mRootView == null) {
                mRootView = inflater.inflate(R.layout.one_team_detail_fragment, container, false);
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

        oneTeamDetailRecyclerView = mRootView.findViewById(R.id.one_team_detail_fragment_recyclerview);
        dataList = new ArrayList<>();
        addMainMenuDummy();
//        setRecyclerView();

        OneTeamDetailFragment.JsoupAsyncTask jsoupAsyncTask = new OneTeamDetailFragment.JsoupAsyncTask();
        jsoupAsyncTask.execute();

    }

    private void addMainMenuDummy() {
        Log.d(TAG, "addMainMenuDummy");
        dataList.add(new OneTeamDetailFragmentRecyclerModel("두산", "aaa"));

       // setRecyclerView();

    }


    private void setRecyclerView() {
        Log.d(TAG, "setRecyclerView");
        mOneTeamDetailFragmentRecyclerAdapter = new OneTeamDetailFrgmentRecyclerAdapter(getContext(), R.layout.one_team_detail_fragment_recyclerview_item, dataList);
        oneTeamDetailRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        oneTeamDetailRecyclerView.setHasFixedSize(true);

        OneTeamDetailFrgmentRecyclerAdapter.OnItemClickListener mOnItemClickListener = new OneTeamDetailFrgmentRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "position ==>  " + position);


            }
        };
        mOneTeamDetailFragmentRecyclerAdapter.setOnItemClickListener(mOnItemClickListener);
        oneTeamDetailRecyclerView.setAdapter(mOneTeamDetailFragmentRecyclerAdapter);

    }


    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Log.d(TAG, "html url ===> " + accessUrl);

                Document doc = Jsoup.connect(accessUrl).get();
//                Log.d(TAG, "doc =====> " + doc);
                Log.d(TAG, "doc =====> " + doc.text());

                //테스트1
                Elements titles = doc.select("ul.playerlist li");
                Log.d(TAG, "titles  =====> " + titles.text());

//                Elements titles0 = doc.select("article.maincontent ul");
//                Log.d(TAG, "titles0  =====> " + titles0.text());
//                Log.d(TAG, "titles0  notext=====> " + titles0);
//                Elements titles1 = doc.select("article.maincontent ul li");
//                Log.d(TAG, "titles1  =====> " + titles1.text());
//                Log.d(TAG, "titles1 notext =====> " + titles1);
//
//                Elements titles2 = doc.select("section#main_container");
//                Log.d(TAG, "titles2  =====> " + titles2.text());
//                Log.d(TAG, "titles2  =====> " + titles2);
//
//                Elements titles3 = doc.select("article.maincontent");
//                Log.d(TAG, "titles3  =====> " + titles3.text());
//                Log.d(TAG, "titles3  =====> " + titles3);
//
//                Elements titles4 = doc.select("a.more");
//                Log.d(TAG, "titles4  =====> " + titles4.text());
//                Log.d(TAG, "titles4  =====> " + titles4);
//
//                Elements titles5 = doc.select("ul.snstabs");
//                Log.d(TAG, "titles5  =====> " + titles5.text());
//
//                Elements titles6 = doc.select("div.inner");
//                Log.d(TAG, "titles6  =====> " + titles6.text());

                for (Element e : titles) {
                    Log.d(TAG, e.text());

                }

//                for(Element e : titles){
//                    dataList.add( new OneTeamDetailFragmentRecyclerModel("aa", ""));
//                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            setRecyclerView();
        }
    }





}
