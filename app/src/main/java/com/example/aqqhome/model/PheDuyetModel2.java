package com.example.aqqhome.model;

import java.util.List;

public class PheDuyetModel2 {
    private boolean success;
    private List<PheDuyetModel> data;

    public PheDuyetModel2(boolean success, List<PheDuyetModel> data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<PheDuyetModel> getData() {
        return data;
    }

    public void setData(List<PheDuyetModel> data) {
        this.data = data;
    }
}
