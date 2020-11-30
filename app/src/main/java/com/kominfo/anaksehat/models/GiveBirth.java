package com.kominfo.anaksehat.models;

import java.util.Date;

public class GiveBirth {
    private long id;
    private Date birth_date;
    private String birth_time;
    private int pregnancy_id;
    private int mother_id;
    private String mother_name;
    private int pregnancy_age;
    private String bitrh_helper;
    private String birth_way_id;
    private int mother_condition_id;
    private String mother_condition_name;
    private String remarks;
    private int child_id;
    private int user_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public int getMother_id() {
        return mother_id;
    }

    public int getPregnancy_age() {
        return pregnancy_age;
    }

    public int getPregnancy_id() {
        return pregnancy_id;
    }

    public int getMother_condition_id() {
        return mother_condition_id;
    }

    public String getBirth_time() {
        return birth_time;
    }

    public String getBirth_way_id() {
        return birth_way_id;
    }

    public String getBitrh_helper() {
        return bitrh_helper;
    }

    public String getMother_condition_name() {
        return mother_condition_name;
    }

    public String getMother_name() {
        return mother_name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public void setBirth_time(String birth_time) {
        this.birth_time = birth_time;
    }

    public void setBirth_way_id(String birth_way_id) {
        this.birth_way_id = birth_way_id;
    }

    public void setBitrh_helper(String bitrh_helper) {
        this.bitrh_helper = bitrh_helper;
    }

    public void setMother_condition_id(int mother_condition_id) {
        this.mother_condition_id = mother_condition_id;
    }

    public void setMother_condition_name(String mother_condition_name) {
        this.mother_condition_name = mother_condition_name;
    }

    public void setMother_id(int mother_id) {
        this.mother_id = mother_id;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public void setPregnancy_age(int pregnancy_age) {
        this.pregnancy_age = pregnancy_age;
    }

    public void setPregnancy_id(int pregnancy_id) {
        this.pregnancy_id = pregnancy_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getChild_id() {
        return child_id;
    }

    public void setChild_id(int child_id) {
        this.child_id = child_id;
    }
}

