package com.example.projetparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    public EditText emailId, password,editRole;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore db ;
    String user,role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = FirebaseFirestore.getInstance();
        editRole = findViewById(R.id.editRole);
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        btnSignUp = findViewById(R.id.button);
        tvSignIn = findViewById(R.id.textView);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Please enter email");
                    emailId.requestFocus();
                }
                else if (pwd.isEmpty()){
                    password.setError("please enter your password");
                    password.requestFocus();
                }
                else if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(MainActivity.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();

                }
                else if(!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this,"Unsuccessful SignUp, Please try again",Toast.LENGTH_SHORT).show();

                            }
                            else {
                                user = editRole.getText().toString();
                                role = editRole.getText().toString();
                                uploadData(user,role);
                                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                            }


                        }
                    });
                }
                else {
                    Toast.makeText(MainActivity.this,"Error Occurred",Toast.LENGTH_SHORT).show();

                }
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

    }
    private void uploadData(String usermail, String role) {

        //Random ID
        String id = UUID.randomUUID().toString();

        Map<String, Object> user = new HashMap<>();
        user.put("id",id);
        user.put("user",usermail);
        user.put("role",role);

        //add Data
        db.collection("Users").document(id).set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
}
