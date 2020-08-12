
package com.alpha.smartpickuser.bookPkg.goodtypesPkg.cartmodelPkg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllVehicle {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("english_name")
    @Expose
    private String english_name;
    /**
     * No args constructor for use in serialization
     * 
     */
    public AllVehicle() {
    }

    /**
     * 
     * @param name
     * @param id
     */
    public AllVehicle(String id, String name,String english_name) {
        super();
        this.id = id;
        this.name = name;
        this.english_name = english_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglish_name() {
        return english_name;
    }

    public void setEnglish_name(String english_name) {
        this.english_name = english_name;
    }
}
