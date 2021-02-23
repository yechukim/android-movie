package com.example.yechu.mymovie.commets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
    TextView title, ratingNum, writeText;
    ImageView ageImage;
    RatingBar ratingBars;
    Toolbar toolbar;

    //bundle
    String age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_c_all);
        title = findViewById(R.id.title);
        ratingNum = findViewById(R.id.ratingNum);
        ageImage = findViewById(R.id.age);
        ratingBars = findViewById(R.id.ratingBars);
        writeText = findViewById(R.id.writeText);

        //toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        assert ab != null;
        ab.setDisplayShowHomeEnabled(true);


        //뒤로 액션바
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        Log.d(TAG, "onCreate: " + bar);
        String num = bundle.getString("ratingNum");
        String title1 = bundle.getString("movie");
        age = bundle.getString("grade");
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

        //작성하기 클릭
        writeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WriteCommentsActivity.class);
                intent.putExtra("movie", title.getText());
                intent.putExtra("grade", age);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

