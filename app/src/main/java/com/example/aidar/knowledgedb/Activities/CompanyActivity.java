package com.example.aidar.knowledgedb.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aidar.knowledgedb.R;
import com.example.aidar.knowledgedb.RecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CompanyActivity extends AppCompatActivity implements RecyclerViewAdapter.RecyclerViewAdapterOnClickHandler {

    List<String> list;

    RecyclerView topicRecyclerView;
    RecyclerView.Adapter rvAdapter;
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = rootRef.child("condition");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        topicRecyclerView = (RecyclerView) findViewById(R.id.topic_recyclerView);
        topicRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        String title = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        getSupportActionBar().setTitle(title);



        list = new ArrayList<String>();
        for(int i=0;i<10;i++)list.add(i+"");
        rvAdapter = new RecyclerViewAdapter(list, this);
        topicRecyclerView.setHasFixedSize(true);
        topicRecyclerView.setAdapter(rvAdapter);
    }


    @Override
    public void onClick(String topic) {
        Log  log = null;
        log.i("RECEIVED", topic);
        Class destinationClass = CompanyActivity.class;
        Intent intentToStartDetailActivity = new Intent(this, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, topic);
        startActivity(intentToStartDetailActivity);
    }

    @Override
    protected void onStart() {
        super.onStart();
        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
