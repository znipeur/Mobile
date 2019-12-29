package com.example.projetparking;

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
    Button btnChemin,btnSubmit,btnParking;
    EditText editUser,editCapacite,editAdresse;
    //Progress Dialog
    ProgressDialog pd;

    //FireStore Instance
    FirebaseFirestore db ;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Parking");

        //initialise composant de la page.
        btnLogout = findViewById(R.id.logout);
        btnChemin = findViewById(R.id.chemin);
        btnSubmit = findViewById(R.id.submit);
        btnParking = findViewById(R.id.parkBtn);
        editUser = findViewById(R.id.editUser);
        editCapacite = findViewById(R.id.editCapacite);
        editAdresse = findViewById(R.id.editAdresse);

        //Progress
        pd = new ProgressDialog(this);

        //Firestore
        db = FirebaseFirestore.getInstance();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intToMain);
            }
        });

        //Chemin Google Maps
        btnChemin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=82 Avenue des Nations Unies+ Rabat");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW,gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        //Click submit to upload
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Input
                String user = editUser.getText().toString();
                String adresse = editAdresse.getText().toString();
                int capacite = Integer.parseInt(editCapacite.getText().toString());
                
                //Upload data
                uploadData(user,adresse,capacite);
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
    }

    private void uploadData(String user, String adresse, int capacite) {
        //Title of progress bar
        pd.setTitle("Uploading Data please wait");

        //Show Progress Bar
        pd.show();

        //Random ID
        String id = UUID.randomUUID().toString();

        Map<String, Object> park = new HashMap<>();
        park.put("id",id);
        park.put("user",user);
        park.put("adresse",adresse);
        park.put("capacite",capacite);

        //add Data
        db.collection("Documents").document(id).set(park)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Upload successful
                        pd.dismiss();
                        Toast.makeText(HomeActivity.this,"Upload Succesful",Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Upload Failed
                        pd.dismiss();
                        Toast.makeText(HomeActivity.this,"Upload Failed . Error = "+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
