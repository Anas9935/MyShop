package com.example.myshop.Activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;
import com.example.myshop.Objects.ChatModel;
import com.example.myshop.R;
import com.example.myshop.Objects.UserDetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.appcompat.widget.Toolbar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.view.ViewGroup.LayoutParams.FILL_PARENT;

@SuppressWarnings("ALL")
public class Chat extends AppCompatActivity {

    DatabaseReference messageRef, userRef;
    String chatwithid;
    boolean type = false;
    RecyclerView chatRecView;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ImageView sendButton;
        final EditText messageArea;
        String res="ShopOwner";
        Toolbar toolbar;
        String shopName=getIntent().getStringExtra("shopName");
        getSupportActionBar().setTitle("shopName");

        sendButton = (ImageView) findViewById(R.id.sendButton);
        messageArea = (EditText) findViewById(R.id.messageArea);
        chatRecView = (RecyclerView) findViewById(R.id.chat_recycler_view);
        chatwithid = getIntent().getStringExtra("sid");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        chatRecView.setHasFixedSize(true);
        chatRecView.setLayoutManager(layoutManager);
        messageRef = FirebaseDatabase.getInstance().getReference("/messages");
        //userRef = FirebaseDatabase.getInstance().getReference("/users");
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if (!messageText.equals("")) {
                    Map<String, String> map = new HashMap<>();
                    map.put("message", messageText);
                    map.put("user", UserDetails.userID);
                    UserDetails.chatRef.push().setValue(map);
                    messageArea.
                            setText("");
                    messageArea.
                            setHint("Message");
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //int count = 0;
        valueEventListener(chatwithid);

    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView chatMessage;
        TextView userText;
        LinearLayout linearLayout;

        public ChatViewHolder(View itemView) {
            super(itemView);
            chatMessage = (TextView) itemView.findViewById(R.id.text_chat);
            userText = (TextView) itemView.findViewById(R.id.user_chat);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.lin_lay);
        }

        public void setChatMessage(String message) {
            chatMessage.setText(message);
        }

        public void setUserText(String userName, boolean type) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, FILL_PARENT);
            if (type) {
                //current logged in user
                params.gravity = Gravity.END;
                userText.setText("You");
                linearLayout.setGravity(Gravity.END);
                //linearLayout.setBackgroundResource(R.drawable.bubble_in);
            } else {
                params.gravity = Gravity.START;
                userText.setText("ShopOwner");
                linearLayout.setGravity(Gravity.START);
                //linearLayout.setBackgroundResource(R.drawable.bubble_out);
            }
            linearLayout.setLayoutParams(params);
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void valueEventListener( final String checkChild) {
        messageRef = FirebaseDatabase.getInstance().getReference("/messages");

        final String type1, type2;
        type1 = UserDetails.userID + "_" + checkChild;
        type2 = checkChild + "_" + UserDetails.userID;

        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot child1 = dataSnapshot.child(type1);
                DataSnapshot child2 = dataSnapshot.child(type2);
                if (child1.exists()) {
                    UserDetails.userType = "type1";
                    UserDetails.chatRef = FirebaseDatabase.getInstance().getReference("/messages").child(type1);
                    //chatRef2 = messageRef.child(type1);
                } else if (child2.exists()) {
                    UserDetails.userType = "type2";
                    UserDetails.chatRef = FirebaseDatabase.getInstance().getReference("/messages").child(type2);

                } else {
                    UserDetails.userType = "type1";
                    messageRef.child(type1).setValue("none");
                    UserDetails.chatRef = FirebaseDatabase.getInstance().getReference("/messages").child(type1);
                }


                if (UserDetails.chatRef != null) {
                    FirebaseRecyclerAdapter<ChatModel, ChatViewHolder> firebaseRecyclerAdapter =
                            new FirebaseRecyclerAdapter<ChatModel, ChatViewHolder>(
                                    ChatModel.class,
                                    R.layout.chat_row,
                                    ChatViewHolder.class,
                                    UserDetails.chatRef) {
                                @Override
                                protected void populateViewHolder(ChatViewHolder viewHolder, ChatModel model, int position) {
                                    viewHolder.setChatMessage(model.getMessage());
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                        type = Objects.equals(model.getUser(), UserDetails.userID);
                                    }
                                    viewHolder.setUserText(model.getUser(), type);
                                }
                            };
                    chatRecView.setAdapter(firebaseRecyclerAdapter);
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Chat.this, Chat.class);
                    startActivity(i);
                    finish();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }
    }
