package com.example.aidar.knowledgedb;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by aidar on 8/21/17.
 */

public class ListItem {
    private String text;
    private DataSnapshot dataSnapshot;

    public DataSnapshot getDataSnapshot() {
        return dataSnapshot;
    }

    public void setDataSnapshot(DataSnapshot dataSnapshot) {
        this.dataSnapshot = dataSnapshot;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public String toString(){
        return text;
    }
}
