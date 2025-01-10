package com.example.aqqhome.model;

public class SoLuongCongViecModel {
    private String c_congviecconhan;
    private String c_congviecsaphethan;
    private String c_congviecquahan;
    private String c_pheduyet;
    private String c_gopy0;
    private String c_gopy1;
    private boolean success;

    public SoLuongCongViecModel(String c_congviecconhan, String c_congviecsaphethan, String c_congviecquahan, String c_pheduyet, String c_gopy0, String c_gopy1, boolean success) {
        this.c_congviecconhan = c_congviecconhan;
        this.c_congviecsaphethan = c_congviecsaphethan;
        this.c_congviecquahan = c_congviecquahan;
        this.c_pheduyet = c_pheduyet;
        this.c_gopy0 = c_gopy0;
        this.c_gopy1 = c_gopy1;
        this.success = success;
    }

    public String getC_congviecconhan() {
        return c_congviecconhan;
    }

    public void setC_congviecconhan(String c_congviecconhan) {
        this.c_congviecconhan = c_congviecconhan;
    }

    public String getC_congviecsaphethan() {
        return c_congviecsaphethan;
    }

    public void setC_congviecsaphethan(String c_congviecsaphethan) {
        this.c_congviecsaphethan = c_congviecsaphethan;
    }

    public String getC_congviecquahan() {
        return c_congviecquahan;
    }

    public void setC_congviecquahan(String c_congviecquahan) {
        this.c_congviecquahan = c_congviecquahan;
    }

    public String getC_pheduyet() {
        return c_pheduyet;
    }

    public void setC_pheduyet(String c_pheduyet) {
        this.c_pheduyet = c_pheduyet;
    }

    public String getC_gopy0() {
        return c_gopy0;
    }

    public void setC_gopy0(String c_gopy0) {
        this.c_gopy0 = c_gopy0;
    }

    public String getC_gopy1() {
        return c_gopy1;
    }

    public void setC_gopy1(String c_gopy1) {
        this.c_gopy1 = c_gopy1;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
