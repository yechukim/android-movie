package com.example.yechu.mymovie;

public class Movie {
    private String img;
    private String title;
    private String book_rate;
    private String age_limit;

    public Movie(String img, String title, String book_rate, String age_limit) {
        this.img = img;
        this.title = title;
        this.book_rate = book_rate;
        this.age_limit = age_limit;
    }

    public Movie() {

    }

    public String getBook_rate() {
        return book_rate;
    }

    public void setBook_rate(String book_rate) {
        this.book_rate = book_rate;
    }

    public String getAge_limit() {
        return age_limit;
    }

    public void setAge_limit(String age_limit) {
        this.age_limit = age_limit;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
