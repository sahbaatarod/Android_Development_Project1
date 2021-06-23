package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

public class AddMessageAdmin extends AppCompatActivity {
    EditText messageet;
    Button btn;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<MessageModel> messageModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message_admin);
        messageet = findViewById(R.id.messageet);
        btn = findViewById(R.id.Postbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageet.getText().toString();
                MessageModel model = new MessageModel();
                model.setMessage(message);

                db.collection("Message").add(model).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(AddMessageAdmin.this, "Message posted successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddMessageAdmin.this,AdminPanel.class));
                    }
                });

            }

        });

    }

}