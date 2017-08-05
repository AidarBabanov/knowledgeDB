package com.example.aidar.knowledgedb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import com.example.aidar.knowledgedb.R;

public class AnswerActivity extends AppCompatActivity {

    TextView answerTextView;
    Button helpedButton;
    Button notHelpedButton;
    ConstraintLayout footerConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        answerTextView = (TextView) findViewById(R.id.answerTextView);
        helpedButton = (Button) findViewById(R.id.helped_button);
        notHelpedButton = (Button) findViewById(R.id.not_helped_button);
        footerConstraintLayout = (ConstraintLayout) findViewById(R.id.footer);

        String text = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        answerTextView.setText(text);
        answerTextView.setMovementMethod(new ScrollingMovementMethod());
    }

}
