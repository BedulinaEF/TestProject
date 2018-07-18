package com.test.elenabedulina.testproject.api.models;

public class CallsIdEndRequest {
    //{ "call_duration": call_duration, "os": os, "os_version": os_version }
    private int callDuration;
    private String os;
    private String osVersion;

    public int getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(int callDuration) {
        this.callDuration = callDuration;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }
}
