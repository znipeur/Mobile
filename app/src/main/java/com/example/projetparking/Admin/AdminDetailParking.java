package com.example.projetparking.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetparking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminDetailParking extends AppCompatActivity {
    Button btnAllow,btnMessage,btnChemin;
    TextView tvCapacite,tvAdresse;
    FirebaseFirestore db;
    Long capacite;
    String id;
    String user;
    String adresse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_parking);

        db = FirebaseFirestore.getInstance();
        btnAllow = findViewById(R.id.btnAllow);
        tvAdresse = findViewById(R.id.tvAdresse);
        tvCapacite = findViewById(R.id.tvCapacite);
        btnMessage = findViewById(R.id.btnMessage);
        btnChemin = findViewById(R.id.btnChemin);
        final Bundle bundle = getIntent().getExtras();
        capacite = bundle.getLong("parkCapacite");
        id = bundle.getString("parkid");
        adresse = bundle.getString("parkAdresse");
        user = bundle.getString("parkUser");
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
        btnChemin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adresse = bundle.getString("parkAdresse");
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+adresse);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW,gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminDetailParking.this, AddMessageActivity.class);
                i.putExtra("destinataire",user);
                startActivity(i);
            }
        });

    }
}
