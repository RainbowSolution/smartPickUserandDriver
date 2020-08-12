package com.alpha.smartpickuser.droplocationAddFragmentPkg;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class PlaceModel implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "product_id")
    private String product_id;

    @ColumnInfo(name = "droplocation")
    private String droplocation;

    @ColumnInfo(name = "receivername")
    private String receivername;

    @ColumnInfo(name = "receivernumber")
    private String receivernumber;

    @ColumnInfo(name = "latitude")
    private Double latitude;

    @ColumnInfo(name = "longitude")
    private Double longitude;

    @ColumnInfo(name = "is_booking_verify")
    private String is_booking_verify;


    @ColumnInfo(name = "finished")
    private boolean finished;

    /*
     * Getters and Setters
     * */

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDroplocation() {
        return droplocation;
    }

    public void setDroplocation(String droplocation) {
        this.droplocation = droplocation;
    }

    public String getReceivername() {
        return receivername;
    }

    public void setReceivername(String receivername) {
        this.receivername = receivername;
    }

    public String getReceivernumber() {
        return receivernumber;
    }

    public void setReceivernumber(String receivernumber) {
        this.receivernumber = receivernumber;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getIs_booking_verify() {
        return is_booking_verify;
    }

    public void setIs_booking_verify(String is_booking_verify) {
        this.is_booking_verify = is_booking_verify;
    }
}
