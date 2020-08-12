package com.alpha.smartpick.driver.passengerPkg;

public class LocationItem {

    String id;
    String booking_id;
    String droplocation;
    String reciver_name;
    String reciver_number;
    String is_booking_verify;

    public String getDroplocation() {
        return droplocation;
    }

    public void setDroplocation(String droplocation) {
        this.droplocation = droplocation;
    }

    public String getReciver_name() {
        return reciver_name;
    }

    public void setReciver_name(String reciver_name) {
        this.reciver_name = reciver_name;
    }

    public String getReciver_number() {
        return reciver_number;
    }

    public void setReciver_number(String reciver_number) {
        this.reciver_number = reciver_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getIs_booking_verify() {
        return is_booking_verify;
    }

    public void setIs_booking_verify(String is_booking_verify) {
        this.is_booking_verify = is_booking_verify;
    }
}