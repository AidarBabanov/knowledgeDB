package com.example.aidar.knowledgedb.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aidar.knowledgedb.R;
import com.example.aidar.knowledgedb.SwipeStackAdapter;

import java.util.ArrayList;
import java.util.List;

import link.fls.swipestack.SwipeStack;

public class SolveIssueActivity extends AppCompatActivity implements SwipeStack.SwipeStackListener {

    SwipeStack swipeStack;
    List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_issue);

        swipeStack = (SwipeStack) findViewById(R.id.swipeStack);
        data = new ArrayList<>();
        for(int i=0;i<10;i++){
            data.add(i+"");
        }
        SwipeStackAdapter swipeStackAdapter = new SwipeStackAdapter(data,this);
        swipeStack.setAdapter(swipeStackAdapter);
    }



    @Override
    public void onViewSwipedToLeft(int position) {

    }

    @Override
    public void onViewSwipedToRight(int position) {

    }

    @Override
    public void onStackEmpty() {

    }
}
