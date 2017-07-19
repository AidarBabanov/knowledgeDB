package com.example.aidar.knowledgedb.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.aidar.knowledgedb.R;
import com.example.aidar.knowledgedb.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class CompanyActivity extends AppCompatActivity {

    List<String> list;

    RecyclerView topicRecyclerView;
    RecyclerView.Adapter rvAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        topicRecyclerView = (RecyclerView) findViewById(R.id.topic_recyclerView);
        topicRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<String>();
        for(int i=0;i<10;i++)list.add(i+"");
        rvAdapter = new RecyclerViewAdapter(list, this);
        topicRecyclerView.setAdapter(rvAdapter);
    }
}
