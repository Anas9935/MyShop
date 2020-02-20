package com.example.myshop.Objects;

public class itemObject {
    private String iid;
    private String name;
    private int quantity;
    private float price;
    private long timestamp;
    private int type;
    private String imgUrl;

    public itemObject(String iid, String name, int quantity, float price, long timestamp, int type, String imgUrl) {
        this.iid = iid;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = timestamp;
        this.type = type;
        this.imgUrl = imgUrl;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
