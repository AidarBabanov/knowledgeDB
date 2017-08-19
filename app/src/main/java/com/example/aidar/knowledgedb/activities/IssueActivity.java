package com.example.aidar.knowledgedb.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aidar.knowledgedb.DatabaseManager;
import com.example.aidar.knowledgedb.KnowledgeDB;
import com.example.aidar.knowledgedb.R;
import com.google.firebase.database.DataSnapshot;

public class IssueActivity extends AppCompatActivity {

    EditText issueEditText;
    DatabaseManager databaseManager;
    DataSnapshot companySnapshot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        //companySnapshot = DatabaseManager.getInstance().getTransferSnapshot();
        databaseManager = DatabaseManager.getInstance();

        //final String companyName = (String) companySnapshot.child(KnowledgeDB.getResourceString(R.string.dbTitle)).getValue();
        getSupportActionBar().setTitle("Казахтелеком");
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
        companySnapshot = databaseManager.findCompanyByName("Казахтелеком".toString());
        Intent intentToStartSolveIssueActivity = new Intent(this, SolveIssueActivity.class);
        DatabaseManager.getInstance().setTransferSnapshot(companySnapshot);
        intentToStartSolveIssueActivity.putExtra(KnowledgeDB.getResourceString(R.string.javaIssue), issueEditText.getText().toString());
        startActivity(intentToStartSolveIssueActivity);
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
