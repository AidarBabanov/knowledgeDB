package com.example.aidar.knowledgedb;

import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;

import java.util.List;

/**
 * Created by aidar on 8/18/17.
 */

public class DataGetterAsyncTask extends AsyncTask<Void, Void, Void> {

    private DataSnapshot dataSnapshot;
    private String issue;
    private DatabaseManager databaseManager;
    private List<Question> result;
    private PostExecuteListener postExecuteListener;

    public List<Question> getResult() {
        return result;
    }

    public interface PostExecuteListener{
        void onTaskCompleted();
    }

    public DataGetterAsyncTask(){
        databaseManager = DatabaseManager.getInstance();
    }

    public void setParams(DataSnapshot dataSnapshot, String issue, PostExecuteListener postExecuteListener){
        this.dataSnapshot = dataSnapshot;
        this.issue = issue;
        this.postExecuteListener = postExecuteListener;
    }

    @Override
    protected Void doInBackground(Void... params) {
        result = databaseManager.findCompanyQuestions(dataSnapshot, issue);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        postExecuteListener.onTaskCompleted();
    }
}
