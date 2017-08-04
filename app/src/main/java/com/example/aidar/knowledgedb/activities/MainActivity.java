package com.example.aidar.knowledgedb.activities;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.aidar.knowledgedb.R;
import com.example.aidar.knowledgedb.RecyclerViewAdapter;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MaterialSearchBar.OnSearchActionListener, RecyclerViewAdapter.RecyclerViewAdapterOnClickHandler {

    MaterialSearchBar searchBar;
    RecyclerView popularCompaniesRV;
    RecyclerViewAdapter rvAdapter;
    List<String> popularCompaniesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchBar = (MaterialSearchBar) findViewById(R.id.search_company_searchBar);
        searchBar.setOnSearchActionListener(this);
        popularCompaniesRV = (RecyclerView) findViewById(R.id.popular_companies_recyclerView);
        popularCompaniesRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        popularCompaniesList = new ArrayList<String>();
        for (int i = 0; i < 10; i++) popularCompaniesList.add("Казахтелеком");
        rvAdapter = new RecyclerViewAdapter(popularCompaniesList, this);
        popularCompaniesRV.setAdapter(rvAdapter);
    }


    @Override
    public void onSearchStateChanged(boolean enabled) {
//        String s = enabled ? "enabled" : "disabled";
//        Toast.makeText(MainActivity.this, "Search " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        startIssueActivity(text.toString());
    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }

    @Override
    public void onClick(String listItemName) {
        startIssueActivity(listItemName);
    }

    private void startIssueActivity(String companyName) {
        Intent intentToStartIssueActivity = new Intent(this, IssueActivity.class);
        intentToStartIssueActivity.putExtra(Intent.EXTRA_TEXT, companyName);
        startActivity(intentToStartIssueActivity);

    }
}
