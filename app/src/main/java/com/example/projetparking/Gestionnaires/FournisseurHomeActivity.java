package com.example.projetparking.Gestionnaires;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projetparking.shared.MessageActivity;
import com.example.projetparking.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FournisseurHomeActivity extends AppCompatActivity {
    Button btnParking,btnMessage;
    FirebaseAuth mfirebaseAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fournisseur_home);
        mfirebaseAuth = FirebaseAuth.getInstance();

        btnParking = findViewById(R.id.parkBtn);
        btnMessage = findViewById(R.id.btnMessage);
        db = FirebaseFirestore.getInstance();


        btnParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FournisseurHomeActivity.this, FournisseurParkingActivity.class).putExtra("user",mfirebaseAuth.getCurrentUser().getEmail()));
            }
        });
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FournisseurHomeActivity.this, MessageActivity.class).putExtra("user",mfirebaseAuth.getCurrentUser().getEmail()));
            }
        });
    }
}
