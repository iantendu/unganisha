package com.example.unganisha;

import java.util.Date;

public class specialist_card {
    public String fname,lname,region,profession,profile,email,phone,bio;

    public specialist_card() {
    }

    public specialist_card(String fname, String lname, String region, String profession, String profile, String email, String phone, String bio) {
        this.fname = fname;
        this.lname = lname;
        this.region = region;
        this.profession = profession;
        this.profile = profile;
        this.email = email;
        this.phone = phone;
        this.bio = bio;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}


