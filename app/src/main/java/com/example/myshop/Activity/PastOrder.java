package com.example.myshop.Activity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myshop.Adapters.PastOrdersAdapter;
import com.example.myshop.Objects.OrderObject;
import com.example.myshop.R;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PastOrder extends AppCompatActivity {

    ArrayList<OrderObject> list;
    ListView lview;
    PastOrdersAdapter adapter;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_order);

        lview=findViewById(R.id.list_orders);

        queue= Volley.newRequestQueue(this);

        list=new ArrayList<>();
        adapter=new PastOrdersAdapter(list,this);
        lview.setAdapter(adapter);
        populatePastOrders();

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OrderObject current=list.get(i);
                Intent intent=new Intent(PastOrder.this,ItemActivity.class);
                intent.putExtra("sid",current.getSid());
                startActivity(intent);
            }
        });

    }

    private void populatePastOrders() {
        String uid= FirebaseAuth.getInstance().getUid();
        String url="https://ravilcartapi.herokuapp.com/orders/"+uid;

        final StringRequest req=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    JSONArray request=object.getJSONArray("results");
                    for(int i=0;i<request.length();i++){
                        JSONObject current=request.getJSONObject(i);
                        String oid=current.getString("_id");
                        HashMap<String,Integer> cartObj=new HashMap<>();
                        JSONArray cartArray=current.getJSONArray("cart");
                        for(int j=0;j<cartArray.length();j++){
                            JSONObject currentItem=cartArray.getJSONObject(j);
                            JSONObject items=currentItem.getJSONObject("items");
                            String itemName=items.getString("name");
                            Integer quantity=currentItem.getInt("quantity");
                            cartObj.put(itemName,quantity);
                        }
                        float amt=current.getInt("amount");
                        JSONObject shopObject=current.getJSONObject("shopId");
                        String sid=shopObject.getString("_id");
                        String shopName=shopObject.getString("name");
                        String tOPString=current.getString("typeOfPayment");
                        int TOP;
                        if(tOPString.equals("COD")){
                            TOP=1;
                        }else{
                            TOP=2;
                        }
                        long time=123456789L;
                        int otp=12345;
                        int status=2;

                        OrderObject obj=new OrderObject(oid,sid,cartObj,amt,TOP,time,otp,status,shopName);
                        list.add(obj);
                        adapter.notifyDataSetChanged();
                    }
                    Collections.sort(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(req);
    }

}
