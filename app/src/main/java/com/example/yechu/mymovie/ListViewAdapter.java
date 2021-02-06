package com.example.yechu.mymovie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

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