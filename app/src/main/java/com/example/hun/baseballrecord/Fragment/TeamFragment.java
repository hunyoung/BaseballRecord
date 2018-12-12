package com.example.hun.baseballrecord.Fragment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.hun.baseballrecord.Adapter.TeamFrgmentRecyclerAdapter;
import com.example.hun.baseballrecord.Model.TeamFragmentRecyclerModel;
import com.example.hun.baseballrecord.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import butterknife.BindView;
//import butterknife.ButterKnife;

public class TeamFragment extends Fragment implements SeekBar.OnSeekBarChangeListener,
        OnChartGestureListener, OnChartValueSelectedListener, View.OnClickListener {
    private static String TAG = "TeamFragment";
    private String htmlURL = "https://www.koreabaseball.com/TeamRank/TeamRank.aspx";

    private View mRootView;
    private List<TeamFragmentRecyclerModel> dataList = null;
    private TeamFrgmentRecyclerAdapter mTeamFragmentRecyclerAdapter = null;
    private List<String> htmlList = new ArrayList<>();
    private RecyclerView teamRecyclerView;
    private LineChart chart;
    private TextView mCurDate;
    private String dateString;
    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;
    private TextView mDoosan, mLG, mNexen, mLotte, mSamsung, mSk, mHanhwa, mKia, mKt, mNc;
    private TextView mAllVisible, mALLGone;
    private ArrayList<ILineDataSet> dataSets;



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
        mDoosan = mRootView.findViewById(R.id.team_doosan);
        mDoosan.setOnClickListener(this);
        mLG = mRootView.findViewById(R.id.team_lg);
        mLG.setOnClickListener(this);
        mLotte = mRootView.findViewById(R.id.team_lotte);
        mLotte.setOnClickListener(this);
        mHanhwa = mRootView.findViewById(R.id.team_hanhwa);
        mHanhwa.setOnClickListener(this);
        mKia = mRootView.findViewById(R.id.team_kia);
        mKia.setOnClickListener(this);
        mKt = mRootView.findViewById(R.id.team_kt);
        mKt.setOnClickListener(this);
        mSamsung = mRootView.findViewById(R.id.team_samsung);
        mSamsung.setOnClickListener(this);
        mSk = mRootView.findViewById(R.id.team_sk);
        mSk.setOnClickListener(this);
        mNc = mRootView.findViewById(R.id.team_nc);
        mNc.setOnClickListener(this);
        mNexen = mRootView.findViewById(R.id.team_nexen);
        mNexen.setOnClickListener(this);
        mAllVisible = mRootView.findViewById(R.id.team_all_visible);
        mAllVisible.setOnClickListener(this);
        mALLGone = mRootView.findViewById(R.id.team_all_gone);
        mALLGone.setOnClickListener(this);


        mCurDate = mRootView.findViewById(R.id.date);
        chart = mRootView.findViewById(R.id.chart);
        tvX = mRootView.findViewById(R.id.tvXMax);
        tvY = mRootView.findViewById(R.id.tvYMax);

        seekBarX = mRootView.findViewById(R.id.seekBar1);
        seekBarX.setOnSeekBarChangeListener(this);

        seekBarY = mRootView.findViewById(R.id.seekBar2);
        seekBarY.setOnSeekBarChangeListener(this);

        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);
        chart.setDrawBorders(false);

        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setDrawAxisLine(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setDrawGridLines(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

        seekBarX.setProgress(1982);

        seekBarY.setProgress(5);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        //l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setTextSize(20);
        l.setFormSize(15);
        l.setFormLineWidth(100);
        l.setDrawInside(false);
        chart.getLegend().setWordWrapEnabled(true);

        addMainMenuDummy();
        //chartSettings();
//        setRecyclerView();
        TeamFragment.JsoupAsyncTask jsoupAsyncTask = new TeamFragment.JsoupAsyncTask();
        jsoupAsyncTask.execute();

    }

    public void onClick(View v) {
        //여기에 할 일을 적어주세요.
        switch (v.getId()) {
            case R.id.team_doosan :
                if((dataSets.get(0)).isVisible()){ (dataSets.get(0)).setVisible(false);}
                else {(dataSets.get(0)).setVisible(true); }
                chart.invalidate();
                break ;
            case R.id.team_lg :
                if((dataSets.get(1)).isVisible()){ (dataSets.get(1)).setVisible(false); }
                else { (dataSets.get(1)).setVisible(true); }
                chart.invalidate();
            break ;
            case R.id.team_samsung :
                if((dataSets.get(2)).isVisible()){ (dataSets.get(2)).setVisible(false); }
                else { (dataSets.get(2)).setVisible(true); }
                chart.invalidate();
                break ;
            case R.id.team_lotte :
                if((dataSets.get(3)).isVisible()){ (dataSets.get(3)).setVisible(false); }
                else { (dataSets.get(3)).setVisible(true); }
                chart.invalidate();
                break ;
            case R.id.team_kia :
                if((dataSets.get(4)).isVisible()){ (dataSets.get(4)).setVisible(false); }
                else { (dataSets.get(4)).setVisible(true); }
                chart.invalidate();
                break ;
            case R.id.team_hanhwa :
                if((dataSets.get(5)).isVisible()){ (dataSets.get(5)).setVisible(false); }
                else { (dataSets.get(5)).setVisible(true); }
                chart.invalidate();
                break ;
            case R.id.team_nc :
                if((dataSets.get(6)).isVisible()){ (dataSets.get(6)).setVisible(false); }
                else { (dataSets.get(6)).setVisible(true); }
                chart.invalidate();
                break ;
            case R.id.team_kt :
                if((dataSets.get(7)).isVisible()){ (dataSets.get(7)).setVisible(false); }
                else { (dataSets.get(7)).setVisible(true); }
                chart.invalidate();
                break ;
            case R.id.team_sk :
                if((dataSets.get(8)).isVisible()){ (dataSets.get(8)).setVisible(false); }
                else { (dataSets.get(8)).setVisible(true); }
                chart.invalidate();
                break ;
            case R.id.team_nexen :
                if((dataSets.get(9)).isVisible()){ (dataSets.get(9)).setVisible(false); }
                else { (dataSets.get(9)).setVisible(true); }
                chart.invalidate();
                break ;
            case R.id.team_all_visible :
                for(int i=0; i<10; i++){
                    (dataSets.get(i)).setVisible(true);
                }
                chart.invalidate();
                break ;
            case R.id.team_all_gone :
                for(int i=0; i<10; i++){
                    (dataSets.get(i)).setVisible(false);
                }
                chart.invalidate();
                break ;


        }
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


    private final int[] colors = new int[] {
            ColorTemplate.VORDIPLOM_COLORS[0],
            ColorTemplate.VORDIPLOM_COLORS[1],
            ColorTemplate.VORDIPLOM_COLORS[2]
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        chart.resetTracking();

        progress = seekBarX.getProgress();

        Log.d(TAG, "progress ===>   "+ progress);
        tvX.setText(String.valueOf(seekBarX.getProgress()));
        tvY.setText(String.valueOf(seekBarY.getProgress()));

        /*ArrayList<ILineDataSet>*/
        dataSets = new ArrayList<>();



        // 여기가 데이터 세팅 하는 부분
        for (int z = 0; z < 10; z++) {

            ArrayList<Entry> values = new ArrayList<>();
            rankList(values, z);

//            for (int i = 0; i < progress; i++) {
//                double val = (Math.random() * seekBarY.getProgress()) + 3;
//                values.add(new Entry(i, (float) val));
//            }

            String tempName ;
            if (z == 0) { tempName = "두산"; }
            else if(z ==1){ tempName = "LG"; }
            else if(z ==2){ tempName = "삼성"; }
            else if(z ==3){ tempName = "롯데"; }
            else if(z ==4){ tempName = "KIA"; }
            else if(z ==5){ tempName = "한화"; }
            else if(z ==6){ tempName = "NC"; }
            else if(z ==7){ tempName = "KT"; }
            else if(z ==8){ tempName = "SK"; }
            else if(z ==9){ tempName = "넥센"; }
            else { tempName = "팀"; }

            LineDataSet d = new LineDataSet(values, tempName);
            LineData lineData = new LineData(d);
            chart.setData(lineData);

            XAxis xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);

            YAxis yRAxis = chart.getAxisRight();
            yRAxis.setDrawGridLines(false);
            yRAxis.setInverted(true);

//            Description des = chart.getDescription();
//            des.setEnabled(false);

//            Legend legend = chart.getLegend();
//            legend.setTextSize(20);
//            legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);

            d.setLineWidth(3);
            d.setCircleRadius(3);

//            int color = colors[z % colors.length];
//            d.setColor(color);
//            d.setCircleColor(color);


            d.setCircleColor(Color.BLACK);
            dataSets.add(d);
        }

        // make the first DataSet dashed
//        ((LineDataSet) dataSets.get(0)).enableDashedLine(10, 10, 0);
//        ((LineDataSet) dataSets.get(0)).setColors(Color.rgb(192, 255, 140));
//        ((LineDataSet) dataSets.get(0)).setColors(ColorTemplate.VORDIPLOM_COLORS);
//        ((LineDataSet) dataSets.get(0)).setCircleColors(ColorTemplate.VORDIPLOM_COLORS);


        ((LineDataSet) dataSets.get(0)).setColors(Color.parseColor("#000054"));
        ((LineDataSet) dataSets.get(1)).setColors(Color.parseColor("#ff3366"));
        ((LineDataSet) dataSets.get(2)).setColors(Color.BLUE);
        ((LineDataSet) dataSets.get(3)).setColors(Color.GREEN);
        ((LineDataSet) dataSets.get(4)).setColors(Color.parseColor("#c90000"));
        ((LineDataSet) dataSets.get(5)).setColors(Color.parseColor("#ed4c00"));
        ((LineDataSet) dataSets.get(6)).setColors(Color.parseColor("#002266"));
        ((LineDataSet) dataSets.get(7)).setColors(Color.BLACK);
        ((LineDataSet) dataSets.get(8)).setColors(Color.parseColor("#ff1212"));
        ((LineDataSet) dataSets.get(9)).setColors(Color.parseColor("#740000"));

        LineData data = new LineData(dataSets);
        chart.setData(data);

        chart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if(lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            chart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart long pressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart fling. VelocityX: " + velocityX + ", VelocityY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", xIndex: " + e.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {}



    private void rankList(ArrayList<Entry> values, int order){
       // values = new ArrayList<>();
        if(order==0){
            values.add(new Entry(1982,10));
            values.add(new Entry(1983,5));
            values.add(new Entry(1984,8));
            values.add(new Entry(1985,7));
            values.add(new Entry(1986,7));
            values.add(new Entry(1987,7));
            values.add(new Entry(1988,6));
            values.add(new Entry(1989,6));
            values.add(new Entry(1990,4));
            values.add(new Entry(1991,3));
            values.add(new Entry(1992,6));
            values.add(new Entry(1993,8));
            values.add(new Entry(1994,4));
            values.add(new Entry(1995,10));
            values.add(new Entry(1996,3));
            values.add(new Entry(1997,6));
            values.add(new Entry(1998,7));
            values.add(new Entry(1999,8));
            values.add(new Entry(2000,9));
            values.add(new Entry(2001,10));
            values.add(new Entry(2002,6));
            values.add(new Entry(2003,4));
            values.add(new Entry(2004,8));
            values.add(new Entry(2005,9));
            values.add(new Entry(2006,6));
            values.add(new Entry(2007,9));
            values.add(new Entry(2008,9));
            values.add(new Entry(2009,8));
            values.add(new Entry(2010,8));
            values.add(new Entry(2011,6));
            values.add(new Entry(2012,8));
            values.add(new Entry(2013,9));
            values.add(new Entry(2014,5));
            values.add(new Entry(2015,10));
            values.add(new Entry(2016,10));
            values.add(new Entry(2017,9));
            values.add(new Entry(2018,9));
        } else if(order ==1){
            values.add(new Entry(1982,8));
            values.add(new Entry(1983,9));
            values.add(new Entry(1984,7));
            values.add(new Entry(1985,6));
            values.add(new Entry(1986,8));
            values.add(new Entry(1987,6));
            values.add(new Entry(1988,5));
            values.add(new Entry(1989,5));
            values.add(new Entry(1990,10));
            values.add(new Entry(1991,5));
            values.add(new Entry(1992,4));
            values.add(new Entry(1993,7));
            values.add(new Entry(1994,10));
            values.add(new Entry(1995,8));
            values.add(new Entry(1996,4));
            values.add(new Entry(1997,9));
            values.add(new Entry(1998,9));
            values.add(new Entry(1999,5));
            values.add(new Entry(2000,7));
            values.add(new Entry(2001,5));
            values.add(new Entry(2002,9));
            values.add(new Entry(2003,5));
            values.add(new Entry(2004,5));
            values.add(new Entry(2005,5));
            values.add(new Entry(2006,3));
            values.add(new Entry(2007,6));
            values.add(new Entry(2008,3));
            values.add(new Entry(2009,4));
            values.add(new Entry(2010,5));
            values.add(new Entry(2011,5));
            values.add(new Entry(2012,4));
            values.add(new Entry(2013,8));
            values.add(new Entry(2014,7));
            values.add(new Entry(2015,2));
            values.add(new Entry(2016,7));
            values.add(new Entry(2017,5));
            values.add(new Entry(2018,3));
        } else if(order ==2){
            values.add(new Entry(1982,9));
            values.add(new Entry(1983,7));
            values.add(new Entry(1984,9));
            values.add(new Entry(1985,10));
            values.add(new Entry(1986,9));
            values.add(new Entry(1987,9));
            values.add(new Entry(1988,7));
            values.add(new Entry(1989,7));
            values.add(new Entry(1990,9));
            values.add(new Entry(1991,8));
            values.add(new Entry(1992,7));
            values.add(new Entry(1993,9));
            values.add(new Entry(1994,6));
            values.add(new Entry(1995,6));
            values.add(new Entry(1996,5));
            values.add(new Entry(1997,7));
            values.add(new Entry(1998,8));
            values.add(new Entry(1999,7));
            values.add(new Entry(2000,8));
            values.add(new Entry(2001,9));
            values.add(new Entry(2002,10));
            values.add(new Entry(2003,7));
            values.add(new Entry(2004,9));
            values.add(new Entry(2005,10));
            values.add(new Entry(2006,10));
            values.add(new Entry(2007,7));
            values.add(new Entry(2008,7));
            values.add(new Entry(2009,6));
            values.add(new Entry(2010,9));
            values.add(new Entry(2011,10));
            values.add(new Entry(2012,10));
            values.add(new Entry(2013,10));
            values.add(new Entry(2014,10));
            values.add(new Entry(2015,9));
            values.add(new Entry(2016,2));
            values.add(new Entry(2017,2));
            values.add(new Entry(2018,5));
        } else if(order ==3){
            values.add(new Entry(1982,6));
            values.add(new Entry(1983,5));
            values.add(new Entry(1984,10));
            values.add(new Entry(1985,9));
            values.add(new Entry(1986,6));
            values.add(new Entry(1987,8));
            values.add(new Entry(1988,8));
            values.add(new Entry(1989,4));
            values.add(new Entry(1990,5));
            values.add(new Entry(1991,7));
            values.add(new Entry(1992,10));
            values.add(new Entry(1993,5));
            values.add(new Entry(1994,5));
            values.add(new Entry(1995,9));
            values.add(new Entry(1996,6));
            values.add(new Entry(1997,3));
            values.add(new Entry(1998,3));
            values.add(new Entry(1999,2));
            values.add(new Entry(2000,6));
            values.add(new Entry(2001,3));
            values.add(new Entry(2002,3));
            values.add(new Entry(2003,3));
            values.add(new Entry(2004,3));
            values.add(new Entry(2005,6));
            values.add(new Entry(2006,4));
            values.add(new Entry(2007,4));
            values.add(new Entry(2008,8));
            values.add(new Entry(2009,7));
            values.add(new Entry(2010,7));
            values.add(new Entry(2011,8));
            values.add(new Entry(2012,7));
            values.add(new Entry(2013,6));
            values.add(new Entry(2014,4));
            values.add(new Entry(2015,3));
            values.add(new Entry(2016,3));
            values.add(new Entry(2017,8));
            values.add(new Entry(2018,4));
        }  else if(order ==4){
            values.add(new Entry(1982,7));
            values.add(new Entry(1983,10));
            values.add(new Entry(1984,6));
            values.add(new Entry(1985,8));
            values.add(new Entry(1986,10));
            values.add(new Entry(1987,10));
            values.add(new Entry(1988,10));
            values.add(new Entry(1989,10));
            values.add(new Entry(1990,8));
            values.add(new Entry(1991,10));
            values.add(new Entry(1992,8));
            values.add(new Entry(1993,10));
            values.add(new Entry(1994,7));
            values.add(new Entry(1995,7));
            values.add(new Entry(1996,10));
            values.add(new Entry(1997,10));
            values.add(new Entry(1998,6));
            values.add(new Entry(1999,4));
            values.add(new Entry(2000,5));
            values.add(new Entry(2001,6));
            values.add(new Entry(2002,8));
            values.add(new Entry(2003,8));
            values.add(new Entry(2004,7));
            values.add(new Entry(2005,3));
            values.add(new Entry(2006,7));
            values.add(new Entry(2007,3));
            values.add(new Entry(2008,5));
            values.add(new Entry(2009,10));
            values.add(new Entry(2010,6));
            values.add(new Entry(2011,7));
            values.add(new Entry(2012,6));
            values.add(new Entry(2013,3));
            values.add(new Entry(2014,3));
            values.add(new Entry(2015,4));
            values.add(new Entry(2016,6));
            values.add(new Entry(2017,10));
            values.add(new Entry(2018,6));
        }   else if(order ==5){
            values.add(new Entry(1986,4));
            values.add(new Entry(1987,5));
            values.add(new Entry(1988,9));
            values.add(new Entry(1989,9));
            values.add(new Entry(1990,7));
            values.add(new Entry(1991,9));
            values.add(new Entry(1992,9));
            values.add(new Entry(1993,6));
            values.add(new Entry(1994,8));
            values.add(new Entry(1995,5));
            values.add(new Entry(1996,7));
            values.add(new Entry(1997,4));
            values.add(new Entry(1998,4));
            values.add(new Entry(1999,10));
            values.add(new Entry(2000,4));
            values.add(new Entry(2001,7));
            values.add(new Entry(2002,4));
            values.add(new Entry(2003,6));
            values.add(new Entry(2004,4));
            values.add(new Entry(2005,7));
            values.add(new Entry(2006,9));
            values.add(new Entry(2007,8));
            values.add(new Entry(2008,6));
            values.add(new Entry(2009,3));
            values.add(new Entry(2010,3));
            values.add(new Entry(2011,5));
            values.add(new Entry(2012,3));
            values.add(new Entry(2013,2));
            values.add(new Entry(2014,2));
            values.add(new Entry(2015,5));
            values.add(new Entry(2016,4));
            values.add(new Entry(2017,3));
            values.add(new Entry(2018,8));
        }   else if(order ==6){
            values.add(new Entry(2013,4));
            values.add(new Entry(2014,8));
            values.add(new Entry(2015,8));
            values.add(new Entry(2016,9));
            values.add(new Entry(2017,7));
            values.add(new Entry(2018,1));
        }   else if(order ==7){
            values.add(new Entry(2015,1));
            values.add(new Entry(2016,1));
            values.add(new Entry(2017,1));
            values.add(new Entry(2018,2));
        }   else if(order ==8){
            values.add(new Entry(2000,3));
            values.add(new Entry(2001,4));
            values.add(new Entry(2002,5));
            values.add(new Entry(2003,9));
            values.add(new Entry(2004,6));
            values.add(new Entry(2005,8));
            values.add(new Entry(2006,5));
            values.add(new Entry(2007,10));
            values.add(new Entry(2008,10));
            values.add(new Entry(2009,9));
            values.add(new Entry(2010,10));
            values.add(new Entry(2011,9));
            values.add(new Entry(2012,9));
            values.add(new Entry(2013,5));
            values.add(new Entry(2014,6));
            values.add(new Entry(2015,6));
            values.add(new Entry(2016,5));
            values.add(new Entry(2017,6));
            values.add(new Entry(2018,10));
        }   else if(order ==9){
            values.add(new Entry(2008,4));
            values.add(new Entry(2009,5));
            values.add(new Entry(2010,4));
            values.add(new Entry(2011,3));
            values.add(new Entry(2012,5));
            values.add(new Entry(2013,7));
            values.add(new Entry(2014,9));
            values.add(new Entry(2015,7));
            values.add(new Entry(2016,8));
            values.add(new Entry(2017,4));
            values.add(new Entry(2018,7));
        }

    }


}
