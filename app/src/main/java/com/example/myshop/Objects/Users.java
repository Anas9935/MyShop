package com.example.myshop.Objects;

public class Users {
    private String uid;
    private String name;
    private String locality;
    private String city;
    private long pincode;
    private String email;
    private long contact1;
    private long contact2;

    public Users(String uid, String name, String locality, String city, long pincode, String email, long contact1, long contact2) {
        this.uid = uid;
        this.name = name;
        this.locality = locality;
        this.city = city;
        this.pincode = pincode;
        this.email = email;
        this.contact1 = contact1;
        this.contact2 = contact2;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getPincode() {
        return pincode;
    }

    public void setPincode(long pincode) {
        this.pincode = pincode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getContact1() {
        return contact1;
    }

    public void setContact1(long contact1) {
        this.contact1 = contact1;
    }

    public long getContact2() {
        return contact2;
    }

    public void setContact2(long contact2) {
        this.contact2 = contact2;
    }
}
