package com.example.aidar.knowledgedb.Activities;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.aidar.knowledgedb.R;

public class MainActivity extends AppCompatActivity {

    EditText searchCompanyEditText;
    Button searchCompanyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchCompanyEditText = (EditText) findViewById(R.id.search_company_editText);
        searchCompanyButton = (Button) findViewById(R.id.search_company_button);
    }

    public void SearchCompany(View view) {
        Intent intentToStartCompanyActivity  = new Intent(this, CompanyActivity.class);
        intentToStartCompanyActivity.putExtra("", "");
        startActivity(intentToStartCompanyActivity);
    }
}
