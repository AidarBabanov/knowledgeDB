package com.example.aidar.knowledgedb.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.aidar.knowledgedb.DatabaseManager;
import com.example.aidar.knowledgedb.DataGetterAsyncTask;
import com.example.aidar.knowledgedb.KnowledgeDB;
import com.example.aidar.knowledgedb.Question;
import com.example.aidar.knowledgedb.R;
import com.example.aidar.knowledgedb.SwipeStackAdapter;
import com.google.firebase.database.DataSnapshot;

import java.util.Collections;
import java.util.List;

import link.fls.swipestack.SwipeStack;

public class SolveIssueActivity extends AppCompatActivity implements SwipeStack.SwipeStackListener, SwipeStackAdapter.ButtonSwipeOnClickHandler, DataGetterAsyncTask.PostExecuteListener {

    SwipeStack swipeStack;
    SwipeStackAdapter<Question> swipeStackAdapter;
    DatabaseManager databaseManager;
    LinearLayout didntFindLinearLayout;
    Button tryAgain;
    Boolean saidYes = false;
    List<Question> questionList;
    ProgressBar progressBar;
    DataGetterAsyncTask dataGetterAsyncTask;
    DataSnapshot companySnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_issue);


        tryAgain = (Button) findViewById(R.id.try_again);
        didntFindLinearLayout = (LinearLayout) findViewById(R.id.didnt_find_LinearLayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        swipeStack = (SwipeStack) findViewById(R.id.swipeStack);
        swipeStack.setListener(this);

        companySnapshot = DatabaseManager.getInstance().getTransferSnapshot();
        String companyName = (String) companySnapshot.child(KnowledgeDB.getResourceString(R.string.dbTitle)).getValue();
        String issue = getIntent().getStringExtra(KnowledgeDB.getResourceString(R.string.javaIssue));
        if (issue == null) issue = "";

        databaseManager = DatabaseManager.getInstance();
        dataGetterAsyncTask = new DataGetterAsyncTask();
        dataGetterAsyncTask.setParams(databaseManager.getTransferSnapshot(), issue, this);
        dataGetterAsyncTask.execute();
        swipeStackAdapter = new SwipeStackAdapter<>(this, this);
        //Log.i("LIST SIZE", questionList.size()+"");
        swipeStack.setAdapter(swipeStackAdapter);

//        Timer loadingTimer = new Timer();
//
//        TimerTask timerTask = new TimerTask() {
//            int time = 0;
//
//            @Override
//            public void run() {
//                time++;
//                Log.i("Timer", time + "");
//                if (swipeStackAdapter.getCount() > 0) cancel();
//                if (time >= 15) {
//                    if (swipeStackAdapter.getCount() == 0) runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            swipeStack.setVisibility(View.GONE);
//                            progressBar.setVisibility(View.GONE);
//                            didntFindLinearLayout.setVisibility(View.VISIBLE);
//                        }
//                    });
//
//                    cancel();
//                }
//            }
//        };
//
//        loadingTimer.scheduleAtFixedRate(timerTask, 0, 1000);

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onViewSwipedToLeft(int position) {
        Log.i("SWIPED LEFT", String.valueOf(position));
        saidYes = false;
    }

    @Override
    public void onViewSwipedToRight(int position) {
        Log.i("SWIPED RIGHT", String.valueOf(position));
        saidYes = true;
        startAnswerActivity(position);
    }

    @Override
    public void onStackEmpty() {
        swipeStack.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        didntFindLinearLayout.setVisibility(View.VISIBLE);
    }

    private void startAnswerActivity(int position) {
        Intent intentToStartSolveIssueActivity = new Intent(this, AnswerActivity.class);
        Question question = swipeStackAdapter.getItem(position);
        intentToStartSolveIssueActivity.putExtra(Intent.EXTRA_TEXT, question.getAnswer());
        startActivity(intentToStartSolveIssueActivity);
    }


    @Override
    public void yesButtonSwiper() {
        swipeStack.swipeTopViewToRight();
    }

    @Override
    public void noButtonSwiper() {
        swipeStack.swipeTopViewToLeft();
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

    @Override
    public void onTaskCompleted() {
        questionList = dataGetterAsyncTask.getResult();
        Collections.sort(questionList);
        Collections.reverse(questionList);
        if (questionList.isEmpty()) {
            swipeStack.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            didntFindLinearLayout.setVisibility(View.VISIBLE);
        }
        if(!questionList.isEmpty()){
            progressBar.setVisibility(View.GONE);
            didntFindLinearLayout.setVisibility(View.GONE);
            swipeStack.setVisibility(View.VISIBLE);
        }
        swipeStackAdapter.setData(questionList);
        swipeStackAdapter.notifyDataSetChanged();
    }
}
