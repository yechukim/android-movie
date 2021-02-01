package com.example.yechu.mymovie;

import android.graphics.drawable.Drawable;

public class MyListItem {

    private Drawable userImage;
    private String userId;
    private String comment;
    private String comment_time;
    private int comment_rating;
    private String recommend;

    MyListItem() {

    }

    public MyListItem(Drawable userImage, String userId, String comment, String comment_time, int comment_rating, String recommend) {
        this.userImage = userImage;
        this.userId = userId;
        this.comment = comment;
        this.comment_time = comment_time;
        this.comment_rating = comment_rating;
        this.recommend = recommend;
    }

    public Drawable getUserImage() {
        return userImage;
    }

    public void setUserImage(Drawable userImage) {
        this.userImage = userImage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public int getComment_rating() {
        return comment_rating;
    }

    public void setComment_rating(int comment_rating) {
        this.comment_rating = comment_rating;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }
}
