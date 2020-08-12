
package com.alpha.smartpick.driver.livetrackPkg.gerroutePkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DropLocation {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("latitude")
    @Expose
    private Float latitude;
    @SerializedName("longitude")
    @Expose
    private Float longitude;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DropLocation() {
    }

    /**
     * 
     * @param address
     * @param latitude
     * @param longitude
     */
    public DropLocation(String address, Float latitude, Float longitude) {
        super();
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

}
