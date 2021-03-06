package com.example.hun.baseballrecord.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hun.baseballrecord.Activity.MainActivity;
import com.example.hun.baseballrecord.Adapter.PlayerFragmentRecyclerAdapter;
import com.example.hun.baseballrecord.Model.PlayerFragmentRecyclerModel;
import com.example.hun.baseballrecord.R;
import com.example.hun.baseballrecord.Utils.PlayerNameDB;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerFragment extends Fragment implements MainActivity.onKeyBackPressedListener{
    private static String TAG = "PlayerFragment";

    private View mRootView;
    private List<PlayerFragmentRecyclerModel> dataList = null;
    private PlayerFragmentRecyclerAdapter PlayerFragmentRecyclerAdapter = null;
    private RecyclerView playerDetailRecyclerView;
    private ImageView playerPhoto;
    private String playerPhotoUrl = "";
    private String playerName = "박치국";
    private String teamName = "두산";
    private String birthTxt = "";
    private boolean searchFisrt = true;
    private String accessUrl = "http://www.statiz.co.kr/player.php?opt=0&name=";
    private String tempInformation = "";
    private String backNumberString = "";
    private TextView backNumber;
    private TextView mPlayerName;
    private TextView mPlayerBirth, mPlayerHitPitch, mPlayerSchool, mPlayerRunYear, mPlayerRunTeam;
    private TextView mPlayerFirstPick, mPlayerRecentTeam, mPlayerRecentPosition, mPlayerWholeTeam, mPlayerWholePosition;
    private String sPlayerBirth, sPlayerHitPitch, sPlayerSchool, sPlayerRunYear, sPlayerRunTeam = "";
    private String sPlayerFirstPick, sPlayerRecentTeam, sPlayerRecentPosition, sPlayerWholeTeam, sPlayerWholePosition = "";
    private String playerId = "";
    private String kboPlayerIdUrl = "";

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

        playerDetailRecyclerView = mRootView.findViewById(R.id.player_fragment_recyclerview);
        dataList = new ArrayList<>();
        mPlayerName = mRootView.findViewById(R.id.player_name);
        backNumber = mRootView.findViewById(R.id.backNumber);
        playerPhoto = mRootView.findViewById(R.id.player_photo);
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


//        addHitterDummy();
//        setRecyclerView();

        PlayerFragment.JsoupAsyncTask jsoupAsyncTask = new PlayerFragment.JsoupAsyncTask();
        jsoupAsyncTask.execute();
        Log.d(TAG, "positionArg ====> " + playerName);
        Log.d(TAG, "teamName ====> " + teamName);


    }

    private void addHitterDummy() {
        Log.d(TAG, "addMainMenuDummy");
        dataList.add(new PlayerFragmentRecyclerModel("","연도", "팀명", "나이", "포지션",
                "G", "타석", "타수", "득점", "안타", "2타",
                "3타", "홈런", "루타", "타점", "도루", "도실",
                "볼넷", "사구", "고4", "삼진", "병살",
                "희타", "희비", "타율", "출루율", "장타율",
                "OPS", "wOBA", "WRC+", "WAR", "WPA"));
//        setRecyclerView();

    }

    private void addPitcherDummy() {
        Log.d(TAG, "addMainMenuDummy");
        dataList.add(new PlayerFragmentRecyclerModel("","연도", "팀명", "나이", "출장",
                "완투", "완봉", "선발", "승", "패", "세이브",
                "홀드", "이닝", "실점", "자책", "타자", "안타",
                "2타", "3타", "홈런", "볼넷", "고4",
                "사구", "삼진", "보크", "폭투", "ERA",
                "FIP", "WFIP", "ERA+", "FIP+", "WAR"));
//        setRecyclerView();

    }


    private void setRecyclerView() {
        Log.d(TAG, "setRecyclerView");
        PlayerFragmentRecyclerAdapter = new PlayerFragmentRecyclerAdapter(getContext(), R.layout.player_fragment_detail_item, dataList);
        playerDetailRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        playerDetailRecyclerView.setHasFixedSize(true);

        playerDetailRecyclerView.setAdapter(PlayerFragmentRecyclerAdapter);

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
            getPlayerDescription();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            asyncDialog.dismiss();
            setUIText();

            if(birthTxt != null && searchFisrt){
                PlayerFragment.JsoupAsyncTask jsoupAsyncTask = new PlayerFragment.JsoupAsyncTask();
                jsoupAsyncTask.execute();
            }
            setRecyclerView();
        }
    }

    private void setUIText(){
        if(!playerId.equals("")){ Glide.with(getContext()).load(playerPhotoUrl).into(playerPhoto); }
        else { Glide.with(getContext()).load(R.drawable.noimage).into(playerPhoto); }

        String[] a = backNumberString.split(" ");

        backNumberString.split(" ");
        Log.d(TAG, "등번호 backNumberString substring ===> " + backNumberString);
        String temp = "";
        if(a.length >= 2){
            for(int i=0; i<a.length; i++){
                Log.d(TAG, "등번호 substring ===> " + a[i]);
                if(i % 2 == 0){
                    temp += a[i] + "\n" + a[i+1];
                }
                temp += "\n";
            }
        }

        backNumber.setText(temp);

        mPlayerName.setText(playerName);
//        backNumber.setText(backNumberString);
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
    private void getPlayerPhoto(){
        try{
            PlayerNameDB db = new PlayerNameDB();
            playerId = db.PlayerDB(playerName);
            Document doc = Jsoup.connect(kboPlayerIdUrl + playerId).get();
            playerId = doc.select("div.photo").select("img").attr("src");
            if(playerId!=null){
                playerPhotoUrl = "https://www.koreabaseball.com" + playerId ;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void getPlayerDescription(){
        try {
            Document doc = Jsoup.connect(accessUrl + playerName + birthTxt).get();
//                Log.d(TAG, "doc ===> " + doc.text());
            searchFisrt = false;
            dataList.clear();
            Elements backNumber = doc.select("div.box-body");
            if(backNumber.size() >2){
                if(!backNumber.get(2).text().isEmpty()){
                    Log.d(TAG, "e =======> " + backNumber.get(2).text());
                    backNumberString = backNumber.get(2).text();
                }
            }

            Elements description = doc.select("ul.dropdown-menu");
//                for(Element e : description){
//                    Log.d(TAG, "e =======> " + e.text());
//                }

            if(description.size() >7){
                if(!description.get(7).text().isEmpty()){
//                        Log.d(TAG, "e =======> " + description.get(7).text());
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

                if(sPlayerWholePosition.equals("투수")){
                    Log.d(TAG, "검색 선수 포지션 = 투수");
                    addPitcherDummy();
                    kboPlayerIdUrl = "https://www.koreabaseball.com/Record/Player/PitcherDetail/Basic.aspx?playerId=";
                    Elements des = doc.select("tr.oddrow_stz0 td");
                    Log.d(TAG, "des size ====> " + des.size());
                    for(int i = 0; i<des.size(); i++){
                        if(i % 33 == 0){
                            dataList.add(new PlayerFragmentRecyclerModel(des.get(i).text(), des.get(i+1).text(), des.get(i+2).text(), des.get(i+3).text(),
                                    des.get(i+4).text(), des.get(i+5).text(), des.get(i+6).text(), des.get(i+7).text(), des.get(i+8).text(), des.get(i+9).text(),
                                    des.get(i+10).text(), des.get(i+11).text(), des.get(i+12).text(), des.get(i+13).text(), des.get(i+14).text(), des.get(i+15).text(),
                                    des.get(i+16).text(), des.get(i+17).text(), des.get(i+18).text(), des.get(i+19).text(), des.get(i+20).text(), des.get(i+21).text(),
                                    des.get(i+22).text(), des.get(i+23).text(), des.get(i+24).text(), des.get(i+25).text(), des.get(i+26).text(), des.get(i+27).text(),
                                    des.get(i+28).text(), des.get(i+29).text(), des.get(i+30).text(), des.get(i+31).text()));
                        }
                    }

                    Elements desEven = doc.select("tr.evenrow_stz0 td");
                    for(int i = 33; i<desEven.size(); i++){
                        if(i % 33 == 0){
                            dataList.add(new PlayerFragmentRecyclerModel(desEven.get(i).text(), desEven.get(i+1).text(), desEven.get(i+2).text(), desEven.get(i+3).text(),
                                    desEven.get(i+4).text(), desEven.get(i+5).text(), desEven.get(i+6).text(), desEven.get(i+7).text(), desEven.get(i+8).text(), desEven.get(i+9).text(),
                                    desEven.get(i+10).text(), desEven.get(i+11).text(), desEven.get(i+12).text(), desEven.get(i+13).text(), desEven.get(i+14).text(), desEven.get(i+15).text(),
                                    desEven.get(i+16).text(), desEven.get(i+17).text(), desEven.get(i+18).text(), desEven.get(i+19).text(), desEven.get(i+20).text(), desEven.get(i+21).text(),
                                    desEven.get(i+22).text(), desEven.get(i+23).text(), desEven.get(i+24).text(), desEven.get(i+25).text(), desEven.get(i+26).text(), desEven.get(i+27).text(),
                                    desEven.get(i+28).text(), desEven.get(i+29).text(), desEven.get(i+30).text(), desEven.get(i+31).text()));
                        }
                    }


                } else {
                    Log.d(TAG, "검색 선수 포지션 = 타자");
                    addHitterDummy();
                    kboPlayerIdUrl = "https://www.koreabaseball.com/Record/Player/HitterDetail/Basic.aspx?playerId=";
                    Elements des = doc.select("tr.oddrow_stz0 td");
                    Log.d(TAG, "des size ====> " + des.size());
                    for(int i = 0; i<des.size(); i++){
                        if(i % 32 == 0){
                            dataList.add(new PlayerFragmentRecyclerModel(des.get(i).text(), des.get(i+1).text(), des.get(i+2).text(), des.get(i+3).text(),
                                    des.get(i+4).text(), des.get(i+5).text(), des.get(i+6).text(), des.get(i+7).text(), des.get(i+8).text(), des.get(i+9).text(),
                                    des.get(i+10).text(), des.get(i+11).text(), des.get(i+12).text(), des.get(i+13).text(), des.get(i+14).text(), des.get(i+15).text(),
                                    des.get(i+16).text(), des.get(i+17).text(), des.get(i+18).text(), des.get(i+19).text(), des.get(i+20).text(), des.get(i+21).text(),
                                    des.get(i+22).text(), des.get(i+23).text(), des.get(i+24).text(), des.get(i+25).text(), des.get(i+26).text(), des.get(i+27).text(),
                                    des.get(i+28).text(), des.get(i+29).text(), des.get(i+30).text(), des.get(i+31).text()));
                        }
                    }

                    Elements desEven = doc.select("tr.evenrow_stz0 td");
                    for(int i = 32; i<desEven.size(); i++){
                        if(i % 32 == 0){
                            dataList.add(new PlayerFragmentRecyclerModel(desEven.get(i).text(), desEven.get(i+1).text(), desEven.get(i+2).text(), desEven.get(i+3).text(),
                                    desEven.get(i+4).text(), desEven.get(i+5).text(), desEven.get(i+6).text(), desEven.get(i+7).text(), desEven.get(i+8).text(), desEven.get(i+9).text(),
                                    desEven.get(i+10).text(), desEven.get(i+11).text(), desEven.get(i+12).text(), desEven.get(i+13).text(), desEven.get(i+14).text(), desEven.get(i+15).text(),
                                    desEven.get(i+16).text(), desEven.get(i+17).text(), desEven.get(i+18).text(), desEven.get(i+19).text(), desEven.get(i+20).text(), desEven.get(i+21).text(),
                                    desEven.get(i+22).text(), desEven.get(i+23).text(), desEven.get(i+24).text(), desEven.get(i+25).text(), desEven.get(i+26).text(), desEven.get(i+27).text(),
                                    desEven.get(i+28).text(), desEven.get(i+29).text(), desEven.get(i+30).text(), desEven.get(i+31).text()));
                        }
                    }

//                        for(Element e : des){
//                            Log.d(TAG, "e =======> " + e.text());
//                        }
                }

                getPlayerPhoto();

            } else if(doc.select("tr.oddrow_stz td").hasText() || doc.select("tr.evenrow_stz td").hasText()){
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
            } else {
                Log.d(TAG, "검색결과 없음");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
