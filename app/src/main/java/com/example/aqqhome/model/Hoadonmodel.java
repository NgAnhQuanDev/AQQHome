package com.example.aqqhome.model;

import java.util.Date;

public class Hoadonmodel {
    public int HoadonID;
    public Date ngaytao;
    public String ngaythanhtoan;
    public String Debt;
    public String daThanhToan;
    public String UserID;
    public String RoomID;
    public String ServiceType;
    public String Phuongthuc;
    private boolean Selected;

    public boolean success;
    private String RoomName;

    public Hoadonmodel(int hoadonID, Date ngaytao, String ngaythanhtoan, String debt, String daThanhToan, String userID, String roomID, String serviceType, String phuongthuc, boolean selected, boolean success, String roomName) {
        HoadonID = hoadonID;
        this.ngaytao = ngaytao;
        this.ngaythanhtoan = ngaythanhtoan;
        Debt = debt;
        this.daThanhToan = daThanhToan;
        UserID = userID;
        RoomID = roomID;
        ServiceType = serviceType;
        Phuongthuc = phuongthuc;
        Selected = selected;
        this.success = success;
        RoomName = roomName;
    }

    public int getHoadonID() {
        return HoadonID;
    }

    public void setHoadonID(int hoadonID) {
        HoadonID = hoadonID;
    }

    public Date getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(Date ngaytao) {
        this.ngaytao = ngaytao;
    }

    public String getNgaythanhtoan() {
        return ngaythanhtoan;
    }

    public void setNgaythanhtoan(String ngaythanhtoan) {
        this.ngaythanhtoan = ngaythanhtoan;
    }

    public String getDebt() {
        return Debt;
    }

    public void setDebt(String debt) {
        Debt = debt;
    }

    public String getDaThanhToan() {
        return daThanhToan;
    }

    public void setDaThanhToan(String daThanhToan) {
        this.daThanhToan = daThanhToan;
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

    public String getServiceType() {
        return ServiceType;
    }

    public void setServiceType(String serviceType) {
        ServiceType = serviceType;
    }

    public String getPhuongthuc() {
        return Phuongthuc;
    }

    public void setPhuongthuc(String phuongthuc) {
        Phuongthuc = phuongthuc;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
    }
}
