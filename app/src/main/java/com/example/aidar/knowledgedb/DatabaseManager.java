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

    private final static String SPLIT_BY = " ";

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DataSnapshot companySnapshot;
    private AfterloadHandler afterloadHandler;

    public interface AfterloadHandler{
        void doAfterLoad();
    }

    public DatabaseManager(AfterloadHandler afterloadHandler){
        this.afterloadHandler = afterloadHandler;
    }

    public void findAllQuestionsInCompany(String companyName, SwipeStackAdapter<Question> adapter, String issue) {
        //Log.d("COMPANY NAME", companyName);
        findCompanySnapshot(companyName, adapter, issue);
    }


    private boolean checkWithIssue(String[] splittedIssue, String splittedStr[]) {
        boolean result = false;
        for (String strPart : splittedStr) {
            strPart = strPart.toLowerCase();
            for (String issuePart : splittedIssue) {
                issuePart = issuePart.toLowerCase();
                //Log.i("ANSWER - QUESTION", strPart+" - "+issuePart);
                if (issuePart.equals(strPart)) result = true;
            }
        }
        return result;
    }

    private void getQuestions(SwipeStackAdapter<Question> adapter, String issue) {
        //Log.d("COMPANY SNAPSHOT", companySnapshot.toString());
        String splittedIssue[] = issue.split(SPLIT_BY);
        List<Question> questionList = new LinkedList<Question>();
        DataSnapshot categoriesSnapshot = companySnapshot.child("categories");
        for (DataSnapshot category : categoriesSnapshot.getChildren()) {
            DataSnapshot subcats = category.child("subcats");
            for (DataSnapshot subcat : subcats.getChildren()) {
                DataSnapshot questions = subcat.child("questions");
                for (DataSnapshot questionDS : questions.getChildren()) {
                    String answerStr = questionDS.child("answer").getValue().toString();
                    String questionStr  =questionDS.child("question").getValue().toString();
                    String splittedAnswer[] = answerStr.split(SPLIT_BY);
                    String splittedQuestion[] = questionStr.split(SPLIT_BY);
                    boolean exist = checkWithIssue(splittedIssue, splittedAnswer);
                    exist = exist && checkWithIssue(splittedIssue, splittedQuestion);
                    if (exist) {
                        Question question = new Question();
                        //Log.d("QuestionDS", questionDS.toString());
                        question.setQuestion(questionDS.child("question").getValue().toString());
                        question.setAnswer(questionDS.child("answer").getValue().toString());
                        questionList.add(question);
                    }
                }
            }
        }
        adapter.setData(questionList);
        adapter.notifyDataSetChanged();
        afterloadHandler.doAfterLoad();
    }

    private void findCompanySnapshot(final String companyName, final SwipeStackAdapter<Question> adapter, final String issue) {
        DatabaseReference localReference = databaseReference.child("companies");
        //Log.d("FINDCOMPANYSNAPSHOT", localReference.toString());
        localReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.d("DATASNAPSHOT", dataSnapshot.toString());

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String currentCompany = (String) snapshot.child("title").getValue();
                    //Log.d("SNAPSHOTS", snapshot.toString());
                    if (currentCompany.equals(companyName)) {
                        companySnapshot = snapshot;
                        getQuestions(adapter, issue);
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
