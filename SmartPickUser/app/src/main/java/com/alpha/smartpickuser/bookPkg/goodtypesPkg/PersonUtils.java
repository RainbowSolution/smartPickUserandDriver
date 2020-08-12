package com.alpha.smartpickuser.bookPkg.goodtypesPkg;

public class PersonUtils {

    private String Id;
    private String jobProfile;

    public PersonUtils(String id, String jobProfile) {
        Id = id;
        this.jobProfile = jobProfile;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getJobProfile() {
        return jobProfile;
    }

    public void setJobProfile(String jobProfile) {
        this.jobProfile = jobProfile;
    }
}
