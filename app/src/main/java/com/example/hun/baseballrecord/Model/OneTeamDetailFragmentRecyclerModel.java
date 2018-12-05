package com.example.hun.baseballrecord.Model;


public class OneTeamDetailFragmentRecyclerModel {

    private String name;
    private String icon;

    public OneTeamDetailFragmentRecyclerModel(String name, String icon){
        this.name = name;
        this.icon = icon;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


}

