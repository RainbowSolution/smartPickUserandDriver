
package com.alpha.smartpickuser.bookPkg.bookingPkgmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllActivities {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("pick_location")
    @Expose
    private String pickLocation;
    @SerializedName("drop_location")
    @Expose
    private String dropLocation;
    @SerializedName("vehicle_type")
    @Expose
    private String vehicleType;
    @SerializedName("pick_time")
    @Expose
    private Object pickTime;
    @SerializedName("pick_date")
    @Expose
    private Object pickDate;
    @SerializedName("stuf_name")
    @Expose
    private String stufName;
    @SerializedName("packaging_required")
    @Expose
    private String packagingRequired;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("receiver_name")
    @Expose
    private String receiverName;
    @SerializedName("receiver_mobilenumber")
    @Expose
    private String receiverMobilenumber;
    @SerializedName("lift_facility")
    @Expose
    private String liftFacility;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("pick_date_time")
    @Expose
    private String pickDateTime;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AllActivities() {
    }

    /**
     * 
     * @param pickDateTime
     * @param amount
     * @param pickTime
     * @param distance
     * @param pickLocation
     * @param receiverName
     * @param receiverMobilenumber
     * @param description
     * @param dropLocation
     * @param pickDate
     * @param paymentType
     * @param packagingRequired
     * @param liftFacility
     * @param id
     * @param landmark
     * @param vehicleType
     * @param stufName
     */
    public AllActivities(Integer id, String pickLocation, String dropLocation, String vehicleType, Object pickTime, Object pickDate, String stufName, String packagingRequired, String amount, String paymentType, String distance, String receiverName, String receiverMobilenumber, String liftFacility, String landmark, String description, String pickDateTime) {
        super();
        this.id = id;
        this.pickLocation = pickLocation;
        this.dropLocation = dropLocation;
        this.vehicleType = vehicleType;
        this.pickTime = pickTime;
        this.pickDate = pickDate;
        this.stufName = stufName;
        this.packagingRequired = packagingRequired;
        this.amount = amount;
        this.paymentType = paymentType;
        this.distance = distance;
        this.receiverName = receiverName;
        this.receiverMobilenumber = receiverMobilenumber;
        this.liftFacility = liftFacility;
        this.landmark = landmark;
        this.description = description;
        this.pickDateTime = pickDateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPickLocation() {
        return pickLocation;
    }

    public void setPickLocation(String pickLocation) {
        this.pickLocation = pickLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Object getPickTime() {
        return pickTime;
    }

    public void setPickTime(Object pickTime) {
        this.pickTime = pickTime;
    }

    public Object getPickDate() {
        return pickDate;
    }

    public void setPickDate(Object pickDate) {
        this.pickDate = pickDate;
    }

    public String getStufName() {
        return stufName;
    }

    public void setStufName(String stufName) {
        this.stufName = stufName;
    }

    public String getPackagingRequired() {
        return packagingRequired;
    }

    public void setPackagingRequired(String packagingRequired) {
        this.packagingRequired = packagingRequired;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobilenumber() {
        return receiverMobilenumber;
    }

    public void setReceiverMobilenumber(String receiverMobilenumber) {
        this.receiverMobilenumber = receiverMobilenumber;
    }

    public String getLiftFacility() {
        return liftFacility;
    }

    public void setLiftFacility(String liftFacility) {
        this.liftFacility = liftFacility;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPickDateTime() {
        return pickDateTime;
    }

    public void setPickDateTime(String pickDateTime) {
        this.pickDateTime = pickDateTime;
    }

}
