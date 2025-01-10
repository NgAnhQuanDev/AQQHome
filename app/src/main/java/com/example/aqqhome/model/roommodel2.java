package com.example.aqqhome.model;

public class roommodel2 {
    private String Code;
    private String RoomName;
    private String RoomID;
    private String NumberOfFloors;
    private String Area;
    private String ParkingCardNumber;
    private String ApartmentID;
    private String Debt;
    private boolean success;
    private String message;
    private String amount;
    private String Chusohuu;
    private String Socancuoc;
    private String Sodienthoai;
    private String Ngaybangiao;
    private String Mahopdong;
    private String Ngaydangky;

    public roommodel2(String code, String roomName, String roomID, String numberOfFloors, String area, String parkingCardNumber, String apartmentID, String debt, boolean success, String message, String amount, String chusohuu, String socancuoc, String sodienthoai, String ngaybangiao, String mahopdong, String ngaydangky) {
        Code = code;
        RoomName = roomName;
        RoomID = roomID;
        NumberOfFloors = numberOfFloors;
        Area = area;
        ParkingCardNumber = parkingCardNumber;
        ApartmentID = apartmentID;
        Debt = debt;
        this.success = success;
        this.message = message;
        this.amount = amount;
        Chusohuu = chusohuu;
        Socancuoc = socancuoc;
        Sodienthoai = sodienthoai;
        Ngaybangiao = ngaybangiao;
        Mahopdong = mahopdong;
        Ngaydangky = ngaydangky;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String roomID) {
        RoomID = roomID;
    }

    public String getNumberOfFloors() {
        return NumberOfFloors;
    }

    public void setNumberOfFloors(String numberOfFloors) {
        NumberOfFloors = numberOfFloors;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getParkingCardNumber() {
        return ParkingCardNumber;
    }

    public void setParkingCardNumber(String parkingCardNumber) {
        ParkingCardNumber = parkingCardNumber;
    }

    public String getApartmentID() {
        return ApartmentID;
    }

    public void setApartmentID(String apartmentID) {
        ApartmentID = apartmentID;
    }

    public String getDebt() {
        return Debt;
    }

    public void setDebt(String debt) {
        Debt = debt;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getChusohuu() {
        return Chusohuu;
    }

    public void setChusohuu(String chusohuu) {
        Chusohuu = chusohuu;
    }

    public String getSocancuoc() {
        return Socancuoc;
    }

    public void setSocancuoc(String socancuoc) {
        Socancuoc = socancuoc;
    }

    public String getSodienthoai() {
        return Sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        Sodienthoai = sodienthoai;
    }

    public String getNgaybangiao() {
        return Ngaybangiao;
    }

    public void setNgaybangiao(String ngaybangiao) {
        Ngaybangiao = ngaybangiao;
    }

    public String getMahopdong() {
        return Mahopdong;
    }

    public void setMahopdong(String mahopdong) {
        Mahopdong = mahopdong;
    }

    public String getNgaydangky() {
        return Ngaydangky;
    }

    public void setNgaydangky(String ngaydangky) {
        Ngaydangky = ngaydangky;
    }
}

