package com.example.myshop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
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

public class itemExpandableListAdapter extends BaseExpandableListAdapter {

    ArrayList<String> groupName;
    HashMap<String,ArrayList<itemObject>>  groupItems;
    HashMap<String,Integer> cart;
    Context context;

    public itemExpandableListAdapter(ArrayList<String> groupName, HashMap<String, ArrayList<itemObject>> groupItems, HashMap<String, Integer> cart, Context context) {
        this.groupName = groupName;
        this.groupItems = groupItems;
        this.cart = cart;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return groupName.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return groupItems.get(groupName.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return groupName.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return groupItems.get(groupName.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.item_parent_list_todo,viewGroup,false);
        }

        TextView tv=view.findViewById(R.id.itemExpandableTextView);
        tv.setText(groupName.get(i));
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final int[] quant = {0};
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.item_todo,viewGroup,false);
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

        final itemObject current=groupItems.get(groupName.get(i)).get(i1);

        name.setText(current.getName());
        stockSize.setText(String.valueOf(current.getQuantity()));
        DOM.setText(UtilityClass.getDate(current.getTimestamp()));
        price.setText(String.valueOf(current.getPrice()));

        try{
            quant[0]=cart.get(current.getIid());
            if(quant[0]>0){
                qty.setText(String.valueOf(quant[0]));
                addLayout.setVisibility(View.VISIBLE);
                addBtn.setVisibility(View.GONE);
            }else{
                addLayout.setVisibility(View.GONE);
                addBtn.setVisibility(View.VISIBLE);
            }
            notifyDataSetChanged();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
//        updateCart(current.getIid(),quant[0]);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quant[0]+=1;
                updateCart(current.getIid(),quant[0]);
                qty.setText(String.valueOf(quant[0]));
                addLayout.setVisibility(View.VISIBLE);
                addBtn.setVisibility(View.GONE);
                notifyDataSetChanged();
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(quant[0]+1>10){
                   Toast.makeText(context, "Cart is full for this item", Toast.LENGTH_SHORT).show();
               }else{
                   quant[0]+=1;
                   updateCart(current.getIid(),quant[0]);
                   qty.setText(String.valueOf(quant[0]));
               }
               notifyDataSetChanged();
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if (quant[0]-1<1){
                  quant[0]=0;
                  updateCart(current.getIid(),0);
                  qty.setText("0");
                  addLayout.setVisibility(View.GONE);
                  addBtn.setVisibility(View.VISIBLE);
              }else{
                  quant[0]--;
                  updateCart(current.getIid(),quant[0]);
                  qty.setText(String.valueOf(quant[0]));

              }
              notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private void updateCart(String iid, int i) {

        if(i==0){
            cart.remove(iid);
        }
        else{
            cart.put(iid,i);
        }
        ItemActivity.updateUi();
    }
}
