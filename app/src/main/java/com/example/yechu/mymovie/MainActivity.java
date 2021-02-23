package com.example.yechu.mymovie;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.yechu.mymovie.nav_ui.APIFragment;
import com.example.yechu.mymovie.nav_ui.BookFragment;
import com.example.yechu.mymovie.nav_ui.MovieFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        FragmentCallback,
        FragmentManager.OnBackStackChangedListener // 프래그먼트에서 액션바 타이틀 바꿔주기 위해서?
{
    private static final String TAG = "main";
    //FragmentCallback은 만든 인터페이스

    //fragment
    MovieFragment movieList;
    APIFragment movieAPI;
    BookFragment movieBook;

    private static final String FRAG_TAG = "root frag";

    //drawer
    DrawerLayout drawerLayout;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main_nav);

        //toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //drawer toggle
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //navi menu
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        movieList = new MovieFragment();
        movieAPI = new APIFragment();
        movieBook = new BookFragment();
        //movie list is shown
        getSupportFragmentManager().beginTransaction().add(R.id.container, movieList).addToBackStack(null).commit();

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_list:
                onFragmentSelected(0, null);
                break;
            case R.id.nav_review:
                onFragmentSelected(1, null);
                break;
            case R.id.nav_book:
                onFragmentSelected(2, null);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentSelected(int position, Bundle bundle) {
        Fragment curFragment = null;

        if (position == 0) {
            curFragment = movieList;
            toolbar.setTitle("영화 목록");
        } else if (position == 1) {
            curFragment = movieAPI;
            toolbar.setTitle("영화 API");
        } else if (position == 2) {
            curFragment = movieBook;
            toolbar.setTitle("영화 예약");
        }
        //replace fragment
        replaceFragment(curFragment);
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onBackStackChanged() {

        try {
            List<Fragment> frags = getSupportFragmentManager().getFragments();

            for (int i = 0; i < frags.size(); i++) {
                if (frags.get(i).getTag().equals(DetailsFragment.class.getSimpleName())) {
                    toolbar.setTitle("영화 상세 화면");
                }
            }
        } catch (Exception e) {
            e.getMessage();
            Log.e(TAG, "onBackStackChanged: ", e.getCause());
        }
    }
}