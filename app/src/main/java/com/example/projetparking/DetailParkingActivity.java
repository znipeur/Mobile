package com.example.projetparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailParkingActivity extends AppCompatActivity {
    Button btnAdd,btnSub;
    TextView tvCapacite,tvAdresse;
    FirebaseFirestore db;
    Long capacite;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_parking);

        db = FirebaseFirestore.getInstance();
        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);
        tvAdresse = findViewById(R.id.tvAdresse);
        tvCapacite = findViewById(R.id.tvCapacite);
        Bundle bundle = getIntent().getExtras();
        capacite = bundle.getLong("parkCapacite");
        id = bundle.getString("parkid");
        tvCapacite.setText(capacite.toString());
        tvAdresse.setText(bundle.getString("parkAdresse"));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capacite =capacite+1;
                tvCapacite.setText(capacite.toString());
                addCapacite(id,capacite);
            }
        });
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capacite =capacite-1;
                tvCapacite.setText(capacite.toString());
                subCapacite(id,capacite);
            }
        });



    }

    private void addCapacite(String id,Long capacite) {
        db.collection("Documents").document(id).update("capacite",capacite).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(DetailParkingActivity.this, "capacity added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void subCapacite(String id,Long capacite) {
        db.collection("Documents").document(id).update("capacite",capacite).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(DetailParkingActivity.this, "capacity added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
