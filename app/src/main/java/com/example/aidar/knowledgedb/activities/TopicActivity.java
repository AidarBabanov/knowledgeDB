package com.example.aidar.knowledgedb.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.aidar.knowledgedb.DatabaseManager;
import com.example.aidar.knowledgedb.KnowledgeDB;
import com.example.aidar.knowledgedb.ListItem;
import com.example.aidar.knowledgedb.Question;
import com.example.aidar.knowledgedb.R;
import com.example.aidar.knowledgedb.RecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;

import java.util.LinkedList;
import java.util.List;

public class TopicActivity extends AppCompatActivity implements RecyclerViewAdapter.RecyclerViewAdapterOnClickHandler {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    DatabaseManager databaseManager;
    List<ListItem> listItems;
    int state;  //state is needed to understand where we are. Category or sub category

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        getSupportActionBar().setTitle(getIntent().getStringExtra(KnowledgeDB.getResourceString(R.string.javaTitle)));

        databaseManager = DatabaseManager.getInstance();
        listItems = getList(databaseManager.getTransferSnapshot());
        recyclerViewAdapter = new RecyclerViewAdapter(listItems, this);
        recyclerView = (RecyclerView) findViewById(R.id.topic_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }

    private List<ListItem> getList(DataSnapshot highTopicSnapshot) {
        state = 1;
        DataSnapshot topicsSnapshot = highTopicSnapshot.child(KnowledgeDB.getResourceString(R.string.dbSubCats));
        if (topicsSnapshot.getValue() == null) {
            state = 2;
            topicsSnapshot = highTopicSnapshot.child(KnowledgeDB.getResourceString(R.string.dbQuestions));
        }

        List<ListItem> listItems = new LinkedList<ListItem>();
        for (DataSnapshot dataSnapshot : topicsSnapshot.getChildren()) {
            ListItem listItem = new ListItem();
            if (state == 1)
                listItem.setText(dataSnapshot.child(KnowledgeDB.getResourceString(R.string.dbTitle)).getValue().toString());
            if (state == 2)
                listItem.setText(dataSnapshot.child(KnowledgeDB.getResourceString(R.string.dbQuestion)).getValue().toString());
            listItem.setDataSnapshot(dataSnapshot);
            listItems.add(listItem);
        }
        return listItems;
    }

    @Override
    public void onClick(int position) {
        if (state == 1) {
            startTopicActivity(position);
        }
        if (state == 2) {
            startAnswerActivity(position);
        }
    }

    private void startTopicActivity(int position) {
        ListItem listItem = listItems.get(position);
        Intent intentToStartTopicActivity = new Intent(this, TopicActivity.class);
        intentToStartTopicActivity.putExtra(KnowledgeDB.getResourceString(R.string.javaTitle), listItem.getText());
        databaseManager.setTransferSnapshot(listItem.getDataSnapshot());
        startActivity(intentToStartTopicActivity);
    }

    private void startAnswerActivity(int position) {
        Intent intentToStartSolveIssueActivity = new Intent(this, AnswerActivity.class);
        ListItem listItem = listItems.get(position);
        String answer = listItem.getDataSnapshot().child(KnowledgeDB.getResourceString(R.string.dbAnswer)).getValue().toString();
        intentToStartSolveIssueActivity.putExtra(KnowledgeDB.getResourceString(R.string.javaQuestion), listItem.getText());
        intentToStartSolveIssueActivity.putExtra(KnowledgeDB.getResourceString(R.string.javaAnswer), answer);
        startActivity(intentToStartSolveIssueActivity);
    }
}
