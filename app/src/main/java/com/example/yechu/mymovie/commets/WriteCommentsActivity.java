package com.example.yechu.mymovie.commets;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.yechu.mymovie.R;

import org.json.JSONException;
import org.json.JSONObject;

public class WriteCommentsActivity extends AppCompatActivity {

    private static final String TAG = "write";
    //ui
    TextView title;
    EditText commentSection;
    RatingBar ratingBar;
    Button saveBtn, cancelBtn;
    ImageView gradeImg;

    //movie info
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_c_write);

        title = findViewById(R.id.title);
        commentSection = findViewById(R.id.commentSection);
        ratingBar = findViewById(R.id.ratingBar);
        saveBtn = findViewById(R.id.saveBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        gradeImg = findViewById(R.id.movie_age);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            String mTitle = bundle.getString("movie");
            id = bundle.getInt("id2");
            String grade = bundle.getString("grade");
            int gradeInt = Integer.parseInt(grade);

            title.setText(mTitle);

            if (gradeInt == 12) {
                gradeImg.setImageResource(R.drawable.ic_12);
            } else if (gradeInt == 15) {
                gradeImg.setImageResource(R.drawable.ic_15);
            } else if (gradeInt == 19) {
                gradeImg.setImageResource(R.drawable.ic_19);
            } else {
                gradeImg.setImageResource(R.drawable.ic_all);
            }
        }

        //counts text live
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                count = s.length();
                if (count > 100) {
                    showToast("100글자를 초과할 수 없습니다.");
                    //초과되면 더 쓸 수 없게 멈추게 만들어보기
                    // commentSection.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        commentSection.addTextChangedListener(textWatcher);

        //click save
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float x = 0.0f;
                if (commentSection.getText() == null || ratingBar.getRating() == x) {
                    showToast("한줄평 또는 평점은 필수 입력사항입니다.");

                } else {
                   /* getIntent().putExtra("comment", commentSection.getText().toString());
                    getIntent().putExtra("rating", ratingBar.getRating());*/

                    String comment = commentSection.getText().toString();
                    float rating = ratingBar.getRating();
                    String writer = "yechu";
                    //서버에 전송 post
                    sendCommentPost(writer, String.valueOf(rating), comment, id);
                    showToast("저장되었습니다.");
                    finish();
                }
            }
        });

        //click cancel
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("취소합니다");
                finish();
            }
        });

    }

    public void sendCommentPost(String writer, String rating, String contents, int id) {
        String postUrl = "http://boostcourse-appapi.connect.or.kr:10000/movie/createComment";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();

        try {
            postData.put("id", id);
            postData.put("writer", writer);
            postData.put("rating", rating);
            postData.put("contents", contents);
            String jsonString = postData.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                postUrl, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            Log.d(TAG, "onResponse: "+jsonObject);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ", error);
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }
}