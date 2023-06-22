package com.example.maru_s_diary;

public class Model {
    private String imageUri;
    Model() {

    }

    public Model(String imageUri){
        this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
