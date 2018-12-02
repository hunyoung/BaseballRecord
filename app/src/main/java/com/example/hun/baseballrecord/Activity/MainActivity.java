package com.example.hun.baseballrecord.Activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 메뉴 추가
        menu.add(0, 0, Menu.NONE, "About");

        // SubMenu 추가
        SubMenu subMenu = menu.addSubMenu("설정");
        // 메뉴 (0~7중) 1을 누르면 나오는 SubMenu
        subMenu.add(1, 3, Menu.NONE, "하나");
        subMenu.add(1, 4, Menu.NONE, "둘");
        subMenu.add(1, 5, Menu.NONE, "셋");

        menu.add(0, 1, Menu.NONE, "Developer");
        menu.add(0, 2, Menu.NONE, "삭제");
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(MainActivity.this, "About", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(MainActivity.this, "Developer", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(MainActivity.this, "삭제", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(MainActivity.this, "하나", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(MainActivity.this, "둘", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(MainActivity.this, "셋", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
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
