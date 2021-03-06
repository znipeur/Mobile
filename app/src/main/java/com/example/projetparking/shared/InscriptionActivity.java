package com.example.projetparking.shared;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetparking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InscriptionActivity extends AppCompatActivity {
    public EditText emailId, password,editRole,editName;
    Button btnSignUp;
    RadioButton btnGestionnaire,btnUser;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore db ;
    String user,role,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = FirebaseFirestore.getInstance();
        editName = findViewById(R.id.editName);
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        btnSignUp = findViewById(R.id.button);
        btnGestionnaire = findViewById(R.id.btnGestionnaire);
        btnUser = findViewById(R.id.btnUser);
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
                    Toast.makeText(InscriptionActivity.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();

                }
                else if(!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(InscriptionActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(InscriptionActivity.this,"Unsuccessful SignUp, Please try again",Toast.LENGTH_SHORT).show();

                            }
                            else {
                                user = emailId.getText().toString();
                                name = editName.getText().toString();
                                if (btnGestionnaire.isChecked()){
                                    role = "fournisseur";
                                }
                                else if (btnUser.isChecked()){
                                    role = "user";
                                }

                                uploadData(user,role,name);
                                startActivity(new Intent(InscriptionActivity.this, HomeActivity.class));
                            }


                        }
                    });
                }
                else {
                    Toast.makeText(InscriptionActivity.this,"Error Occurred",Toast.LENGTH_SHORT).show();

                }
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InscriptionActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

    }
    private void uploadData(String usermail, String role,String name) {

        //Random ID
        Map<String, Object> user = new HashMap<>();

        List<Message> listMessage = new ArrayList<>();
        user.put("user",usermail);
        user.put("role",role);
        user.put("name",name);
        user.put("allowed",false);

        //add Data
        db.collection("Users").document(usermail).set(user)
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
