package com.kominfo.anaksehat.models;
import java.util.Date;

public class NifasHistory {
    private long id;
    private long give_birth_id;
    private long nifas_history_type_id;
    private String nifas_history_type_name;
    private Date history_date;
    private String mother_condition;
    private String blooding_pervaginam;
    private String perineum;
    private String infection;
    private String uteri;
    private String fundus_uteri;
    private String lokhia;
    private String suggestion;
    private String birth_canal;
    private String breast;
    private String asi;
    private String vitamin_a;
    private String kontrasepsi;
    private String high_risk;
    private String bab;
    private String bak;
    private String good_food;
    private String drink;
    private String cleanliness;
    private String take_a_rest;
    private String caesar_take_care;
    private String breastfeeding;
    private String baby_treatment;
    private String baby_cry;
    private String baby_communication;
    private String kb_consultation;
    private int blood_pressure_top;
    private int blood_pressure_bottom;
    private double temp;
    private double respiration;
    private double pulse;

    public void setGive_birth_id(long give_birth_id) {
        this.give_birth_id = give_birth_id;
    }

    public Date getHistory_date() {
        return history_date;
    }

    public long getGive_birth_id() {
        return give_birth_id;
    }

    public long getId() {
        return id;
    }

    public long getNifas_history_type_id() {
        return nifas_history_type_id;
    }

    public String getBirth_canal() {
        return birth_canal;
    }

    public String getBlooding_pervaginam() {
        return blooding_pervaginam;
    }

    public String getAsi() {
        return asi;
    }

    public String getBreast() {
        return breast;
    }

    public String getFundus_uteri() {
        return fundus_uteri;
    }

    public String getInfection() {
        return infection;
    }

    public String getLokhia() {
        return lokhia;
    }

    public String getMother_condition() {
        return mother_condition;
    }

    public String getPerineum() {
        return perineum;
    }

    public String getUteri() {
        return uteri;
    }

    public void setBirth_canal(String birth_canal) {
        this.birth_canal = birth_canal;
    }

    public String getHigh_risk() {
        return high_risk;
    }

    public String getVitamin_a() {
        return vitamin_a;
    }

    public String getBab() {
        return bab;
    }

    public void setAsi(String asi) {
        this.asi = asi;
    }

    public String getKontrasepsi() {
        return kontrasepsi;
    }

    public void setBab(String bab) {
        this.bab = bab;
    }

    public int getBlood_pressure_top() {
        return blood_pressure_top;
    }

    public void setBlood_pressure_top(int blood_pressure_top) {
        this.blood_pressure_top = blood_pressure_top;
    }

    public int getBlood_pressure_bottom() {
        return blood_pressure_bottom;
    }

    public void setBlood_pressure_bottom(int blood_pressure_bottom) {
        this.blood_pressure_bottom = blood_pressure_bottom;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getRespiration() {
        return respiration;
    }

    public void setRespiration(double respiration) {
        this.respiration = respiration;
    }

    public double getPulse() {
        return pulse;
    }

    public void setPulse(double pulse) {
        this.pulse = pulse;
    }

    public void setBlooding_pervaginam(String blooding_pervaginam) {
        this.blooding_pervaginam = blooding_pervaginam;
    }

    public void setBreast(String breast) {
        this.breast = breast;
    }

    public void setFundus_uteri(String fundus_uteri) {
        this.fundus_uteri = fundus_uteri;
    }

    public void setHigh_risk(String high_risk) {
        this.high_risk = high_risk;
    }

    public void setHistory_date(Date history_date) {
        this.history_date = history_date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setInfection(String infection) {
        this.infection = infection;
    }

    public void setKontrasepsi(String kontrasepsi) {
        this.kontrasepsi = kontrasepsi;
    }

    public void setLokhia(String lokhia) {
        this.lokhia = lokhia;
    }

    public String getBak() {
        return bak;
    }

    public void setMother_condition(String mother_condition) {
        this.mother_condition = mother_condition;
    }

    public void setNifas_history_type_id(long nifas_history_type_id) {
        this.nifas_history_type_id = nifas_history_type_id;
    }

    public void setBak(String bak) {
        this.bak = bak;
    }

    public void setPerineum(String perineum) {
        this.perineum = perineum;
    }

    public void setUteri(String uteri) {
        this.uteri = uteri;
    }

    public void setVitamin_a(String vitamin_a) {
        this.vitamin_a = vitamin_a;
    }

    public String getBaby_communication() {
        return baby_communication;
    }

    public String getBaby_cry() {
        return baby_cry;
    }

    public String getBaby_treatment() {
        return baby_treatment;
    }

    public String getBreastfeeding() {
        return breastfeeding;
    }

    public String getGood_food() {
        return good_food;
    }

    public String getCaesar_take_care() {
        return caesar_take_care;
    }

    public String getCleanliness() {
        return cleanliness;
    }

    public String getDrink() {
        return drink;
    }

    public String getKb_consultation() {
        return kb_consultation;
    }

    public String getTake_a_rest() {
        return take_a_rest;
    }

    public void setBaby_communication(String baby_communication) {
        this.baby_communication = baby_communication;
    }

    public void setBaby_cry(String baby_cry) {
        this.baby_cry = baby_cry;
    }

    public void setBaby_treatment(String baby_treatment) {
        this.baby_treatment = baby_treatment;
    }

    public void setBreastfeeding(String breastfeeding) {
        this.breastfeeding = breastfeeding;
    }

    public void setCaesar_take_care(String caesar_take_care) {
        this.caesar_take_care = caesar_take_care;
    }

    public void setCleanliness(String cleanliness) {
        this.cleanliness = cleanliness;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public void setGood_food(String good_food) {
        this.good_food = good_food;
    }

    public void setKb_consultation(String kb_consultation) {
        this.kb_consultation = kb_consultation;
    }

    public void setTake_a_rest(String take_a_rest) {
        this.take_a_rest = take_a_rest;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getNifas_history_type_name() {
        return nifas_history_type_name;
    }

    public void setNifas_history_type_name(String nifas_history_type_name) {
        this.nifas_history_type_name = nifas_history_type_name;
    }
}
