package com.kominfo.anaksehat.models;

import android.support.annotation.Nullable;

public class Checkbox {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return ((Checkbox)obj).getId() == this.id;
    }
}
