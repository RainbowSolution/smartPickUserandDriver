
package com.alpha.smartpick.driver.homeFragmentPkg.getbookingPkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private String id;
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
    @SerializedName("floor")
    @Expose
    private Object floor;
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
    @SerializedName("attachment_image")
    @Expose
    private Object attachmentImage;
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
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("driver_id")
    @Expose
    private String driverId;
    @SerializedName("driver_location")
    @Expose
    private String driverLocation;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("is_status")
    @Expose
    private String isStatus;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("time")
    @Expose
    private String time;
    /**
     * No args constructor for use in serialization
     * 
     */
    public Datum() {
    }

    /**
     * 
     * @param distance
     * @param pickLocation
     * @param isStatus
     * @param receiverMobilenumber
     * @param description
     * @param pickDate
     * @param paymentType
     * @param packagingRequired
     * @param attachmentImage
     * @param id
     * @param floor
     * @param landmark
     * @param vehicleType
     * @param pickDateTime
     * @param image
     * @param amount
     * @param driverLocation
     * @param pickTime
     * @param receiverName
     * @param dropLocation
     * @param userId
     * @param liftFacility
     * @param phoneNumber
     * @param driverId
     * @param stufName
     * @param status
     * @param username
     */
    public Datum(String id, String pickLocation, String dropLocation, String vehicleType, Object pickTime, Object pickDate, String stufName, Object floor, String packagingRequired, String amount, String paymentType, String distance, Object attachmentImage, String receiverName, String receiverMobilenumber, String liftFacility, String landmark, String description, String pickDateTime, String userId, String driverId, String driverLocation, String status, String isStatus, String username, String phoneNumber, String image,String time) {
        super();
        this.id = id;
        this.pickLocation = pickLocation;
        this.dropLocation = dropLocation;
        this.vehicleType = vehicleType;
        this.pickTime = pickTime;
        this.pickDate = pickDate;
        this.stufName = stufName;
        this.floor = floor;
        this.packagingRequired = packagingRequired;
        this.amount = amount;
        this.paymentType = paymentType;
        this.distance = distance;
        this.attachmentImage = attachmentImage;
        this.receiverName = receiverName;
        this.receiverMobilenumber = receiverMobilenumber;
        this.liftFacility = liftFacility;
        this.landmark = landmark;
        this.description = description;
        this.pickDateTime = pickDateTime;
        this.userId = userId;
        this.driverId = driverId;
        this.driverLocation = driverLocation;
        this.status = status;
        this.isStatus = isStatus;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Object getFloor() {
        return floor;
    }

    public void setFloor(Object floor) {
        this.floor = floor;
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

    public Object getAttachmentImage() {
        return attachmentImage;
    }

    public void setAttachmentImage(Object attachmentImage) {
        this.attachmentImage = attachmentImage;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverLocation() {
        return driverLocation;
    }

    public void setDriverLocation(String driverLocation) {
        this.driverLocation = driverLocation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(String isStatus) {
        this.isStatus = isStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
