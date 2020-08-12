
package com.alpha.smartpickuser.bookPkg.bookingPkgmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalculatAmount {

    @SerializedName("price_per_distance")
    @Expose
    private Float pricePerDistance;
    @SerializedName("commission_amount")
    @Expose
    private String commissionAmount;
    @SerializedName("coupon_amount")
    @Expose
    private String couponAmount;
    @SerializedName("total")
    @Expose
    private Float total;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CalculatAmount() {
    }

    /**
     * 
     * @param couponAmount
     * @param total
     * @param pricePerDistance
     * @param commissionAmount
     */
    public CalculatAmount(Float pricePerDistance, String commissionAmount, String couponAmount, Float total) {
        super();
        this.pricePerDistance = pricePerDistance;
        this.commissionAmount = commissionAmount;
        this.couponAmount = couponAmount;
        this.total = total;
    }

    public Float getPricePerDistance() {
        return pricePerDistance;
    }

    public void setPricePerDistance(Float pricePerDistance) {
        this.pricePerDistance = pricePerDistance;
    }

    public String getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(String commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public String getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

}
