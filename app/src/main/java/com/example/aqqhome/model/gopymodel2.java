package com.example.aqqhome.model;

import java.util.List;

public class gopymodel2 {
    private boolean success;
    private List<gopymodel> data;
    private String RoomID;


    public gopymodel2(boolean success, List<gopymodel> data, String roomID) {
        this.success = success;
        this.data = data;
        RoomID = roomID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<gopymodel> getData() {
        return data;
    }

    public void setData(List<gopymodel> data) {
        this.data = data;
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String roomID) {
        RoomID = roomID;
    }
}
