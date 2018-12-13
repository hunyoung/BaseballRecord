package com.example.hun.baseballrecord.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hun.baseballrecord.Activity.MainActivity;
import com.example.hun.baseballrecord.Adapter.OneTeamDetailFrgmentRecyclerAdapter;
import com.example.hun.baseballrecord.Model.OneTeamDetailFragmentRecyclerModel;
import com.example.hun.baseballrecord.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerFragment extends Fragment implements MainActivity.onKeyBackPressedListener{
    private static String TAG = "OneTeamDetailFragment";

    private View mRootView;
    private List<OneTeamDetailFragmentRecyclerModel> dataList = null;
    private OneTeamDetailFrgmentRecyclerAdapter mOneTeamDetailFragmentRecyclerAdapter = null;
    private RecyclerView oneTeamDetailRecyclerView;
    private String playerName = "박치국";
    private String teamName = "두산";
    private String birthTxt = "";
    private boolean searchFisrt = true;
    private String accessUrl = "http://www.statiz.co.kr/player.php?opt=0&name=";
    private String tempInformation = "";
    private TextView mPlayerName;
    private TextView mPlayerBirth, mPlayerHitPitch, mPlayerSchool, mPlayerRunYear, mPlayerRunTeam;
    private TextView mPlayerFirstPick, mPlayerRecentTeam, mPlayerRecentPosition, mPlayerWholeTeam, mPlayerWholePosition;
    private String sPlayerBirth, sPlayerHitPitch, sPlayerSchool, sPlayerRunYear, sPlayerRunTeam = "";
    private String sPlayerFirstPick, sPlayerRecentTeam, sPlayerRecentPosition, sPlayerWholeTeam, sPlayerWholePosition = "";

    public PlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            playerName = getArguments().getString("PLAYER_NAME");
            teamName = getArguments().getString("SELECT_TEAM");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mRootView == null || !mRootView.isShown()) {
            if (mRootView == null) {
                mRootView = inflater.inflate(R.layout.player_fragment, container, false);
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
        mPlayerName = mRootView.findViewById(R.id.player_name);

        mPlayerBirth = mRootView.findViewById(R.id.player_birth);
        mPlayerHitPitch = mRootView.findViewById(R.id.player_hitPitch);
        mPlayerSchool = mRootView.findViewById(R.id.player_school);
        mPlayerRunYear = mRootView.findViewById(R.id.player_run_year);
        mPlayerRunTeam = mRootView.findViewById(R.id.player_run_team);
        mPlayerFirstPick = mRootView.findViewById(R.id.player_first_pick);
        mPlayerRecentTeam = mRootView.findViewById(R.id.player_recent_team);
        mPlayerRecentPosition = mRootView.findViewById(R.id.player_recent_position);
        mPlayerWholeTeam = mRootView.findViewById(R.id.player_whole_team);
        mPlayerWholePosition = mRootView.findViewById(R.id.player_whole_position);


        addMainMenuDummy();
//        setRecyclerView();

        PlayerFragment.JsoupAsyncTask jsoupAsyncTask = new PlayerFragment.JsoupAsyncTask();
        jsoupAsyncTask.execute();
        Log.d(TAG, "positionArg ====> " + playerName);
        Log.d(TAG, "teamName ====> " + teamName);


    }

    private void addMainMenuDummy() {
        Log.d(TAG, "addMainMenuDummy");
//        dataList.add(new OneTeamDetailFragmentRecyclerModel("두산", "aaa"));
       // setRecyclerView();

    }


    private void setRecyclerView() {
        Log.d(TAG, "setRecyclerView");
        mOneTeamDetailFragmentRecyclerAdapter = new OneTeamDetailFrgmentRecyclerAdapter(getContext(), R.layout.one_team_detail_fragment_recyclerview_item, dataList);
        oneTeamDetailRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        oneTeamDetailRecyclerView.setHasFixedSize(true);

        oneTeamDetailRecyclerView.setAdapter(mOneTeamDetailFragmentRecyclerAdapter);

    }


    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog asyncDialog = new ProgressDialog(getContext());

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중입니다..");
            // show dialog
            asyncDialog.show();

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {


                Document doc = Jsoup.connect(accessUrl + playerName + birthTxt).get();
//                Log.d(TAG, "doc ===> " + doc.text());
                searchFisrt = false;
                dataList.clear();
                String temp = "";

                Elements description = doc.select("ul.dropdown-menu");
                for(Element e : description){
                    Log.d(TAG, "e =======> " + e.text());
                }

                if(description.size() >7){
                    if(!description.get(7).text().isEmpty()){
                        Log.d(TAG, "e =======> " + description.get(7).text());
                        tempInformation = description.get(7).text();
                    }
                    sPlayerBirth = tempInformation.substring(5, tempInformation.indexOf("투타"));
                    sPlayerHitPitch = tempInformation.substring(tempInformation.indexOf("투타")+3, tempInformation.indexOf("출신학교"));
                    sPlayerSchool = tempInformation.substring(tempInformation.indexOf("출신학교")+5, tempInformation.indexOf("활약연도"));
                    sPlayerRunYear = tempInformation.substring(tempInformation.indexOf("활약연도")+5, tempInformation.indexOf("활약팀"));
                    sPlayerRunTeam = tempInformation.substring(tempInformation.indexOf("활약팀")+4, tempInformation.indexOf("신인지명"));
                    sPlayerFirstPick = tempInformation.substring(tempInformation.indexOf("신인지명")+5, tempInformation.indexOf("최근 소속"));
                    sPlayerRecentTeam = tempInformation.substring(tempInformation.indexOf("최근 소속")+6, tempInformation.indexOf("최근 포지션"));
                    sPlayerRecentPosition = tempInformation.substring(tempInformation.indexOf("최근 포지션")+7, tempInformation.indexOf("통산 소속"));
                    sPlayerWholeTeam = tempInformation.substring(tempInformation.indexOf("통산 소속")+6, tempInformation.indexOf("통산 포지션"));
                    sPlayerWholePosition = tempInformation.substring(tempInformation.indexOf("통산 포지션")+7, tempInformation.length());
                } else {
                    Log.d(TAG, "동명 이인 존재");
                    searchFisrt = true;
                    Elements birthOdd = doc.select("tr.oddrow_stz td");

                    for(int i=0; i< birthOdd.size(); i++){
                        if(birthOdd.get(i).text().equals(teamName)){
                            Log.d(TAG, "홀수에서 검색됨  생년월일 ===> " + birthOdd.get(i-2).text());
                            birthTxt = "&birth=" + birthOdd.get(i-2).text();
                        }
                    }

                    Elements birthEven = doc.select("tr.evenrow_stz td");

                    for(int i=0; i< birthEven.size(); i++){
                        if(birthEven.get(i).text().equals(teamName)){
                            Log.d(TAG, "짝수에서 검색됨  생년월일 ===> " + birthEven.get(i-2).text());
                            birthTxt = "&birth=" + birthEven.get(i-2).text();
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mPlayerName.setText(playerName);
            asyncDialog.dismiss();

            mPlayerBirth.setText(sPlayerBirth);
            mPlayerHitPitch.setText(sPlayerHitPitch);
            mPlayerSchool.setText(sPlayerSchool);
            mPlayerRunYear.setText(sPlayerRunYear);
            mPlayerRunTeam.setText(sPlayerRunTeam);
            mPlayerFirstPick.setText(sPlayerFirstPick);
            mPlayerRecentTeam.setText(sPlayerRecentTeam);
            mPlayerRecentPosition.setText(sPlayerRecentPosition);
            mPlayerWholeTeam.setText(sPlayerWholeTeam);
            mPlayerWholePosition.setText(sPlayerWholePosition);

            if(birthTxt != null && searchFisrt){
                PlayerFragment.JsoupAsyncTask jsoupAsyncTask = new PlayerFragment.JsoupAsyncTask();
                jsoupAsyncTask.execute();
            }

//            setRecyclerView();
        }
    }


    @Override
    public void onBackKey(){
        MainActivity activity = (MainActivity) getActivity();
        activity.setOnKeyBackPressedListener(null);
        activity.onBackPressed();
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        ((MainActivity)context).setOnKeyBackPressedListener(this);
    }



}
