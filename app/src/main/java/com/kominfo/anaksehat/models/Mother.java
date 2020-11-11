package com.kominfo.anaksehat.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Mother {
    private long id;
    private Date birth_date;
    private String name;
    private String blood_type;
    private String spouse_name;
    private String address;
    private String state_name;
    private String district_name;
    private int height;
    private double weight;
    private int blood_pressure_top;
    private int blood_pressure_bottom;
    @SerializedName("children_count")
    private int children;
    private double imt_rate;
    @SerializedName("mother_nutrition_category")
    private String nutrition_category;
    @SerializedName("mother_weight_index")
    private String weight_index;
    private String photo_url;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public double getImt_rate() {
        return imt_rate;
    }

    public int getBlood_pressure_bottom() {
        return blood_pressure_bottom;
    }

    public int getBlood_pressure_top() {
        return blood_pressure_top;
    }

    public int getChildren() {
        return children;
    }

    public int getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public String getNutrition_category() {
        return nutrition_category;
    }

    public String getSpouse_name() {
        return spouse_name;
    }

    public String getState_name() {
        return state_name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public void setBlood_pressure_top(int blood_pressure_top) {
        this.blood_pressure_top = blood_pressure_top;
    }

    public void setBlood_pressure_bottom(int blood_pressure_bottom) {
        this.blood_pressure_bottom = blood_pressure_bottom;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getWeight_index() {
        return weight_index;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setImt_rate(double imt_rate) {
        this.imt_rate = imt_rate;
    }

    public void setNutrition_category(String nutrition_category) {
        this.nutrition_category = nutrition_category;
    }

    public void setSpouse_name(String spouse_name) {
        this.spouse_name = spouse_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public void setWeight_index(String weight_index) {
        this.weight_index = weight_index;
    }

    @Override
    public String toString() {
        return name;
    }
}
