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

import com.example.hun.baseballrecord.Adapter.TeamFrgmentRecyclerAdapter;
import com.example.hun.baseballrecord.Model.TeamFragmentRecyclerModel;
import com.example.hun.baseballrecord.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamFragment extends Fragment {
    private static String TAG = "TeamFragment";
    private String htmlURL = "https://www.koreabaseball.com/TeamRank/TeamRank.aspx";

    private View mRootView;
    private List<TeamFragmentRecyclerModel> dataList = null;
    private TeamFrgmentRecyclerAdapter mTeamFrgmentRecyclerAdapter = null;
    private RecyclerView teamRecyclerView;


    public TeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        if (mRootView == null || !mRootView.isShown()) {
            if (mRootView == null) {
                mRootView = inflater.inflate(R.layout.team_fragment, container, false);
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

        teamRecyclerView = mRootView.findViewById(R.id.teamRecyclerView);

        dataList = new ArrayList<>();

        addMainMenuDummy();
        setRecyclerView();
//        TeamFragment.JsoupAsyncTask jsoupAsyncTask = new TeamFragment.JsoupAsyncTask();
//        jsoupAsyncTask.execute();

    }

    private void addMainMenuDummy() {
        Log.d(TAG, "addMainMenuDummy");
        dataList.add(new TeamFragmentRecyclerModel("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
        dataList.add(new TeamFragmentRecyclerModel("10", "20", "30", "40", "50", "60", "70", "80", "90", "100", "110", "120"));

    }


    private void setRecyclerView() {
        Log.d(TAG, "setRecyclerView");
        mTeamFrgmentRecyclerAdapter = new TeamFrgmentRecyclerAdapter(getContext(), R.layout.team_rank_item, dataList);
        teamRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        teamRecyclerView.setHasFixedSize(true);
        teamRecyclerView.setAdapter(mTeamFrgmentRecyclerAdapter);

    }


//
//    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                Document doc = Jsoup.connect(htmlURL).get();
//
//                //테스트1
//                Elements titles = doc.select("table.table tr td");
//                //Elements titles= doc.select("div.box-body tr td");    //한개 씩 뜯어서 나옴
//                int i = 0;
//                for (Element e : titles) {
//                    htmlList.add(e.text());
//                    //       dataList.add(new RecyclerModel(String.valueOf(++i), e.text()));
//                }
//
//                String link = "http://www.statiz.co.kr/" + doc.select("table.table tr td a").attr("href");
//                Log.d(TAG, "link ====>>> " + link);
//
//                for (int a = 0; a < 929 /*htmlList.size()*/; a++) {
//                    if (a % 31 == 1) {
//                        //                Log.d(TAG,"a ==== " + a + " 값 ==>  " + htmlList.get(a));
//                        dataList.add(new RecyclerModel(String.valueOf(++i), htmlList.get(a), htmlList.get(a + 28)));
//
//                    }
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            setRecyclerView();
//        }
//    }
//

}
