package com.nFactorial.aidar.knowledgedb;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

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

    @NonNull
    public static String getResourceString(@StringRes int id){
        return appContext.getResources().getString(id);
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static void setAppContext(Context appContext) {
        KnowledgeDB.appContext = appContext;
    }
}
