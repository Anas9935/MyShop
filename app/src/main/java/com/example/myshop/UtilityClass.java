package com.example.myshop;

import com.example.myshop.Objects.itemObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UtilityClass {

    public static String getDate(long timestamp){
        SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(new Date(timestamp));
    }
    public static ArrayList<itemObject> getList(){
        ArrayList<itemObject> itemList=new ArrayList<>();
        itemList.add(new itemObject("1234","Pulses",25,100.0f,11220542L,0,null));
        itemList.add(new itemObject("1235","Paste",65,120.0f,11220542L,0,null));
        itemList.add(new itemObject("1236","Rice",10,160.0f,11220542L,0,null));
        itemList.add(new itemObject("1237","Meat",54,170.0f,11220542L,0,null));
        itemList.add(new itemObject("1238","Sauce",5,90.0f,11220542L,0,null));
        itemList.add(new itemObject("1239","Vegies",80,80.0f,11220542L,0,null));
        itemList.add(new itemObject("1212","Speices",15,150.0f,11220542L,0,null));
        itemList.add(new itemObject("1233","Biscuits",26,140.0f,11220542L,0,null));
        itemList.add(new itemObject("1239","Milk",98,200.0f,11220542L,0,null));
        itemList.add(new itemObject("1245","Tea",1,260.0f,11220542L,0,null));
        return itemList;
    }

}
