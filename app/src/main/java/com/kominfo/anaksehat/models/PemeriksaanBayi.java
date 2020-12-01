package com.kominfo.anaksehat.models;

import android.support.annotation.NonNull;

import java.util.Date;

public class PemeriksaanBayi {

    private int id;
    private Date history_date;
    private int weight;
    private int height;
    private int temp;
    private double imt;
    private String note;
    private int head_round;
    private String child_bbu_index;
    private String child_pbu_index;
    private String child_bbpb_index;
    private String child_imtu_index;
    private int respiratory;
    private int heart_beat;
    private String infection;
    private String ikterus;
    private String diare;
    private String low_weight;
    private String k_vitamin;
    private String hb_bcg_polio;
    private String shk;
    private String shk_confirmation;
    private String treatment;
    private int user_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getHistory_date() {
        return history_date;
    }

    public void setHistory_date(Date history_date) {
        this.history_date = history_date;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }


    public int getHeart_beat() {
        return heart_beat;
    }

    public void setHeart_beat(int heart_beat) {
        this.heart_beat = heart_beat;
    }

    public String getInfection() {
        return infection;
    }

    public void setInfection(String infection) {
        this.infection = infection;
    }

    public String getIkterus() {
        return ikterus;
    }

    public void setIkterus(String ikterus) {
        this.ikterus = ikterus;
    }

    public String getDiare() {
        return diare;
    }

    public void setDiare(String diare) {
        this.diare = diare;
    }

    public String getLow_weight() {
        return low_weight;
    }

    public void setLow_weight(String low_weight) {
        this.low_weight = low_weight;
    }

    public String getK_vitamin() {
        return k_vitamin;
    }

    public void setK_vitamin(String k_vitamin) {
        this.k_vitamin = k_vitamin;
    }

    public String getHb_bcg_polio() {
        return hb_bcg_polio;
    }

    public void setHb_bcg_polio(String hb_bcg_polio) {
        this.hb_bcg_polio = hb_bcg_polio;
    }

    public String getShk() {
        return shk;
    }

    public void setShk(String shk) {
        this.shk = shk;
    }

    public int getRespiratory() {
        return respiratory;
    }

    public void setRespiratory(int respiratory) {
        this.respiratory = respiratory;
    }

    public String getShk_confirmation() {
        return shk_confirmation;
    }

    public void setShk_confirmation(String shk_confirmation) {
        this.shk_confirmation = shk_confirmation;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getImt() {
        return imt;
    }

    public void setImt(double imt) {
        this.imt = imt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getHead_round() {
        return head_round;
    }

    public void setHead_round(int head_round) {
        this.head_round = head_round;
    }

    public String getChild_bbu_index() {
        return child_bbu_index;
    }

    public void setChild_bbu_index(String child_bbu_index) {
        this.child_bbu_index = child_bbu_index;
    }

    public String getChild_pbu_index() {
        return child_pbu_index;
    }

    public void setChild_pbu_index(String child_pbu_index) {
        this.child_pbu_index = child_pbu_index;
    }

    public String getChild_bbpb_index() {
        return child_bbpb_index;
    }

    public void setChild_bbpb_index(String child_bbpb_index) {
        this.child_bbpb_index = child_bbpb_index;
    }

    public String getChild_imtu_index() {
        return child_imtu_index;
    }

    public void setChild_imtu_index(String child_imtu_index) {
        this.child_imtu_index = child_imtu_index;
    }
}
