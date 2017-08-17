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
import com.example.aidar.knowledgedb.DatabaseManager2;
import com.example.aidar.knowledgedb.KnowledgeDB;
import com.example.aidar.knowledgedb.Question;
import com.example.aidar.knowledgedb.R;
import com.example.aidar.knowledgedb.SwipeStackAdapter;
import com.google.firebase.database.DataSnapshot;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import link.fls.swipestack.SwipeStack;

public class SolveIssueActivity extends AppCompatActivity implements SwipeStack.SwipeStackListener, SwipeStackAdapter.ButtonSwipeOnClickHandler, DatabaseManager.AfterloadHandler {

    SwipeStack swipeStack;
    SwipeStackAdapter<Question> swipeStackAdapter;
    DatabaseManager2 databaseManager2;
    ProgressBar progressBar;
    LinearLayout didntFindLinearLayout;
    Button tryAgain;
    Boolean saidYes = false;

    DataSnapshot companySnapshot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_issue);


        tryAgain = (Button) findViewById(R.id.try_again);
        didntFindLinearLayout = (LinearLayout) findViewById(R.id.didnt_find_LinearLayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        swipeStack = (SwipeStack) findViewById(R.id.swipeStack);
        swipeStack.setListener(this);

        companySnapshot = DatabaseManager2.getInstance().getTransferSnapshot();
        String companyName = (String) companySnapshot.child(KnowledgeDB.getResourceString(R.string.dbTitle)).getValue();
        String issue = getIntent().getStringExtra(KnowledgeDB.getResourceString(R.string.javaIssue));
        if (issue == null) issue = "";

        databaseManager2 = DatabaseManager2.getInstance();
        swipeStackAdapter = new SwipeStackAdapter<>(this, this);
        List<Question> questionList = databaseManager2.findCompanyQuestions(databaseManager2.getTransferSnapshot(), issue);
        Log.i("LIST SIZE", questionList.size()+"");
        Collections.sort(questionList);
        Collections.reverse(questionList);
        swipeStackAdapter.setData(questionList);
        swipeStack.setAdapter(swipeStackAdapter);

        Timer loadingTimer = new Timer();

        TimerTask timerTask = new TimerTask() {
            int time = 0;

            @Override
            public void run() {
                time++;
                Log.i("Timer", time + "");
                if (swipeStackAdapter.getCount() > 0) cancel();
                if (time >= 15) {
                    if (swipeStackAdapter.getCount() == 0) runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            swipeStack.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            didntFindLinearLayout.setVisibility(View.VISIBLE);
                        }
                    });

                    cancel();
                }
            }
        };

        loadingTimer.scheduleAtFixedRate(timerTask, 0, 1000);

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
//        if (!saidYes) {
//            swipeStack.setVisibility(View.GONE);
//            progressBar.setVisibility(View.GONE);
//            didntFindLinearLayout.setVisibility(View.VISIBLE);
//        }
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
    public void doAfterLoad() {
        if (swipeStackAdapter.getCount() > 0) progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }
}
