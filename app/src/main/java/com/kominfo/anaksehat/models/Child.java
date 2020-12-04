package com.kominfo.anaksehat.models;

import java.util.Date;
import java.util.List;

public class Child {
    private long id;
    private Date birth_date;
    private String name;
    private String blood_type;
    private int height;
    private double weight;
    private String gender;
    private int first_length;
    private double first_weight;
    private int first_head_round;
    private String mother_name;
    private String photo_url;
    private int give_birth_id;
    private int child_number;
    private String jampersal_status;
    private String note;
    private List<Checkbox> nb_baby_treatments;
    private List<Checkbox> nb_baby_conditions;

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

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public int getFirst_head_round() {
        return first_head_round;
    }

    public int getFirst_length() {
        return first_length;
    }

    public double getFirst_weight() {
        return first_weight;
    }

    public String getMother_name() {
        return mother_name;
    }

    public void setFirst_head_round(int first_head_round) {
        this.first_head_round = first_head_round;
    }

    public void setFirst_length(int first_length) {
        this.first_length = first_length;
    }

    public void setFirst_weight(double first_weight) {
        this.first_weight = first_weight;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setGive_birth_id(int give_birth_id) {
        this.give_birth_id = give_birth_id;
    }

    public int getGive_birth_id() {
        return give_birth_id;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public int getChild_number() {
        return child_number;
    }

    public List<Checkbox> getNb_baby_conditions() {
        return nb_baby_conditions;
    }

    public List<Checkbox> getNb_baby_treatments() {
        return nb_baby_treatments;
    }

    public String getJampersal_status() {
        return jampersal_status;
    }

    public void setChild_number(int child_number) {
        this.child_number = child_number;
    }

    public void setJampersal_status(String jampersal_status) {
        this.jampersal_status = jampersal_status;
    }

    public void setNb_baby_conditions(List<Checkbox> nb_baby_conditions) {
        this.nb_baby_conditions = nb_baby_conditions;
    }

    public void setNb_baby_treatments(List<Checkbox> nb_baby_treatments) {
        this.nb_baby_treatments = nb_baby_treatments;
    }
}
