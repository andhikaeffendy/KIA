package com.kominfo.anaksehat.models;

import com.google.gson.annotations.SerializedName;

public class User {
    private String name;
    private String username;
    private String email;
    @SerializedName("photo_url")
    private String photo;
    @SerializedName("user_id")
    private long id;
    private String token;
    private int posyandu;
    @SerializedName("mother_id")
    private long motherId;
    @SerializedName("district_id")
    private int districtId;

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoto() {
        return photo;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getPosyandu() {
        return posyandu;
    }

    public void setPosyandu(int posyandu) {
        this.posyandu = posyandu;
    }

    public long getMotherId() {
        return motherId;
    }

    public void setMotherId(long motherId) {
        this.motherId = motherId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }
}
