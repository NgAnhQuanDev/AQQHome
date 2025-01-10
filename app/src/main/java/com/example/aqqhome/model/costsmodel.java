package com.example.aqqhome.model;

public class costsmodel {
    private String IsManagerServiceUsed;
    private String IsGarbageServiceUsed;
    private String NumberOfParkingCardsUsed;

    private String IsOtherServiceUsed;
    private String ManagementFee;
    private String GarbageFee;

    private String Guixe;
    private String Khac;
    private String ApartmentID;
    private boolean success;

    public costsmodel(String isManagerServiceUsed, String isGarbageServiceUsed, String numberOfParkingCardsUsed, String isOtherServiceUsed, String managementFee, String garbageFee, String guixe, String khac, String apartmentID, boolean success) {
        IsManagerServiceUsed = isManagerServiceUsed;
        IsGarbageServiceUsed = isGarbageServiceUsed;
        NumberOfParkingCardsUsed = numberOfParkingCardsUsed;
        IsOtherServiceUsed = isOtherServiceUsed;
        ManagementFee = managementFee;
        GarbageFee = garbageFee;
        Guixe = guixe;
        Khac = khac;
        ApartmentID = apartmentID;
        this.success = success;
    }

    public String getIsManagerServiceUsed() {
        return IsManagerServiceUsed;
    }

    public void setIsManagerServiceUsed(String isManagerServiceUsed) {
        IsManagerServiceUsed = isManagerServiceUsed;
    }

    public String getIsGarbageServiceUsed() {
        return IsGarbageServiceUsed;
    }

    public void setIsGarbageServiceUsed(String isGarbageServiceUsed) {
        IsGarbageServiceUsed = isGarbageServiceUsed;
    }

    public String getNumberOfParkingCardsUsed() {
        return NumberOfParkingCardsUsed;
    }

    public void setNumberOfParkingCardsUsed(String numberOfParkingCardsUsed) {
        NumberOfParkingCardsUsed = numberOfParkingCardsUsed;
    }

    public String getIsOtherServiceUsed() {
        return IsOtherServiceUsed;
    }

    public void setIsOtherServiceUsed(String isOtherServiceUsed) {
        IsOtherServiceUsed = isOtherServiceUsed;
    }

    public String getManagementFee() {
        return ManagementFee;
    }

    public void setManagementFee(String managementFee) {
        ManagementFee = managementFee;
    }

    public String getGarbageFee() {
        return GarbageFee;
    }

    public void setGarbageFee(String garbageFee) {
        GarbageFee = garbageFee;
    }

    public String getGuixe() {
        return Guixe;
    }

    public void setGuixe(String guixe) {
        Guixe = guixe;
    }

    public String getKhac() {
        return Khac;
    }

    public void setKhac(String khac) {
        Khac = khac;
    }

    public String getApartmentID() {
        return ApartmentID;
    }

    public void setApartmentID(String apartmentID) {
        ApartmentID = apartmentID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
