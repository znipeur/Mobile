package com.example.projetparking.shared;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetparking.Admin.AdminHomeActivity;
import com.example.projetparking.Gestionnaires.FournisseurHomeActivity;
import com.example.projetparking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    public EditText emailId, password;
    Button btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore db;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //database
        mFirebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        //interface
        emailId = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        btnSignIn = findViewById(R.id.button);
        tvSignUp = findViewById(R.id.textView);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if( mFirebaseUser != null ) {
                    Toast.makeText(LoginActivity.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    //startActivity(i);
                }
                else {
                    Toast.makeText(LoginActivity.this,"Please Login",Toast.LENGTH_SHORT).show();

                }
            }
        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(LoginActivity.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();

                }
                else if(!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this,"Login Error , Please Login Again",Toast.LENGTH_SHORT).show();

                            }
                            else {
                                db.collection("Users").document(emailId.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            String role = task.getResult().getString("role");
                                            if (role.equals("admin")){
                                                startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
                                            }
                                            else if (role.equals("user")){
                                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                            }
                                            else {
                                                if (task.getResult().getBoolean("allowed")==false){
                                                    mFirebaseAuth.signOut();
                                                    Toast.makeText(LoginActivity.this, "You are not allowed to login please wait for an admin to deblock your account", Toast.LENGTH_SHORT).show();
                                                }
                                                startActivity(new Intent(LoginActivity.this, FournisseurHomeActivity.class));
                                            }
                                        }
                                        else {
                                            Toast.makeText(LoginActivity.this, "error bd", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                            }
                        }
                    });
                }
                else {
                    Toast.makeText(LoginActivity.this,"Error Occurred",Toast.LENGTH_SHORT).show();

                }

            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intToSignup = new Intent( LoginActivity.this, InscriptionActivity.class);
                startActivity(intToSignup);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
        mFirebaseAuth.signOut();

    }
}
