
package com.alpha.smartpick.driver.homeFragmentPkg.getbookingPkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailableStatusModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private String data;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AvailableStatusModel() {
    }

    /**
     * 
     * @param data
     * @param message
     * @param status
     */
    public AvailableStatusModel(Boolean status, String message, String data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
