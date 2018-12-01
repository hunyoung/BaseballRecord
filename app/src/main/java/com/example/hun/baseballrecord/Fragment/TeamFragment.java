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
import android.widget.TextView;

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
    private List<String> htmlList = new ArrayList<>();
    private RecyclerView teamRecyclerView;
    private TextView mCurDate;
    private String dateString;


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
        mCurDate = mRootView.findViewById(R.id.date);

        addMainMenuDummy();
//        setRecyclerView();
        TeamFragment.JsoupAsyncTask jsoupAsyncTask = new TeamFragment.JsoupAsyncTask();
        jsoupAsyncTask.execute();

    }

    private void addMainMenuDummy() {
        Log.d(TAG, "addMainMenuDummy");
        dataList.add(new TeamFragmentRecyclerModel("순위", "팀명", "경기", "승", "패", "무", "승률", "게임차", "최근10경기", "연속", "홈", "원정"));

    }


    private void setRecyclerView() {
        Log.d(TAG, "setRecyclerView");
        mTeamFrgmentRecyclerAdapter = new TeamFrgmentRecyclerAdapter(getContext(), R.layout.team_rank_item, dataList);
        teamRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        teamRecyclerView.setHasFixedSize(true);
        teamRecyclerView.setAdapter(mTeamFrgmentRecyclerAdapter);

    }



    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(htmlURL).get();

                //테스트1
                Elements titles = doc.select("table.tData tr td");
                Elements currentDate = doc.select("span.exp2");

                Elements currentYear = doc.select("select.select01 option");
                String curYear = "";
                for(int i=0; i<currentYear.size(); i++){
                    curYear = currentYear.get(0).text();
                }

                dateString = curYear + currentDate.text();
                for (Element e : titles) {
                    htmlList.add(e.text());
//                    Log.d(TAG, "e.text ==>>  " + e.text());
                }

                for (int a = 0; a < 120 /*htmlList.size()*/; a++) {
                    if (a % 12 == 0) {
                //        Log.d(TAG,"a ==== " + a + " 값 ==>  " + htmlList.get(a));
                        dataList.add(new TeamFragmentRecyclerModel(htmlList.get(a), htmlList.get(a+1), htmlList.get(a+2), htmlList.get(a+3),
                                htmlList.get(a+4), htmlList.get(a+5), htmlList.get(a+6), htmlList.get(a+7), htmlList.get(a+8),
                                htmlList.get(a+9), htmlList.get(a+10), htmlList.get(a+11)));

                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            setRecyclerView();
            mCurDate.setText(dateString);
        }
    }


}
