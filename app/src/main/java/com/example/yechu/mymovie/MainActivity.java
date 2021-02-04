package com.example.yechu.mymovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ic_likes = findViewById(R.id.ic_likes);
        ic_hate = findViewById(R.id.ic_hates);
        num_likes = findViewById(R.id.num_likes);
        num_hates = findViewById(R.id.num_hates);
        my_list = findViewById(R.id.my_list);
        write = findViewById(R.id.write);
        see_all = findViewById(R.id.see_all);

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
        my_list = findViewById(R.id.my_list);
        my_list.setAdapter(listViewAdapter);

        //add comments
        listViewAdapter.addComment("hello1", "첫 댓글인가요",3, 1);
        listViewAdapter.addComment("movietheBest","이거 진짜 재밌던데요?",5,0);

        //작성하기 클릭
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WriteComment_Activity.class);
                startActivityForResult(intent,100);

            }
        });

        //모두보기 클릭
        see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeeAllComments_Activity.class);
                startActivity(intent);
            }
        });


    }

    public static class ListViewAdapter extends BaseAdapter {
        //arraylist
        private ArrayList<MyListItem> itemArrayList = new ArrayList<>();

        //constructor
        public ListViewAdapter() {

        }

        @Override
        public int getCount() {
            return itemArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return itemArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Context context = parent.getContext();
            int pos = position;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.my_list_item, parent, false);
            }

            ImageView userImage = convertView.findViewById(R.id.userImage);
            TextView userId = convertView.findViewById(R.id.userId);
            TextView comment = convertView.findViewById(R.id.comment);
            RatingBar commentRating = convertView.findViewById(R.id.comment_rating);
            TextView recommend = convertView.findViewById(R.id.recommendCount);
            TextView report = convertView.findViewById(R.id.report);

            MyListItem myListItem = itemArrayList.get(position);

            // userImage.setImageResource(myListItem.getUserImage());
            userId.setText(myListItem.getUserId());
            comment.setText(myListItem.getComment());
            commentRating.setRating(myListItem.getComment_rating());
            recommend.setText(myListItem.getRecommend());

            return convertView;
        }


        public void addComment(String userId, String comment, float ratingBar, int recommendCount) {
            MyListItem item = new MyListItem();
            item.setUserId(userId);
            item.setComment(comment);
            item.setComment_rating(ratingBar);
            item.setRecommend(String.valueOf(recommendCount));
            itemArrayList.add(item);
        }
    }
}