package com.example.yechu.mymovie;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.yechu.mymovie.ui.APIFragment;
import com.example.yechu.mymovie.ui.BookFragment;
import com.example.yechu.mymovie.ui.MovieFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NavActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,FragmentCallback {//FragmentCallback은 만든 인터페이스

    MovieFragment movieList;
    APIFragment movieAPI;
    BookFragment movieBook;

    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nav);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //drawer toggle
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        //toggle.setDrawerArrowDrawable(R.drawable.ic_hamburger_menu);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        movieList = new MovieFragment();
        movieAPI = new APIFragment();
        movieBook = new BookFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.container, movieList).commit();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_movie_list, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
       switch (id){
           case R.id.nav_list: onFragmentSelected(0,null);
           break;
           case R.id.nav_review: onFragmentSelected(1,null);
           break;
           case R.id.nav_book: onFragmentSelected(2,null);
           break;

       }
       drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentSelected(int position, Bundle bundle) {
        Fragment curFragment = null;

        if(position==0){
            curFragment = movieList;
            toolbar.setTitle("영화 목록");
        }else if(position==1){
            curFragment = movieAPI;
            toolbar.setTitle("영화 API");
        }else if(position==2){
            curFragment = movieBook;
            toolbar.setTitle("영화 예약");
        }
        replaceFragment(curFragment);
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }


}