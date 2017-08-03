package com.example.aidar.knowledgedb.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.aidar.knowledgedb.R;
import com.example.aidar.knowledgedb.SwipeStackAdapter;

import java.util.ArrayList;
import java.util.List;

import link.fls.swipestack.SwipeStack;

public class SolveIssueActivity extends AppCompatActivity implements SwipeStack.SwipeStackListener {

    SwipeStack swipeStack;
    List<String> data;
    SwipeStackAdapter swipeStackAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_issue);

        swipeStack = (SwipeStack) findViewById(R.id.swipeStack);
        swipeStack.setListener(this);
        data = new ArrayList<>();
        for(int i=0;i<10;i++){
            data.add(i+"");
        }
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
    }

    @Override
    public void onStackEmpty() {

    }
}
