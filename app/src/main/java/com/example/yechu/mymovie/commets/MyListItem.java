package com.example.yechu.mymovie.commets;

import android.os.Parcel;
import android.os.Parcelable;

public class MyListItem implements Parcelable {

    private String userId;
    private String userName;
    private String content;
    private String time;
    private float comment_rating;
    private String recommend;

    public MyListItem(String userId, String userName, String content, String time, float comment_rating, String recommend) {
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.time = time;
        this.comment_rating = comment_rating;
        this.recommend = recommend;
    }

    MyListItem() {

    }

    protected MyListItem(Parcel in) {
        userId = in.readString();
        userName = in.readString();
        content = in.readString();
        time = in.readString();
        comment_rating = in.readFloat();
        recommend = in.readString();
    }

    public static final Creator<MyListItem> CREATOR = new Creator<MyListItem>() {
        @Override
        public MyListItem createFromParcel(Parcel in) {
            return new MyListItem(in);
        }

        @Override
        public MyListItem[] newArray(int size) {
            return new MyListItem[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getComment_rating() {
        return comment_rating;
    }

    public void setComment_rating(float comment_rating) {
        this.comment_rating = comment_rating;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(userName);
        dest.writeString(content);
        dest.writeString(time);
        dest.writeFloat(comment_rating);
        dest.writeString(recommend);
    }
}
