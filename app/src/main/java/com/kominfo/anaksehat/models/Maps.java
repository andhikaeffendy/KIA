package com.kominfo.anaksehat.models;

import com.google.gson.annotations.SerializedName;

public class Maps {

    private int id;
    private String name;
    private String address;
    private double lat;
    @SerializedName("long")
    private double mLong;
    private int pregnancy_count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getmLong() {
        return mLong;
    }

    public void setmLong(double mLong) {
        this.mLong = mLong;
    }

    public int getPregnancy_count() {
        return pregnancy_count;
    }

    public void setPregnancy_count(int pregnancy_count) {
        this.pregnancy_count = pregnancy_count;
    }
}
