package com.kominfo.anaksehat.models;

import java.util.Date;

public class Neonatal {
    private long id;
    private String neonatal_visit_type;
    private Date history_date;
    private double weight;
    private double imt;
    private int height;
    private double temperature;
    private String note;
    private int child_id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setHistory_date(Date history_date) {
        this.history_date = history_date;
    }

    public Date getHistory_date() {
        return history_date;
    }

    public void setChild_id(int child_id) {
        this.child_id = child_id;
    }

    public int getChild_id() {
        return child_id;
    }

    public double getImt() {
        return imt;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getWeight() {
        return weight;
    }

    public void setInfection(String infection) {
        this.infection = infection;
    }

    public int getHeight() {
        return height;
    }

    public String getInfection() {
        return infection;
    }

    public int getHeart_beat() {
        return heart_beat;
    }

    public int getRespiratory() {
        return respiratory;
    }

    public String getNote() {
        return note;
    }

    public String getDiare() {
        return diare;
    }

    public String getIkterus() {
        return ikterus;
    }

    public void setHeart_beat(int heart_beat) {
        this.heart_beat = heart_beat;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getLow_weight() {
        return low_weight;
    }

    public void setDiare(String diare) {
        this.diare = diare;
    }

    public void setIkterus(String ikterus) {
        this.ikterus = ikterus;
    }

    public void setImt(double imt) {
        this.imt = imt;
    }

    public void setLow_weight(String low_weight) {
        this.low_weight = low_weight;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setRespiratory(int respiratory) {
        this.respiratory = respiratory;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getHb_bcg_polio() {
        return hb_bcg_polio;
    }

    public String getK_vitamin() {
        return k_vitamin;
    }

    public String getShk() {
        return shk;
    }

    public String getShk_confirmation() {
        return shk_confirmation;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setHb_bcg_polio(String hb_bcg_polio) {
        this.hb_bcg_polio = hb_bcg_polio;
    }

    public void setK_vitamin(String k_vitamin) {
        this.k_vitamin = k_vitamin;
    }

    public void setShk(String shk) {
        this.shk = shk;
    }

    public void setShk_confirmation(String shk_confirmation) {
        this.shk_confirmation = shk_confirmation;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getChild_bbpb_index() {
        return child_bbpb_index;
    }

    public String getChild_bbu_index() {
        return child_bbu_index;
    }

    public String getChild_imtu_index() {
        return child_imtu_index;
    }

    public String getChild_pbu_index() {
        return child_pbu_index;
    }

    public void setChild_bbpb_index(String child_bbpb_index) {
        this.child_bbpb_index = child_bbpb_index;
    }

    public void setChild_bbu_index(String child_bbu_index) {
        this.child_bbu_index = child_bbu_index;
    }

    public void setChild_imtu_index(String child_imtu_index) {
        this.child_imtu_index = child_imtu_index;
    }

    public void setChild_pbu_index(String child_pbu_index) {
        this.child_pbu_index = child_pbu_index;
    }

    public String getNeonatal_visit_type() {
        return neonatal_visit_type;
    }

    public void setNeonatal_visit_type(String neonatal_visit_type) {
        this.neonatal_visit_type = neonatal_visit_type;
    }
}
