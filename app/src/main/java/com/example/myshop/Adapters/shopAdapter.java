package com.example.myshop.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.myshop.Activity.Chat;
import com.example.myshop.Objects.ShopObject;
import com.example.myshop.R;
import com.example.myshop.Objects.UserDetails;

import java.util.ArrayList;

public class shopAdapter extends BaseAdapter {
    ArrayList<ShopObject> list;
    Context context;

    public shopAdapter(ArrayList<ShopObject> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.shop_todo,viewGroup,false);
        }
        TextView name=view.findViewById(R.id.shop_todo_name);
        TextView address=view.findViewById(R.id.shop_todo_address);
        RatingBar ratingBar=view.findViewById(R.id.shop_todo_rating_bar);
        ImageView menuBtn=view.findViewById(R.id.shop_todo_menuBtn);

        final ShopObject current=list.get(i);

        name.setText(current.getShopName());
        String builder = current.getLocality() + ", " + current.getCity() +
                ", " + current.getPincode();
        address.setText(builder);

        ratingBar.setRating(current.getRating());
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(context,view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.dashboard_action_support:
                                {
                                //goto message box with usedid and shop id
                                    Intent intent=new Intent(context,Chat.class);
                                    intent.putExtra("sid",current.getSid());
                                    context.startActivity(intent);
                                break;
                            }
                            case R.id.dashboard_action_email:{
                                Intent intent=new Intent(Intent.ACTION_SENDTO);
                                intent.setData(Uri.parse("mailto:"+current.getEmail()+
                                        "?cc="+
                                        "&subject=Customer Query From My Shop"));
                                intent.putExtra(Intent.EXTRA_SUBJECT,"Customer Query From My Shop");
                                context.startActivity(Intent.createChooser(intent,"Choose an App for Mailing"));
                                break;
                            }
                            case R.id.dashboard_action_Contact:{
                                Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+current.getContact()));
                                context.startActivity(intent);
                                break;
                            }
                            case R.id.dashboard_action_Chat:
                            {
                                Intent intent =new Intent(context, Chat.class);
                                intent.putExtra("sid", UserDetails.userID);
                                context.startActivity(intent);
                                break;
                            }
                        }
                        return true;
                    }
                });
                //MenuInflater inflater=popupMenu.getMenuInflater();
                //inflater.inflate(R.menu.dashboard_list_item_menu,popupMenu.getMenu());
                popupMenu.inflate(R.menu.dashboard_list_item_menu);
                popupMenu.show();
            }
        });

        return view;
    }
}
