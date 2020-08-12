
package com.alpha.smartpickuser.editProfilePkg.getuserPkgmodel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileModle {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("all_activities")
    @Expose
    private List<AllActivity> allActivities = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UserProfileModle() {
    }

    /**
     * 
     * @param allActivities
     * @param message
     * @param status
     */
    public UserProfileModle(Boolean status, String message, List<AllActivity> allActivities) {
        super();
        this.status = status;
        this.message = message;
        this.allActivities = allActivities;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AllActivity> getAllActivities() {
        return allActivities;
    }

    public void setAllActivities(List<AllActivity> allActivities) {
        this.allActivities = allActivities;
    }

}
