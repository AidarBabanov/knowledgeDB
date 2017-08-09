package com.example.aidar.knowledgedb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.aidar.knowledgedb.R;

public class AnswerActivity extends AppCompatActivity {

    TextView answerTextView;
    Button helpedButton;
    Button notHelpedButton;
    Button startNewSearchButton;
    Button dontStartnewSearchButton;
    Button continueSearchButton;
    Button dontcontinueSearchButton;
    ConstraintLayout footerHelpedConstraintlayout;
    ConstraintLayout footerNewSearchConstraintlayout;
    ConstraintLayout footerContiuneSearchConstraintlayout;
    FrameLayout footerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        answerTextView = (TextView) findViewById(R.id.answerTextView);
        helpedButton = (Button) findViewById(R.id.helped_button);
        notHelpedButton = (Button) findViewById(R.id.not_helped_button);
        startNewSearchButton = (Button) findViewById(R.id.start_new_search);
        dontStartnewSearchButton = (Button) findViewById(R.id.dont_start_new_search);
        continueSearchButton = (Button) findViewById(R.id.continue_search);
        dontcontinueSearchButton = (Button) findViewById(R.id.dont_continue_search);

        footerFrameLayout = (FrameLayout) findViewById(R.id.footer);
        footerHelpedConstraintlayout = (ConstraintLayout) findViewById(R.id.footer_helped);
        footerNewSearchConstraintlayout = (ConstraintLayout) findViewById(R.id.footer_start_new_search);
        footerContiuneSearchConstraintlayout = (ConstraintLayout) findViewById(R.id.footer_continue_search);

        String text = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        answerTextView.setText(text);
        answerTextView.setMovementMethod(new ScrollingMovementMethod());

        helpedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                footerNewSearchConstraintlayout.setVisibility(View.VISIBLE);
                footerHelpedConstraintlayout.setVisibility(View.INVISIBLE);
            }
        });

        notHelpedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                footerContiuneSearchConstraintlayout.setVisibility(View.VISIBLE);
                footerHelpedConstraintlayout.setVisibility(View.INVISIBLE);
            }
        });

        startNewSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewMainActivity();
            }
        });

        dontStartnewSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                footerFrameLayout.animate().translationY(96);
                footerNewSearchConstraintlayout.setVisibility(View.INVISIBLE);
            }
        });

        continueSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dontcontinueSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                footerFrameLayout.animate().translationY(96);
                footerContiuneSearchConstraintlayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    void startNewMainActivity() {
        Intent intentToStartSolveIssueActivity = new Intent(this, MainActivity.class);
        startActivity(intentToStartSolveIssueActivity);
    }

}
