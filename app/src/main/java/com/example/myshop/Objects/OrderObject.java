package com.example.myshop.Objects;

import java.util.HashMap;

public class OrderObject implements Comparable<OrderObject> {
    private String oid;
    private String sid;
    private HashMap<String,Integer> cart;
    private float amount;
    private int TOP;
    private long time;
    private int otp;
    private int status;
    private String shopName;


    public OrderObject(String oid, String sid, HashMap<String, Integer> cart, float amount, int TOP, long time, int otp, int status, String shopName) {
        this.oid = oid;
        this.sid=sid;
        this.cart = cart;
        this.amount = amount;
        this.TOP = TOP;
        this.time = time;
        this.otp = otp;
        this.status = status;
        this.shopName = shopName;
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


    public HashMap<String, Integer> getCart() {
        return cart;
    }

    public void setCart(HashMap<String, Integer> cart) {
        this.cart = cart;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getTOP() {
        return TOP;
    }

    public void setTOP(int TOP) {
        this.TOP = TOP;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    @Override
    public int compareTo(OrderObject orderObject) {
        if(time>orderObject.time){
            return 1;
        }else if(time<orderObject.time){
            return -1;
        }else{
            return 0;
        }
    }
}
