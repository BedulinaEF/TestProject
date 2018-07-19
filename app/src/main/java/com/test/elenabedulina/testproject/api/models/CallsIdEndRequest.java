package com.test.elenabedulina.testproject.api.models;

public class CallsIdEndRequest {
    //{ "call_duration": call_duration, "os": os, "os_version": os_version }
    private String callDuration;
    private String os="android";
    private String osVersion;


    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
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
