package com.example.yechu.mymovie.db;

import android.provider.BaseColumns;

import com.example.yechu.mymovie.Movie;

public final class MovieData {

    private MovieData(){
    }

    public static class MovieEntry implements BaseColumns{
        public static final String TBL_NAME ="movies";
        public static final String COL_MOVIE_NAME ="title";
        public static final String COL_MOVIE_IMAGE ="movieImage";
        public static final String COL_RATING = "rating";
        public static final String COL_GRADE = "grade";

    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE" +MovieEntry.TBL_NAME + "(" +
                    MovieEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT ," +
                    MovieEntry.COL_MOVIE_NAME + " TEXT, " +
                    MovieEntry.COL_MOVIE_IMAGE +" TEXT, " +
                    MovieEntry.COL_GRADE + " TEXT, "+
                    MovieEntry.COL_RATING + " TEXT )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " +MovieEntry.TBL_NAME;

}
