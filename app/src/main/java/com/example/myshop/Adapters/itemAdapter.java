package com.example.myshop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshop.Activity.ItemActivity;
import com.example.myshop.Objects.itemObject;
import com.example.myshop.R;
import com.example.myshop.UtilityClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class itemAdapter extends BaseAdapter {
    ArrayList<itemObject> list;
    Context context;

    public itemAdapter(ArrayList<itemObject> list, Context context) {
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
        Boolean isOpen=false;
        final int[] quant = {0};
        if(view==null){
            view=LayoutInflater.from(context).inflate(R.layout.item_todo,viewGroup,false);
        }
        final TextView name,stockSize,DOM,addBtn,minus,plus,qty,price;
        final LinearLayout addLayout=view.findViewById(R.id.item_todo_addLayout);
        ImageView img=view.findViewById(R.id.item_todo_img);
        name=view.findViewById(R.id.item_todo_name);
        stockSize=view.findViewById(R.id.item_todo_quantity);
        qty=view.findViewById(R.id.item_todo_qty);
        DOM=view.findViewById(R.id.item_todo_DOM);
        addBtn=view.findViewById(R.id.item_todo_addBtn);
        plus=view.findViewById(R.id.item_todo_sumBtn);
        minus=view.findViewById(R.id.item_todo_subBtn);
        price=view.findViewById(R.id.item_todo_price);

        final itemObject current=list.get(i);
        name.setText(current.getName());
        stockSize.setText(String.valueOf(current.getQuantity()));
        DOM.setText(UtilityClass.getDate(current.getTimestamp()));
        price.setText(String.valueOf(current.getPrice()));

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBtn.setVisibility(View.GONE);
                addLayout.setVisibility(View.VISIBLE);
                quant[0]++;
                updateCart(current.getIid(),quant[0]);
                qty.setText(String.valueOf(1));
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quant[0]+1>10){
                    Toast.makeText(context, "Cart Exceed its Limit", Toast.LENGTH_SHORT).show();
                }else{
                    quant[0]++;
                    updateCart(current.getIid(),quant[0]);
                    qty.setText(String.valueOf(quant[0]));
                }
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quant[0]==1){
                    quant[0]--;
                    updateCart(current.getIid(),quant[0]);
                    qty.setText("0");
                    addBtn.setVisibility(View.VISIBLE);
                    addLayout.setVisibility(View.GONE);
                }else{
                    quant[0]--;
                    updateCart(current.getIid(),quant[0]);
                    qty.setText(String.valueOf(quant[0]));
                }
            }
        });

        return view;
    }

    private void updateCart(String iid, int i) {
        HashMap<String,Integer> map=ItemActivity.cart;
        if(i==0){
            map.remove(iid);
        }
        else{
            map.put(iid,i);
        }
        ItemActivity.updateUi();
    }
}
