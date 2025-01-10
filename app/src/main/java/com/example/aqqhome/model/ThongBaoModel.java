package com.example.aqqhome.model;

public class ThongBaoModel {
    private String IDTB;
    private String Type;
    private String RoomID;
    private String Seen;
    private String ngay;
    private String UserID;
    private String HoadonID;
    private String ApartmentID;
    private String text;

    public ThongBaoModel(String IDTB, String type, String roomID, String seen, String ngay, String userID, String hoadonID, String apartmentID, String text) {
        this.IDTB = IDTB;
        Type = type;
        RoomID = roomID;
        Seen = seen;
        this.ngay = ngay;
        UserID = userID;
        HoadonID = hoadonID;
        ApartmentID = apartmentID;
        this.text = text;
    }

    public String getIDTB() {
        return IDTB;
    }

    public void setIDTB(String IDTB) {
        this.IDTB = IDTB;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String roomID) {
        RoomID = roomID;
    }

    public String getSeen() {
        return Seen;
    }

    public void setSeen(String seen) {
        Seen = seen;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getHoadonID() {
        return HoadonID;
    }

    public void setHoadonID(String hoadonID) {
        HoadonID = hoadonID;
    }

    public String getApartmentID() {
        return ApartmentID;
    }

    public void setApartmentID(String apartmentID) {
        ApartmentID = apartmentID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
