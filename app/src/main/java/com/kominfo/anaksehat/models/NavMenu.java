package com.kominfo.anaksehat.models;

public class NavMenu {
    private long id;
    private int icon;
    private String name;

    public NavMenu(long id, int icon, String name){
        this.id = id;
        this.icon = icon;
        this.name = name;
    }

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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return name;
    }
}
