package com.example.hun.baseballrecord.Activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.hun.baseballrecord.Adapter.MainMenuRecyclerAdapter;
import com.example.hun.baseballrecord.Fragment.MainFragment;
import com.example.hun.baseballrecord.Fragment.TeamFragment;
import com.example.hun.baseballrecord.Global.GlobalVariable;
import com.example.hun.baseballrecord.Model.MainMenuRecyclerModel;
import com.example.hun.baseballrecord.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";

    private RecyclerView mainMenuRecyclerView = null;
    private MainMenuRecyclerAdapter mainMenuRecyclerAdapter = null;
    private List<MainMenuRecyclerModel> menuList = null;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    /**
     * 레이아웃 초기화
     */
    private void init() {
        Log.d(TAG, "init()");
        mainMenuRecyclerView = findViewById(R.id.menuRecyclerView);
        menuList = new ArrayList<>();
        mDrawerLayout = findViewById(R.id.drawerLayout);
        addMainMenuDummy();
        setMainMenuRecyclerView();
        callFragment(GlobalVariable.Main_Fragment);
    }


    private void addMainMenuDummy() {
        Log.d(TAG, "addMainMenuDummy");
        menuList.add(new MainMenuRecyclerModel("선수 기록"));
        menuList.add(new MainMenuRecyclerModel("시즌 순위"));
        menuList.add(new MainMenuRecyclerModel("3번"));
        menuList.add(new MainMenuRecyclerModel("4번"));
        menuList.add(new MainMenuRecyclerModel("5번"));
    }

    private void setMainMenuRecyclerView() {
        Log.d(TAG, "setMainMenuRecyclerView");
        mainMenuRecyclerAdapter = new MainMenuRecyclerAdapter(getApplicationContext(), R.layout.main_menu_recyclerview_item, menuList);
        mainMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        MainMenuRecyclerAdapter.OnItemClickListener mOnItemClickListener = new MainMenuRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "position ==>  " + position);
                mDrawerLayout.closeDrawers();
                callFragment(position);
            }
        };
        mainMenuRecyclerAdapter.setOnItemClickListener(mOnItemClickListener);

        mainMenuRecyclerView.setAdapter(mainMenuRecyclerAdapter);
    }

    private void callFragment(int fragmentNum) {

        // 프래그먼트 사용을 위해
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragmentNum) {
            case 0:
                // '프래그먼트1' 호출
                MainFragment fragment1 = new MainFragment();
                transaction.replace(R.id.fragment_container, fragment1);
                transaction.commit();
                break;

            case 1:
                // '프래그먼트2' 호출
                TeamFragment fragment2 = new TeamFragment();
                transaction.replace(R.id.fragment_container, fragment2);
                transaction.commit();
                break;

            case 2:
                // '프래그먼트3' 호출
                MainFragment fragment3 = new MainFragment();
                transaction.replace(R.id.fragment_container, fragment3);
                transaction.commit();
                break;

            case 3:
                // '프래그먼트4' 호출
                TeamFragment fragment4 = new TeamFragment();
                transaction.replace(R.id.fragment_container, fragment4);
                transaction.commit();
                break;

            case 4:
                // '프래그먼트5' 호출
                MainFragment fragment5 = new MainFragment();
                transaction.replace(R.id.fragment_container, fragment5);
                transaction.commit();
                break;
        }

    }

}
