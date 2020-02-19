package com.example.myshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshop.R;


public class signup2Activity extends AppCompatActivity {

    String uid;
    TextView nextBtn;
    EditText username,locality,city,pincode,ph1,ph2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        uid=getIntent().getStringExtra("uid");
        initializeViews();

        if (saveData()){
            Toast.makeText(this, "Save Data", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(signup2Activity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Data not saved Check the data", Toast.LENGTH_SHORT).show();
        }
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
