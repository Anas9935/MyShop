package com.example.myshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myshop.Adapters.CheckoutItemAdapter;
import com.example.myshop.Objects.itemObject;
import com.example.myshop.R;
import com.example.myshop.UtilityClass;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CheckoutActivity extends AppCompatActivity {

    public static HashMap<String,Integer> cart;
    String sid;
    ListView itemListView;
    ArrayList<itemObject> itemList;
    CheckoutItemAdapter adapter;

    TextView shop_name,shop_address,fullPrice, deliveryPrice,totalPrice,changeAddressBtn,payBtn,codBtn,cardBtn;
    TextView shop_img;
    float grandTotal=0;

    boolean isPayOpened=false;

    RequestQueue queue;

    ProgressBar progressBar;
    String shopName,shopAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        cart=(HashMap<String, Integer>) getIntent().getSerializableExtra("cart");
        sid=getIntent().getStringExtra("sid");
        shopName=getIntent().getStringExtra("shopName");
        shopAddress=getIntent().getStringExtra("shopAddress");
        //updating shop details
        initializeViews();
        shop_name.setText("ShopName");
        shop_address.setText("shopAddress");
        shop_img.setText(String.valueOf('A'));
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        shop_img.setTextColor(color);
        

        //Log.e("checkout", "onCreate: "+cart );

        progressBar.setVisibility(View.GONE);
        queue= Volley.newRequestQueue(this);
        itemList=new ArrayList<>();
        adapter=new CheckoutItemAdapter(itemList,this);
        itemListView.setAdapter(adapter);
        fetchList();
        updateUI();
        changeAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("CheckOutActivity", "onClick: change Btn Clicked" );
                Log.e("THis", "onClick: "+"Not yet implemented" );
            }
        });
        
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPayOpened){
                    isPayOpened=false;
                    closePayBtn();
                }else{
                    isPayOpened=true;
                    openPayBtn();
                }
            }
        });

        codBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                int otp=getOtp();
                if(grandTotal!=0) {
                    saveNdUpdate(otp);
                    }
                else{
                    Toast.makeText(CheckoutActivity.this, "Data is not loaded yet", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

        cardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CheckoutActivity.this, "Not Created yet", Toast.LENGTH_SHORT).show();
            }
        });
        
    }


    private int getOtp() {
        Random random=new Random();
        int randInt=random.nextInt(90000);
        int otp=randInt+9999;
        return otp;
    }

    private void saveNdUpdate(final int otp) {
        //create the request and update the quantity of the ordered items
        JSONObject jsonObject=new JSONObject();
        try {
            JSONArray jsonCart=new JSONArray();
            for(Map.Entry i:cart.entrySet()){
                JSONObject innerObj=new JSONObject();
                innerObj.put("items",i.getKey().toString());
                innerObj.put("quantity",i.getValue().toString());
                jsonCart.put(innerObj);
            }
            jsonObject.put("cart",jsonCart);
            jsonObject.put("amount",grandTotal);
            jsonObject.put("userId", FirebaseAuth.getInstance().getUid());
            jsonObject.put("shopId",sid);
            jsonObject.put("typeOfPayment","COD");
            jsonObject.put("otp",otp);
            jsonObject.put("status",0);
            jsonObject.put("timeOfOrder",System.currentTimeMillis());
        } catch (JSONException e) {
            e.printStackTrace();
            progressBar.setVisibility(View.GONE);
        }

        String url="https://ravilcartapi.herokuapp.com/userorder";
        JsonObjectRequest req=new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Checkout", "onResponse: "+response.toString() );
                Toast.makeText(CheckoutActivity.this, "Yay!! Order is received", Toast.LENGTH_SHORT).show();
                    moveToNextActivity(otp);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CheckoutActivity.this, "OOPs!Something went wrong. Retry", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
        queue.add(req);
    }

    private void moveToNextActivity(int otp) {
        Intent intent=new Intent(CheckoutActivity.this,BillActivity.class);
        intent.putExtra("cart",cart);
        intent.putExtra("payType",0);       //0->cod 1->card
        intent.putExtra("otp",otp);
        startActivity(intent);
        progressBar.setVisibility(View.GONE);
        finish();
    }

    private void openPayBtn() {
        cardBtn.setVisibility(View.VISIBLE);
        codBtn.setVisibility(View.VISIBLE);

        OvershootInterpolator interpolator=new OvershootInterpolator(10.0F);
        codBtn.animate().translationY(-200).setDuration(150).withLayer().setInterpolator(interpolator).start();
        cardBtn.animate().translationY(-400).setDuration(200).withLayer().setInterpolator(interpolator).start();
        Log.e("This", "openPayBtn: "+"isopened" );
    }

    private void closePayBtn() {
        cardBtn.setVisibility(View.GONE);
        codBtn.setVisibility(View.GONE);

        OvershootInterpolator interpolator=new OvershootInterpolator(10.0F);
        codBtn.animate().translationY(0).setDuration(150).setInterpolator(interpolator).start();
        cardBtn.animate().translationY(0).setDuration(200).setInterpolator(interpolator).start();
        Log.e("thar", "closePayBtn: is closed" );
    }

    private void initializeViews() {
        shop_name=findViewById(R.id.checkout_shop_name);
        shop_address=findViewById(R.id.checkout_shop_locality);
        fullPrice=findViewById(R.id.checkout_full_price);
        deliveryPrice =findViewById(R.id.checkout_deliveryPrice);
        totalPrice=findViewById(R.id.checkout_total_price);
        changeAddressBtn=findViewById(R.id.checkout_addressChangeBtn);
        payBtn=findViewById(R.id.checkout_payBtn);
        codBtn=findViewById(R.id.checkout_cashBtn);
        cardBtn=findViewById(R.id.checkout_cardBtn);
        itemListView=findViewById(R.id.checkout_item_listView);
        shop_img=findViewById(R.id.checkout_img);
        progressBar=findViewById(R.id.checkout_progress_bar);
    }
    private void fetchList() {
        for(Map.Entry i:cart.entrySet()){
            itemObject obj=getItemObject(i.getKey().toString());
            if(obj!=null){
                itemList.add(obj);
            }else{
                Log.e("Checkout", "fetchList: "+"Item detail cant be fetched from server" );
            }
        }
//        Log.e("checkout", "fetchList: "+itemList.size() );
        adapter.notifyDataSetChanged();
    }
    ArrayList<itemObject> lst= UtilityClass.getList();
    private itemObject getItemObject(String key) {
        //change it so that it may fetch from the server
        for(itemObject i:lst){
//            Log.e("checkout", "getItemObject: "+i.getIid()+" "+key );
            if(i.getIid().equals(key)){
                return i;
            }
        }
        return null;
    }
    public void updateUI(){
//        Log.e("checkout", "updateUI: "+cart );
        float total=0;
        for(itemObject i:itemList){
            total+=i.getPrice()*cart.get(i.getIid());
        }
        fullPrice.setText(String.valueOf(total));
        float delivery=0;
        if(total<1000){
            delivery=50;
        }else if(total>1000&&total<3000){
            delivery=100;
        }else{
            delivery=150;
        }
        deliveryPrice.setText(String.valueOf(delivery));
        totalPrice.setText(String.valueOf(total+delivery));
        grandTotal=total+delivery;

    }


}
