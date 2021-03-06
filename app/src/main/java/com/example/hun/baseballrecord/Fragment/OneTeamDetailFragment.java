package com.example.hun.baseballrecord.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hun.baseballrecord.Activity.MainActivity;
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

public class OneTeamDetailFragment extends Fragment implements MainActivity.onKeyBackPressedListener{
    private static String TAG = "OneTeamDetailFragment";

    private View mRootView;
    private List<OneTeamDetailFragmentRecyclerModel> dataList = null;
    private OneTeamDetailFrgmentRecyclerAdapter mOneTeamDetailFragmentRecyclerAdapter = null;
    private RecyclerView oneTeamDetailRecyclerView;
    private int positionArg = 0;
    private Spinner yearSpinner;
    private TextView mSearchBtn;
    private TextView noRecord;
    private String accessUrl = "http://www.statiz.co.kr/team.php?opt=0&sopt=7";
    private String teamString = "두산";
    private String mSearchYear = "2018";
    private boolean validCheck = true;
//    private String accessUrl = "http://www.statiz.co.kr/team.php?opt=0&sopt=7&year=2018&team=lg";


    public OneTeamDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            positionArg = getArguments().getInt("POSITION");
        }

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
        yearSpinner = mRootView.findViewById(R.id.search_year_player);
        mSearchBtn = mRootView.findViewById(R.id.search_year_btn);
        noRecord = mRootView.findViewById(R.id.no_record);
        addMainMenuDummy();
//        setRecyclerView();


        if(positionArg==0) teamString = "두산";
        else if(positionArg==1) teamString = "sk";
        else if(positionArg==2) teamString = "kt";
        else if(positionArg==3) teamString = "lg";
        else if(positionArg==4) teamString = "nc";
        else if(positionArg==5) teamString = "삼성";
        else if(positionArg==6) teamString = "롯데";
        else if(positionArg==7) teamString = "kia";
        else if(positionArg==8) teamString = "넥센";
        else if(positionArg==9) teamString = "한화";

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mSearchYear = String.valueOf(adapterView.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "search year ===> " + mSearchYear);
                OneTeamDetailFragment.JsoupAsyncTask jsoupAsyncTask = new OneTeamDetailFragment.JsoupAsyncTask();
                jsoupAsyncTask.execute();
            }
        });


        OneTeamDetailFragment.JsoupAsyncTask jsoupAsyncTask = new OneTeamDetailFragment.JsoupAsyncTask();
        jsoupAsyncTask.execute();
        Log.d(TAG, "positionArg ====> " + positionArg);



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
            public void onItemClick(View v, int position, String playerName) {
                Log.d(TAG, "position ==>  " + position);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Bundle args = new Bundle();
                args.putString("PLAYER_NAME", playerName);
                args.putString("SELECT_TEAM", teamString);

                PlayerFragment fragment1 = new PlayerFragment();
                transaction.replace(R.id.fragment_container, fragment1);
                transaction.addToBackStack(null);
                fragment1.setArguments(args);
                transaction.commit();

            }
        };
        mOneTeamDetailFragmentRecyclerAdapter.setOnItemClickListener(mOnItemClickListener);
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


                Log.d(TAG, "html url ===> " + accessUrl +  "&year=" + mSearchYear + "&team=" + teamString);
                Document doc = Jsoup.connect(accessUrl +  "&year=" + mSearchYear + "&team=" + teamString).get();


                dataList.clear();
                String temp = "";

                Elements backNumber = doc.select("div.box-body");

                if(!backNumber.get(2).text().isEmpty()){
                    validCheck = true;
                    temp = backNumber.get(2).text();
                    Log.d(TAG, "backNumber ===> " + backNumber.get(2).text());
                    temp.split(" ");
                    Log.d(TAG, "length   " + temp.split(" ").length);

                    String[] arr;

                    arr = temp.split(" ");

                    for(int i=0; i<arr.length; i = i+2){
                        dataList.add( new OneTeamDetailFragmentRecyclerModel(arr[i+1], arr[i]));
                    }
                    // http://www.statiz.co.kr/player.php?opt=0&name=     ////// 특정 선수 조회
                } else {
                    validCheck = false;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            asyncDialog.dismiss();
            if(!validCheck){
                noRecord.setVisibility(View.VISIBLE);
            } else {
                noRecord.setVisibility(View.GONE);
            }
            setRecyclerView();
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
