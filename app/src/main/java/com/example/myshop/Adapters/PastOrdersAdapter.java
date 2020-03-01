package com.example.myshop.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myshop.Objects.OrderObject;
import com.example.myshop.R;
import com.example.myshop.UtilityClass;

import java.util.ArrayList;
import java.util.Map;

public class PastOrdersAdapter extends BaseAdapter {
    private ArrayList<OrderObject> list;
    Context context;

    public PastOrdersAdapter(ArrayList<OrderObject> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.list_pastorder,viewGroup,false);
        }

        TextView shop,items,price,otp,orderTime;
        shop=view.findViewById(R.id.item_name);
        items=view.findViewById(R.id.item_quantity);
        price=view.findViewById(R.id.item_amount);
        otp=view.findViewById(R.id.item_OTP);
        orderTime=view.findViewById(R.id.item_orderTime);

        OrderObject current=list.get(i);
        shop.setText(current.getShopName());
        StringBuilder builder=new StringBuilder();
        for(Map.Entry j:current.getCart().entrySet()){
            builder.append(j.getKey().toString()).append(" (");
            builder.append(j.getValue().toString()).append("),");
        }
        items.setText(builder.toString());
        price.setText(String.valueOf(current.getAmount()));
        orderTime.setText(UtilityClass.getDate(current.getTime()));
        if(current.getStatus()!=2){
            otp.setText(String.valueOf(current.getOtp()));
        }else{
            otp.setText("OTP expired");
        }
        return view;
    }

}
