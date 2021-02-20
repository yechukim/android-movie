package com.example.yechu.mymovie.nav_ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.yechu.mymovie.MainActivity;
import com.example.yechu.mymovie.Movie;
import com.example.yechu.mymovie.MyPagerAdapter;
import com.example.yechu.mymovie.R;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieFragment extends Fragment {

    private static final String TAG = "영화";
    // ui view
    ViewPager viewPager;
    TabLayout tabLayout;
    MyPagerAdapter adapter;
    FragmentManager fm;

    Movie mMovie;

    //volley
    RequestQueue queue;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        fm = getActivity().getSupportFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_viewpager, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tab_layout);

        String url = "http://boostcourse-appapi.connect.or.kr:10000";
        sendRequest(url);
        adapter = new MyPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(3);

       // Log.d(TAG, "viewpager 어댑터 세팅?");
    }

    public void sendRequest(String url) {
        //데이터 요청하기
        queue = Volley.newRequestQueue(getContext()); //큐 생성

        //영화 순위 리스트 조회 링크 추가
        url += "/movie/readMovieList";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //response
                        try {
                            //결과 부분
                            JSONArray result = response.getJSONArray("result");
                            if (result.length() > 0) {
                                // 서버에 있는 영화만큼 반복
                                for (int i = 0; i < result.length(); i++) {
                                    //결과만큼 영화 객체 생성
                                    mMovie = new Movie();
                                    JSONObject jsonObject = result.getJSONObject(i);
                                    //이미지 가져와서 set
                                    String image = jsonObject.isNull("image") ? "" : jsonObject.optString("image");
                                    mMovie.setImg(image);

                                    String title = jsonObject.isNull("title") ? "" : jsonObject.optString("title");
                                    mMovie.setTitle(title);

                                    String user_rating =  jsonObject.isNull("audience_rating") ? "" : jsonObject.optString("audience_rating");
                                    mMovie.setBook_rate(user_rating);

                                    String grade =  jsonObject.isNull("grade") ? "" : jsonObject.optString("grade");
                                    mMovie.setAge_limit(grade);

                                    singleFragment(i, mMovie);
                                }

                            }
                        } catch (JSONException e) {
                            e.getMessage();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error
            }
        }
        );
        //캐시를 false 하여 매번 새로운 결과 보여줌
        request.setShouldCache(false);
        //큐에 추가
        queue.add(request);

    }

    private SingleFragment singleFragment(int index, Movie movie) {
        SingleFragment fragment = new SingleFragment();
        fragment.setArguments(createBundle(index, movie));
        adapter.addItem(fragment);
        adapter.notifyDataSetChanged();//이걸 onCreate에 하니까 안됐었다.
        return fragment;
    }

    private Bundle createBundle(int index, Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putInt("bundle_key", index);
        bundle.putString("bundle_title", movie.getTitle());
        bundle.putString("bundle_image", movie.getImg());
        bundle.putString("bundle_age",movie.getAge_limit());
        bundle.putString("bundle_grade",movie.getBook_rate());
        return bundle;
    }

    @Override
    public void onDetach() {
        fm.isDestroyed();
        super.onDetach();
    }

}
