package com.example.aidar.knowledgedb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText searchCompanyEditText;
    Button searchCompanyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchCompanyEditText = (EditText) findViewById(R.id.search_company_editText);
        searchCompanyButton = (Button) findViewById(R.id.search_company_button);
        searchCompanyButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Log.i("Test", "Hello");
                Toast toast = Toast.makeText(getApplicationContext(), "Pressed", Toast.LENGTH_LONG);
                toast.show();

            }
        });
    }
}
