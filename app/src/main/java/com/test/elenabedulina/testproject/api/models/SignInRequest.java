package com.test.elenabedulina.testproject.api.models;

public class SignInRequest {
    private String iin;
    private String password;


    public String getIin() {
        return iin;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
