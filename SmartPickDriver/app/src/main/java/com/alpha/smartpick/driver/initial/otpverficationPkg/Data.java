
package com.alpha.smartpick.driver.initial.otpverficationPkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("Vehicle_Type")
    @Expose
    private String vehicleType;
    @SerializedName("vehicle_number")
    @Expose
    private String vehicleNumber;
    @SerializedName("Firebase_token")
    @Expose
    private String firebaseToken;
    @SerializedName("image")
    @Expose
    private String image;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param image
     * @param phoneNumber
     * @param firebaseToken
     * @param vehicleNumber
     * @param otp
     * @param id
     * @param vehicleType
     * @param username
     */
    public Data(String id, String username, String phoneNumber, String otp, String vehicleType, String vehicleNumber, String firebaseToken, String image) {
        super();
        this.id = id;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.otp = otp;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.firebaseToken = firebaseToken;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
