package com.example.yechu.mymovie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.yechu.mymovie.commets.AllCommentsActivity;
import com.example.yechu.mymovie.commets.MyListItem;
import com.example.yechu.mymovie.commets.WriteCommentsActivity;
import com.example.yechu.mymovie.commets.ListViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class DetailsFragment extends Fragment {

    private static final String TAG = "detail";
    //ui
    ImageView ic_likes, ic_hate;
    TextView num_likes, num_hates, write;
    boolean likes_status, hates_status = false;
    Button see_all;

    //comment lists
    ListViewAdapter listViewAdapter;

    //api ui
    int id;
    RatingBar ratingBar;
    TextView plot, plot_title, release_date, genre_time, reserve_grade, rating_num, ppl, director_name, actor_name;
    ImageView poster;
    int like, dislike, count_hates, count_likes;
    String grade;
    Float rate;
    String movieId;
    ListView my_list;
    //버튼 누를 때 보낼 리스트아이템 List
    ArrayList<MyListItem> list = new ArrayList<>();

    //volley
    RequestQueue queue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //툴바 변경
        ((MainActivity)(getActivity())).toolbar.setTitle("상세화면");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View details = inflater.inflate(R.layout.frag_btn_detail, container, false);
        return details;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        write = view.findViewById(R.id.write);
        see_all = view.findViewById(R.id.see_all);

        //작성하기 클릭
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WriteCommentsActivity.class);
                intent.putExtra("movie", plot_title.getText());
                intent.putExtra("grade", grade);
                intent.putExtra("id2",id);
                Log.d(TAG, "onClick: "+id);
                startActivity(intent);
            }
        });

        //예매하기 버튼
        Button btn_reserve = view.findViewById(R.id.btn_reserve);
        btn_reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //예매하기 fragment로 이동

            }
        });

        //정보 뿌릴 캐스팅 및 가져오기
        poster = view.findViewById(R.id.poster);
        plot_title = view.findViewById(R.id.title);
        plot = view.findViewById(R.id.plot_detail);
        genre_time = view.findViewById(R.id.genre_time);
        release_date = view.findViewById(R.id.release_date);
        reserve_grade = view.findViewById(R.id.reserve_grade);
        rating_num = view.findViewById(R.id.rate_num);
        ratingBar = view.findViewById(R.id.ratingBar);
        ppl = view.findViewById(R.id.ppl);
        director_name = view.findViewById(R.id.director_name);
        actor_name = view.findViewById(R.id.actor_name);

        ic_likes = view.findViewById(R.id.ic_likes);
        ic_hate = view.findViewById(R.id.ic_hates);
        num_likes = view.findViewById(R.id.num_likes);
        num_hates = view.findViewById(R.id.num_hates);

        //영화 상세정보 불러오기
        String url = "http://boostcourse-appapi.connect.or.kr:10000/movie/readMovie/?id=";

        if (getArguments() != null) {
            Bundle args = getArguments();
            id = args.getInt("bundle_id");
            url += id;
            sendRequest(url);
        }

        //댓글 불러오기
        String list_url = "http://boostcourse-appapi.connect.or.kr:10000/movie/readCommentList/?id=";

        if (getArguments() != null) {
            Bundle args = getArguments();
            id = args.getInt("bundle_id");
            list_url += id;
            sendListRequest(list_url);
            //  Log.d(TAG, "onViewCreated: "+list_url);
        }

        //리스트 설정
        my_list = view.findViewById(R.id.my_list);
        listViewAdapter = new ListViewAdapter();

        //리스트뷰 스크롤되게 설정함
        my_list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        //모두보기 클릭
        see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllCommentsActivity.class);
                //이미 정보 추가되어 있는 상태
                if (list != null) {
                    intent.putParcelableArrayListExtra("list", list);
                    intent.putExtra("movie", plot_title.getText());
                    intent.putExtra("ratingBar", rate);
                    // Log.d(TAG, "onClick: "+ratingBar.getRating());
                    intent.putExtra("ratingNum", rating_num.getText());
                    intent.putExtra("grade", grade);
                    startActivityForResult(intent, 200);
                }

            }
        });

    }

    //  댓글 불러옴
    private void sendListRequest(String url) {
        queue = Volley.newRequestQueue(getActivity().getApplicationContext()); //큐 생성

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //response
                        try {
                            JSONArray result = response.getJSONArray("result");

                            for (int i = 0; i < result.length(); i++) {
                                //결과 부분
                                JSONObject object = result.getJSONObject(i);

                                movieId = object.getString("id");
                                String content = object.getString("contents");
                                String writer = object.getString("writer");
                                String writeTime = object.getString("time");
                                String rating = object.getString("rating");
                                String recommend = object.getString("recommend");

                                //번들에 보낼꺼 저장하기
                                //위에  List<MyListItem> list = new ArrayList<>();로 선언함
                                list.add(new MyListItem(movieId, writer, content, writeTime, Float.parseFloat(rating), recommend));

                                //이건 디테일 프래그먼트에서 보여지는 부분이고
                                listViewAdapter.addComment(movieId, writer, content, writeTime, Float.parseFloat(rating), Integer.parseInt(recommend));
                                listViewAdapter.notifyDataSetChanged();
                                //   Log.d(TAG, "onResponse: "+object);
                            }
                            my_list.setAdapter(listViewAdapter);

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
        request.setShouldCache(false);
        //큐에 추가
        queue.add(request);
    }

    //데이터 요청하기
    public void sendRequest(String url) {
        queue = Volley.newRequestQueue(getActivity().getApplicationContext()); //큐 생성
        // Log.d(TAG, "sendRequest:큐? ");

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
                            JSONObject object = result.getJSONObject(0);

                            String title = object.getString("title");
                            String thumb = object.getString("thumb");
                            String releaseDate = object.getString("date");
                            String genre = object.getString("genre");
                            String duration = object.getString("duration");
                            String synopsis = object.getString("synopsis");
                            String director = object.getString("director");
                            String actor = object.getString("actor");
                            String reservation_grade = object.getString("reservation_grade");
                            String reservation_rate = object.getString("reservation_rate");
                            //  String stars = object.getString("user_rating");
                            String review_rate = object.getString("reviewer_rating");
                            String audience = object.getString("audience");
                            grade = object.getString("grade");

                            like = object.getInt("like");
                            dislike = object.getInt("dislike");
                            num_likes.setText(String.valueOf(like));
                            num_hates.setText(String.valueOf(dislike));
                            plot.setText(synopsis);
                            Glide.with(getContext()).load(thumb).error(R.drawable.ic_launcher_foreground)
                                    .into(poster);
                            plot_title.setText(title);
                            release_date.setText(releaseDate + " 개봉");
                            genre_time.setText(genre + " / " + duration + "분");
                            reserve_grade.setText(reservation_grade + "위 " + reservation_rate + "%");
                            ratingBar.setRating(Float.parseFloat(review_rate));
                            //번들에 스트링으로 넘겨줄 때 all 액티비티에서 null로 되서 아예 이렇게 보내봄
                            rate = Float.parseFloat(review_rate);

                            rating_num.setText(review_rate);
                            ppl.setText(audience + "명");
                            director_name.setText(director);
                            actor_name.setText(actor);

                            count_hates = dislike;
                            count_likes = like;

                            //like click
                            ic_likes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //hates selected

                                    if (hates_status) {
                                        likes_status = true;
                                        hates_status = false;
                                        count_hates--;
                                        ic_hate.setImageResource(R.drawable.ic_thumb_down);
                                        ic_likes.setImageResource(R.drawable.ic_thumb_up_selected);
                                        count_likes++;
                                        num_likes.setText(String.valueOf(count_likes));
                                        num_hates.setText(String.valueOf(count_hates));
                                    } else {//hates not selected

                                        //like unselected
                                        if (!likes_status) {
                                            likes_status = true;
                                            count_likes++;
                                            ic_likes.setImageResource(R.drawable.ic_thumb_up_selected);
                                        } else {
                                            //like selected
                                            likes_status = false;
                                            count_likes--;
                                            ic_likes.setImageResource(R.drawable.ic_thumb_up);
                                        }
                                        num_likes.setText(String.valueOf(count_likes));

                                    }
                                }
                            });

                            //dislike click
                            ic_hate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //likes selected
                                    if (likes_status) {
                                        hates_status = true;
                                        likes_status = false;
                                        count_likes--;
                                        ic_likes.setImageResource(R.drawable.ic_thumb_up);
                                        ic_hate.setImageResource(R.drawable.ic_thumb_down_selected);
                                        count_hates++;
                                        num_likes.setText(String.valueOf(count_likes));
                                    } else { //likes not selected

                                        if (!hates_status) {// hates not selected
                                            hates_status = true;
                                            count_hates++;
                                            ic_hate.setImageResource(R.drawable.ic_thumb_down_selected);
                                        } else {// hates selected
                                            hates_status = false;
                                            count_hates--;
                                            ic_hate.setImageResource(R.drawable.ic_thumb_down);
                                        }
                                    }
                                    num_hates.setText(String.valueOf(count_hates));
                                }
                            });
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
}