
package com.alpha.smartpickuser.bookPkg.bookingPkgmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingAmountModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("calculat_amount")
    @Expose
    private CalculatAmount calculatAmount;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BookingAmountModel() {
    }

    /**
     * 
     * @param calculatAmount
     * @param message
     * @param status
     */
    public BookingAmountModel(Boolean status, String message, CalculatAmount calculatAmount) {
        super();
        this.status = status;
        this.message = message;
        this.calculatAmount = calculatAmount;
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

    public CalculatAmount getCalculatAmount() {
        return calculatAmount;
    }

    public void setCalculatAmount(CalculatAmount calculatAmount) {
        this.calculatAmount = calculatAmount;
    }

}
