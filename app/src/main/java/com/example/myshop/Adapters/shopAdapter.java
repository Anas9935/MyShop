package com.example.myshop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.myshop.Objects.ShopObject;
import com.example.myshop.R;

import java.util.ArrayList;

public class shopAdapter extends BaseAdapter {
    ArrayList<ShopObject> list;
    Context context;

    public shopAdapter(ArrayList<ShopObject> list, Context context) {
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
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.shop_todo,viewGroup,false);
        }
        TextView name=view.findViewById(R.id.shop_todo_name);
        TextView address=view.findViewById(R.id.shop_todo_address);
        RatingBar ratingBar=view.findViewById(R.id.shop_todo_rating_bar);
        ImageView menuBtn=view.findViewById(R.id.shop_todo_menuBtn);

        ShopObject current=list.get(i);

        name.setText(current.getShopName());
        StringBuilder builder=new StringBuilder();
        builder.append(current.getLocality()).append(", ").append(current.getCity());
        builder.append(", ").append(String.valueOf(current.getPincode()));
        address.setText(builder.toString());

        ratingBar.setRating(current.getRating());
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

        return view;
    }
}
