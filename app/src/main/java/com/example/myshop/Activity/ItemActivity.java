package com.example.myshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.myshop.Adapters.itemAdapter;
import com.example.myshop.Chat;
import com.example.myshop.Objects.itemObject;
import com.example.myshop.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemActivity extends AppCompatActivity {
String sid;

ListView itemsLv;
ArrayList<itemObject> itemList;
public static HashMap<String,Integer> cart;
itemAdapter adapter;
public static LinearLayout cartBtn;
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_CHAT:
                Intent i = new Intent(this, Chat.class);
                i.putExtra("sid",sid);
                startActivity(i);
                return true;
            default:
                return true;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        sid=getIntent().getStringExtra("sid");
        initializeViews();
        cart=new HashMap<String,Integer>();
        itemList=new ArrayList<>();
        adapter=new itemAdapter(itemList,this);
        fetchItems();
        itemsLv.setAdapter(adapter);
        updateUi();

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ItemActivity.this,CheckoutActivity.class);
                intent.putExtra("sid",sid);
                intent.putExtra("cart",cart);
                startActivity(intent);
                Log.e("CArtBtn", "onClick: "+cart.entrySet() );
            }
        });
    }

    public static void updateUi(){
        if(cart.size()!=0){
            cartBtn.setVisibility(View.VISIBLE);
        }else{
            cartBtn.setVisibility(View.GONE);
        }
    }

    private void initializeViews() {
        itemsLv=findViewById(R.id.item_list);
        cartBtn=findViewById(R.id.item_cartBtn);
    }

    private void fetchItems() {
        itemList.add(new itemObject("1234","Pulses",25,100.0f,11220542L,0,null));
        itemList.add(new itemObject("1235","Paste",65,120.0f,11220542L,0,null));
        itemList.add(new itemObject("1236","Rice",10,160.0f,11220542L,0,null));
        itemList.add(new itemObject("1237","Meat",54,170.0f,11220542L,0,null));
        itemList.add(new itemObject("1238","Sauce",5,90.0f,11220542L,0,null));
        itemList.add(new itemObject("1239","Vegies",80,80.0f,11220542L,0,null));
        itemList.add(new itemObject("1212","Speices",15,150.0f,11220542L,0,null));
        itemList.add(new itemObject("1233","Biscuits",26,140.0f,11220542L,0,null));
        itemList.add(new itemObject("1239","Milk",98,200.0f,11220542L,0,null));
        itemList.add(new itemObject("1245","Tea",1,260.0f,11220542L,0,null));
        adapter.notifyDataSetChanged();
    }
}
