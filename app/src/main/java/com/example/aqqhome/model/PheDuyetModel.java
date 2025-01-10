package com.example.aqqhome.model;

public class PheDuyetModel {
    private String PheDuyetID;
    private String UserID;
    private String nameroom;
    private String name;
    private String phone;
    private String time;
    private String kichhoat;
    private String area;
    private String tang;
    private String masohopdong;
    private String code;
    private String ApartmentID;
    private String NameApartment;
    private boolean success;


    public PheDuyetModel(String pheDuyetID, String userID, String nameroom, String name, String phone, String time, String kichhoat, String area, String tang, String masohopdong, String code, String apartmentID, String nameApartment, boolean success) {
        PheDuyetID = pheDuyetID;
        UserID = userID;
        this.nameroom = nameroom;
        this.name = name;
        this.phone = phone;
        this.time = time;
        this.kichhoat = kichhoat;
        this.area = area;
        this.tang = tang;
        this.masohopdong = masohopdong;
        this.code = code;
        ApartmentID = apartmentID;
        NameApartment = nameApartment;
        this.success = success;
    }

    public String getPheDuyetID() {
        return PheDuyetID;
    }

    public void setPheDuyetID(String pheDuyetID) {
        PheDuyetID = pheDuyetID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getNameroom() {
        return nameroom;
    }

    public void setNameroom(String nameroom) {
        this.nameroom = nameroom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKichhoat() {
        return kichhoat;
    }

    public void setKichhoat(String kichhoat) {
        this.kichhoat = kichhoat;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTang() {
        return tang;
    }

    public void setTang(String tang) {
        this.tang = tang;
    }

    public String getMasohopdong() {
        return masohopdong;
    }

    public void setMasohopdong(String masohopdong) {
        this.masohopdong = masohopdong;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getApartmentID() {
        return ApartmentID;
    }

    public void setApartmentID(String apartmentID) {
        ApartmentID = apartmentID;
    }

    public String getNameApartment() {
        return NameApartment;
    }

    public void setNameApartment(String nameApartment) {
        NameApartment = nameApartment;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
