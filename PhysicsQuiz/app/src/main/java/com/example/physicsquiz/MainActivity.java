package com.example.physicsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private int questionCount = 0;

    private final Question[] questions = new Question[]{
            new Question(R.string.question1, true, false),
            new Question(R.string.question2, true, false),
            new Question(R.string.question3, false, false),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.question_text);

        textView.setText(questions[questionCount].getTextId());
    }

    public void answerTrue(View view) {
        if (questions[questionCount].isAnswer()){
//            questions[questionCount].answe
        }
    }

    public void answerFalse(View view) {
    }

    public void prevQuestion(View view) {
        questionCount--;
        if (questionCount < 0){
            questionCount = questions.length-1;
        }

        textView.setText(questions[questionCount].getTextId());
    }

    public void nextQuestion(View view) {
        questionCount++;
        if (questionCount >= questions.length){
            questionCount = 0;
        }

        textView.setText(questions[questionCount].getTextId());
    }
}