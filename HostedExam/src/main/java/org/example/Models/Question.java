package org.example.Models;

public abstract class Question {
    protected String questionText;

    public Question(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionText() {
        return questionText;
    }

    public abstract void displayQuestion();
    public abstract boolean checkAnswer(String answer);
}

