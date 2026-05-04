package org.example.Models;

import java.util.List;

public class MultipleChoice extends Question {
    private List<String> choices;
    private String correctAnswer;

    public MultipleChoice(String questionText, List<String> choices, String correctAnswer) {
        super(questionText);
        this.choices = choices;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public void displayQuestion() {
        System.out.println(questionText);
        for (int i = 0; i < choices.size(); i++) {
            System.out.println((i + 1) + ". " + choices.get(i));
        }
    }

    @Override
    public boolean checkAnswer(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

