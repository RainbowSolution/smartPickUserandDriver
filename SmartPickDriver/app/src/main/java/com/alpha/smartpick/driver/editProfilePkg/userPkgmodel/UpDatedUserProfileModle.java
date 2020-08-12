
package com.alpha.smartpick.driver.editProfilePkg.userPkgmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpDatedUserProfileModle {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("all_activities")
    @Expose
    private AllActivities allActivities;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UpDatedUserProfileModle() {
    }

    /**
     * 
     * @param allActivities
     * @param message
     * @param status
     */
    public UpDatedUserProfileModle(Boolean status, String message, AllActivities allActivities) {
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

    public AllActivities getAllActivities() {
        return allActivities;
    }

    public void setAllActivities(AllActivities allActivities) {
        this.allActivities = allActivities;
    }

}
