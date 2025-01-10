package com.example.aqqhome.model;

import java.util.List;

public class TestModell {
    private List<String> NameApartment;

    public TestModell(List<String> nameApartment) {
        NameApartment = nameApartment;
    }

    public List<String> getNameApartment() {
        return NameApartment;
    }

    public void setNameApartment(List<String> nameApartment) {
        NameApartment = nameApartment;
    }
}
