package com.example.aqqhome.model;

public class Congviecmodel {
    private String NoteID;
    private String Loai;
    private String noidung;
    private String ngayhethan;
    private String Ten;
    private String ApartmentID;

    public Congviecmodel(String noteID, String loai, String noidung, String ngayhethan, String ten, String apartmentID) {
        NoteID = noteID;
        Loai = loai;
        this.noidung = noidung;
        this.ngayhethan = ngayhethan;
        Ten = ten;
        ApartmentID = apartmentID;
    }

    public String getNoteID() {
        return NoteID;
    }

    public void setNoteID(String noteID) {
        NoteID = noteID;
    }

    public String getLoai() {
        return Loai;
    }

    public void setLoai(String loai) {
        Loai = loai;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getNgayhethan() {
        return ngayhethan;
    }

    public void setNgayhethan(String ngayhethan) {
        this.ngayhethan = ngayhethan;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getApartmentID() {
        return ApartmentID;
    }

    public void setApartmentID(String apartmentID) {
        ApartmentID = apartmentID;
    }
}
