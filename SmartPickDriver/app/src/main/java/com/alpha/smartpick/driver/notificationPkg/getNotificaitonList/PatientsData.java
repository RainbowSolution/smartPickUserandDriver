
package com.alpha.smartpick.driver.notificationPkg.getNotificaitonList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientsData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("disease")
    @Expose
    private String disease;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("postal_code")
    @Expose
    private String postalCode;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("vehicle_number")
    @Expose
    private String vehicleNumber;
    @SerializedName("image")
    @Expose
    private String image;
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
    public PatientsData() {
    }

    /**
     * 
     * @param lastName
     * @param country
     * @param image
     * @param disease
     * @param address
     * @param gender
     * @param city
     * @param postalCode
     * @param otp
     * @param firstName
     * @param password
     * @param phoneNumber
     * @param firebaseToken
     * @param dob
     * @param vehicleNumber
     * @param id
     * @param state
     * @param email
     * @param username
     * @param status
     */
    public PatientsData(String id, String firstName, String lastName, String username, String email, String password, String dob, String gender, String disease, String address, String country, String city, String state, String postalCode, String phoneNumber, String vehicleNumber, String image, String status, String firebaseToken, String otp) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.gender = gender;
        this.disease = disease;
        this.address = address;
        this.country = country;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.vehicleNumber = vehicleNumber;
        this.image = image;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
