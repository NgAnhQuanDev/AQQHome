package com.example.aqqhome.model;
public class CanHoModel {
    public String soCanHo;
    public String tenToaNha;

    public CanHoModel(String soCanHo, String tenToaNha) {
        this.soCanHo = soCanHo;
        this.tenToaNha = tenToaNha;
    }

    public String getSoCanHo() {
        return soCanHo;
    }

    public void setSoCanHo(String soCanHo) {
        this.soCanHo = soCanHo;
    }

    public String getTenToaNha() {
        return tenToaNha;
    }

    public void setTenToaNha(String tenToaNha) {
        this.tenToaNha = tenToaNha;
    }
}