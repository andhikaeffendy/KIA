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
    private long give_birth_id;
    private long mother_id;
    private int arm_round;
    private int kek_status;
    private String kontrasepsi;
    private String disease_histories;
    private String allergy_histories;
    private int pregnancy_number;
    private int birth_count;
    private int miscariage_count;
    private int g_count;
    private int p_count;
    private int a_count;
    private int children_count;
    private int dead_birth_count;
    private int premature_children_count;
    private String distance;
    private String immunization_status;
    private String last_birth_helper;
    private String last_birth_way;

    public int getArm_round() {
        return arm_round;
    }

    public void setArm_round(int arm_round) {
        this.arm_round = arm_round;
    }

    public int getKek_status() {
        return kek_status;
    }

    public void setKek_status(int kek_status) {
        this.kek_status = kek_status;
    }

    public String getKontrasepsi() {
        return kontrasepsi;
    }

    public void setKontrasepsi(String kontrasepsi) {
        this.kontrasepsi = kontrasepsi;
    }

    public String getDisease_histories() {
        return disease_histories;
    }

    public void setDisease_histories(String disease_histories) {
        this.disease_histories = disease_histories;
    }

    public String getAllergy_histories() {
        return allergy_histories;
    }

    public void setAllergy_histories(String allergy_histories) {
        this.allergy_histories = allergy_histories;
    }

    public int getPregnancy_number() {
        return pregnancy_number;
    }

    public void setPregnancy_number(int pregnancy_number) {
        this.pregnancy_number = pregnancy_number;
    }

    public int getBirth_count() {
        return birth_count;
    }

    public void setBirth_count(int birth_count) {
        this.birth_count = birth_count;
    }

    public int getMiscariage_count() {
        return miscariage_count;
    }

    public void setMiscariage_count(int miscariage_count) {
        this.miscariage_count = miscariage_count;
    }

    public int getG_count() {
        return g_count;
    }

    public void setG_count(int g_count) {
        this.g_count = g_count;
    }

    public int getP_count() {
        return p_count;
    }

    public void setP_count(int p_count) {
        this.p_count = p_count;
    }

    public int getA_count() {
        return a_count;
    }

    public void setA_count(int a_count) {
        this.a_count = a_count;
    }

    public int getChildren_count() {
        return children_count;
    }

    public void setChildren_count(int children_count) {
        this.children_count = children_count;
    }

    public int getDead_birth_count() {
        return dead_birth_count;
    }

    public void setDead_birth_count(int dead_birth_count) {
        this.dead_birth_count = dead_birth_count;
    }

    public int getPremature_children_count() {
        return premature_children_count;
    }

    public void setPremature_children_count(int premature_children_count) {
        this.premature_children_count = premature_children_count;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getImmunization_status() {
        return immunization_status;
    }

    public void setImmunization_status(String immunization_status) {
        this.immunization_status = immunization_status;
    }

    public String getLast_birth_helper() {
        return last_birth_helper;
    }

    public void setLast_birth_helper(String last_birth_helper) {
        this.last_birth_helper = last_birth_helper;
    }

    public String getLast_birth_way() {
        return last_birth_way;
    }

    public void setLast_birth_way(String last_birth_way) {
        this.last_birth_way = last_birth_way;
    }

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

    public long getGive_birth_id() {
        return give_birth_id;
    }

    public void setMother_id(long mother_id) {
        this.mother_id = mother_id;
    }

    public void setGive_birth_id(long give_birth_id) {
        this.give_birth_id = give_birth_id;
    }

    public long getMother_id() {
        return mother_id;
    }
}

