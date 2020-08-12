
package com.alpha.smartpickuser.bookPkg.bookingPkgmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("old_amount")
    @Expose
    private String oldAmount;
    @SerializedName("new_amount")
    @Expose
    private String newAmount;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("coupon_id")
    @Expose
    private String couponId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param oldAmount
     * @param discount
     * @param newAmount
     * @param couponId
     */
    public Data(String oldAmount, String newAmount, String discount, String couponId) {
        super();
        this.oldAmount = oldAmount;
        this.newAmount = newAmount;
        this.discount = discount;
        this.couponId = couponId;
    }

    public String getOldAmount() {
        return oldAmount;
    }

    public void setOldAmount(String oldAmount) {
        this.oldAmount = oldAmount;
    }

    public String getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(String newAmount) {
        this.newAmount = newAmount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

}
