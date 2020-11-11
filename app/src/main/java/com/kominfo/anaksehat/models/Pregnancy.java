package com.kominfo.anaksehat.models;

import java.util.Date;

public class Pregnancy {
    private long id;
    private Date last_period_date;
    private double start_weight;
    private int start_height;
    private double weight_gain;
    private String pregnancy_status;
    private Date hpl_date;
    private String period_type;
    private double imt;
    private String mother_name;
    private String mother_nutrition_category;
    private String mother_weight_index;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setImt(double imt) {
        this.imt = imt;
    }

    public double getImt() {
        return imt;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getMother_name() {
        return mother_name;
    }

    public Date getHpl_date() {
        return hpl_date;
    }

    public Date getLast_period_date() {
        return last_period_date;
    }

    public double getStart_weight() {
        return start_weight;
    }

    public double getWeight_gain() {
        return weight_gain;
    }

    public int getStart_height() {
        return start_height;
    }

    public String getMother_nutrition_category() {
        return mother_nutrition_category;
    }

    public String getMother_weight_index() {
        return mother_weight_index;
    }

    public String getPeriod_type() {
        return period_type;
    }

    public void setHpl_date(Date hpl_date) {
        this.hpl_date = hpl_date;
    }

    public String getPregnancy_status() {
        return pregnancy_status;
    }

    public void setLast_period_date(Date last_period_date) {
        this.last_period_date = last_period_date;
    }

    public void setMother_nutrition_category(String mother_nutrition_category) {
        this.mother_nutrition_category = mother_nutrition_category;
    }

    public void setMother_weight_index(String mother_weight_index) {
        this.mother_weight_index = mother_weight_index;
    }

    public void setPeriod_type(String period_type) {
        this.period_type = period_type;
    }

    public void setPregnancy_status(String pregnancy_status) {
        this.pregnancy_status = pregnancy_status;
    }

    public void setStart_height(int start_height) {
        this.start_height = start_height;
    }

    public void setStart_weight(double start_weight) {
        this.start_weight = start_weight;
    }

    public void setWeight_gain(double weight_gain) {
        this.weight_gain = weight_gain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

