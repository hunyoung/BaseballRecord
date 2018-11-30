package com.example.hun.baseballrecord.Activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.hun.baseballrecord.Adapter.MainMenuRecyclerAdapter;
import com.example.hun.baseballrecord.Adapter.RecyclerAdapter;
import com.example.hun.baseballrecord.Model.MainMenuRecyclerModel;
import com.example.hun.baseballrecord.Model.RecyclerModel;
import com.example.hun.baseballrecord.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";

    private RecyclerView recyclerView = null;
    private RecyclerAdapter recyclerAdapter = null;
    private List<RecyclerModel> dataList=null;

    private RecyclerView mainMenuRecyclerView = null;
    private MainMenuRecyclerAdapter mainMenuRecyclerAdapter = null;
    private List<MainMenuRecyclerModel> menuList = null;

    private String htmlURL = "http://www.statiz.co.kr/stat.php?re=0&lr=";  //타격
    // 투구 http://www.statiz.co.kr/stat.php?re=1&lr=

    //http://www.statiz.co.kr/stat.php?opt=0&sopt=0&re=0&ys=1993&ye=2018&se=0&te=&tm=&ty=0&qu=auto&po=0&as=&ae=&hi=&un=&pl=&da=1&o1=WAR_ALL_ADJ&o2=TPA&de=1&lr=0&tr=&cv=&ml=1&sn=30&si=&cn=
    // re == 0 종합 1 타격 2 수비 7 종합 ,
    // ys == 시작년도
    // ye == 종료년도
    // te == 팀 이름
    // se == 시즌(0~6) 정규, 포스트, 한국S, 플옵, 준플, 와카, 올스타, 섬머
    // po = 포지션(1 투수, 2 포수, ~~ 10 지타)

   private List<String> htmlList = new ArrayList<>();
    private List<String> nameLinkList = new ArrayList<>();
    private HashMap<String, String> map = new HashMap<String, String>();
    String a = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

      //  addDummy();
      //  setRecyclerView();

    }

    /**
     * 레이아웃 초기화
     */
    private void init(){
        Log.d(TAG, "init()");
        recyclerView = findViewById(R.id.recyclerView);
        dataList = new ArrayList<>();

        mainMenuRecyclerView = findViewById(R.id.menuRecyclerView);
        menuList = new ArrayList<>();
        addMainMenuDummy();

        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();

    }

    private void addDummy(){
//        dataList.add(new RecyclerModel("순위", "이름", "war"));

    }

    private void addMainMenuDummy(){
        menuList.add(new MainMenuRecyclerModel("선수 기록"));
        menuList.add(new MainMenuRecyclerModel("2번"));
        menuList.add(new MainMenuRecyclerModel("3번"));

        setMainMenuRecyclerView();
    }


    private void setMainMenuRecyclerView(){
        Log.d(TAG, "setMainMenuRecyclerView");
        mainMenuRecyclerAdapter = new MainMenuRecyclerAdapter(getApplicationContext(), R.layout.main_menu_recyclerview_item, menuList);
        mainMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mainMenuRecyclerView.setAdapter(mainMenuRecyclerAdapter);
    }


    private void setRecyclerView(){
        Log.d(TAG, "setRecyclerView");
        recyclerAdapter=new RecyclerAdapter(getApplicationContext(),R.layout.activity_main_item, dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
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
                Document doc = Jsoup.connect(htmlURL).get();

                //테스트1
                Elements titles= doc.select("table.table tr td");
                //Elements titles= doc.select("div.box-body tr td");    //한개 씩 뜯어서 나옴
                int i = 0;
                for(Element e: titles){
                    htmlList.add(e.text());
         //       dataList.add(new RecyclerModel(String.valueOf(++i), e.text()));
                }

                String link= "http://www.statiz.co.kr/" + doc.select("table.table tr td a").attr("href");
                Log.d(TAG, "link ====>>> " + link);

                for(int a = 0; a< 929 /*htmlList.size()*/; a++){
                    if(a % 31 == 1 ){
                        Log.d(TAG,"a ==== " + a + " 값 ==>  " + htmlList.get(a));
                        dataList.add(new RecyclerModel(String.valueOf(++i), htmlList.get(a), htmlList.get(a+28)));

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
