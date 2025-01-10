package com.example.aqqhome.model;

public class newfeedmodel {
    private String NewID;
    private String Text;
    private String avatar_path;
    private String NewTime;
    private String Name;
    private String ApartmentID;
    private String RoomName;
    private String UserID;

    private String Url1;
    private String Url2;
    private String Url3;
    private boolean success;
    private String message;

    public newfeedmodel(String newID, String text, String avatar_path, String newTime, String name, String apartmentID, String roomName, String userID, String url1, String url2, String url3, boolean success, String message) {
        NewID = newID;
        Text = text;
        this.avatar_path = avatar_path;
        NewTime = newTime;
        Name = name;
        ApartmentID = apartmentID;
        RoomName = roomName;
        UserID = userID;
        Url1 = url1;
        Url2 = url2;
        Url3 = url3;
        this.success = success;
        this.message = message;
    }

    public String getNewID() {
        return NewID;
    }

    public void setNewID(String newID) {
        NewID = newID;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getAvatar_path() {
        return avatar_path;
    }

    public void setAvatar_path(String avatar_path) {
        this.avatar_path = avatar_path;
    }

    public String getNewTime() {
        return NewTime;
    }

    public void setNewTime(String newTime) {
        NewTime = newTime;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getApartmentID() {
        return ApartmentID;
    }

    public void setApartmentID(String apartmentID) {
        ApartmentID = apartmentID;
    }

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUrl1() {
        return Url1;
    }

    public void setUrl1(String url1) {
        Url1 = url1;
    }

    public String getUrl2() {
        return Url2;
    }

    public void setUrl2(String url2) {
        Url2 = url2;
    }

    public String getUrl3() {
        return Url3;
    }

    public void setUrl3(String url3) {
        Url3 = url3;
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
}

