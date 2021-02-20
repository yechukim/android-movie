package com.example.yechu.mymovie;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.yechu.mymovie.commets.AllCommentsActivity;
import com.example.yechu.mymovie.commets.WriteCommentsActivity;
import com.example.yechu.mymovie.commets.ListViewAdapter;

public class DetailsFragment extends Fragment{

    //ui
    ImageView ic_likes, ic_hate;
    TextView num_likes, num_hates, write;
    int count_likes = 15;
    int count_hates = 1;
    boolean likes_status, hates_status = false;
    Button see_all;

    //comment lists
    ListViewAdapter listViewAdapter;
    ListView my_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View details = inflater.inflate(R.layout.frag_btn_detail, container, false);

        ic_likes = details.findViewById(R.id.ic_likes);
        ic_hate = details.findViewById(R.id.ic_hates);
        num_likes = details.findViewById(R.id.num_likes);
        num_hates = details.findViewById(R.id.num_hates);
        my_list = details.findViewById(R.id.my_list);
        write = details.findViewById(R.id.write);
        see_all = details.findViewById(R.id.see_all);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("영화 상세");

        //like
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

        //dont' like
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

        //comment lists
        listViewAdapter = new ListViewAdapter();
        my_list = details.findViewById(R.id.my_list);
        my_list.setAdapter(listViewAdapter);

        //add comments
        listViewAdapter.addComment("hello1", "첫 댓글인가요", 3, 1);
        listViewAdapter.addComment("movietheBest", "이거 진짜 재밌던데요?", 5, 0);

        //작성하기 클릭
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WriteCommentsActivity.class);
                startActivityForResult(intent, 100);

            }
        });

        //모두보기 클릭
        see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllCommentsActivity.class);
                startActivityForResult(intent, 200);
            }
        });

        return details;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String url = "http://boostcourse-appapi.connect.or.kr:10000";
        if(getArguments()!=null){
            Bundle args = getArguments();
            int id = args.getInt("bundle_key");// id값 가져오기
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){

        } else if(requestCode ==200){

        }

    }

}
