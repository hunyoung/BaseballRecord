package com.example.hun.baseballrecord.Model;


public class NewsModel {

    private String title;
    private String description;
    private String pubdate;
    private String link;

    public NewsModel(String title, String description, String pubdate, String link){
        this.title = title;
        this.description = description;
        this.pubdate = pubdate;
        this.link = link;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String titlev) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}

