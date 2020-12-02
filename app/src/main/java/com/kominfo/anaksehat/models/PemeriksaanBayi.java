package com.kominfo.anaksehat.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PemeriksaanBayi {

    private int id;
    private Date history_date;
    private double weight;
    private int height;
    private double temperature;
    private double imt;
    private String note;
    private int head_round;
    private String child_bbu_index;
    private String child_pbu_index;
    private String child_bbpb_index;
    private String child_imtu_index;
    @SerializedName("respiratory")
    private int respiratory;
    @SerializedName("heart_beat")
    private int heartBeat;
    private String infection;
    private String ikterus;
    private String diare;
    @SerializedName("low_weight")
    private String lowWeight;
    @SerializedName("k_vitamin")
    private String kVitamin;
    @SerializedName("hb_bcg_polio")
    private String hBP;
    private String shk;
    @SerializedName("shk_confirmation")
    private String shkConfirmation;
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



    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
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

    public int getRespiratory() {
        return respiratory;
    }

    public void setRespiratory(int respiratory) {
        this.respiratory = respiratory;
    }

    public int getHeartBeat() {
        return heartBeat;
    }

    public void setHeartBeat(int heartBeat) {
        this.heartBeat = heartBeat;
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

    public String getLowWeight() {
        return lowWeight;
    }

    public void setLowWeight(String lowWeight) {
        this.lowWeight = lowWeight;
    }

    public String getkVitamin() {
        return kVitamin;
    }

    public void setkVitamin(String kVitamin) {
        this.kVitamin = kVitamin;
    }

    public String gethBP() {
        return hBP;
    }

    public void sethBP(String hBP) {
        this.hBP = hBP;
    }

    public String getShk() {
        return shk;
    }

    public void setShk(String shk) {
        this.shk = shk;
    }

    public String getShkConfirmation() {
        return shkConfirmation;
    }

    public void setShkConfirmation(String shkConfirmation) {
        this.shkConfirmation = shkConfirmation;
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
