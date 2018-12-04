package com.example.hun.baseballrecord.Fragment;

import android.graphics.Color;
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

//import butterknife.BindView;
//import butterknife.ButterKnife;

public class TeamFragment extends Fragment {
    private static String TAG = "TeamFragment";
    private String htmlURL = "https://www.koreabaseball.com/TeamRank/TeamRank.aspx";

    private View mRootView;
    private List<TeamFragmentRecyclerModel> dataList = null;
    private TeamFrgmentRecyclerAdapter mTeamFragmentRecyclerAdapter = null;
    private List<String> htmlList = new ArrayList<>();
    private RecyclerView teamRecyclerView;
    private LineChart lineChart;
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
        lineChart = mRootView.findViewById(R.id.chart);
        addMainMenuDummy();
        chartSettings();
//        setRecyclerView();
        TeamFragment.JsoupAsyncTask jsoupAsyncTask = new TeamFragment.JsoupAsyncTask();
        jsoupAsyncTask.execute();

    }
    private void chartSettings(){
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1982,10));
        entries.add(new Entry(1983,5));
        entries.add(new Entry(1984,8));
        entries.add(new Entry(1985,7));
        entries.add(new Entry(1986,7));
        entries.add(new Entry(1987,7));
        entries.add(new Entry(1988,6));
        entries.add(new Entry(1989,6));
        entries.add(new Entry(1990,4));
        entries.add(new Entry(1991,3));
        entries.add(new Entry(1992,6));
        entries.add(new Entry(1993,8));
        entries.add(new Entry(1994,4));
        entries.add(new Entry(1995,10));
        entries.add(new Entry(1996,3));
        entries.add(new Entry(1997,6));
        entries.add(new Entry(1998,7));
        entries.add(new Entry(1999,8));
        entries.add(new Entry(2000,9));
        entries.add(new Entry(2001,10));
        entries.add(new Entry(2002,6));
        entries.add(new Entry(2003,4));
        entries.add(new Entry(2004,8));
        entries.add(new Entry(2005,9));
        entries.add(new Entry(2006,6));
        entries.add(new Entry(2007,9));
        entries.add(new Entry(2008,9));
        entries.add(new Entry(2009,8));
        entries.add(new Entry(2010,8));
        entries.add(new Entry(2011,6));
        entries.add(new Entry(2012,8));
        entries.add(new Entry(2013,9));
        entries.add(new Entry(2014,5));
        entries.add(new Entry(2015,10));
        entries.add(new Entry(2016,10));
        entries.add(new Entry(2017,9));
        entries.add(new Entry(2018,9));

        LineDataSet lineDataSet = new LineDataSet(entries, "두산");
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(4);
        lineDataSet.setCircleColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setCircleColorHole(Color.BLUE);
        lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawValues(false);

        LineData lineData = new LineData(lineDataSet);

        List<Entry> entries2 = new ArrayList<>();
        entries2.add(new Entry(1982,8));
        entries2.add(new Entry(1983,9));
        entries2.add(new Entry(1984,7));
        entries2.add(new Entry(1985,6));
        entries2.add(new Entry(1986,8));
        entries2.add(new Entry(1987,6));
        entries2.add(new Entry(1988,5));
        entries2.add(new Entry(1989,5));
        entries2.add(new Entry(1990,10));
        entries2.add(new Entry(1991,5));
        entries2.add(new Entry(1992,4));
        entries2.add(new Entry(1993,7));
        entries2.add(new Entry(1994,10));
        entries2.add(new Entry(1995,8));
        entries2.add(new Entry(1996,4));
        entries2.add(new Entry(1997,9));
        entries2.add(new Entry(1998,9));
        entries2.add(new Entry(1999,5));
        entries2.add(new Entry(2000,7));
        entries2.add(new Entry(2001,5));
        entries2.add(new Entry(2002,9));
        entries2.add(new Entry(2003,5));
        entries2.add(new Entry(2004,5));
        entries2.add(new Entry(2005,5));
        entries2.add(new Entry(2006,3));
        entries2.add(new Entry(2007,6));
        entries2.add(new Entry(2008,3));
        entries2.add(new Entry(2009,4));
        entries2.add(new Entry(2010,5));
        entries2.add(new Entry(2011,5));
        entries2.add(new Entry(2012,4));
        entries2.add(new Entry(2013,8));
        entries2.add(new Entry(2014,7));
        entries2.add(new Entry(2015,2));
        entries2.add(new Entry(2016,7));
        entries2.add(new Entry(2017,5));
        entries2.add(new Entry(2018,8));

        LineDataSet lineDataSet2 = new LineDataSet(entries2, "LG");
        lineDataSet2.setLineWidth(2);
        lineDataSet2.setCircleRadius(6);
        lineDataSet2.setCircleColor(Color.parseColor("#FFFF00"));
        lineDataSet2.setCircleColorHole(Color.WHITE);
        lineDataSet2.setColor(Color.parseColor("#FFFFFF00"));
        lineDataSet2.setDrawCircleHole(true);
        lineDataSet2.setDrawCircles(true);
        lineDataSet2.setDrawHorizontalHighlightIndicator(false);
        lineDataSet2.setDrawHighlightIndicators(false);
        lineDataSet2.setDrawValues(false);

        LineData lineData2 = new LineData(lineDataSet2);


        lineChart.setData(lineData);
//        lineChart.setData(lineData2);


        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.enableGridDashedLine(8, 24, 0);

        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        Description description = new Description();
        description.setText("");

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription(description);
        lineChart.animateY(2000, Easing.EasingOption.EaseInCubic);
        lineChart.invalidate();
    }

    private void addMainMenuDummy() {
        Log.d(TAG, "addMainMenuDummy");
        dataList.add(new TeamFragmentRecyclerModel("순위", "팀명", "경기", "승", "패", "무", "승률", "게임차", "최근10경기", "연속", "홈", "원정"));

    }


    private void setRecyclerView() {
        Log.d(TAG, "setRecyclerView");
        mTeamFragmentRecyclerAdapter = new TeamFrgmentRecyclerAdapter(getContext(), R.layout.team_rank_item, dataList);
        teamRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        teamRecyclerView.setHasFixedSize(true);
        teamRecyclerView.setAdapter(mTeamFragmentRecyclerAdapter);

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
