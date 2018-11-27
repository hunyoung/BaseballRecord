package com.example.hun.baseballrecord.Model;

public class RecyclerModel {

    private String title;
    private String aurthor;

    public RecyclerModel(String title, String aurthor){
        this.title=title;
        this.aurthor=aurthor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAurthor() {
        return aurthor;
    }

    public void setAurthor(String artist) {
        this.aurthor = aurthor;
    }

}

