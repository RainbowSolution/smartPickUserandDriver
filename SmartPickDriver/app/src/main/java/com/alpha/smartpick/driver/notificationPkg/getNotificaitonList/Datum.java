
package com.alpha.smartpick.driver.notificationPkg.getNotificaitonList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("notification_id")
    @Expose
    private String notificationId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("patients_data")
    @Expose
    private PatientsData patientsData;
    @SerializedName("vehicle_type")
    @Expose
    private String vehicleType;
    @SerializedName("driver_id")
    @Expose
    private String driverId;
    @SerializedName("driver_data")
    @Expose
    private DriverData driverData;
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("booking_data")
    @Expose
    private BookingData bookingData;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("read_status")
    @Expose
    private String readStatus;
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
     * @param dateTime
     * @param readStatus
     * @param title
     * @param message
     * @param userId
     * @param bookingId
     * @param driverId
     * @param driverData
     * @param notificationId
     * @param time
     * @param patientsData
     * @param vehicleType
     * @param bookingData
     */
    public Datum(String notificationId, String userId, PatientsData patientsData, String vehicleType, String driverId, DriverData driverData, String bookingId, BookingData bookingData, String title, String message, String dateTime, String readStatus, String time) {
        super();
        this.notificationId = notificationId;
        this.userId = userId;
        this.patientsData = patientsData;
        this.vehicleType = vehicleType;
        this.driverId = driverId;
        this.driverData = driverData;
        this.bookingId = bookingId;
        this.bookingData = bookingData;
        this.title = title;
        this.message = message;
        this.dateTime = dateTime;
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

    public PatientsData getPatientsData() {
        return patientsData;
    }

    public void setPatientsData(PatientsData patientsData) {
        this.patientsData = patientsData;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public DriverData getDriverData() {
        return driverData;
    }

    public void setDriverData(DriverData driverData) {
        this.driverData = driverData;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public BookingData getBookingData() {
        return bookingData;
    }

    public void setBookingData(BookingData bookingData) {
        this.bookingData = bookingData;
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
