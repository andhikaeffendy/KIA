package com.kominfo.anaksehat.models;

import java.util.Date;

public class PregnancyHistory {
    private long id;
    private Date history_date;
    private double weight;
    private int blood_pressure_top;
    private int blood_pressure_bottom;
    private String note;
    private double baby_weight;
    private String gender_prediction;
    private String amniotic_condition;
    private String pregnancy_weight_gain;
    private String mother_weight_index;
    private String mother_nutrition_category;
    private int fundus_height;
    private String fetus_position;
    private int heart_beat;
    private String leg;
    private String lab;
    private String treatment;
    private String suggestion;
    private Date next_visit_date;
    private String pregnancy_age;
    private int blooding;
    private int infection;
    private double hb;
    private String other_risks;

    public String getPregnancy_age() {
        return pregnancy_age;
    }

    public void setPregnancy_age(String pregnancy_age) {
        this.pregnancy_age = pregnancy_age;
    }

    public void setHistory_date(Date history_date) {
        this.history_date = history_date;
    }

    public Date getHistory_date() {
        return history_date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setBlood_pressure_bottom(int blood_pressure_bottom) {
        this.blood_pressure_bottom = blood_pressure_bottom;
    }

    public void setBlood_pressure_top(int blood_pressure_top) {
        this.blood_pressure_top = blood_pressure_top;
    }

    public int getBlood_pressure_top() {
        return blood_pressure_top;
    }

    public int getBlood_pressure_bottom() {
        return blood_pressure_bottom;
    }

    public double getBaby_weight() {
        return baby_weight;
    }

    public double getWeight() {
        return weight;
    }

    public String getAmniotic_condition() {
        return amniotic_condition;
    }

    public String getGender_prediction() {
        return gender_prediction;
    }

    public String getPregnancy_weight_gain() {
        return pregnancy_weight_gain;
    }

    public void setAmniotic_condition(String amniotic_condition) {
        this.amniotic_condition = amniotic_condition;
    }

    public void setBaby_weight(double baby_weight) {
        this.baby_weight = baby_weight;
    }

    public void setGender_prediction(String gender_prediction) {
        this.gender_prediction = gender_prediction;
    }

    public void setPregnancy_weight_gain(String pregnancy_weight_gain) {
        this.pregnancy_weight_gain = pregnancy_weight_gain;
    }

    public void setMother_weight_index(String mother_weight_index) {
        this.mother_weight_index = mother_weight_index;
    }

    public void setMother_nutrition_category(String mother_nutrition_category) {
        this.mother_nutrition_category = mother_nutrition_category;
    }

    public String getMother_weight_index() {
        return mother_weight_index;
    }

    public String getMother_nutrition_category() {
        return mother_nutrition_category;
    }

    public int getFundus_height() {
        return fundus_height;
    }

    public void setFundus_height(int fundus_height) {
        this.fundus_height = fundus_height;
    }

    public String getFetus_position() {
        return fetus_position;
    }

    public void setFetus_position(String fetus_position) {
        this.fetus_position = fetus_position;
    }

    public int getHeart_beat() {
        return heart_beat;
    }

    public void setHeart_beat(int heart_beat) {
        this.heart_beat = heart_beat;
    }

    public String getLeg() {
        return leg;
    }

    public void setLeg(String leg) {
        this.leg = leg;
    }

    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Date getNext_visit_date() {
        return next_visit_date;
    }

    public void setNext_visit_date(Date next_visit_date) {
        this.next_visit_date = next_visit_date;
    }

    public void setInfection(int infection) {
        this.infection = infection;
    }

    public double getHb() {
        return hb;
    }

    public int getBlooding() {
        return blooding;
    }

    public int getInfection() {
        return infection;
    }

    public String getOther_risks() {
        return other_risks;
    }

    public void setBlooding(int blooding) {
        this.blooding = blooding;
    }

    public void setHb(double hb) {
        this.hb = hb;
    }

    public void setOther_risks(String other_risks) {
        this.other_risks = other_risks;
    }
}

