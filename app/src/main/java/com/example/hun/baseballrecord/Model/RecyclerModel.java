package com.example.hun.baseballrecord.Model;

public class RecyclerModel {

    private String rank;
    private String name;
    private String war;

    public RecyclerModel(String rank, String name, String war){
        this.rank = rank;
        this.name = name;
        this.war  = war;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWar(){ return war;}

    public void setWar(String war){
        this.war = war;
    }

}

