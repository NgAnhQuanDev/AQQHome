package com.example.aqqhome.model;

import java.util.List;

public class Congviecmodel2 {
    private boolean success;
    private String message;
    private List<Congviecmodel> data;

    public Congviecmodel2(boolean success, String message, List<Congviecmodel> data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Congviecmodel> getData() {
        return data;
    }

    public void setData(List<Congviecmodel> data) {
        this.data = data;
    }
}
