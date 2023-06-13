package com.example.maru_s_diary;

import android.widget.ImageView;

public class Post {
    private String documentId;
    private String title;
    private String contents;
//    private int weather;
//    private int feeling;
    private String date;


    public Post() {
    }

    //int weather, int feeling,
    public Post(String documentId, String title, String contents, String date) {
        this.documentId = documentId;
        this.title = title;
        this.contents = contents;
//        this.weather = weather;
//        this.feeling = feeling;
        this.date = date;
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
