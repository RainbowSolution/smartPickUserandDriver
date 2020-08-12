
package com.alpha.smartpick.driver.notificationPkg.getNotificaitonList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingData {

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
    private String pickTime;
    @SerializedName("pick_date")
    @Expose
    private String pickDate;
    @SerializedName("phonenum")
    @Expose
    private String phonenum;
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
    @SerializedName("attachment_image")
    @Expose
    private Object attachmentImage;
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
    @SerializedName("booking_date")
    @Expose
    private String bookingDate;
    @SerializedName("booking_time")
    @Expose
    private String bookingTime;
    @SerializedName("pick_up_letter")
    @Expose
    private String pickUpLetter;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("is_status")
    @Expose
    private String isStatus;
    @SerializedName("customer_name")
    @Expose
    private String customerName;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BookingData() {
    }

    /**
     * 
     * @param dateTime
     * @param distance
     * @param pickLocation
     * @param isStatus
     * @param description
     * @param pickDate
     * @param paymentType
     * @param packagingRequired
     * @param attachmentImage
     * @param pickUpLetter
     * @param landmark
     * @param vehicleType
     * @param pickDateTime
     * @param amount
     * @param driverLocation
     * @param bookingTime
     * @param pickTime
     * @param dropLocation
     * @param phonenum
     * @param userId
     * @param customerName
     * @param liftFacility
     * @param driverId
     * @param bookingDate
     * @param stufName
     */
    public BookingData(String pickLocation, String dropLocation, String vehicleType, String pickTime, String pickDate, String phonenum, String stufName, String packagingRequired, String amount, String paymentType, String distance, Object attachmentImage, String liftFacility, String landmark, String description, String pickDateTime, String userId, String driverId, String driverLocation, String bookingDate, String bookingTime, String pickUpLetter, String dateTime, String isStatus, String customerName) {
        super();
        this.pickLocation = pickLocation;
        this.dropLocation = dropLocation;
        this.vehicleType = vehicleType;
        this.pickTime = pickTime;
        this.pickDate = pickDate;
        this.phonenum = phonenum;
        this.stufName = stufName;
        this.packagingRequired = packagingRequired;
        this.amount = amount;
        this.paymentType = paymentType;
        this.distance = distance;
        this.attachmentImage = attachmentImage;
        this.liftFacility = liftFacility;
        this.landmark = landmark;
        this.description = description;
        this.pickDateTime = pickDateTime;
        this.userId = userId;
        this.driverId = driverId;
        this.driverLocation = driverLocation;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.pickUpLetter = pickUpLetter;
        this.dateTime = dateTime;
        this.isStatus = isStatus;
        this.customerName = customerName;
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

    public String getPickTime() {
        return pickTime;
    }

    public void setPickTime(String pickTime) {
        this.pickTime = pickTime;
    }

    public String getPickDate() {
        return pickDate;
    }

    public void setPickDate(String pickDate) {
        this.pickDate = pickDate;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
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

    public Object getAttachmentImage() {
        return attachmentImage;
    }

    public void setAttachmentImage(Object attachmentImage) {
        this.attachmentImage = attachmentImage;
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

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getPickUpLetter() {
        return pickUpLetter;
    }

    public void setPickUpLetter(String pickUpLetter) {
        this.pickUpLetter = pickUpLetter;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(String isStatus) {
        this.isStatus = isStatus;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

}
