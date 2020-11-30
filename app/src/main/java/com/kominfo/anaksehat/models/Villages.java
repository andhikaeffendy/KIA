package com.kominfo.anaksehat.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Villages {
    private long id;
    private String name;
    @SerializedName("district_id")
    private String districtId;
    @SerializedName("district_name")
    private String districtName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
