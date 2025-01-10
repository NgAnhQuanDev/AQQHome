package com.example.aqqhome.model;

public class ChatModel {
    private String RoomID;

    private String ApartmentID;

    private String RoomName;
    private long lastMessageTime;

    private String lastMessage;

    private String daxem;




    public ChatModel() {
    }

    public ChatModel(String roomID, String apartmentID, String roomName, long lastMessageTime, String lastMessage, String daxem) {
        RoomID = roomID;
        ApartmentID = apartmentID;
        RoomName = roomName;
        this.lastMessageTime = lastMessageTime;
        this.lastMessage = lastMessage;
        this.daxem = daxem;
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String roomID) {
        RoomID = roomID;
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

    public long getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(long lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getDaxem() {
        return daxem;
    }

    public void setDaxem(String daxem) {
        this.daxem = daxem;
    }
}
