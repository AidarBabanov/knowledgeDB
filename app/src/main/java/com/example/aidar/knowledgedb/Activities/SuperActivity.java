package com.example.aidar.knowledgedb.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.aidar.knowledgedb.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SuperActivity extends AppCompatActivity {

    TextView answerTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super);
        String title = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        getSupportActionBar().setTitle(title);

        answerTextView = (TextView) findViewById(R.id.answerTextView);
        String str = getIntent().getStringExtra(Intent.EXTRA_INDEX);

        answerTextView.setText(str);
    }
}
