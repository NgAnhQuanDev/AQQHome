package com.example.aqqhome.model;

public class Xacminhpheduyetmodel {
    private String kichhoat;
    private String Code;
    private String ApartmentID;
    private String NameApartment;
    private String PheDuyetID;
    private boolean success;
    private String message;

    public Xacminhpheduyetmodel(String kichhoat, String code, String apartmentID, String nameApartment, String pheDuyetID, boolean success, String message) {
        this.kichhoat = kichhoat;
        Code = code;
        ApartmentID = apartmentID;
        NameApartment = nameApartment;
        PheDuyetID = pheDuyetID;
        this.success = success;
        this.message = message;
    }

    public String getKichhoat() {
        return kichhoat;
    }

    public void setKichhoat(String kichhoat) {
        this.kichhoat = kichhoat;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
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

    public String getPheDuyetID() {
        return PheDuyetID;
    }

    public void setPheDuyetID(String pheDuyetID) {
        PheDuyetID = pheDuyetID;
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
