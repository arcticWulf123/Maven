package org.example.Services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import org.example.Models.MultipleChoice;
import org.example.Models.Question;
import org.example.Models.TrueFalse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class QuestionService {
    private List<Question> questions = new ArrayList<>();

    public QuestionService() {
        loadQuestions();
    }

    public void loadQuestions() {
        try (BufferedReader br = new BufferedReader(new FileReader("questions.json"))) {
            RuntimeTypeAdapterFactory<Question> questionAdapter =
                    RuntimeTypeAdapterFactory.of(Question.class, "type")
                            .registerSubtype(MultipleChoice.class, "multiple_choice")
                            .registerSubtype(TrueFalse.class, "true_false");
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(questionAdapter)
                    .setPrettyPrinting()
                    .create();
            Type listType = new TypeToken<List<Question>>() {}.getType();

            List<Question> questions = gson.fromJson(
                    br,
                    listType);
        } catch (IOException e) {
            System.out.println("Error reading questions.json");
        }

    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
