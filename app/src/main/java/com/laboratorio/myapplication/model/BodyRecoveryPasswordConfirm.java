package com.laboratorio.myapplication.model;

public class BodyRecoveryPasswordConfirm {
    private String email;
    private String code;
    private String newPassword;
    private String oldPassword;

    public BodyRecoveryPasswordConfirm() {

    }

    public BodyRecoveryPasswordConfirm(String email, String code, String newPassword, String oldPassword) {
        this.email = email;
        this.code = code;
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
