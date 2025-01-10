package com.example.aqqhome.model;

public class Responsemodel {
    private String message;
    private boolean success;
    private String image_url;

    public Responsemodel(String message, boolean success, String image_url) {
        this.message = message;
        this.success = success;
        this.image_url = image_url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
