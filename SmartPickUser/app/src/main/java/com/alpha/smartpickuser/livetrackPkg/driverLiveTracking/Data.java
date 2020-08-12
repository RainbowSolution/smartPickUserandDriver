
package com.alpha.smartpickuser.livetrackPkg.driverLiveTracking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("driver_location")
    @Expose
    private String driverLocation;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param driverLocation
     */
    public Data(String driverLocation) {
        super();
        this.driverLocation = driverLocation;
    }

    public String getDriverLocation() {
        return driverLocation;
    }

    public void setDriverLocation(String driverLocation) {
        this.driverLocation = driverLocation;
    }

}
