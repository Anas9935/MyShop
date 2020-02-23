package com.example.myshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myshop.R;

import java.util.HashMap;

public class BillActivity extends AppCompatActivity {

    HashMap<String,Integer> cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        cart=(HashMap<String, Integer>) getIntent().getSerializableExtra("cart");

        initialiseViews();

    }

    private void initialiseViews() {

    }
}
