package com.example.aidar.knowledgedb;

import android.provider.ContactsContract;

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

    public List<Question> findAllQuestionsInCompany(String companyName) {
        List<Question> questionList = new LinkedList<Question>();
        findCompanySnapshot(companyName);
        DataSnapshot categoriesSnapshot = companySnapshot.child("categories");
        for (DataSnapshot category : categoriesSnapshot.getChildren()) {
            DataSnapshot subcats = category.child("subcats");
            for (DataSnapshot subcat : subcats.getChildren()) {
                DataSnapshot questions = subcat.child("questions");
                for (DataSnapshot questionDS : questions.getChildren()) {
                    Question question = new Question();
                    question.setQuesion(questionDS.child("Question").getValue().toString());
                    question.setAnswer(questionDS.child("Answer").getValue().toString());
                    question.setPriority(0);
                    questionList.add(question);
                }
            }
        }

        return questionList;
    }

    private void findCompanySnapshot(final String companyName) {
        DatabaseReference localReference = databaseReference.child("companies");
        localReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String currentCompany = (String) snapshot.child("title").getValue();
                    if (currentCompany.equals(companyName)) {
                        companySnapshot = snapshot;
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
