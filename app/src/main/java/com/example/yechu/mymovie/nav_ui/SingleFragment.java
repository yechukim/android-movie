package com.example.yechu.mymovie.nav_ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageHelper;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.yechu.mymovie.DetailsFragment;
import com.example.yechu.mymovie.MainActivity;
import com.example.yechu.mymovie.Movie;
import com.example.yechu.mymovie.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SingleFragment extends Fragment {

    private static final String TAG = "싱글";
    //ui
    ImageView mImage;
    TextView mTitle;
    TextView mAgeLimit;
    TextView mBookRate;
    Button btn_details;
    int index;

    //fm
    FragmentManager fm;

    @Override
    public void onAttach(@NonNull Context context) {
        fm = getChildFragmentManager();

        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Log.d(TAG, "백 버튼 클릭");
                getActivity().finish();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.single_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //inflate 된 뷰에서 연결해줌
        mImage = view.findViewById(R.id.image);
        mTitle = view.findViewById(R.id.title);
        mAgeLimit = view.findViewById(R.id.bookRate);
        mBookRate = view.findViewById(R.id.age);
        btn_details = view.findViewById(R.id.btn_detail);

        if(getArguments()!=null)
        {
            Bundle args = getArguments();
            index = args.getInt("bundle_key");
            String title = args.getString("bundle_title");
            String image_url = args.getString("bundle_image");
            String age_limit = args.getString("bundle_age");
            String user_rating = args.getString("bundle_grade");

            mTitle.setText(title);
            Glide.with(getContext()).load(image_url)
                    .placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground) //로딩중 + 에러날때 이미지
                    .override(700,700)
                    .into(mImage);
            mAgeLimit.setText(age_limit+ "세 관람가");
            mBookRate.setText("예매율: " + user_rating);
        }

        btn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment dFragment = new DetailsFragment();
                //메인 액티비티의 메소드로 프래그먼트 교체함
                ((MainActivity)getActivity()).replaceFragment(dFragment);
                sendBundle(index);
                Log.d(TAG, "onClick: index 확인 "+ index);
            }
        });
    }

    private Bundle sendBundle(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt("bundle_key", index+1);// id 는 1부터 시작
        return bundle;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //프래그맨트 매니저를 destroy 하는걸 빼봄
    }
}
