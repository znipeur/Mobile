package com.example.projetparking.Gestionnaires;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.projetparking.Admin.AdminHomeActivity;
import com.example.projetparking.shared.InscriptionActivity;
import com.example.projetparking.shared.MessageActivity;
import com.example.projetparking.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FournisseurHomeActivity extends AppCompatActivity {
    Button btnParking,btnLogout,btnMessage;
    TextView mF;
    FirebaseAuth mfirebaseAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fournisseur_home);
        mfirebaseAuth = FirebaseAuth.getInstance();
        btnLogout = findViewById(R.id.logout);
        btnParking = findViewById(R.id.parkBtn);
        btnMessage = findViewById(R.id.btnMessage);
        mF = findViewById(R.id.mF);
        mF.setText(mfirebaseAuth.getCurrentUser().getEmail());
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
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(FournisseurHomeActivity.this, InscriptionActivity.class);
                startActivity(intToMain);
            }
        });
    }
}
