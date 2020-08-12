
package com.alpha.smartpick.driver.notificationPkg.getNotificaitonList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriverData {

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
    @SerializedName("fees")
    @Expose
    private String fees;
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
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("postal_code")
    @Expose
    private String postalCode;
    @SerializedName("joning_date")
    @Expose
    private String joningDate;
    @SerializedName("assign_id")
    @Expose
    private String assignId;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("biography")
    @Expose
    private String biography;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("profession")
    @Expose
    private String profession;
    @SerializedName("exp")
    @Expose
    private String exp;
    @SerializedName("hospital_id")
    @Expose
    private String hospitalId;
    @SerializedName("Appointment_limit")
    @Expose
    private String appointmentLimit;
    @SerializedName("Firebase_token")
    @Expose
    private String firebaseToken;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longnitue")
    @Expose
    private String longnitue;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("Vehicle_Type")
    @Expose
    private String vehicleType;
    @SerializedName("vehicle_number")
    @Expose
    private String vehicleNumber;
    @SerializedName("available_staus")
    @Expose
    private String availableStaus;
    @SerializedName("online_status")
    @Expose
    private String onlineStatus;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DriverData() {
    }

    /**
     * 
     * @param lastName
     * @param country
     * @param fees
     * @param joningDate
     * @param education
     * @param gender
     * @param city
     * @param postalCode
     * @param latitude
     * @param onlineStatus
     * @param password
     * @param hospitalId
     * @param vehicleNumber
     * @param id
     * @param state
     * @param exp
     * @param longnitue
     * @param email
     * @param assignId
     * @param vehicleType
     * @param availableStaus
     * @param profession
     * @param image
     * @param appointmentLimit
     * @param address
     * @param otp
     * @param biography
     * @param deviceToken
     * @param firstName
     * @param phoneNumber
     * @param firebaseToken
     * @param dob
     * @param userType
     * @param username
     * @param status
     */
    public DriverData(String id, String firstName, String lastName, String username, String fees, String email, String password, String dob, String gender, String address, String country, String education, String city, String state, String postalCode, String joningDate, String assignId, String phoneNumber, String image, String biography, String status, String profession, String exp, String hospitalId, String appointmentLimit, String firebaseToken, String otp, String latitude, String longnitue, String userType, String vehicleType, String vehicleNumber, String availableStaus, String onlineStatus, String deviceToken) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.fees = fees;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.country = country;
        this.education = education;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.joningDate = joningDate;
        this.assignId = assignId;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.biography = biography;
        this.status = status;
        this.profession = profession;
        this.exp = exp;
        this.hospitalId = hospitalId;
        this.appointmentLimit = appointmentLimit;
        this.firebaseToken = firebaseToken;
        this.otp = otp;
        this.latitude = latitude;
        this.longnitue = longnitue;
        this.userType = userType;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.availableStaus = availableStaus;
        this.onlineStatus = onlineStatus;
        this.deviceToken = deviceToken;
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

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
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

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
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

    public String getJoningDate() {
        return joningDate;
    }

    public void setJoningDate(String joningDate) {
        this.joningDate = joningDate;
    }

    public String getAssignId() {
        return assignId;
    }

    public void setAssignId(String assignId) {
        this.assignId = assignId;
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

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getAppointmentLimit() {
        return appointmentLimit;
    }

    public void setAppointmentLimit(String appointmentLimit) {
        this.appointmentLimit = appointmentLimit;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongnitue() {
        return longnitue;
    }

    public void setLongnitue(String longnitue) {
        this.longnitue = longnitue;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    public String getAvailableStaus() {
        return availableStaus;
    }

    public void setAvailableStaus(String availableStaus) {
        this.availableStaus = availableStaus;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

}
