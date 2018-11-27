package com.example.hun.baseballrecord.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.hun.baseballrecord.Adapter.RecyclerAdapter;
import com.example.hun.baseballrecord.Model.RecyclerModel;
import com.example.hun.baseballrecord.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView = null;
    private RecyclerAdapter recyclerAdapter = null;
    private List<RecyclerModel> dataList=null;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        addDummy();
        setRecyclerView();
    }

    /**
     * 레이아웃 초기화
     */
    private void init(){

        recyclerView = findViewById(R.id.recyclerView);
        dataList=new ArrayList<RecyclerModel>();
    }

    private void addDummy(){
        dataList.add(new RecyclerModel("111", "222"));
        dataList.add(new RecyclerModel("333", "444"));
        dataList.add(new RecyclerModel("555", "666"));
        dataList.add(new RecyclerModel("777", "888"));
        dataList.add(new RecyclerModel("999", "999"));
    }
    private void setRecyclerView(){
        recyclerAdapter=new RecyclerAdapter(getApplicationContext(),R.layout.activity_main_item, dataList);
        recyclerAdapter.set(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d("#####", v+" "+hasFocus);
            }
        });


        recyclerAdapter.OnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("####", ""+ v);
            }
        });



        RecyclerAdapter.OnItemClickListener mOnItemClickListener = new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                count = position;
                Log.d("position ==>", " " + position);
            }
        };

        recyclerAdapter.setOnItemClickListener(mOnItemClickListener);


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.requestFocus();

    }

}
