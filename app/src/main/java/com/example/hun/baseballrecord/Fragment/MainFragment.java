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

import com.example.hun.baseballrecord.Adapter.MainMenuRecyclerAdapter;
import com.example.hun.baseballrecord.Adapter.RecyclerAdapter;
import com.example.hun.baseballrecord.Model.MainMenuRecyclerModel;
import com.example.hun.baseballrecord.Model.RecyclerModel;
import com.example.hun.baseballrecord.Popup.SearchRecordPopup;
import com.example.hun.baseballrecord.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private static String TAG = "MainFragment";
    private RecyclerView recyclerView = null;
    private RecyclerAdapter recyclerAdapter = null;
    private List<RecyclerModel> dataList = null;

    private List<String> htmlList = new ArrayList<>();
    private View mRootView;
    private TextView mSearch;
    private TextView mTotalRecord;
    private TextView mBattingRecord;
    private TextView mPitchingRecord;
    private int statMode = 1;
    private TextView firstValue;
    private TextView secondValue;
    private TextView thirdValue;
    private TextView fourthValue;

    private String htmlURL = "http://www.statiz.co.kr/stat.php?re=0&lr=";  //타격
    // 투구 http://www.statiz.co.kr/stat.php?re=1&lr=
    // 종합 http://www.statiz.co.kr/stat.php?re=7

    //http://www.statiz.co.kr/stat.php?opt=0&sopt=0&re=0&ys=1993&ye=2018&se=0&te=&tm=&ty=0&qu=auto&po=0&as=&ae=&hi=&un=&pl=&da=1&o1=WAR_ALL_ADJ&o2=TPA&de=1&lr=0&tr=&cv=&ml=1&sn=30&si=&cn=
    // re == 0 종합 1 타격 2 수비 7 종합 ,
    // ys == 시작년도
    // ye == 종료년도
    // te == 팀 이름
    // se == 시즌(0~6) 정규, 포스트, 한국S, 플옵, 준플, 와카, 올스타, 섬머
    // po = 포지션(1 투수, 2 포수, ~~ 10 지타)v

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

    /**
     * 레이아웃 초기화
     */
    private void init() {
        Log.d(TAG, "init()");
        recyclerView = mRootView.findViewById(R.id.recyclerView);
        dataList = new ArrayList<>();
        mSearch = mRootView.findViewById(R.id.search_total_record);
        mTotalRecord = mRootView.findViewById(R.id.total_record);
        mBattingRecord = mRootView.findViewById(R.id.batting_record);
        mPitchingRecord = mRootView.findViewById(R.id.pitching_record);
        recyclerAdapter = new RecyclerAdapter(getContext(), R.layout.activity_main_item, dataList);
        firstValue = mRootView.findViewById(R.id.firstValue);
        secondValue = mRootView.findViewById(R.id.secondValue);
        thirdValue = mRootView.findViewById(R.id.thirdValue);
        fourthValue = mRootView.findViewById(R.id.fourthValue);
        statMode = 1;

        mSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "팝업 노출");
                SearchRecordPopup popup = new SearchRecordPopup(getContext());
                popup.callFunction();

            }

        });

//        mTotalRecord.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Log.d(TAG, "Total Record");
//                htmlURL = "http://www.statiz.co.kr/stat.php?re=7";
//                htmlList.clear();
//                MainFragment.JsoupAsyncTask jsoupAsyncTask = new MainFragment.JsoupAsyncTask();
//                jsoupAsyncTask.execute();
//            }
//        });

        mPitchingRecord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "Pitching Record");
                htmlURL = "http://www.statiz.co.kr/stat.php?re=1&lr=";
                htmlList.clear();
                dataList.clear();
                statMode = 2;
                firstValue.setText("승");
                secondValue.setText("패");
                thirdValue.setText("세");
                fourthValue.setText("ERA");
                MainFragment.JsoupAsyncTask jsoupAsyncTask = new MainFragment.JsoupAsyncTask();
                jsoupAsyncTask.execute();
                //WAR 승 패 세 ERA
            }
        });

        mBattingRecord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "Batting Record");
                htmlURL = "http://www.statiz.co.kr/stat.php?re=0&lr=";
                htmlList.clear();
                dataList.clear();
                statMode = 1;
                firstValue.setText("타율");
                secondValue.setText("출루율");
                thirdValue.setText("장타율");
                fourthValue.setText("OPS");
                MainFragment.JsoupAsyncTask jsoupAsyncTask = new MainFragment.JsoupAsyncTask();
                jsoupAsyncTask.execute();
            }
        });


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

                if (statMode ==1){
                    for (int a = 0; a < 929 /*htmlList.size()*/; a++) {
                        if (a % 31 == 1) {
                            //                Log.d(TAG,"a ==== " + a + " 값 ==>  " + htmlList.get(a));
                            dataList.add(new RecyclerModel(String.valueOf(++i), htmlList.get(a), htmlList.get(a + 28),
                                    htmlList.get(a + 22), htmlList.get(a + 23), htmlList.get(a + 24) , htmlList.get(a + 25)));
//                        Log.d(TAG, "선수 이름 ===> " + htmlList.get(a));
                        }
                    }
                } else if(statMode ==2){
                    for (int a = 0; a < 962 /*htmlList.size()*/; a++) {
                        if (a % 33 == 1) {
                            //                Log.d(TAG,"a ==== " + a + " 값 ==>  " + htmlList.get(a));
                            dataList.add(new RecyclerModel(String.valueOf(++i), htmlList.get(a), htmlList.get(a + 30),
                                    htmlList.get(a + 7), htmlList.get(a + 8), htmlList.get(a + 9) , htmlList.get(a + 26)));
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


}
