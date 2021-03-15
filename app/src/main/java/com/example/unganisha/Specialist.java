package com.example.unganisha;

public class Specialist {
    public String fname, lname, phone, email, region, profile, profession, bio;

    Specialist() {
    }

    Specialist(String fName, String lName, String phone, String email, String region, String profession, String bio) {
        this.fname = fName;
        this.lname = lName;
        this.phone = phone;
        this.email = email;
        this.region = region;
        this.profession = profession;
        this.bio = bio;
        this.profile = "specialist";
    }
}
