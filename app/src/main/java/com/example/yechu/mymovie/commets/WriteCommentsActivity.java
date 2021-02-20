package com.example.yechu.mymovie.commets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yechu.mymovie.R;

public class WriteCommentsActivity extends AppCompatActivity {

    //ui
    TextView title;
    EditText commentSection;
    RatingBar ratingBar;
    Button saveBtn, cancelBtn;

    //counts 100
    int count =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_c_write);

        title = findViewById(R.id.title);
        commentSection = findViewById(R.id.commentSection);
        ratingBar = findViewById(R.id.ratingBar);
        saveBtn = findViewById(R.id.saveBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        //counts text live
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              count =  s.length();
              if(count > 100) {
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
                if(commentSection.getText()==null && ratingBar.getRating() == x){
                    showToast("한줄평을 입력하세요");
                }else{
                        getIntent().putExtra("comment",commentSection.getText().toString());
                        getIntent().putExtra("rating",ratingBar.getRating());
                        startActivity(getIntent());
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
    public void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();

    }
}