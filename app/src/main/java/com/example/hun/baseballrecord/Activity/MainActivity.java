package com.example.hun.baseballrecord.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hun.baseballrecord.Adapter.MainMenuRecyclerAdapter;
import com.example.hun.baseballrecord.Fragment.MainFragment;
import com.example.hun.baseballrecord.Fragment.NewsFragment;
import com.example.hun.baseballrecord.Fragment.TeamDetailFragment;
import com.example.hun.baseballrecord.Fragment.TeamFragment;
import com.example.hun.baseballrecord.Global.GlobalVariable;
import com.example.hun.baseballrecord.Model.MainMenuRecyclerModel;
import com.example.hun.baseballrecord.R;
import com.example.hun.baseballrecord.Settings.SettingActivity;

import java.util.ArrayList;
import java.util.List;

//import butterknife.BindView;
//import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    private static String TAG = "MainActivity";

    private RecyclerView mainMenuRecyclerView = null;
    private MainMenuRecyclerAdapter mainMenuRecyclerAdapter = null;
    private List<MainMenuRecyclerModel> menuList = null;
    private DrawerLayout mDrawerLayout;
    private Toolbar toolBar;
    private LinearLayout mDrawerView;

//    @BindView(R.id.drawerView)
//    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
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
        mDrawerView = findViewById(R.id.drawerView);
        toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        }

        toolBar.setTitle(R.string.total);
        toolBar.setSubtitle("부제목");
        toolBar.setNavigationIcon(R.drawable.btn_list);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDrawerView.getVisibility()==View.VISIBLE){
                    mDrawerLayout.closeDrawers();
                } else {
                    mDrawerLayout.openDrawer(mDrawerView);
                }

            }
        });
        addMainMenuDummy();
        setMainMenuRecyclerView();
        callFragment(GlobalVariable.Main_Fragment);
    }

    //툴바에 메뉴 넣기
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.actionbar_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home :
                Toast.makeText(MainActivity.this, "back button click", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_bt1:
                Toast.makeText(this, "개발 중인 기능입니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_bt2:
                Toast.makeText(this, "개발 중인 기능입니다.", Toast.LENGTH_SHORT).show();
                Intent SettingActivity = new Intent(this, com.example.hun.baseballrecord.Settings.SettingActivity.class);
                startActivity(SettingActivity);
                break;
            default :
                break;
        }
        return super.onOptionsItemSelected(item) ;
    }


    private void addMainMenuDummy() {
        Log.d(TAG, "addMainMenuDummy");
        menuList.add(new MainMenuRecyclerModel("시즌 순위"));
        menuList.add(new MainMenuRecyclerModel("선수 기록"));
        menuList.add(new MainMenuRecyclerModel("전체 팀"));
        menuList.add(new MainMenuRecyclerModel("네이버 뉴스"));
        menuList.add(new MainMenuRecyclerModel("유투브 검색"));
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
                TeamFragment fragment1 = new TeamFragment();
                transaction.replace(R.id.fragment_container, fragment1);
                transaction.commit();
//                transaction.addToBackStack(null);
                toolBar.setTitle(R.string.team_total);
                toolBar.setSubtitle("야구");
                break;

            case 1:
                // '프래그먼트2' 호출
                MainFragment fragment2 = new MainFragment();
                transaction.replace(R.id.fragment_container, fragment2);
                transaction.commit();
                transaction.addToBackStack(null);
                toolBar.setTitle(R.string.total);
                toolBar.setSubtitle("종합 기록");
                break;

            case 2:
                // '프래그먼트3' 호출
                TeamDetailFragment fragment3 = new TeamDetailFragment();
                transaction.replace(R.id.fragment_container, fragment3);
                transaction.commit();
                transaction.addToBackStack(null);
                toolBar.setTitle("선수 기록");
                toolBar.setSubtitle("기록");
                break;

            case 3:
                // '프래그먼트4' 호출
                NewsFragment fragment4 = new NewsFragment();
                transaction.replace(R.id.fragment_container, fragment4);
                transaction.commit();
                transaction.addToBackStack(null);
                toolBar.setTitle("네이버 뉴스");
                toolBar.setSubtitle("프로 야구");
                break;

            case 4:
                // '프래그먼트5' 호출
//                NewsFragment fragment5 = new NewsFragment();
//                transaction.replace(R.id.fragment_container, fragment5);
//                transaction.commit();
//                toolBar.setTitle("비디오");
                Intent intent = new Intent(this, VideoActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onBackStackChanged() {

    }

    public interface onKeyBackPressedListener {
        void onBackKey();
    }
    private onKeyBackPressedListener mOnKeyBackPressedListener;
    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener){
        mOnKeyBackPressedListener = listener;
    }

    @Override
    public void onBackPressed(){
        if(mOnKeyBackPressedListener != null){
            mOnKeyBackPressedListener.onBackKey();
        } else {
            if(getSupportFragmentManager().getBackStackEntryCount()==0){
//                Toast.makeText(getApplicationContext(), "종료하려면 한번 더 누르세요.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                super.onBackPressed();
            }
        }
    }


}
