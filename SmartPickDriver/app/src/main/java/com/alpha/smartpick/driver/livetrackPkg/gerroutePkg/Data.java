
package com.alpha.smartpick.driver.livetrackPkg.gerroutePkg;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("picup_location")
    @Expose
    private List<PicupLocation> picupLocation = null;
    @SerializedName("drop_location")
    @Expose
    private List<DropLocation> dropLocation = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param picupLocation
     * @param dropLocation
     */
    public Data(List<PicupLocation> picupLocation, List<DropLocation> dropLocation) {
        super();
        this.picupLocation = picupLocation;
        this.dropLocation = dropLocation;
    }

    public List<PicupLocation> getPicupLocation() {
        return picupLocation;
    }

    public void setPicupLocation(List<PicupLocation> picupLocation) {
        this.picupLocation = picupLocation;
    }

    public List<DropLocation> getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(List<DropLocation> dropLocation) {
        this.dropLocation = dropLocation;
    }

}
