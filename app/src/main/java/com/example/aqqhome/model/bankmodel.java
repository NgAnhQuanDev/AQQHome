package com.example.aqqhome.model;

public class bankmodel {
    private String UserID;
    private String MoneyID;
    private String thoigiannap;
    private String Sotiennap;
    private String success;
    private String message;
    private String token;

    private String type;

    public bankmodel(String userID, String moneyID, String thoigiannap, String sotiennap, String success, String message, String token, String type) {
        UserID = userID;
        MoneyID = moneyID;
        this.thoigiannap = thoigiannap;
        Sotiennap = sotiennap;
        this.success = success;
        this.message = message;
        this.token = token;
        this.type = type;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getMoneyID() {
        return MoneyID;
    }

    public void setMoneyID(String moneyID) {
        MoneyID = moneyID;
    }

    public String getThoigiannap() {
        return thoigiannap;
    }

    public void setThoigiannap(String thoigiannap) {
        this.thoigiannap = thoigiannap;
    }

    public String getSotiennap() {
        return Sotiennap;
    }

    public void setSotiennap(String sotiennap) {
        Sotiennap = sotiennap;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
