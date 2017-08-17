package com.example.aidar.knowledgedb;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by aidar on 8/17/17.
 */

public class KnowledgeDB extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
