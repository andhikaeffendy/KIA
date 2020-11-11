package com.kominfo.anaksehat.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ChildHistory {
    private long id;
    private Date history_date;
    private int height;
    private double weight;
    private int head_round;
    private double imt;
    private String child_name;
    private int temperature;
    private String note;
    @SerializedName("child_bbu_index")
    private String bbu_index;
    @SerializedName("child_pbu_index")
    private String pbu_index;
    @SerializedName("child_bbpb_index")
    private String bbpb_index;
    @SerializedName("child_imtu_index")
    private String imtu_index;

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getHistory_date() {
        return history_date;
    }

    public int getHead_round() {
        return head_round;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getChild_name() {
        return child_name;
    }

    public String getNote() {
        return note;
    }

    public void setChild_name(String child_name) {
        this.child_name = child_name;
    }

    public void setHead_round(int head_round) {
        this.head_round = head_round;
    }

    public void setHistory_date(Date history_date) {
        this.history_date = history_date;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public double getImt() {
        return imt;
    }

    public void setImt(double imt) {
        this.imt = imt;
    }

    public String getBbpb_index() {
        return bbpb_index;
    }

    public String getBbu_index() {
        return bbu_index;
    }

    public void setBbpb_index(String bbpb_index) {
        this.bbpb_index = bbpb_index;
    }

    public String getImtu_index() {
        return imtu_index;
    }

    public String getPbu_index() {
        return pbu_index;
    }

    public void setBbu_index(String bbu_index) {
        this.bbu_index = bbu_index;
    }

    public void setImtu_index(String imtu_index) {
        this.imtu_index = imtu_index;
    }

    public void setPbu_index(String pbu_index) {
        this.pbu_index = pbu_index;
    }
}
