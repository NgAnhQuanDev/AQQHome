package com.example.aqqhome.model;

public class usermodel2 {
    private String UserID;
    private String PhoneNumber;
    private String Email;
    private String FullName;
    private String Money;
    private String message;
    private boolean success;
    private String uid;

    private String avatar_path;


    private String Type;
    private String Manager;

    private String JWT;

    private String RoomID;

    public usermodel2() {
    }

    public usermodel2(String userID, String phoneNumber, String email, String fullName, String money, String message, boolean success, String uid, String avatar_path, String type, String manager, String JWT, String roomID) {
        UserID = userID;
        PhoneNumber = phoneNumber;
        Email = email;
        FullName = fullName;
        Money = money;
        this.message = message;
        this.success = success;
        this.uid = uid;
        this.avatar_path = avatar_path;
        Type = type;
        Manager = manager;
        this.JWT = JWT;
        RoomID = roomID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getMoney() {
        return Money;
    }

    public void setMoney(String money) {
        Money = money;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAvatar_path() {
        return avatar_path;
    }

    public void setAvatar_path(String avatar_path) {
        this.avatar_path = avatar_path;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getManager() {
        return Manager;
    }

    public void setManager(String manager) {
        Manager = manager;
    }

    public String getJWT() {
        return JWT;
    }

    public void setJWT(String JWT) {
        this.JWT = JWT;
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String roomID) {
        RoomID = roomID;
    }
}
