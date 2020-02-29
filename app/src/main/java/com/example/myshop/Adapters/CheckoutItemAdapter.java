package com.example.myshop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshop.Activity.CheckoutActivity;
import com.example.myshop.Objects.itemObject;
import com.example.myshop.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CheckoutItemAdapter extends BaseAdapter {

    ArrayList<itemObject> list;
    Context context;

    public CheckoutItemAdapter(ArrayList<itemObject> list, Context context) {
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
        final int[] quant={0};
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.checkout_item_todo,viewGroup,false);
        }
        TextView name=view.findViewById(R.id.checkout_todo_itemName);
        final TextView qty=view.findViewById(R.id.checkout_todo_itemQty);
        TextView plus=view.findViewById(R.id.checkout_todo_itemSum);
        TextView minus=view.findViewById(R.id.checkout_todo_itemSub);
        final TextView price=view.findViewById(R.id.checkout_todo_itemPrice);

        final itemObject current=list.get(i);
        final HashMap<String,Integer> map= CheckoutActivity.cart;
        name.setText(current.getName());
        final Integer quantity=map.get(current.getIid());
        qty.setText(String.valueOf(quantity));

        float totalPrice=current.getPrice();
        if(quantity!=null){
            price.setText(new StringBuilder().append("₹").append(totalPrice * quantity).toString());
            quant[0]=quantity;

        }
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quant[0] + 1 > 10) {
                    Toast.makeText(context, "Cart Exceed its Limit", Toast.LENGTH_SHORT).show();
                } else {
                    quant[0]++;
                    qty.setText(String.valueOf(quant[0]));
                    price.setText(new StringBuilder("₹").append(current.getPrice() * quant[0]));
                    CheckoutActivity.cart.put(current.getIid(),quant[0]);
                    updateUi();
                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quant[0]==1){
                    quant[0]--;
                    CheckoutActivity.cart.remove(current.getIid());
                    list.remove(current);
                    notifyDataSetChanged();
                    updateUi();
                }else{
                    quant[0]--;
                    qty.setText(String.valueOf(quant[0]));
                    price.setText(new StringBuilder("₹").append(current.getPrice() * quant[0]));
                    CheckoutActivity.cart.put(current.getIid(),quant[0]);
                    updateUi();
                }
            }
        });



        return view;
    }

    private void updateUi() {
        ((CheckoutActivity)context).updateUI();
//        if(context instanceof CheckoutActivity){
//            Log.e("adapter", "updateUi: " );
//            ((CheckoutActivity)context).updateUI();
//        }
    }


}
