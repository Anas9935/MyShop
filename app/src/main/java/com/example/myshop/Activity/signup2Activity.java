package com.example.myshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myshop.R;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class signup2Activity extends AppCompatActivity {

    String uid,email;
    TextView nextBtn;
    EditText username,locality,city,pincode,ph1,ph2;


    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        uid=getIntent().getStringExtra("uid");
        email=getIntent().getStringExtra("email");
        initializeViews();
        queue= Volley.newRequestQueue(this);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saveData()){
                    Toast.makeText(signup2Activity.this, "Save Data", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(signup2Activity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(signup2Activity.this, "Data not saved Check the data", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private boolean saveData() {
        String name=username.getText().toString();
        String loc=locality.getText().toString();
        String ct=city.getText().toString();
        long pin=Long.parseLong(pincode.getText().toString());
        long con1=Long.parseLong(ph1.getText().toString());
        long con2=Long.parseLong(ph2.getText().toString());

        if(uid!=null && !name.equals("") && !loc.equals("") && !ct.equals("") && pin!=0 && con1!=0){
            //save data in the server
            //if is saves successfully return true
            StringBuilder  builder=new StringBuilder();
            builder.append(locality).append(",").append(city).append(",").append(pin);
            Map<String,String> jsonObject=new HashMap<>();
            jsonObject.put("_id",uid);
            jsonObject.put("name",name);
            jsonObject.put("email",email);
            jsonObject.put("number",String.valueOf(con1));
            if(con2!=0){
            //    jsonObject.put("number2",String.valueOf(con2));
            }
            jsonObject.put("address",builder.toString());

            String url="http://ravilcartapi.herokuapp.com/createuser";
            JsonObjectRequest req=new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonObject)
                    , new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("this", "onResponse: "+response.toString() );
                    Toast.makeText(signup2Activity.this, "Data Saved", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Signup2", "onErrorResponse: "+error.networkResponse.toString() );
                    Toast.makeText(signup2Activity.this, "Data not saved", Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(req);
            return true;
        }

        return false;
    }

    private void initializeViews() {
        nextBtn=findViewById(R.id.signup2_nextBtn);
        username=findViewById(R.id.signup2_username);
        locality=findViewById(R.id.signup2_locality);
        city=findViewById(R.id.signup2_city);
        pincode=findViewById(R.id.signup2_pincode);
        ph1=findViewById(R.id.signup2_ph1);
        ph2=findViewById(R.id.signup2_ph2);
    }

}
