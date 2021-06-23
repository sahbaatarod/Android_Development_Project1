package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminPanel extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<MessageModel> messageModels = new ArrayList<>();
    RecyclerView recyclerView;
    List<String> list;
    AdminMessageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        floatingActionButton = findViewById(R.id.addmessage);
        recyclerView = (RecyclerView)findViewById(R.id.messagerl);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new AdminMessageAdapter(messageModels,this);
        recyclerView.setAdapter(adapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPanel.this,AddMessageAdmin.class));
            }
        });
        db.collection("Message").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list) {
                            MessageModel model = d.toObject(MessageModel.class);
                            messageModels.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });


    }
}