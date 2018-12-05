package com.example.hun.baseballrecord.Model;


public class NewsModel {

    private String title;
    private String description;
    private String link;
    private String pubdate;

    public NewsModel(String title, String description, String link, String pubdate){
        this.title = title;
        this.description = description;
        this.link = link;
        this.pubdate = pubdate;

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

}

