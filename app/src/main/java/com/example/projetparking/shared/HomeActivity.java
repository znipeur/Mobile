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

import com.example.projetparking.InscriptionActivity;
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
        btnChemin = findViewById(R.id.chemin);
        btnSubmit = findViewById(R.id.submit);
        btnParking = findViewById(R.id.parkBtn);
        editUser = findViewById(R.id.editUser);
        editCapacite = findViewById(R.id.editCapacite);
        editAdresse = findViewById(R.id.editAdresse);
        btnMessage = findViewById(R.id.btnMessage);

        //IF we are coming here after an Intent from another activity;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //Update
            actionBar.setTitle("Update Parking");
            btnSubmit.setText("Update");

            //get Data
            parkId = bundle.getString("parkId");
            parkUser = bundle.getString("parkUser");
            parkAdresse = bundle.getString("parkAdresse");
            parkCapacite = bundle.getString("parkCapacite");

            //set Data
            editUser.setText(parkUser);
            editAdresse.setText(parkAdresse);
            editCapacite.setText(parkCapacite);
        }
        else {
            actionBar.setTitle("Add Parking");
            btnSubmit.setText("Save");
        }

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
                Bundle bundle = getIntent().getExtras();
                if(bundle!=null){
                    String user = editUser.getText().toString();
                    String adresse = editAdresse.getText().toString();
                    Long capacite = Long.parseLong(editCapacite.getText().toString());
                    String id = parkId;

                    //Updating the data
                    updateData(id,user,adresse,capacite);

                }
                else {
                    //adding new
                    String user = editUser.getText().toString();
                    String adresse = editAdresse.getText().toString();
                    Long capacite = Long.parseLong(editCapacite.getText().toString());

                    //Upload data
                    uploadData(user,adresse,capacite);

                }
                // Input

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

    private void updateData(String id, String user, String adresse, Long capacite) {
        //Title of progress bar
        pd.setTitle("Updating Data please wait");

        //Show Progress Bar
        pd.show();

        //updating method
        db.collection("Documents").document(id)
                .update("user",user,"adresse",adresse,"capacite",capacite)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Succesful Update
                        pd.dismiss();
                        Toast.makeText(HomeActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Unsuccessful Update
                        pd.dismiss();
                        Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void uploadData(String user, String adresse, Long capacite) {
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
