package com.example.yechu.mymovie.commets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.yechu.mymovie.R;
import com.example.yechu.mymovie.commets.ListViewAdapter;

public class AllCommentsActivity extends AppCompatActivity {

    ListView my_list_item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_c_all);

        //뒤로 액션바
      // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //load all comments
        ListViewAdapter listViewAdapter = new ListViewAdapter();
        ListView all_lists = findViewById(R.id.all_lists);
        all_lists.setAdapter(listViewAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

