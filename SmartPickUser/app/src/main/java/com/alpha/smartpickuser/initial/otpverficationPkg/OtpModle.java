
package com.alpha.smartpickuser.initial.otpverficationPkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpModle {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     * 
     */
    public OtpModle() {
    }

    /**
     * 
     * @param data
     * @param message
     * @param status
     */
    public OtpModle(Boolean status, Data data, String message) {
        super();
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
