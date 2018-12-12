package com.example.hun.baseballrecord.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hun.baseballrecord.Adapter.TeamDetailFrgmentRecyclerAdapter;
import com.example.hun.baseballrecord.Model.TeamDetailFragmentRecyclerModel;
import com.example.hun.baseballrecord.R;

import java.util.ArrayList;
import java.util.List;

public class TeamDetailFragment extends Fragment {
    private static String TAG = "TeamDetailFragment";

    private View mRootView;
    private List<TeamDetailFragmentRecyclerModel> dataList = null;
    private TeamDetailFrgmentRecyclerAdapter mTeamDetailFragmentRecyclerAdapter = null;
    private RecyclerView teamDetailRecyclerView;


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

        teamDetailRecyclerView = mRootView.findViewById(R.id.team_detail_fragment_recyclerview);
        dataList = new ArrayList<>();
        addMainMenuDummy();
//        setRecyclerView();

    }

    private void addMainMenuDummy() {
        Log.d(TAG, "addMainMenuDummy");
        dataList.add(new TeamDetailFragmentRecyclerModel("두산", getResources().getDrawable(R.drawable.doosan)));
        dataList.add(new TeamDetailFragmentRecyclerModel("SK", getResources().getDrawable(R.drawable.sk)));
        dataList.add(new TeamDetailFragmentRecyclerModel("KT", getResources().getDrawable(R.drawable.kt)));
        dataList.add(new TeamDetailFragmentRecyclerModel("LG", getResources().getDrawable(R.drawable.lg)));
        dataList.add(new TeamDetailFragmentRecyclerModel("NC", getResources().getDrawable(R.drawable.nc)));
        dataList.add(new TeamDetailFragmentRecyclerModel("삼성", getResources().getDrawable(R.drawable.samsung)));
        dataList.add(new TeamDetailFragmentRecyclerModel("롯데", getResources().getDrawable(R.drawable.lotte)));
        dataList.add(new TeamDetailFragmentRecyclerModel("기아", getResources().getDrawable(R.drawable.kia)));
        dataList.add(new TeamDetailFragmentRecyclerModel("넥센", getResources().getDrawable(R.drawable.nexen)));
        dataList.add(new TeamDetailFragmentRecyclerModel("한화", getResources().getDrawable(R.drawable.hanhwa)));
        setRecyclerView();

    }


    private void setRecyclerView() {
        Log.d(TAG, "setRecyclerView");
        mTeamDetailFragmentRecyclerAdapter = new TeamDetailFrgmentRecyclerAdapter(getContext(), R.layout.team_detail_fragment_recyclerview_item, dataList);
        teamDetailRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        teamDetailRecyclerView.setHasFixedSize(true);

        TeamDetailFrgmentRecyclerAdapter.OnItemClickListener mOnItemClickListener = new TeamDetailFrgmentRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "position ==>  " + position);
                Toast.makeText(getContext(), "개발 중인 기능입니다.", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                OneTeamDetailFragment fragment1 = new OneTeamDetailFragment();
                transaction.replace(R.id.fragment_container, fragment1);
                transaction.commit();

            }
        };
        mTeamDetailFragmentRecyclerAdapter.setOnItemClickListener(mOnItemClickListener);


        teamDetailRecyclerView.setAdapter(mTeamDetailFragmentRecyclerAdapter);



    }





}
