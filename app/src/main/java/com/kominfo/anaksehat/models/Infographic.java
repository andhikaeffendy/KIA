package com.kominfo.anaksehat.models;

import java.util.Date;

public class Infographic {
    private long id;
    private Date created_at;
    private String title;
    private String summary;
    private String body;
    private String image_url;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public String getSummary() {
        return summary;
    }

    public String getTitle() {
        return title;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
