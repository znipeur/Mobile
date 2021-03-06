package com.example.projetparking.Gestionnaires;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetparking.R;
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
    EditText editDescription,editCapacite,editAdresse;
    //Progress Dialog
    ProgressDialog pd;

    //FireStore Instance
    FirebaseFirestore db ;
    FirebaseAuth mFireAuth;

    String parkId,parkUser,parkAdresse,parkCapacite,parkDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parking);

        //Action Bar
        ActionBar actionBar = getSupportActionBar();
        mFireAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        //initialise composant de la page.
        btnSubmit = findViewById(R.id.submit);
        btnParking = findViewById(R.id.parkBtn);
        editDescription = findViewById(R.id.editDescription);
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
                String description=editDescription.getText().toString();
                //Upload data
                uploadData(user,adresse,capacite,description);
            }
        });
    }

    private void uploadData(String user, String adresse, Long capacite,String description) {

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
        park.put("description",description);
        park.put("capacite",capacite);
        park.put("allowed",false);

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
