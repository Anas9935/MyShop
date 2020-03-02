package com.example.myshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.util.Util;
import com.example.myshop.Adapters.itemAdapter;
import com.example.myshop.Adapters.itemExpandableListAdapter;
import com.example.myshop.Objects.itemObject;
import com.example.myshop.R;
import com.example.myshop.UtilityClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemActivity extends AppCompatActivity {
String sid;

ListView itemsLv;
ArrayList<itemObject> itemList;
public static HashMap<String,Integer> cart;
//itemAdapter adapter;
public static LinearLayout cartBtn;

ExpandableListView explistview;
ArrayList<String> topicName;
ConcurrentHashMap<String,ArrayList<itemObject>> groupItems;
itemExpandableListAdapter adapter2;

RequestQueue queue;

String shopName,shopAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        sid=getIntent().getStringExtra("sid");

        initializeViews();
        queue= Volley.newRequestQueue(this);
        cart= new HashMap<>();
        itemList=new ArrayList<>();
//        adapter=new itemAdapter(itemList,this);

        topicName=new ArrayList<>();
        groupItems=new ConcurrentHashMap<>();
     //   helper();

        adapter2=new itemExpandableListAdapter(topicName,groupItems,cart,this);
        explistview.setAdapter(adapter2);

        fetchItems();
//        itemsLv.setAdapter(adapter);
        updateUi();

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ItemActivity.this,CheckoutActivity.class);
                intent.putExtra("sid",sid);
                intent.putExtra("cart",cart);
                intent.putExtra("shopName",shopName);
                intent.putExtra("shopAddress",shopAddress);
                startActivity(intent);
                Log.e("CArtBtn", "onClick: "+cart.entrySet() );
            }
        });
    }

    private void helper() {

        topicName.add("Pulses");
        topicName.add("Essentials");
        ArrayList<itemObject> l= UtilityClass.getList();

        for(String i:topicName){
            groupItems.put(i,new ArrayList<itemObject>());
        }


        for(itemObject i:l){
            if(i.getType()==0){
                ArrayList<itemObject> temp=groupItems.get(topicName.get(0));
                temp.add(i);
            }else if(i.getType()==1){
                ArrayList<itemObject> temp=groupItems.get(topicName.get(1));
                temp.add(i);
            }
        }

    }

    public static void updateUi(){
        if(cart.size()!=0){
            cartBtn.setVisibility(View.VISIBLE);
        }else{
            cartBtn.setVisibility(View.GONE);
        }
    }

    private void initializeViews() {
        //itemsLv=findViewById(R.id.item_list);
        explistview=findViewById(R.id.item_exp_list);
        cartBtn=findViewById(R.id.item_cartBtn);
    }

    private void fetchItems() {

        String url="https://ravilcartapi.herokuapp.com/getshopdetails/"+sid;
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.e("this", "onResponse: "+response );
                try{
                    JSONObject object=new JSONObject(response);
                    JSONObject ars=object.getJSONObject("arrayOfShop");
                    JSONArray itemArray=ars.getJSONArray("items");
                    for(int i=0;i<itemArray.length();i++){
                        JSONObject current=itemArray.getJSONObject(i);
                        String iid=current.getString("_id");
                        String itmName=current.getString("name");
                        float price=Float.valueOf(current.getString("price"));
                        int qty=current.getInt("quantity");
                        String dt=current.getString("updatedAt");
                        long timeStamp=0;
                        int type=current.getInt("type");

                        try {
                            timeStamp=UtilityClass.getDate(dt);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String url=current.getString("url");
                        itemObject obj=new itemObject(iid,itmName,qty,price,timeStamp,type,url);
                        itemList.add(obj);
                        Log.e("this", "onResponse: "+obj.getIid() );


                    }
                    shopName=ars.getString("name");
                    shopAddress=ars.getString("address");

                    postLoadList();

                }catch (JSONException e){
                    e.printStackTrace();

                    postLoadList();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ItemActivity", "onErrorResponse: "+error.toString() );
            }
        });
        queue.add(request);
    }

    private void postLoadList() {
        topicName.addAll(UtilityClass.getCategories());
        //ArrayList<itemObject> l= UtilityClass.getList();
        itemList.addAll(UtilityClass.getList());
        for(String i:topicName){
            groupItems.put(i,new ArrayList<itemObject>());
        }

        for(itemObject i:itemList){
            groupItems.get(topicName.get(i.getType())).add(i);
        }
        for(Map.Entry i:groupItems.entrySet()){
            ArrayList<itemObject> tmp=(ArrayList<itemObject>) i.getValue();
            if(tmp.size()==0){
                groupItems.remove(i.getKey());
                topicName.remove(i.getKey());
                adapter2.notifyDataSetChanged();
            }

        }
        adapter2.notifyDataSetChanged();
    }
}
