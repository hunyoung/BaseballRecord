package com.example.hun.baseballrecord.Model;

public class TeamFragmentRecyclerModel {

    private String rank;
    private String name;
    private String game;
    private String win;
    private String lose;
    private String draw;
    private String game_dis;
    private String winning_rate;
    private String recent_ten_game;
    private String continuous;
    private String home_game;
    private String expedition_game;

    public TeamFragmentRecyclerModel(String rank, String name, String game, String win, String lose,
            String draw, String game_dis, String winning_rate, String recent_ten_game, String continuous,
            String home_game, String expedition_game){
        this.rank = rank;
        this.name = name;
        this.game = game;
        this.win = win;
        this.lose = lose;
        this.draw = draw;
        this.game_dis = game_dis;
        this.winning_rate = winning_rate;
        this.recent_ten_game = recent_ten_game;
        this.continuous = continuous;
        this.home_game = home_game;
        this.expedition_game = expedition_game;
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

    public String getGame(){ return game;}

    public void setGame(String game){
        this.game = game;
    }

    public String getWin(){ return win;}

    public void setWin(String win){
        this.win = win;
    }

    public String getLose(){ return lose;}

    public void setLose(String lose){
        this.lose = lose;
    }

    public String getDraw(){ return draw;}

    public void setDraw(String draw){
        this.draw = draw;
    }

    public String getGameDis(){ return game_dis;}

    public void setGame_dis(String game_dis){
        this.game_dis = game_dis;
    }

    public String getWinningRate(){ return winning_rate;}

    public void setWinningRate(String winning_rate){
        this.winning_rate = winning_rate;
    }

    public String getRecentTenGame(){ return recent_ten_game;}

    public void setRecentTenGame(String recent_ten_game){
        this.recent_ten_game = recent_ten_game;
    }

    public String getContinuous(){ return continuous;}

    public void setContinuous(String continuous){
        this.continuous = continuous;
    }

    public String getHomeGame(){ return home_game;}

    public void setHomeGame(String home_game){
        this.home_game = home_game;
    }

    public String getExpeditionGame(){ return expedition_game;}

    public void setExpeditionGame(String expedition_game){
        this.expedition_game = expedition_game;
    }

}

