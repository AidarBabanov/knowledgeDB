package com.example.aidar.knowledgedb;

import android.support.annotation.NonNull;

/**
 * Created by aidar on 8/4/17.
 */

public class Question implements Comparable<Question> {
    private String question;
    private String answer;
    private double similarity;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String toString() {
        return getQuestion();
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    @Override
    public int compareTo(@NonNull Question question) {
        if (similarity < question.similarity) return -1;
        else if (similarity == question.similarity) return 0;
        else return 1;
    }
}
