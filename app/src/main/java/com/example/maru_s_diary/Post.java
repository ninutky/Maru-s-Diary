package com.example.maru_s_diary;

import android.content.SharedPreferences;
import android.widget.ImageView;

public class Post {
    private String documentId;
    private String title;
    private String contents;
//    private int weather;
//    private int feeling;
    private String date;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    private String postId;
    public String getHeart() {
        return heart;
    }

    public void setHeart(String heart) {
        this.heart = heart;
    }

    private String heart;

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    private String mood;

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    private String weather;

    public Post() {
    }

    //int weather, int feeling,
    public Post(String documentId, String title, String contents, String date,String mood,String weather,String heart) {
        this.documentId = documentId;
        this.title = title;
        this.contents = contents;
//        this.weather = weather;
//        this.feeling = feeling;
        this.date = date;
        this.mood=mood;
        this.weather=weather;

    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

//    public int getWeather() {
//        return weather;
//    }
//
//    public void setWeather(int weather) {
//        this.weather = weather;
//    }
//
//    public int getFeeling() {
//        return feeling;
//    }
//
//    public void setFeeling(int feeling) {
//        this.feeling = feeling;
//    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Post{" +
                "documentId='" + documentId + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
