package com.kominfo.anaksehat.models;

import android.support.annotation.NonNull;

import java.util.Date;

public class PemeriksaanBayi {

    private int id;
    private Date history_date;
    private int weight;
    private int length;
    private int temp;
    private int repiratory;
    private int heart_beat;
    private String infection;
    private String ikterus;
    private String diare;
    private String low_weight;
    private String k_vitamin;
    private String hb_bcg_polio;
    private String shk;
    private String shk_confrimation;
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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getRepiratory() {
        return repiratory;
    }

    public void setRepiratory(int repiratory) {
        this.repiratory = repiratory;
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

    public String getShk_confrimation() {
        return shk_confrimation;
    }

    public void setShk_confrimation(String shk_confrimation) {
        this.shk_confrimation = shk_confrimation;
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
}
