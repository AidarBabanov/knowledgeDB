package com.example.aidar.knowledgedb;

/**
 * Created by aidar on 8/4/17.
 */

public class Question {
    private String quesion;
    private String answer;
    private int priority;

    public String getQuesion() {
        return quesion;
    }

    public void setQuesion(String quesion) {
        this.quesion = quesion;
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
}
