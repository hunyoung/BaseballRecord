package com.example.hun.baseballrecord.Fragment;

import android.app.Dialog;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hun.baseballrecord.Activity.MainActivity;
import com.example.hun.baseballrecord.Adapter.RecyclerAdapter;
import com.example.hun.baseballrecord.Model.RecyclerModel;
import com.example.hun.baseballrecord.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements MainActivity.onKeyBackPressedListener{
    private static String TAG = "MainFragment";
    private RecyclerView recyclerView = null;
    private RecyclerAdapter recyclerAdapter = null;
    private List<RecyclerModel> dataList = null;

    private List<String> htmlList = new ArrayList<>();
    private View mRootView;
    private TextView mSearch;
    private TextView mBattingRecord;
    private TextView mPitchingRecord;
    private int statMode = 1;
    private TextView firstValue;
    private TextView secondValue;
    private TextView thirdValue;
    private TextView fourthValue;
    private String mSearchYear = "2018년";
    private String mSearchTeam = "팀 = 전체";
    private String mSearchPosition = "포지션 = 전체";
    private String mSearchSeason = "시즌 = 정규";
    private String mSearchBatting = "타석 = 규정";
    public String mRequestString = "";
    private int boolCheck = 1; //1 타격 2 수비

    private String htmlURL = "http://www.statiz.co.kr/stat.php?re=0&lr=&sn=100&pa=0";  //타격
    // 투구 http://www.statiz.co.kr/stat.php?re=1&lr=
    // 종합 http://www.statiz.co.kr/stat.php?re=7

    //http://www.statiz.co.kr/stat.php?opt=0&sopt=0&re=0&ys=1993&ye=2018&se=0&te=&tm=&ty=0&qu=auto&po=0&as=&ae=&hi=&un=&pl=&da=1&o1=WAR_ALL_ADJ&o2=TPA&de=1&lr=0&tr=&cv=&ml=1&sn=30&si=&cn=sn=100&pa=0
    // re == 0 종합 1 타격 2 수비 7 종합 ,
    // ys == 시작년도
    // ye == 종료년도
    // te == 팀 이름
    // se == 시즌(0~6) 정규, 포스트, 한국S, 플옵, 준플, 와카, 올스타, 섬머
    // po = 포지션(1 투수, 2 포수, ~~ 10 지타)v
    // pa =0 1등부터 // sn = 100  100등까지
    // qu = 규정 타석 (규정 auto, 70% = p70  50% = p50, 30% = p30, 전제 = all, 500> = 500 ~~~~

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mRootView == null || !mRootView.isShown()) {
            if (mRootView == null) {
                mRootView = inflater.inflate(R.layout.main_fragment, container, false);
                init();
            }
        }
        return mRootView;
    }

    @Override
    public void onResume(){
        Log.d(TAG, "onResume");
        super.onResume();
    }

    /**
     * 레이아웃 초기화
     */
    private void init() {
        Log.d(TAG, "init()");
        recyclerView = mRootView.findViewById(R.id.recyclerView);
        dataList = new ArrayList<>();
        mSearch = mRootView.findViewById(R.id.search_total_record);
        mBattingRecord = mRootView.findViewById(R.id.batting_record);
        mPitchingRecord = mRootView.findViewById(R.id.pitching_record);
        recyclerAdapter = new RecyclerAdapter(getContext(), R.layout.activity_main_item, dataList);
        firstValue = mRootView.findViewById(R.id.firstValue);
        secondValue = mRootView.findViewById(R.id.secondValue);
        thirdValue = mRootView.findViewById(R.id.thirdValue);
        fourthValue = mRootView.findViewById(R.id.fourthValue);
        statMode = 1;
//        addMainMenuDummy(1);

        mSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "팝업 노출");
                callFunction();
            }

        });


        mPitchingRecord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "Pitching Record");
                htmlURL = "http://www.statiz.co.kr/stat.php?re=1&lr=&sn=100&pa=0";
                htmlList.clear();
                dataList.clear();
                statMode = 2;
                firstValue.setText("승");
                secondValue.setText("패");
                thirdValue.setText("세");
                fourthValue.setText("ERA");
//                addMainMenuDummy(2);
                MainFragment.JsoupAsyncTask jsoupAsyncTask = new MainFragment.JsoupAsyncTask();
                jsoupAsyncTask.execute();
            }
        });

        mBattingRecord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "Batting Record");
                htmlURL = "http://www.statiz.co.kr/stat.php?re=0&lr=&sn=100&pa=0";
                htmlList.clear();
                dataList.clear();
                statMode = 1;
                firstValue.setText(R.string.battingAvg);
                secondValue.setText(R.string.onBasePercent);
                thirdValue.setText(R.string.slugAvg);
                fourthValue.setText(R.string.ops);
//                addMainMenuDummy(1);
                MainFragment.JsoupAsyncTask jsoupAsyncTask = new MainFragment.JsoupAsyncTask();
                jsoupAsyncTask.execute();
            }
        });


        MainFragment.JsoupAsyncTask jsoupAsyncTask = new MainFragment.JsoupAsyncTask();
        jsoupAsyncTask.execute();

    }

    private void addMainMenuDummy(int type) {
        Log.d(TAG, "addMainMenuDummy");
        if(type == 1){
            dataList.add(new RecyclerModel("순위", "이름", "팀명", "WAR", "타율", "출루율", "장타율", "OPS"));
        } else {
            dataList.add(new RecyclerModel("순위", "이름", "팀명", "WAR", "승", "패", "세이브", "방어율"));
        }


    }

    public void result(String searchRequestUrl){
        htmlURL = searchRequestUrl;
        if(boolCheck == 1){
            statMode =1;
            firstValue.setText(R.string.battingAvg);
            secondValue.setText(R.string.onBasePercent);
            thirdValue.setText(R.string.slugAvg);
            fourthValue.setText(R.string.ops);
        } else {
            statMode =2;
            firstValue.setText("승");
            secondValue.setText("패");
            thirdValue.setText("세");
            fourthValue.setText("ERA");
        }

        htmlList.clear();
        dataList.clear();
        MainFragment.JsoupAsyncTask jsoupAsyncTask = new MainFragment.JsoupAsyncTask();
        jsoupAsyncTask.execute();
    }


    private void setRecyclerView() {
        Log.d(TAG, "setRecyclerView");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.requestFocus();

    }


    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Log.d(TAG, "html url ===> " + htmlURL);

                Document doc = Jsoup.connect(htmlURL).get();

                //테스트1
                Elements titles = doc.select("table.table tr td");

                //Elements titles= doc.select("div.box-body tr td");    //한개 씩 뜯어서 나옴
                int i = 0;
                for (Element e : titles) {
                    htmlList.add(e.text());
                }

                if(statMode ==2){    //투구
                    for (int a = 0; a < htmlList.size(); a++) {
                        if (a % 33 == 0) {
                            if(htmlList.get(a).equals(String.valueOf(i+1)) || htmlList.get(a).equals(String.valueOf(""))){
                                dataList.add(new RecyclerModel(String.valueOf(++i), htmlList.get(a+1), htmlList.get(a+2), htmlList.get(a + 31),
                                        htmlList.get(a + 8), htmlList.get(a + 9), htmlList.get(a + 10) , htmlList.get(a + 27)));
                            }

                        }
                    }
                } else if (statMode ==1){  //타격
                    for (int a = 0; a < htmlList.size(); a++) {
                        if (a % 31 == 0) {
                            if(htmlList.get(a).equals(String.valueOf(i+1)) || htmlList.get(a).equals(String.valueOf(""))){
//                                Log.d(TAG, "htmlList.get(a)  " + htmlList.get(a));
                                dataList.add(new RecyclerModel(String.valueOf(++i), htmlList.get(a+1), htmlList.get(a+2), htmlList.get(a + 29),
                                        htmlList.get(a + 23), htmlList.get(a + 24), htmlList.get(a + 25) , htmlList.get(a + 26)));
                            }

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
            setRecyclerView();
        }
    }


    public void callFunction() {

        final Dialog dlg = new Dialog(getContext());
        
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.search_record_popup);
        
        dlg.show();

        final Button okButton =  dlg.findViewById(R.id.okButton);
        final Button cancelButton = dlg.findViewById(R.id.cancelButton);
        final Spinner yearSpinner = dlg.findViewById(R.id.search_year);
        final TextView yearSearchTxt = dlg.findViewById(R.id.search_year_txt);
        final Spinner positionSpinner = dlg.findViewById(R.id.search_position);
        final TextView positionSearchTxt = dlg.findViewById(R.id.search_position_txt);
        final Spinner teamSpinner = dlg.findViewById(R.id.search_team);
        final TextView teamSearchTxt = dlg.findViewById(R.id.search_team_txt);
        final Spinner seasonSpinner = dlg.findViewById(R.id.search_season);
        final TextView seasonSearchTxt = dlg.findViewById(R.id.search_season_txt);
        final Spinner battingSpinner = dlg.findViewById(R.id.search_batting);
        final TextView battingSearchTxt = dlg.findViewById(R.id.search_batting_txt);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stringBuilder(mSearchYear, mSearchTeam, mSearchPosition, mSearchSeason, mSearchBatting);
                dlg.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "취소 했습니다.", Toast.LENGTH_SHORT).show();
                dlg.dismiss();
            }
        });

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                yearSearchTxt.setText(adapterView.getItemAtPosition(position) + "년");
                mSearchYear = String.valueOf(adapterView.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        positionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                positionSearchTxt.setText(String.valueOf(adapterView.getItemAtPosition(position)));
                mSearchPosition = String.valueOf(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        teamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                teamSearchTxt.setText(String.valueOf(adapterView.getItemAtPosition(position)));
                mSearchTeam = String.valueOf(adapterView.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        seasonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                seasonSearchTxt.setText(String.valueOf(adapterView.getItemAtPosition(position)));
                mSearchSeason = String.valueOf(adapterView.getItemAtPosition(position));
                // se == 시즌(0~7) 정규, 포스트, 한국S, 플옵, 준플, 올스타, 섬머, 와카
                if(position == 0) mSearchSeason = "0";
                if(position == 1) mSearchSeason = "1";
                if(position == 2) mSearchSeason = "2";
                if(position == 3) mSearchSeason = "3";
                if(position == 4) mSearchSeason = "4";
                if(position == 5) mSearchSeason = "7";
                if(position == 6) mSearchSeason = "5";
                if(position == 7) mSearchSeason = "6";
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        battingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                battingSearchTxt.setText(String.valueOf(adapterView.getItemAtPosition(position)));
                // qu = 규정 타석 (규정 auto, 70% = p70  50% = p50, 30% = p30, 전제 = all, 500> = 500 ~~~~

                if(position == 0) mSearchBatting = "auto";
                if(position == 1) mSearchBatting = "p70";
                if(position == 2) mSearchBatting = "p50";
                if(position == 3) mSearchBatting = "p30";
                if(position == 4) mSearchBatting = "p30";
                if(position == 5) mSearchBatting = "all";
                if(position == 6) mSearchBatting = "500";
                if(position == 7) mSearchBatting = "300";
                if(position == 8) mSearchBatting = "200";
                if(position == 9) mSearchBatting = "100";
                if(position == 10) mSearchBatting = "50";
                if(position == 11) mSearchBatting = "25";
                if(position == 12) mSearchBatting = "10";
                if(position == 13) mSearchBatting = "5";

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }

    //http://www.statiz.co.kr/stat.php?opt=0&sopt=0&re=0&ys=1993&ye=2018&se=0&te=&tm=&ty=0&qu=auto&po=0&as=&ae=&hi=&un=&pl=&da=1&o1=WAR_ALL_ADJ&o2=TPA&de=1&lr=0&tr=&cv=&ml=1&sn=30&si=&cn=sn=100&pa=0
    // re == 0 종합 1 타격 2 수비 7 종합 ,
    // ys == 시작년도
    // ye == 종료년도
    // te == 팀 이름
    // se == 시즌(0~6) 정규, 포스트, 한국S, 플옵, 준플, 와카, 올스타, 섬머
    // po = 포지션(1 투수, 2 포수, ~~ 10 지타)v
    // pa =0 1등부터 // sn = 100  100등까지
    // qu = 규정 타석 (규정 auto, 70% = p70  50% = p50, 30% = p30, 전제 = all, 500> = 500 ~~~~

    public void stringBuilder(String yearStart, String teamName, String position, String season, String batting){
        String url = "";

        if(position.equals("1")){
            boolCheck = 2;
            url = "http://www.statiz.co.kr/stat.php?re=1&lr=&sn=100&pa=0";
        } else {
            boolCheck = 1;
            url = "http://www.statiz.co.kr/stat.php?re=0&lr=&sn=100&pa=0";
        }

        url += "&ys="+yearStart + "&ye="+yearStart +"&te="+teamName+"&po=" + position + "&se="+season + "&qu=" + batting;

        mRequestString = url;
        result(mRequestString);

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
