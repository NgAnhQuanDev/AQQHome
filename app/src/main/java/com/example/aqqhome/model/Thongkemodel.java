package com.example.aqqhome.model;

import java.util.List;

public class Thongkemodel{
    private String ApartmentID;
    private String Fund;
    private String Month;
    private String TongTienNo;
    private String TongSoHoaDon;
    private String TongSoHoaDonThanhToan;
    private String TongSoTienDaThanhToan;
    private String TongSoTienChuaThanhToan;
    private List<Hoadonmodel> data;

    public Thongkemodel(String apartmentID, String fund, String month, String tongTienNo, String tongSoHoaDon, String tongSoHoaDonThanhToan, String tongSoTienDaThanhToan, String tongSoTienChuaThanhToan, List<Hoadonmodel> data) {
        ApartmentID = apartmentID;
        Fund = fund;
        Month = month;
        TongTienNo = tongTienNo;
        TongSoHoaDon = tongSoHoaDon;
        TongSoHoaDonThanhToan = tongSoHoaDonThanhToan;
        TongSoTienDaThanhToan = tongSoTienDaThanhToan;
        TongSoTienChuaThanhToan = tongSoTienChuaThanhToan;
        this.data = data;
    }

    public String getApartmentID() {
        return ApartmentID;
    }

    public void setApartmentID(String apartmentID) {
        ApartmentID = apartmentID;
    }

    public String getFund() {
        return Fund;
    }

    public void setFund(String fund) {
        Fund = fund;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getTongTienNo() {
        return TongTienNo;
    }

    public void setTongTienNo(String tongTienNo) {
        TongTienNo = tongTienNo;
    }

    public String getTongSoHoaDon() {
        return TongSoHoaDon;
    }

    public void setTongSoHoaDon(String tongSoHoaDon) {
        TongSoHoaDon = tongSoHoaDon;
    }

    public String getTongSoHoaDonThanhToan() {
        return TongSoHoaDonThanhToan;
    }

    public void setTongSoHoaDonThanhToan(String tongSoHoaDonThanhToan) {
        TongSoHoaDonThanhToan = tongSoHoaDonThanhToan;
    }

    public String getTongSoTienDaThanhToan() {
        return TongSoTienDaThanhToan;
    }

    public void setTongSoTienDaThanhToan(String tongSoTienDaThanhToan) {
        TongSoTienDaThanhToan = tongSoTienDaThanhToan;
    }

    public String getTongSoTienChuaThanhToan() {
        return TongSoTienChuaThanhToan;
    }

    public void setTongSoTienChuaThanhToan(String tongSoTienChuaThanhToan) {
        TongSoTienChuaThanhToan = tongSoTienChuaThanhToan;
    }

    public List<Hoadonmodel> getData() {
        return data;
    }

    public void setData(List<Hoadonmodel> data) {
        this.data = data;
    }
}
