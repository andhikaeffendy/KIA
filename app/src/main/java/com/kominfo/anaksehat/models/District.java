package com.kominfo.anaksehat.models;

import com.google.gson.annotations.SerializedName;

public class District {
    private long id;
    private String name;
    @SerializedName("state_name")
    private String state;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return name;
    }
}
