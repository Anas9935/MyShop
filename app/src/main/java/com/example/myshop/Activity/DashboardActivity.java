package com.example.myshop.Activity;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myshop.Adapters.shopAdapter;
import com.example.myshop.Chat;
import com.example.myshop.Objects.ShopObject;
import com.example.myshop.R;
import com.example.myshop.UserDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    EditText searchView;
    ListView shop_list;
    ArrayList<ShopObject> list;
    shopAdapter adapter;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeViews();
        queue= Volley.newRequestQueue(this);

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

        String url="https://ravilcartapi.herokuapp.com/getshops";
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.e("this", "onResponse: "+response );
                try{
                    JSONObject obj=new JSONObject(response);
                    JSONArray u=obj.getJSONArray("u");
                    for(int i=0;i<u.length();i++){
                        JSONObject current=u.getJSONObject(i);
                        JSONArray itemArray=current.getJSONArray("items");
                        String id=current.getString("_id");
                        String mail=current.getString("email");
                        long con=current.getLong("number");
                        double rat=0;
                        try{
                            rat=current.getDouble("rating");
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        String local=current.getString("address");
                        String nm=current.getString("name");
                        ShopObject o=new ShopObject(id,nm,local,null,0L,mail,con,(float)rat);
                        Log.e("this", "onResponse: "+"item added "+id);
                        list.add(o);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DashboardActivity.this, "Error! Refresh after few Minutes", Toast.LENGTH_SHORT).show();
            }
        });
    queue.add(request);



        ShopObject dummy1=new ShopObject("SID","New Shop","Bharat nagar","New Delhi",110025L,"aanasari9999@gamil.com",99353380207L,3.5f);
        ShopObject dummy2=new ShopObject("SID2","New Shop two","Okhla","New Delhi",110025L,"aanasari9999@gamil.com",99353380207L,4.5f);
        //list.add(dummy1);
        //list.add(dummy2);
        ///adapter.notifyDataSetChanged();
    }


    private void initializeViews() {
        searchView=findViewById(R.id.dashboard_search);
        shop_list=findViewById(R.id.dashboard_list);
    }
}
