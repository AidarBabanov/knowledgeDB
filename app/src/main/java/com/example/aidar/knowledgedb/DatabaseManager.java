package com.example.aidar.knowledgedb;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by aidar on 8/4/17.
 */

public class DatabaseManager {

    private static DatabaseManager instance;
    private DatabaseReference rootReference = FirebaseDatabase.getInstance().getReference();
    private DataSnapshot rootSnapshot;
    private DataSnapshot transferSnapshot;
    private final static String SPLIT_BY = " ";

    public DatabaseManager() {
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

    public static DatabaseManager getInstance() {
        if (instance == null) instance = new DatabaseManager();
        return instance;
    }

    public DataSnapshot findCompanyByName(String companyName) {
        String dbCompanies = KnowledgeDB.getResourceString(R.string.dbCompanies);
        DataSnapshot companiesSnapshot = rootSnapshot.child(dbCompanies);
        DataSnapshot resultSnapshot = companiesSnapshot.child("0");
        double maxSimilarity = -1;
        for (DataSnapshot companySnapshot : companiesSnapshot.getChildren()) {
            String dbTitle = KnowledgeDB.getResourceString(R.string.dbTitle);
            String currentCompanyName = (String) companySnapshot.child(dbTitle).getValue();
            double currentSimilarity = getSimilarity(companyName, currentCompanyName);
            if (currentSimilarity >= maxSimilarity) {
                resultSnapshot = companySnapshot;
                maxSimilarity = currentSimilarity;
            }
        }

        return resultSnapshot;
    }

    private double paragraphSimilarity(String[] p1, String[] p2) {
        double similarity = 0;
        int similarityNum = 0;
        for (String s1 : p1) {
            for (String s2 : p2) {
                double currentSimilarity = getSimilarity(s1, s2);
                //Log.i("Word similarity",s1+" "+s2+" "+currentSimilarity);
                if (currentSimilarity >= 0.5) {
                    //Log.i("Word similarity",currentSimilarity+"");
                    similarity += currentSimilarity;
                    similarityNum++;
                }
            }
        }
        if (similarityNum == 0) return 0;
        return similarity / similarityNum;
    }

    public List<Question> findCompanyQuestions(DataSnapshot companySnapshot, String issue) {
        List<Question> questionList = new LinkedList<>();
        String splittedIssue[] = issue.split(SPLIT_BY);

        DataSnapshot categoriesSnapshot = companySnapshot.child(KnowledgeDB.getResourceString(R.string.dbCategories));
        //Log.i("categoriesSnapshot", categoriesSnapshot.toString());
        for (DataSnapshot category : categoriesSnapshot.getChildren()) {
            DataSnapshot subcats = category.child(KnowledgeDB.getResourceString(R.string.dbSubCats));
            //Log.i("subcats", subcats.toString());
            for (DataSnapshot subcat : subcats.getChildren()) {
                DataSnapshot questions = subcat.child(KnowledgeDB.getResourceString(R.string.dbQuestions));
                //Log.i("questions", questions.toString());
                for (DataSnapshot questionDS : questions.getChildren()) {
                    String answerStr = questionDS.child(KnowledgeDB.getResourceString(R.string.dbAnswer)).getValue().toString();
                    String questionStr = questionDS.child(KnowledgeDB.getResourceString(R.string.dbQuestion)).getValue().toString();
                    String splittedAnswer[] = answerStr.split(SPLIT_BY);
                    String splittedQuestion[] = questionStr.split(SPLIT_BY);
                    double questionSimilarity = paragraphSimilarity(splittedQuestion, splittedIssue);
                    double answerSimilarity = paragraphSimilarity(splittedAnswer, splittedIssue);
                    double totalSimilarity = (questionSimilarity + answerSimilarity) / 2;
                    if (totalSimilarity > 0) {
                        //Log.i("QUESTION SIMILARITY", totalSimilarity + "");
                        Question question = new Question();
                        question.setQuestion(questionDS.child(KnowledgeDB.getResourceString(R.string.dbQuestion)).getValue().toString());
                        question.setAnswer(questionDS.child(KnowledgeDB.getResourceString(R.string.dbAnswer)).getValue().toString());
                        question.setSimilarity(totalSimilarity);
                        Log.d("QuestionDS", question.toString());
                        questionList.add(question);
                    }
                }
            }
        }
        return questionList;
    }

    private static int distLowenstein(String S1, String S2) {
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

    private static double getSimilarity(String S1, String S2) {
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

    public DataSnapshot getTransferSnapshot() {
        return transferSnapshot;
    }

    public void setTransferSnapshot(DataSnapshot transferSnapshot) {
        this.transferSnapshot = transferSnapshot;
    }
}
