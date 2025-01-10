package com.example.aqqhome.model;

public class ChatModel2 {
    private String RoomID;
    private String message;
    private String ApartmentID;
    private String time;

    private Boolean Admin;

    public ChatModel2() {
    }

    public ChatModel2(String roomID, String message, String apartmentID, String time, Boolean admin) {
        RoomID = roomID;
        this.message = message;
        ApartmentID = apartmentID;
        this.time = time;
        Admin = admin;
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String roomID) {
        RoomID = roomID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getApartmentID() {
        return ApartmentID;
    }

    public void setApartmentID(String apartmentID) {
        ApartmentID = apartmentID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getAdmin() {
        return Admin;
    }

    public void setAdmin(Boolean admin) {
        Admin = admin;
    }
}
