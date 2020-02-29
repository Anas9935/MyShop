package com.example.myshop.Objects;

public class ShopObject {
    private String sid;
    private String shopName;
    private String locality;
    private String city;
    private long pincode;
    private String email;
    private long contact;
    private float rating;

    public ShopObject(String sid, String shopName, String locality, String city, long pincode, String email, long contact, float rating) {
        this.sid = sid;
        this.shopName = shopName;
        this.locality = locality;
        this.city = city;
        this.pincode = pincode;
        this.email = email;
        this.contact = contact;
        this.rating = rating;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
