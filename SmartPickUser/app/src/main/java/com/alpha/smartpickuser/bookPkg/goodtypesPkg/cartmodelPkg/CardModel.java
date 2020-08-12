
package com.alpha.smartpickuser.bookPkg.goodtypesPkg.cartmodelPkg;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("all_vehicle")
    @Expose
    private List<AllVehicle> allVehicle = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CardModel() {
    }

    /**
     * 
     * @param allVehicle
     * @param message
     * @param status
     */
    public CardModel(Boolean status, String message, List<AllVehicle> allVehicle) {
        super();
        this.status = status;
        this.message = message;
        this.allVehicle = allVehicle;
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

    public List<AllVehicle> getAllVehicle() {
        return allVehicle;
    }

    public void setAllVehicle(List<AllVehicle> allVehicle) {
        this.allVehicle = allVehicle;
    }

}
