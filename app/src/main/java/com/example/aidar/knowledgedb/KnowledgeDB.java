package com.example.aidar.knowledgedb;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by aidar on 8/17/17.
 */

public class KnowledgeDB extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        appContext = this;
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static void setAppContext(Context appContext) {
        KnowledgeDB.appContext = appContext;
    }
}
