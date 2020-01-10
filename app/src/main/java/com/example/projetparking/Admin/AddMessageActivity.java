package com.example.projetparking.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class AddMessageActivity extends AppCompatActivity {
    Button btnSend;
    TextView muserTv;
    EditText editUser,editMessage;
    FirebaseFirestore db;
    FirebaseAuth mFireBaseAuth;
    ProgressDialog pd;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message);

        btnSend = findViewById(R.id.btnSendMessage);
        editMessage = findViewById(R.id.editMessage);
        muserTv = findViewById(R.id.userTv);
        db = FirebaseFirestore.getInstance();
        mFireBaseAuth = FirebaseAuth.getInstance();
        bundle = getIntent().getExtras();
        muserTv.setText(bundle.getString("destinatire"));


        String id = UUID.randomUUID().toString();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editMessage.getText().toString();

                String user = bundle.getString("destinataire");
                uploadData(user,mFireBaseAuth.getCurrentUser().getEmail(),message);
            }
        });


    }
    private void uploadData(String user,String sender, String message) {

        //Title of progress bar
       // pd.setTitle("Uploading Data please wait");

        //Show Progress Bar
        //pd.show();

        //Random ID
        String id = UUID.randomUUID().toString();

        Map<String, Object> park = new HashMap<>();
        park.put("id",id);
        park.put("user",sender);
        park.put("message",message);


        //add Data
        db.collection("Users/"+user+"/message").document(id).set(park)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Upload successful
                       // pd.dismiss();
                        Toast.makeText(AddMessageActivity.this,"Upload Succesful",Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Upload Failed
                        //pd.dismiss();
                        Toast.makeText(AddMessageActivity.this,"Upload Failed . Error = "+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }
}

