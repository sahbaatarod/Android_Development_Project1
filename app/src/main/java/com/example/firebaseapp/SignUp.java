package com.example.firebaseapp;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

public class SignUp extends AppCompatActivity {
    EditText fnameet;
    EditText lnameet;
    EditText emailet;
    EditText passet;
    EditText roleet;
    Button btn;
    TextView logintv;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fnameet = findViewById(R.id.fnametextinput);
        lnameet = findViewById(R.id.lnametextinput);
        emailet = findViewById(R.id.emailtextinput);
        passet = findViewById(R.id.passtextinput);
        roleet = findViewById(R.id.roletv);
        btn = findViewById(R.id.loginbtn);
        logintv = findViewById(R.id.logintv);
        mAuth = FirebaseAuth.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fnametxt, lnametxt, emailtxt, passtxt,role;
                fnametxt = fnameet.getText().toString();
                lnametxt = lnameet.getText().toString();
                emailtxt = emailet.getText().toString();
                passtxt = passet.getText().toString();
                role = roleet.getText().toString();
                if ((fnametxt == "" || fnametxt == " ") || (lnametxt == "" || lnametxt == " ") || (emailtxt == "" || emailtxt == " ") || (passtxt == "" || (passtxt == " "))) {
                    Toast.makeText(SignUp.this, "Please enter your details", Toast.LENGTH_SHORT).show();
                } else if (passtxt.length() < 6) {
                    Toast.makeText(SignUp.this, "Length of password must be greater than or equals to 6", Toast.LENGTH_SHORT).show();
                } else {
                    createUser(fnametxt, lnametxt, emailtxt, passtxt,role);
                }
            }
        });
        logintv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,LoginActivity.class));
            }
        });

    }

    private void createUser(String fnametxt, String lnametxt, String emailtxt, String passtxt,String role) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth.createUserWithEmailAndPassword(emailtxt,passtxt)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            UserModel model = new UserModel();
                            model.setFname(fnametxt);
                            model.setLname(lnametxt);
                            model.setEmail(emailtxt);
                            model.setRole(role);
                            model.setUID(mAuth.getUid());
                            db.collection("User").add(model).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Toast.makeText(SignUp.this, "Register successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUp.this,WorkActivity.class));
                                }
                            });
                        }
                    }
                });
    }

}


