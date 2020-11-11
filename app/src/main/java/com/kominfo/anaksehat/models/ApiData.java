package com.kominfo.anaksehat.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiData<t> {
    @SerializedName("data")
    public List<t> data;

    public List<t> getData() {
        return data;
    }

    public void setData(List<t> data) {
        this.data = data;
    }
}
