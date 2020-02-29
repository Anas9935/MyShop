package com.example.myshop.Activity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.myshop.Adapters.shopAdapter;
import com.example.myshop.Chat;
import com.example.myshop.Objects.ShopObject;
import com.example.myshop.R;
import com.example.myshop.UserDetails;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    EditText searchView;
    ListView shop_list;
    ArrayList<ShopObject> list;
    shopAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeViews();
        list=new ArrayList<>();
        adapter=new shopAdapter(list,this);
        fetchList();
        shop_list.setAdapter(adapter);

        shop_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShopObject current=list.get(i);
                UserDetails.chatwithid=current.getSid();
                Intent intent=new Intent(DashboardActivity.this, ItemActivity.class);
                intent.putExtra("sid",current.getSid());
                startActivity(intent);
            }
        });
    }

    private void fetchList() {
        ShopObject dummy1=new ShopObject("SID","New Shop","Bharat nagar","New Delhi",110025L,"aanasari9999@gamil.com",99353380207L,3.5f);
        ShopObject dummy2=new ShopObject("SID2","New Shop two","Okhla","New Delhi",110025L,"aanasari9999@gamil.com",99353380207L,4.5f);
        list.add(dummy1);
        list.add(dummy2);
        adapter.notifyDataSetChanged();
    }


    private void initializeViews() {
        searchView=findViewById(R.id.dashboard_search);
        shop_list=findViewById(R.id.dashboard_list);
    }
}
