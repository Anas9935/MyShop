package com.example.myshop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshop.Adapters.CheckoutItemAdapter;
import com.example.myshop.Objects.itemObject;
import com.example.myshop.R;
import com.example.myshop.UtilityClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {

    public static HashMap<String,Integer> cart;
    ListView itemListView;
    ArrayList<itemObject> itemList;
    CheckoutItemAdapter adapter;

    TextView shop_name,shop_address,fullPrice,dilerveryPrice,totalPrice,changeAddressBtn,payBtn,codBtn,cardBtn;
    ImageView shop_img;

    boolean isPayOpened=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        cart=(HashMap<String, Integer>) getIntent().getSerializableExtra("cart");
        //Log.e("checkout", "onCreate: "+cart );
        initializeViews();
        itemList=new ArrayList<>();
        adapter=new CheckoutItemAdapter(itemList,this);
        itemListView.setAdapter(adapter);
        fetchList();
        updateUI();
        changeAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("CheckOutActivity", "onClick: change Btn Clicked" );
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
                Intent intent=new Intent(CheckoutActivity.this,BillActivity.class);
                intent.putExtra("cart",cart);
                startActivity(intent);

                saveNdUpdate();
            }
        });

        cardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CheckoutActivity.this, "Not Created yet", Toast.LENGTH_SHORT).show();
            }
        });
        
    }

    private void saveNdUpdate() {
        //create the request and update the quantity of the ordered items

    }

    private void openPayBtn() {
        cardBtn.setVisibility(View.VISIBLE);
        codBtn.setVisibility(View.VISIBLE);

        OvershootInterpolator interpolator=new OvershootInterpolator(10.0F);
        codBtn.animate().translationY(-200).setDuration(150).withLayer().setInterpolator(interpolator).start();
        cardBtn.animate().translationY(-400).setDuration(200).withLayer().setInterpolator(interpolator).start();
        Log.e("This", "openPayBtn: "+"isopened" );
        //ViewCompat.animate(payBtn).rotation(135.0F).withLayer().setDuration(300).setInterpolator(interpolator).start();
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
        dilerveryPrice=findViewById(R.id.checkout_deliveryPrice);
        totalPrice=findViewById(R.id.checkout_total_price);
        changeAddressBtn=findViewById(R.id.checkout_addressChangeBtn);
        payBtn=findViewById(R.id.checkout_payBtn);
        codBtn=findViewById(R.id.checkout_cashBtn);
        cardBtn=findViewById(R.id.checkout_cardBtn);
        itemListView=findViewById(R.id.checkout_item_listView);
        shop_img=findViewById(R.id.checkout_img);
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
        dilerveryPrice.setText(String.valueOf(delivery));
        totalPrice.setText(String.valueOf(total+delivery));

    }


}
