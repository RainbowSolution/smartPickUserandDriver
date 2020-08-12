
package com.alpha.smartpickuser.initial.otpverficationPkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("Firebase_token")
    @Expose
    private String firebaseToken;
    @SerializedName("otp")
    @Expose
    private String otp;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param image
     * @param address
     * @param phoneNumber
     * @param firebaseToken
     * @param gender
     * @param dob
     * @param otp
     * @param id
     * @param username
     * @param status
     */
    public Data(String id, String username, String dob, String gender, String address, String image, String phoneNumber, String status, String firebaseToken, String otp) {
        super();
        this.id = id;
        this.username = username;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.image = image;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.firebaseToken = firebaseToken;
        this.otp = otp;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}
