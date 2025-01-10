package com.example.aqqhome.model;

import java.util.List;

public class Hoadongroupmodel {
    public String Nam;
    public String Thang;
    public List<Hoadonmodel> Hoadons;

    public Hoadongroupmodel(String nam, String thang, List<Hoadonmodel> hoadons) {
        Nam = nam;
        Thang = thang;
        Hoadons = hoadons;
    }

    public String getNam() {
        return Nam;
    }

    public void setNam(String nam) {
        Nam = nam;
    }

    public String getThang() {
        return Thang;
    }

    public void setThang(String thang) {
        Thang = thang;
    }

    public List<Hoadonmodel> getHoadons() {
        return Hoadons;
    }

    public void setHoadons(List<Hoadonmodel> hoadons) {
        Hoadons = hoadons;
    }
}
