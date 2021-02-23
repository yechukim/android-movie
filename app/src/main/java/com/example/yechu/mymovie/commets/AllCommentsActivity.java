package com.example.yechu.mymovie.commets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.yechu.mymovie.R;
import com.example.yechu.mymovie.commets.ListViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class AllCommentsActivity extends AppCompatActivity {

    private static final String TAG = "all";

    //ui
    TextView title, ratingNum;
    ImageView ageImage;
    RatingBar ratingBars;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_c_all);
        title = findViewById(R.id.title);
        ratingNum = findViewById(R.id.ratingNum);
        ageImage = findViewById(R.id.age);
        ratingBars = findViewById(R.id.ratingBars);


        //뒤로 액션바
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //load all comments
        ListViewAdapter listViewAdapter = new ListViewAdapter();
        ListView all_lists = findViewById(R.id.all_lists);

        //detail fragment에서 받은 번들
        Bundle bundle = getIntent().getExtras();
        List<MyListItem> list = bundle.getParcelableArrayList("list");
//        Log.d(TAG, "onCreate: "+list);
        Log.d(TAG, "onCreate: " + list.get(0));

        for (int i = 0; i < list.size(); i++) {
            String id = list.get(i).getUserId();
            String name = list.get(i).getUserName();
            String content = list.get(i).getContent();
            String time = list.get(i).getTime();
            Float rate = list.get(i).getComment_rating();
            String recommend = list.get(i).getRecommend();
            listViewAdapter.addComment(id, name, content, time, rate, Integer.parseInt(recommend));
            listViewAdapter.notifyDataSetChanged();
        }
        all_lists.setAdapter(listViewAdapter);

        Float bar = bundle.getFloat("ratingBar");
        Log.d(TAG, "onCreate: "+bar);
        String num = bundle.getString("ratingNum");
        String title1 = bundle.getString("movie");
        String age = bundle.getString("grade");
        int ageInt = Integer.parseInt(age);

        //리스트 위에 제목, 평점, 연령제한 아이콘 설정
        title.setText(title1);
        ratingBars.setRating(bar);
        ratingNum.setText(num);

        if (ageInt == 12) {
            ageImage.setImageResource(R.drawable.ic_12);
        } else if (ageInt == 15) {
            ageImage.setImageResource(R.drawable.ic_15);
        } else if (ageInt == 19) {
            ageImage.setImageResource(R.drawable.ic_19);
        } else {
            ageImage.setImageResource(R.drawable.ic_all);
        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

