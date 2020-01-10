package com.example.projetparking.shared;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetparking.R;
import com.example.projetparking.ShowParkingActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity {
    Button btnLogout;
    FirebaseAuth mFirebaseAuth;
    Button btnChemin,btnSubmit,btnParking,btnMessage;
    EditText editUser,editCapacite,editAdresse;
    //Progress Dialog
    ProgressDialog pd;

    //FireStore Instance
    FirebaseFirestore db ;

    String parkId,parkUser,parkAdresse,parkCapacite;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Action Bar
        ActionBar actionBar = getSupportActionBar();


        //initialise composant de la page.
        btnLogout = findViewById(R.id.logout);
        btnSubmit = findViewById(R.id.submit);
        btnParking = findViewById(R.id.parkBtn);
        editCapacite = findViewById(R.id.editCapacite);
        editAdresse = findViewById(R.id.editAdresse);
        btnMessage = findViewById(R.id.btnMessage);
        //Progress
        pd = new ProgressDialog(this);

        //Firestore
        mFirebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(HomeActivity.this, InscriptionActivity.class);
                startActivity(intToMain);
            }
        });



        //Show Parking
        btnParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(HomeActivity.this, ShowParkingActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(HomeActivity.this, MessageActivity.class);
                i.putExtra("user",mFirebaseAuth.getCurrentUser().getEmail());
                startActivity(i);
            }
        });
    }


    }
