package com.example.projetparking.shared;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.projetparking.CustomAdapter;
import com.example.projetparking.Gestionnaires.AddParkingActivity;
import com.example.projetparking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    List<Message> messageList = new ArrayList<>();
    RecyclerView mRecycleView;
    FloatingActionButton mAddBtn;
    FirebaseAuth mFireAuth;

    //layout manager for recycle view
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db;

    CustomAdapter adapter;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("List Notifications");

        //init firebase
        db = FirebaseFirestore.getInstance();

        //initialize view
        mRecycleView = findViewById(R.id.message_view);
        mAddBtn = findViewById(R.id.addBtn);


        //set recycler view properties
        mRecycleView.setAdapter(adapter);
        mRecycleView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(layoutManager);
        pd = new ProgressDialog(this);
        Bundle bundle = getIntent().getExtras();
        //show data in recycleView
        showData(bundle.getString("user"));
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageActivity.this, AddParkingActivity.class));
                finish();
            }
        });}
    private void showData(String user) {
        pd.setTitle("Loading");
        pd.show();

        db.collection("Users/"+user+"/message")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        pd.dismiss();

                        //show data
                        for (DocumentSnapshot doc: task.getResult()) {
                            Message message = new Message(
                                    doc.getString("user"),
                                    doc.getString("message")
                                    );
                            messageList.add(message);
                            Toast.makeText(MessageActivity.this, message.getEmailSender(), Toast.LENGTH_SHORT).show();
                        }
                        //adapter
                        adapter = new CustomAdapter(MessageActivity.this,messageList,"message");
                        //set adpater to recycle view
                        mRecycleView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(MessageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    }

