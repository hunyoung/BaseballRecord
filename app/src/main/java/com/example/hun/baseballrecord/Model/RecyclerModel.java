package com.example.hun.baseballrecord.Model;

public class RecyclerModel {

    private String rank;
    private String name;
    private String war;
    private String battingAvg;
    private String onBasePercent;
    private String slugAvg;
    private String ops;

    public RecyclerModel(String rank, String name, String war, String battingAvg,
                         String onBasePercent, String slugAvg, String ops){
        this.rank = rank;
        this.name = name;
        this.war  = war;
        this.battingAvg = battingAvg;
        this.onBasePercent = onBasePercent;
        this.slugAvg = slugAvg;
        this.ops = ops;
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

    public String getBattingAvg(){ return battingAvg;}

    public void setBattingAvg(String battingAvg){
        this.battingAvg = battingAvg;
    }

    public String getOnBasePercent(){ return onBasePercent;}

    public void setOnBasePercent(String onBasePercent){
        this.onBasePercent = onBasePercent;
    }

    public String getSlugAvg(){ return slugAvg;}

    public void setSlugAvg(String slugAvg){
        this.slugAvg = slugAvg;
    }

    public String getOps(){ return ops;}

    public void setOps(String ops){
        this.ops = ops;
    }
}

