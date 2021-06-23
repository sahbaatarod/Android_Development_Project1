package com.example.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class WorkActivity extends AppCompatActivity {
    List<MessageModel> messageModels = new ArrayList<>();
    RecyclerView recyclerView;
    ClientAdapter clientAdapter;
    Button logoutbtn;
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        logoutbtn = findViewById(R.id.logoutbtn);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                startActivity(new Intent(WorkActivity.this,LoginActivity.class));
            }
        });
        recyclerView = findViewById(R.id.messagelist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        clientAdapter = new ClientAdapter(this,messageModels);
        recyclerView.setAdapter(clientAdapter);
        db.collection("Message").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot d:list) {
                    MessageModel model = d.toObject(MessageModel.class);
                    messageModels.add(model);
                }
                clientAdapter.notifyDataSetChanged();
            }
        });
    }
}