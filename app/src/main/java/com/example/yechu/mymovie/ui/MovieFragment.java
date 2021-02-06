package com.example.yechu.mymovie.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yechu.mymovie.R;
import com.example.yechu.mymovie.movie_lists.FirstMovie;
import com.example.yechu.mymovie.movie_lists.SecondMovie;
import com.example.yechu.mymovie.movie_lists.ThirdMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MovieFragment extends Fragment {

    private static final String TAG = "m";
    ViewPager pager1;
    PageAdapter adapter;
    //ArrayList<Fragment> movies;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //getParentFragmentManager는 또 제대로 안됨
        adapter = new PageAdapter(getChildFragmentManager());//안에 프래그먼트가 또 있을 때
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View bookView = inflater.inflate(R.layout.fragment_movie_lists, container, false);
        pager1 = bookView.findViewById(R.id.pager1);
        pager1.setOffscreenPageLimit(5);

        Log.d(TAG, "onCreateView() ran ");

        FirstMovie firstMovie = new FirstMovie();
        SecondMovie secondMovie = new SecondMovie();
        ThirdMovie thirdMovie = new ThirdMovie();

        adapter.addItem(firstMovie);
        adapter.addItem(secondMovie);
        adapter.addItem(thirdMovie);

        pager1.setAdapter(adapter);

        //volley
        String url = "http://boostcourse-appapi.connect.or.kr:10000/movie/readMovieList";

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONObject result = response.getJSONObject("result");

                            JSONArray array = response.getJSONArray("result");

                        }catch (JSONException e){
                            e.getMessage();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(jor);


        return bookView;

    }


    //view pager
    class PageAdapter extends FragmentPagerAdapter {
        //fragmentStatePagerAdapter는 state 저장이 안되나보다 그래서 오버라이드 했었음 이거는 문제 없네

        ArrayList<Fragment> lists = new ArrayList<>();

        public PageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        public void addItem(Fragment movie) {
            lists.add(movie);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return lists.get(position);
        }

        @Override
        public int getCount() {
            return lists.size();
        }

//        @Override
//        public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {
//            //state 복원하려고 이거 오버라이드?
//            try {
//                super.restoreState(state, loader);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }


}
