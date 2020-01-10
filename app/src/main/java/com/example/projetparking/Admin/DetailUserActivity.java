package com.example.projetparking.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.projetparking.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailUserActivity extends AppCompatActivity {

    TextView mRoleTv,mMailTv,mNameTv;
    Button btnMessage,btnAllow;
    FirebaseFirestore db;
    String mail;
    Boolean allow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        mRoleTv = findViewById(R.id.mRoleTv);
        mNameTv = findViewById(R.id.mNameTv);
        mMailTv = findViewById(R.id.mEmailTv);
        btnAllow = findViewById(R.id.btnAllow);
        btnMessage = findViewById(R.id.btnMessage);
        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        mRoleTv.setText(bundle.getString("role"));
        mMailTv.setText(bundle.getString("mail"));
        mNameTv.setText(bundle.getString("name"));
        mail = bundle.getString("mail");
        allow = bundle.getBoolean("allow");
        btnAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Users").document(mail).update("allowed",!allow);
            }
        });
    }
}
