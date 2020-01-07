package com.example.projetparking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

public class AdminHomeActivity extends AppCompatActivity {
    Button btnParking;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        btnParking = findViewById(R.id.parkBtn);
        db = FirebaseFirestore.getInstance();

        btnParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this,AdminParkingNotAllowedActivity.class));
            }
        });
    }
}
