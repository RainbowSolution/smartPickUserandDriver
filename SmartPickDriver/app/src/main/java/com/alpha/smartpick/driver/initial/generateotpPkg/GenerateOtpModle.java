
package com.alpha.smartpick.driver.initial.generateotpPkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerateOtpModle {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("otp")
    @Expose
    private Integer otp;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GenerateOtpModle() {
    }

    /**
     * 
     * @param phoneNumber
     * @param otp
     * @param message
     * @param status
     */
    public GenerateOtpModle(Boolean status, String message, Integer otp, String phoneNumber) {
        super();
        this.status = status;
        this.message = message;
        this.otp = otp;
        this.phoneNumber = phoneNumber;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
