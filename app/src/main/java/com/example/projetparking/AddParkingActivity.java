package com.example.projetparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

public class AddParkingActivity extends AppCompatActivity {
    Button btnChemin,btnSubmit,btnParking;
    EditText editUser,editCapacite,editAdresse;
    //Progress Dialog
    ProgressDialog pd;

    //FireStore Instance
    FirebaseFirestore db ;
    FirebaseAuth mFireAuth;

    String parkId,parkUser,parkAdresse,parkCapacite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parking);

        //Action Bar
        ActionBar actionBar = getSupportActionBar();
        mFireAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        //initialise composant de la page.
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

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adding new
                String user = mFireAuth.getCurrentUser().getEmail();
                String adresse = editAdresse.getText().toString();
                Long capacite = Long.parseLong(editCapacite.getText().toString());

                //Upload data
                uploadData(user,adresse,capacite);
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
                        Toast.makeText(AddParkingActivity.this,"Upload Succesful",Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Upload Failed
                        pd.dismiss();
                        Toast.makeText(AddParkingActivity.this,"Upload Failed . Error = "+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
