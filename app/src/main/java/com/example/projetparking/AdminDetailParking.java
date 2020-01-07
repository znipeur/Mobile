package com.example.projetparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminDetailParking extends AppCompatActivity {
    Button btnAllow;
    TextView tvCapacite,tvAdresse;
    FirebaseFirestore db;
    Long capacite;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_parking);

        db = FirebaseFirestore.getInstance();
        btnAllow = findViewById(R.id.btnAllow);
        tvAdresse = findViewById(R.id.tvAdresse);
        tvCapacite = findViewById(R.id.tvCapacite);
        Bundle bundle = getIntent().getExtras();
        capacite = bundle.getLong("parkCapacite");
        id = bundle.getString("parkid");
        tvCapacite.setText(capacite.toString());
        tvAdresse.setText(bundle.getString("parkAdresse"));

        btnAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Documents").document(id).update("allowed",true).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AdminDetailParking.this, "Allowed", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(AdminDetailParking.this, "Error Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
