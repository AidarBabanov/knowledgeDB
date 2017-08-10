package com.example.aidar.knowledgedb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.example.aidar.knowledgedb.R;

public class AnswerActivity extends AppCompatActivity {

    TextView answerTextView;
    Button helpedButton;
    Button notHelpedButton;
    Button startNewSearchButton;
    Button dontStartnewSearchButton;
    Button continueSearchButton;
    Button dontContinueSearchButton;
    ConstraintLayout footerHelpedConstraintlayout;
    ConstraintLayout footerNewSearchConstraintlayout;
    ConstraintLayout footerContiuneSearchConstraintlayout;
    FrameLayout footerFrameLayout;
    long fadeInDuration = 150;
    long fadeOutDuration  = 150;

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
        dontContinueSearchButton = (Button) findViewById(R.id.dont_continue_search);

        footerFrameLayout = (FrameLayout) findViewById(R.id.footer);
        footerHelpedConstraintlayout = (ConstraintLayout) findViewById(R.id.footer_helped);
        footerNewSearchConstraintlayout = (ConstraintLayout) findViewById(R.id.footer_start_new_search);
        footerContiuneSearchConstraintlayout = (ConstraintLayout) findViewById(R.id.footer_continue_search);

        final Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setStartOffset(fadeOutDuration);
        fadeIn.setDuration(fadeInDuration);

        final Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(fadeOutDuration);

        String text = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        answerTextView.setText(text);
        answerTextView.setMovementMethod(new ScrollingMovementMethod());

        helpedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                footerHelpedConstraintlayout.startAnimation(fadeOut);
                footerHelpedConstraintlayout.setVisibility(View.INVISIBLE);

                footerNewSearchConstraintlayout.startAnimation(fadeIn);
                footerNewSearchConstraintlayout.setVisibility(View.VISIBLE);

            }
        });

        notHelpedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                footerHelpedConstraintlayout.startAnimation(fadeOut);
                footerHelpedConstraintlayout.setVisibility(View.INVISIBLE);

                footerContiuneSearchConstraintlayout.startAnimation(fadeIn);
                footerContiuneSearchConstraintlayout.setVisibility(View.VISIBLE);
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
                footerFrameLayout.startAnimation(fadeOut);
                footerFrameLayout.setVisibility(View.INVISIBLE);
            }
        });

        continueSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dontContinueSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                footerFrameLayout.animate().translationY(96);
                footerFrameLayout.startAnimation(fadeOut);
                footerFrameLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    void startNewMainActivity() {
        Intent intentToStartSolveIssueActivity = new Intent(this, MainActivity.class);
        startActivity(intentToStartSolveIssueActivity);
    }

}
