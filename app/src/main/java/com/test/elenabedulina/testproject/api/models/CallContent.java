package com.test.elenabedulina.testproject.api.models;

import com.google.gson.annotations.SerializedName;

public class CallContent {
    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
