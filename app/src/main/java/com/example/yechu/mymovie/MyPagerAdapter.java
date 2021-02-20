package com.example.yechu.mymovie;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> frags = new ArrayList<>();

    public MyPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void addItem(Fragment frag) {
        frags.add(frag);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return frags.get(position);
    }

    @Override
    public int getCount() {
        return frags.size();
    }

}
