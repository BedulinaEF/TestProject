package com.test.elenabedulina.testproject.api.models;

import com.google.gson.annotations.SerializedName;

public class Content {
    @SerializedName("call_center")
    private String callCenter;

    public String getCallCenter() {
        return callCenter;
    }

    public void setCallCenter(String callCenter) {
        this.callCenter = callCenter;
    }

}
