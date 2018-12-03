package com.example.hun.baseballrecord.Fragment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hun.baseballrecord.Adapter.TeamDetailFrgmentRecyclerAdapter;
import com.example.hun.baseballrecord.Adapter.TeamFrgmentRecyclerAdapter;
import com.example.hun.baseballrecord.Model.TeamDetailFragmentRecyclerModel;
import com.example.hun.baseballrecord.Model.TeamFragmentRecyclerModel;
import com.example.hun.baseballrecord.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeamDetailFragment extends Fragment {
    private static String TAG = "TeamDetailFragment";

    private View mRootView;
    private List<TeamDetailFragmentRecyclerModel> dataList = null;
    private TeamDetailFrgmentRecyclerAdapter mTeamDetailFragmentRecyclerAdapter = null;
    private RecyclerView teamRecyclerView;


    public TeamDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        if (mRootView == null || !mRootView.isShown()) {
            if (mRootView == null) {
                mRootView = inflater.inflate(R.layout.team_detail_fragment, container, false);
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

    }

    private void addMainMenuDummy() {
        Log.d(TAG, "addMainMenuDummy");
        dataList.add(new TeamDetailFragmentRecyclerModel("두산", getResources().getDrawable(R.drawable.btn_list)));

    }


    private void setRecyclerView() {
        Log.d(TAG, "setRecyclerView");
        mTeamDetailFragmentRecyclerAdapter = new TeamDetailFrgmentRecyclerAdapter(getContext(), R.layout.team_rank_item, dataList);
        teamRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), GridLayoutManager.VERTICAL, false));
        teamRecyclerView.setHasFixedSize(true);
        teamRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        teamRecyclerView.setAdapter(mTeamDetailFragmentRecyclerAdapter);



    }





}
