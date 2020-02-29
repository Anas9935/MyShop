package com.example.myshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Pair;
import android.widget.TextView;

import com.example.myshop.Objects.itemObject;
import com.example.myshop.R;
import com.example.myshop.UtilityClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BillActivity extends AppCompatActivity {

    HashMap<String,Integer> cart;
    int payTypeInt,otpInt;
    TextView itemList,amtList,total,delCharges,grandTotal,otp,payType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        cart=(HashMap<String, Integer>) getIntent().getSerializableExtra("cart");
        payTypeInt=getIntent().getIntExtra("payType",-1);
        otpInt=getIntent().getIntExtra("otp", 0);

        initialiseViews();
        getItemDetails();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void getItemDetails() {
        StringBuilder builder=new StringBuilder();
        StringBuilder amtBuilder=new StringBuilder();
        float totalAmt=0;
        for(Map.Entry i:cart.entrySet()){
            Pair<String,Float> itemDetail=getItemDetail(i.getKey().toString());
            String itemName=itemDetail.first;
            float itemPrice=itemDetail.second;
            int quant=Integer.valueOf(i.getValue().toString());
            float totAmtOfItem=itemPrice*quant;
            totalAmt+=totAmtOfItem;
            builder.append(itemName);
            builder.append(" (").append(i.getValue().toString());
            builder.append(")\n");
            amtBuilder.append("₹");
            amtBuilder.append(totAmtOfItem);
            amtBuilder.append("\n");
        }
        //getting delivery
        float delivery=0;
        if(totalAmt<1000){
            delivery=50;
        }else if(totalAmt>1000&&totalAmt<3000){
            delivery=100;
        }else{
            delivery=150;
        }

        //calculating grand total
        float gTotal=delivery+totalAmt;
        //updating ui

        itemList.setText(builder.toString());
        amtList.setText(amtBuilder.toString());
        total.setText(new StringBuilder().append("₹").append(totalAmt));
        delCharges.setText(new StringBuilder().append("₹").append(delivery));
        grandTotal.setText(new StringBuilder().append("₹").append(gTotal));
        if(payTypeInt==0){
            payType.setText("Cash on Delivery");
        }else{
            payType.setText("Credit card");
        }
        if(otpInt!=0){
            otp.setText(String.valueOf(otpInt));
        }else {
            otp.setText("00000");
        }
    }

    private Pair<String, Float> getItemDetail(String key) {
        ArrayList<itemObject> list= UtilityClass.getList();
        Pair<String,Float> pair=null;
        for(itemObject i:list){
            if(i.getIid().equals(key)){
                pair=new Pair<>(i.getName(),i.getPrice());
                return pair;
            }
        }
        return pair;
    }

    private void initialiseViews() {
        itemList=findViewById(R.id.bill_item_list);
        amtList=findViewById(R.id.bill_amt_list);
        total=findViewById(R.id.bill_total);
        delCharges=findViewById(R.id.bill_delCharges);
        grandTotal=findViewById(R.id.bill_grandTotal);
        otp=findViewById(R.id.bill_otpgen);
        payType=findViewById(R.id.bill_payType);
    }
}
