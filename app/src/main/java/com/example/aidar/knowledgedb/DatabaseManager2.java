package com.example.aidar.knowledgedb;

import android.content.res.Resources;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by aidar on 8/4/17.
 */

public class DatabaseManager2 {

    private static DatabaseManager2 instance;
    private DatabaseReference rootReference = FirebaseDatabase.getInstance().getReference();
    private DataSnapshot rootSnapshot;

    public DatabaseManager2() {
        rootReference.keepSynced(true);
        rootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rootSnapshot = dataSnapshot;
                Log.i("Firebase data changed", dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase error", databaseError.toString());
            }
        });
    }

    public static DatabaseManager2 getInstance() {
        if (instance == null) instance = new DatabaseManager2();
        return instance;
    }

    public DataSnapshot findCompanyByName(String companyName) {
        String dbCompanies = KnowledgeDB.getAppContext().getResources().getString(R.string.dbCompanies);
        DataSnapshot companiesSnapshot = rootSnapshot.child(dbCompanies);
        DataSnapshot resultSnapshot = companiesSnapshot.child("0");
        double maxSimilarity=-1;
        for(DataSnapshot companySnapshot: companiesSnapshot.getChildren()){
            String dbTitle = KnowledgeDB.getAppContext().getResources().getString(R.string.dbTitle);
            String currentCompanyName = (String) companySnapshot.child(dbTitle).getValue();
            double currentSimilarity = getSimilarity(companyName, currentCompanyName);
            if(currentSimilarity>=maxSimilarity){
                resultSnapshot = companySnapshot;
                maxSimilarity = currentSimilarity;
            }
        }

        return resultSnapshot;
    }

    static int distLowenstein(String S1, String S2) {
        int m = S1.length(), n = S2.length();
        int[] D1;
        int[] D2 = new int[n + 1];

        for (int i = 0; i <= n; i++)
            D2[i] = i;

        for (int i = 1; i <= m; i++) {
            D1 = D2;
            D2 = new int[n + 1];
            for (int j = 0; j <= n; j++) {
                if (j == 0) D2[j] = i;
                else {
                    int cost = (S1.charAt(i - 1) != S2.charAt(j - 1)) ? 1 : 0;
                    if (D2[j - 1] < D1[j] && D2[j - 1] < D1[j - 1] + cost)
                        D2[j] = D2[j - 1] + 1;
                    else if (D1[j] < D1[j - 1] + cost)
                        D2[j] = D1[j] + 1;
                    else
                        D2[j] = D1[j - 1] + cost;
                }
            }
        }
        return D2[n];
    }

    static double getSimilarity(String S1, String S2) {
        double similar;
        int totalLength = Math.max(S1.length(), S2.length());
        similar = 1.0 * (totalLength - distLowenstein(S1, S2)) / totalLength;
        return similar;
    }

    public DatabaseReference getRootReference() {
        return rootReference;
    }

    public void setRootReference(DatabaseReference rootReference) {
        this.rootReference = rootReference;
    }

    public DataSnapshot getRootSnapshot() {
        return rootSnapshot;
    }

    public void setRootSnapshot(DataSnapshot rootSnapshot) {
        this.rootSnapshot = rootSnapshot;
    }
}
