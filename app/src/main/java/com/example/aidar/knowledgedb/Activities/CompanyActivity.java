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
import java.util.Objects;

import static android.R.attr.path;

public class CompanyActivity extends AppCompatActivity implements RecyclerViewAdapter.RecyclerViewAdapterOnClickHandler {

    List<String> list;

    RecyclerView topicRecyclerView;
    RecyclerViewAdapter rvAdapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference localRef;
    String refPath;
    String current = "";
    String answer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        topicRecyclerView = (RecyclerView) findViewById(R.id.topic_recyclerView);
        topicRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        String title = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        getSupportActionBar().setTitle(title);


        list = new ArrayList<String>();
        //for(int i=0;i<10;i++)list.add(i+"");
        rvAdapter = new RecyclerViewAdapter(list, this);
        topicRecyclerView.setHasFixedSize(true);
        topicRecyclerView.setAdapter(rvAdapter);
    }


//    @Override
//    public void onClick(String topic, int position) {
//        if (current.equals("questions")) {
//            Class destinationClass = SuperActivity.class;
//            Intent intentToStartDetailActivity = new Intent(this, destinationClass);
//            intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, topic);
//            DatabaseReference ref2 = localRef.child("/"+position+"/answer");
//            ref2.addValueEventListener(new ValueEventListener() {
//
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Log.i("DataSnapshot", dataSnapshot.toString());
//                    answer = (String) dataSnapshot.getValue();
//                    Log.i("SuperPuperAnswer", answer);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//
//            intentToStartDetailActivity.putExtra(Intent.EXTRA_INDEX, answer);
//            Log.i("SuperAnswer", String.valueOf(answer.length()));
//            startActivity(intentToStartDetailActivity);
//        } else {
//            Class destinationClass = CompanyActivity.class;
//            Intent intentToStartDetailActivity = new Intent(this, destinationClass);
//            intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, topic);
//            current = localRef.getKey();
//            String next = "";
//            if (current.equals("categories")) next = "subcats";
//            else if (current.equals("subcats")) next = "questions";
//            String path = refPath + "/" + position + "/" + next;
//            intentToStartDetailActivity.putExtra(Intent.EXTRA_INDEX, path);
//            startActivity(intentToStartDetailActivity);
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();
        refPath = getIntent().getStringExtra(Intent.EXTRA_INDEX);
        localRef = databaseReference.child(refPath);
        Log.i("DB reference", localRef.getKey());
        localRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                current = localRef.getKey();
                for (DataSnapshot topicSnapShot : dataSnapshot.getChildren()) {
                    Log.i("DataSnapshot", topicSnapShot.toString());
                    String topic = (String) topicSnapShot.child("title").getValue();
                    if (current.equals("questions"))
                        topic = (String) topicSnapShot.child("question").getValue();
                    Log.i("DB data", topic);
                    list.add(topic);
                }
                rvAdapter.setData(list);
                Log.i("List", list.toString());
                rvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(String listItemName) {

    }
}
