package com.example.firebaseapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 101;
    FirebaseAuth mAuth;
    EditText etEmail;
    EditText etPass;
    TextView tv;
    Button btn;
    Button btngoogle;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.emailtextet);
        etPass = findViewById(R.id.passtextet);
        btn = findViewById(R.id.loginbtn);
        tv = findViewById(R.id.signuptv);
        btngoogle = findViewById(R.id.googlebtn);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        btngoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                signIn();
                resultLauncher.launch(mGoogleSignInClient.getSignInIntent());
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_text;
                String pass_text;
                email_text = etEmail.getText().toString();
                pass_text = etPass.getText().toString();
                if ((email_text == "" || email_text == " ") || (pass_text == "" || pass_text == " ")) {
                    Toast.makeText(LoginActivity.this, "Please enter your details", Toast.LENGTH_SHORT).show();
                } else if (pass_text.length() < 6) {
                    Toast.makeText(LoginActivity.this, "Length of password must equal to greater than 6", Toast.LENGTH_SHORT).show();
                } else {
                    LoginUser(email_text,pass_text);
                }
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUp.class));
            }
        });

    }
    public void LoginUser(String email,String pass) {
        mAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                            String id = mAuth.getUid();
                            if(id.equals("iQGrNjS5hyVRA6EHuCliSCBCvJ02")) {
                                Toast.makeText(LoginActivity.this, "admin", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this,AdminPanel.class));

                            }
                            else {
                                startActivity(new Intent(LoginActivity.this,WorkActivity.class));
                            }
                        }
                    }
                });
    }

    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
    @Override
    public void onActivityResult(ActivityResult result) {
        if(result.getResultCode() == Activity.RESULT_OK) {
            Intent intent = result.getData();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
//                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
//                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

            }
        }
    }
});

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserModel model = new UserModel();
                            model.setFname(user.getDisplayName());
                            model.setLname("none");
                            model.setEmail(user.getEmail());
                            model.setUID(user.getUid());
                            model.setRole("Coustmer");
                            db.collection("User").add(model);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        startActivity(new Intent(LoginActivity.this,WorkActivity.class));
    }


}
