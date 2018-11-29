package com.example.hun.baseballrecord.Activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.hun.baseballrecord.Adapter.RecyclerAdapter;
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
    private String htmlURL = "http://www.statiz.co.kr/stat.php";
    private int count = 0;
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

        recyclerView = findViewById(R.id.recyclerView);
        dataList=new ArrayList<RecyclerModel>();
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
    }

    private void addDummy(){
//        dataList.add(new RecyclerModel("순위", "이름", "war"));

    }


    private void setRecyclerView(){
        recyclerAdapter=new RecyclerAdapter(getApplicationContext(),R.layout.activity_main_item, dataList);
        recyclerAdapter.set(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d("#####", v+" "+hasFocus);
            }
        });

//        recyclerAdapter.OnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Log.d("####", ""+ v);
//            }
//        });
//
//        RecyclerAdapter.OnItemClickListener mOnItemClickListener = new RecyclerAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View v, int position) {
//                count = position;
//                Log.d("position ==>", " " + count);
//            }
//        };

//        recyclerAdapter.setOnItemClickListener(mOnItemClickListener);


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

                dataList.add(new RecyclerModel("순위", "이름", "WAR"));

                //테스트1
//                Elements titles= doc.select("div.box-body tr td span");
                Elements titles= doc.select("table.table tr td");

                //Elements titles= doc.select("div.box-body tr td");    //한개 씩 뜯어서 나옴
                int i = 0;
                System.out.println("-------------------------------------------------------------");
                for(Element e: titles){
 //                   System.out.println("title: " + e.text());
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








//                Elements titlesss= doc.select("div.box-body tr.colhead_stz0");
//
//                System.out.println("-------------------------------------------------------------");
//                for(Element e: titlesss){
//                       System.out.println("titlesss : " + e.text());
//                    dataList.add(new RecyclerModel(String.valueOf(++i), e.text()));
//                }

//                //테스트2
//                titles= doc.select("div.news-con h2.tit-news");
//
//                System.out.println("-------------------------------------------------------------");
//                for(Element e: titles){
//                    System.out.println("title: " + e.text());
//                }
//
//                //테스트3
//                titles= doc.select("li.section02 div.con h2.news-tl");
//
//                System.out.println("-------------------------------------------------------------");
//                for(Element e: titles){
//                    System.out.println("title: " + e.text());
//                }
//                System.out.println("-------------------------------------------------------------");

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
