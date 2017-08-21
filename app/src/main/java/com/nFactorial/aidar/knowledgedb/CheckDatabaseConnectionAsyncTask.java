package com.nFactorial.aidar.knowledgedb;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

/**
 * Created by aidar on 8/21/17.
 */

public class CheckDatabaseConnectionAsyncTask extends AsyncTask<Void, Void, Void> {

    private DatabaseManager databaseManager;
    private Context startUp;
    private CheckDatabaseResponces checkDatabaseResponces;
    private Activity startUpActivity;

    public interface CheckDatabaseResponces {
        public void alertNoInternet();

        public void alertDownloading();

        public void onComplete();
    }

    public CheckDatabaseConnectionAsyncTask(Context startUp, CheckDatabaseResponces checkDatabaseResponces, Activity startUpActivity) {
        this.startUp = startUp;
        this.checkDatabaseResponces = checkDatabaseResponces;
        this.startUpActivity = startUpActivity;
    }

    @Override
    protected Void doInBackground(Void... params) {
        boolean messageRepeat = false;
        databaseManager = DatabaseManager.getInstance();
        if (databaseManager.getRootSnapshot() == null) {
            while (!isNetworkAvailable()) {
                if (!messageRepeat) {
                    startUpActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            checkDatabaseResponces.alertNoInternet();

                        }
                    });
                    messageRepeat = true;
                }
            }
            messageRepeat = false;
            while (databaseManager.getRootSnapshot() == null) {
                if (!messageRepeat) {
                    startUpActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            checkDatabaseResponces.alertDownloading();
                        }
                    });
                }
                messageRepeat = true;
            }


        }
        return null;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) startUp.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        checkDatabaseResponces.onComplete();
    }
}
