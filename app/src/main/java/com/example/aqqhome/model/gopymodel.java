package com.example.aqqhome.model;

public class gopymodel {
    private String GopyID;
    private String tieude;
    private String text;
    private String time;
    private String UserID;
    private String RoomID;

    private String TrangThai;
    private String ApartmentID;
    private boolean success;

    public gopymodel(String gopyID, String tieude, String text, String time, String userID, String roomID, String trangThai, String apartmentID, boolean success) {
        GopyID = gopyID;
        this.tieude = tieude;
        this.text = text;
        this.time = time;
        UserID = userID;
        RoomID = roomID;
        TrangThai = trangThai;
        ApartmentID = apartmentID;
        this.success = success;
    }

    public String getGopyID() {
        return GopyID;
    }

    public void setGopyID(String gopyID) {
        GopyID = gopyID;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String roomID) {
        RoomID = roomID;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public String getApartmentID() {
        return ApartmentID;
    }

    public void setApartmentID(String apartmentID) {
        ApartmentID = apartmentID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
