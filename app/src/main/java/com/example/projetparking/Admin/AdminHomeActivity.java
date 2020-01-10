package com.example.projetparking.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projetparking.R;
import com.example.projetparking.ViewHolder;
import com.example.projetparking.shared.HomeActivity;
import com.example.projetparking.shared.InscriptionActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminHomeActivity extends AppCompatActivity {
    Button btnParkingNotAllowed,btnUserNotAllowed,btnAllUsers,btnAllParkings;
    FirebaseFirestore db;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        btnParkingNotAllowed = findViewById(R.id.parkBtn);
        btnUserNotAllowed = findViewById(R.id.btnGestionnaire);
        btnAllUsers = findViewById(R.id.btnUsers);
        btnLogout = findViewById(R.id.logout);
        db = FirebaseFirestore.getInstance();
        btnAllParkings = findViewById(R.id.btnParks);
        btnParkingNotAllowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this, AdminParkingNotAllowedActivity.class));
            }
        });
        btnUserNotAllowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this,AdminGestionnaireAllow.class));
            }
        });
        btnAllUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this,AdminListAllUser.class));
            }
        });
        btnAllParkings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this,AdminListAllUser.class));
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(AdminHomeActivity.this, InscriptionActivity.class);
                startActivity(intToMain);
            }
        });

    }
}
