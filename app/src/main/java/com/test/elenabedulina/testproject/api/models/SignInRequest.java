package com.test.elenabedulina.testproject.api.models;

public class SignInRequest {
    private String inn;
    private String password;

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
