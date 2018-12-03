package com.example.hun.baseballrecord.Model;

import android.graphics.drawable.Drawable;

public class TeamDetailFragmentRecyclerModel {

    private String name;
    private Drawable icon;

    public TeamDetailFragmentRecyclerModel(String name, Drawable icon){
        this.name = name;
        this.icon = icon;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setName(Drawable icon) {
        this.icon = icon;
    }


}

