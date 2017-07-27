package com.example.aidar.knowledgedb.Activities;


import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aidar.knowledgedb.R;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MaterialSearchBar.OnSearchActionListener {

    private List<String> lastSearches;
    MaterialSearchBar searchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchBar = (MaterialSearchBar) findViewById(R.id.search_company_searchBar);
        searchBar.setOnSearchActionListener(this);

    }


    @Override
    public void onSearchStateChanged(boolean enabled) {
//        String s = enabled ? "enabled" : "disabled";
//        Toast.makeText(MainActivity.this, "Search " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        Intent intentToStartCompanyActivity = new Intent(this, CompanyActivity.class);
        intentToStartCompanyActivity.putExtra(Intent.EXTRA_TEXT, text.toString());
        startActivity(intentToStartCompanyActivity);
    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }
}
