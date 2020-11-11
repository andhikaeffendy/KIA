package com.kominfo.anaksehat.models;

import java.util.Date;

public class Immunization {
    private long id;
    private Date immunization_date;
    private String vaccine_name;
    private Date start_date;
    private Date end_date;
    private String color;
    private String child_name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public Date getImmunization_date() {
        return immunization_date;
    }

    public Date getStart_date() {
        return start_date;
    }

    public String getColor() {
        return color;
    }

    public String getVaccine_name() {
        return vaccine_name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public void setImmunization_date(Date immunization_date) {
        this.immunization_date = immunization_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public void setVaccine_name(String vaccine_name) {
        this.vaccine_name = vaccine_name;
    }
}
