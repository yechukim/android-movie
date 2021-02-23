package com.example.yechu.mymovie.commets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.yechu.mymovie.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    //array list
    public ArrayList<MyListItem> itemArrayList = new ArrayList<>();

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
            convertView = inflater.inflate(R.layout.c_list, parent, false);
        }

        TextView userId = convertView.findViewById(R.id.userId);
        TextView writer = convertView.findViewById(R.id.writer);
        TextView comment = convertView.findViewById(R.id.comment);
        TextView time = convertView.findViewById(R.id.time);
        RatingBar commentRating = convertView.findViewById(R.id.comment_rating);
        TextView recommend = convertView.findViewById(R.id.recommendCount);

        MyListItem myListItem = itemArrayList.get(position);

        userId.setText(myListItem.getUserId());
        writer.setText(myListItem.getUserName());
        comment.setText(myListItem.getContent());
        commentRating.setRating(myListItem.getComment_rating());
        recommend.setText(myListItem.getRecommend());
        time.setText(myListItem.getTime());
        return convertView;
    }


    public void addComment(String userId, String writerName, String comment, String time, float ratingBar, int recommendCount) {
        MyListItem item = new MyListItem();
        item.setUserId("사용자 ID: "+userId);
        item.setUserName("작성자: "+writerName);
        item.setContent(comment);
        item.setComment_rating(ratingBar);
        item.setTime(time+"에 작성됨");
        item.setRecommend(String.valueOf(recommendCount));
        itemArrayList.add(item);
    }
}