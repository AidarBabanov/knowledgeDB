package com.example.aidar.knowledgedb;

/**
 * Created by aidar on 8/4/17.
 */

public class Question {
    private String question;
    private String answer;
    private int priority;

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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String toString() {
        return getQuestion();
    }
}
