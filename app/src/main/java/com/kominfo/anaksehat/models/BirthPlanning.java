package com.kominfo.anaksehat.models;

import java.util.Date;

public class BirthPlanning {
    private long id;
    private Date meeting_date;
    private Date birth_planning_date;
    private long pregnancy_id;
    private long mother_id;
    private String mother_name;
    private String location;
    private String birth_planning_place;
    private String birth_helper_mother;
    private String birth_helper_family;
    private String birth_notice;
    private String danger_notice;
    private String transportation_problem;
    private String mother_keeper;
    private String cost_problem;
    private String blood_giver;
    private String birth_partner;
    private String children_keeper;
    private String kb_method;
    private String helper_discussion;
    private String home_condition;
    private String home_equipment;
    private String user_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMother_id() {
        return mother_id;
    }

    public void setMother_id(long mother_id) {
        this.mother_id = mother_id;
    }

    public void setPregnancy_id(long pregnancy_id) {
        this.pregnancy_id = pregnancy_id;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getMother_name() {
        return mother_name;
    }

    public Date getBirth_planning_date() {
        return birth_planning_date;
    }

    public Date getMeeting_date() {
        return meeting_date;
    }

    public long getPregnancy_id() {
        return pregnancy_id;
    }

    public String getBirth_helper_family() {
        return birth_helper_family;
    }

    public String getBirth_planning_place() {
        return birth_planning_place;
    }

    public String getBirth_helper_mother() {
        return birth_helper_mother;
    }

    public String getBirth_notice() {
        return birth_notice;
    }

    public String getLocation() {
        return location;
    }

    public String getDanger_notice() {
        return danger_notice;
    }

    public String getTransportation_problem() {
        return transportation_problem;
    }

    public void setBirth_helper_family(String birth_helper_family) {
        this.birth_helper_family = birth_helper_family;
    }

    public void setBirth_helper_mother(String birth_helper_mother) {
        this.birth_helper_mother = birth_helper_mother;
    }

    public void setBirth_notice(String birth_notice) {
        this.birth_notice = birth_notice;
    }

    public void setBirth_planning_date(Date birth_planning_date) {
        this.birth_planning_date = birth_planning_date;
    }

    public void setBirth_planning_place(String birth_planning_place) {
        this.birth_planning_place = birth_planning_place;
    }

    public void setDanger_notice(String danger_notice) {
        this.danger_notice = danger_notice;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMeeting_date(Date meeting_date) {
        this.meeting_date = meeting_date;
    }

    public String getMother_keeper() {
        return mother_keeper;
    }

    public void setTransportation_problem(String transportation_problem) {
        this.transportation_problem = transportation_problem;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCost_problem() {
        return cost_problem;
    }

    public String getBirth_partner() {
        return birth_partner;
    }

    public String getBlood_giver() {
        return blood_giver;
    }

    public String getChildren_keeper() {
        return children_keeper;
    }

    public void setMother_keeper(String mother_keeper) {
        this.mother_keeper = mother_keeper;
    }

    public String getHelper_discussion() {
        return helper_discussion;
    }

    public String getHome_condition() {
        return home_condition;
    }

    public String getHome_equipment() {
        return home_equipment;
    }

    public String getKb_method() {
        return kb_method;
    }

    public void setCost_problem(String cost_problem) {
        this.cost_problem = cost_problem;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setBirth_partner(String birth_partner) {
        this.birth_partner = birth_partner;
    }

    public void setBlood_giver(String blood_giver) {
        this.blood_giver = blood_giver;
    }

    public void setChildren_keeper(String children_keeper) {
        this.children_keeper = children_keeper;
    }

    public void setHelper_discussion(String helper_discussion) {
        this.helper_discussion = helper_discussion;
    }

    public void setHome_condition(String home_condition) {
        this.home_condition = home_condition;
    }

    public void setHome_equipment(String home_equipment) {
        this.home_equipment = home_equipment;
    }

    public void setKb_method(String kb_method) {
        this.kb_method = kb_method;
    }
}


