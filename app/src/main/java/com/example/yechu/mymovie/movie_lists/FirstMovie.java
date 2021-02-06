package com.example.yechu.mymovie.movie_lists;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.yechu.mymovie.DetailsFragment;
import com.example.yechu.mymovie.NavActivity;
import com.example.yechu.mymovie.R;

public class FirstMovie extends Fragment implements View.OnClickListener {

    Button btn_detail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View bookView = inflater.inflate(R.layout.first_movie, container, false);
        btn_detail = bookView.findViewById(R.id.btn_detail);
        btn_detail.setOnClickListener(this);

        return bookView;
    }

    @Override
    public void onClick(View v) {
        DetailsFragment detailsFragment = new DetailsFragment();
        ((NavActivity)getActivity()).replaceFragment(detailsFragment);
    }
}
