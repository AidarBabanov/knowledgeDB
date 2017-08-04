package com.example.aidar.knowledgedb.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.aidar.knowledgedb.DatabaseManager;
import com.example.aidar.knowledgedb.R;
import com.example.aidar.knowledgedb.SwipeStackAdapter;

import java.util.ArrayList;
import java.util.List;

import link.fls.swipestack.SwipeStack;

public class SolveIssueActivity extends AppCompatActivity implements SwipeStack.SwipeStackListener {

    SwipeStack swipeStack;
    List<String> data;
    SwipeStackAdapter swipeStackAdapter;
    DatabaseManager databaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_issue);

        swipeStack = (SwipeStack) findViewById(R.id.swipeStack);
        swipeStack.setListener(this);
        databaseManager = new DatabaseManager();
        databaseManager.findAllQuestionsInCompany("Казахтелеком");
//        data = new ArrayList<>();
//        for(int i=0;i<10;i++){
//            data.add(i+"");
//        }
        swipeStackAdapter = new SwipeStackAdapter(data,this);
        swipeStack.setAdapter(swipeStackAdapter);
    }



    @Override
    public void onViewSwipedToLeft(int position) {
        Log.i("SWIPED LEFT", position+"");
        swipeStackAdapter.insertItemIntoStack(position+1, "SUKA");
    }

    @Override
    public void onViewSwipedToRight(int position) {
        Log.i("SWIPED RIGHT", position+"");
        startAnswerActivity();
    }

    @Override
    public void onStackEmpty() {
    }

    private void startAnswerActivity(){
        Intent intentToStartSolveIssueActivity = new Intent(this, AnswerActivity.class);
        //intentToStartSolveIssueActivity.putExtra(Intent.EXTRA_TEXT, companyName);
        startActivity(intentToStartSolveIssueActivity);
    }
}
