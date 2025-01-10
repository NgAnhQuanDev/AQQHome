package com.example.aqqhome.model;

import java.util.List;

public class ThongBaoModel2 {
    private boolean success;
    private String message;
    private List<ThongBaoModel> data;

    public ThongBaoModel2(boolean success, String message, List<ThongBaoModel> data) {
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

    public List<ThongBaoModel> getData() {
        return data;
    }

    public void setData(List<ThongBaoModel> data) {
        this.data = data;
    }
}
