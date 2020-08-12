
package com.alpha.smartpickuser.notificationPkg.getNotificationListPkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("notification_id")
    @Expose
    private String notificationId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("booking_data")
    @Expose
    private BookingData bookingData;
    @SerializedName("user_data")
    @Expose
    private UserData userData;
    @SerializedName("vehicle_number")
    @Expose
    private String vehicleNumber;
    @SerializedName("driver_image")
    @Expose
    private String driverImage;
    @SerializedName("driver_name")
    @Expose
    private String driverName;
    @SerializedName("driver_username")
    @Expose
    private String driverUsername;
    @SerializedName("driver_number")
    @Expose
    private String driverNumber;
    @SerializedName("read_status")
    @Expose
    private String readStatus;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Datum() {
    }

    /**
     * 
     * @param dateTime
     * @param readStatus
     * @param userData
     * @param driverUsername
     * @param driverImage
     * @param title
     * @param message
     * @param userId
     * @param driverNumber
     * @param vehicleNumber
     * @param notificationId
     * @param driverName
     * @param bookingData
     */
    public Datum(String notificationId, String userId, String title, String message, String dateTime, BookingData bookingData, UserData userData, String vehicleNumber, String driverImage, String driverName, String driverUsername, String driverNumber, String readStatus,String time) {
        super();
        this.notificationId = notificationId;
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.dateTime = dateTime;
        this.bookingData = bookingData;
        this.userData = userData;
        this.vehicleNumber = vehicleNumber;
        this.driverImage = driverImage;
        this.driverName = driverName;
        this.driverUsername = driverUsername;
        this.driverNumber = driverNumber;
        this.readStatus = readStatus;
        this.time = time;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public BookingData getBookingData() {
        return bookingData;
    }

    public void setBookingData(BookingData bookingData) {
        this.bookingData = bookingData;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getDriverImage() {
        return driverImage;
    }

    public void setDriverImage(String driverImage) {
        this.driverImage = driverImage;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverUsername() {
        return driverUsername;
    }

    public void setDriverUsername(String driverUsername) {
        this.driverUsername = driverUsername;
    }

    public String getDriverNumber() {
        return driverNumber;
    }

    public void setDriverNumber(String driverNumber) {
        this.driverNumber = driverNumber;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
