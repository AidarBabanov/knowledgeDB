package com.example.aidar.knowledgedb.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aidar.knowledgedb.R;

public class IssueActivity extends AppCompatActivity {

    EditText issueEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        String title = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        getSupportActionBar().setTitle(title);
        issueEditText = (EditText) findViewById(R.id.issue_desription_editText);

        //Make editText multiline, other way doesn't work with keyboard
        issueEditText.setHorizontallyScrolling(false);
        issueEditText.setMaxLines(Integer.MAX_VALUE);
        issueEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        //Search button in keyboard
        issueEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    startSolveIssueActivity();
                    return true;
                }
                return false;
            }
        });
    }

    private void startSolveIssueActivity(){
        Intent intentToStartSolveIssueActivity = new Intent(this, SolveIssueActivity.class);
        //intentToStartSolveIssueActivity.putExtra(Intent.EXTRA_TEXT, companyName);
        startActivity(intentToStartSolveIssueActivity);
    }
}
