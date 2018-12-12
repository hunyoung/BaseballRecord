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
import java.util.Arrays;
import java.util.List;

public class OneTeamDetailFragment extends Fragment {
    private static String TAG = "TeamDetailFragment";

    private View mRootView;
    private List<OneTeamDetailFragmentRecyclerModel> dataList = null;
    private OneTeamDetailFrgmentRecyclerAdapter mOneTeamDetailFragmentRecyclerAdapter = null;
    private RecyclerView oneTeamDetailRecyclerView;
    private String accessUrl = "http://www.statiz.co.kr/team.php?opt=0&sopt=7&year=2018&team=두산";
//    private String accessUrl = "http://www.statiz.co.kr/team.php?opt=0&sopt=7&year=2018&team=lg";


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
//        dataList.add(new OneTeamDetailFragmentRecyclerModel("두산", "aaa"));
       // setRecyclerView();

    }


    private void setRecyclerView() {
        Log.d(TAG, "setRecyclerView");
        mOneTeamDetailFragmentRecyclerAdapter = new OneTeamDetailFrgmentRecyclerAdapter(getContext(), R.layout.one_team_detail_fragment_recyclerview_item, dataList);
        oneTeamDetailRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
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
//                Log.d(TAG, "doc =====> " + doc.text());
                List<String> htmlList = new ArrayList<>();

                String temp = "";

                Elements backNumber = doc.select("div.box-body");

//                Log.d(TAG, "backNumber  =====> " + backNumber.text());
//                for(int i=0; i<backNumber.size(); i++){

//                }
                Log.d(TAG, "backNumber ===> " + backNumber.get(2).text());
                temp = backNumber.get(2).text();
                temp.split(" ");
                Log.d(TAG, "length   " + temp.split(" ").length);

                String[] arr;

                arr = temp.split(" ");
                for(int i=0; i<arr.length; i = i+2){
                    dataList.add( new OneTeamDetailFragmentRecyclerModel(arr[i+1], arr[i]));
                }

                //테스트1
                Elements titles = doc.select("div.box-body a");
                Log.d(TAG, "titles  =====> " + titles.text());
                for(int i=0; i<titles.size(); i++){
                    if(i>12){
//                        dataList.add( new OneTeamDetailFragmentRecyclerModel(titles.get(i).text(), ""));
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
        }
    }





}
