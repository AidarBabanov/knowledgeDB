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

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DataSnapshot companySnapshot;

    public void findAllQuestionsInCompany(String companyName, SwipeStackAdapter<Question> adapter) {
        Log.d("COMPANY NAME", companyName);
        findCompanySnapshot(companyName, adapter);


    }

    private void getQuestions(SwipeStackAdapter<Question> adapter){
        Log.d("COMPANY SNAPSHOT", companySnapshot.toString());
        List<Question> questionList = new LinkedList<Question>();
        DataSnapshot categoriesSnapshot = companySnapshot.child("categories");
        for (DataSnapshot category : categoriesSnapshot.getChildren()) {
            DataSnapshot subcats = category.child("subcats");
            for (DataSnapshot subcat : subcats.getChildren()) {
                DataSnapshot questions = subcat.child("questions");
                for (DataSnapshot questionDS : questions.getChildren()) {
                    Question question = new Question();
                    Log.d("QuestionDS", questionDS.toString());
                    question.setQuestion(questionDS.child("question").getValue().toString());
                    question.setAnswer(questionDS.child("answer").getValue().toString());
                    question.setPriority(0);
                    questionList.add(question);
                }
            }
        }
        adapter.setData(questionList);
        adapter.notifyDataSetChanged();
    }

    private void findCompanySnapshot(final String companyName, final SwipeStackAdapter<Question> adapter) {
        DatabaseReference localReference = databaseReference.child("companies");
        Log.d("FINDCOMPANYSNAPSHOT", localReference.toString());
        localReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("DATASNAPSHOT", dataSnapshot.toString());

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String currentCompany = (String) snapshot.child("title").getValue();
                    Log.d("SNAPSHOTS", snapshot.toString());
                    if (currentCompany.equals(companyName)) {
                        companySnapshot = snapshot;
                        getQuestions(adapter);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public void setDatabaseReference(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }
}
