package com.example.projetparking;

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

import com.example.projetparking.shared.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowParkingActivity extends AppCompatActivity {

    List<Model> modelList = new ArrayList<>();
    RecyclerView mRecycleView;
    FloatingActionButton mAddBtn;

    //layout manager for recycle view
    RecyclerView.LayoutManager layoutManager;

    //firestore instance
    FirebaseFirestore db;

    CustomAdapter adapter;

    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_parking);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("List Parking");

        //init firebase
        db = FirebaseFirestore.getInstance();

        //initialize view
        mRecycleView = findViewById(R.id.recycler_view);
        mAddBtn = findViewById(R.id.addBtn);


        //set recycler view properties
        mRecycleView.setAdapter(adapter );
        mRecycleView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(layoutManager);

        pd = new ProgressDialog(this);
        //show data in recycleView
        showData();
        mAddBtn.hide();
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowParkingActivity.this, HomeActivity.class));
                finish();
            }
        });

    }

    private void showData() {
        pd.setTitle("Loading");
        pd.show();

        db.collection("Documents").whereEqualTo("allowed",true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        pd.dismiss();
                        //show data
                        for (DocumentSnapshot doc: task.getResult()) {
                            Model model = new Model(doc.getString("id"),
                                    doc.getString("user"),
                                    doc.getString("adresse"),
                                    doc.getLong("capacite"),doc.getBoolean("allowed"),doc.getString("description"));
                            modelList.add(model);
                        }
                        //adapter
                        adapter = new CustomAdapter(ShowParkingActivity.this,modelList,"user");
                        //set adpater to recycle view
                        mRecycleView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(ShowParkingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}

