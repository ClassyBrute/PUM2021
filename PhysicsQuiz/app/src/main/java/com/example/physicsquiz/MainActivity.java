package com.example.physicsquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button buttonTrue;
    private Button buttonFalse;
    private Button buttonNext;
    private Button buttonPrev;
    private Button buttonRestart;
    private int questionCount = 0;
    private int answeredCount = 0;
    private int points = 0;

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
        buttonTrue = findViewById(R.id.buttonTrue);
        buttonFalse = findViewById(R.id.buttonFalse);
        buttonNext = findViewById(R.id.buttonNext);
        buttonPrev = findViewById(R.id.buttonPrev);
        buttonRestart = findViewById(R.id.buttonRestart);

        textView.setText(questions[questionCount].getTextId());

    }

//  przywracanie stanu widoczności przycisków oraz wartości zmiennych
//  zapamiętywanie czy pytanie zostało już odpowiedziane niezależnie od ilości pytań
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {

            buttonTrue.setVisibility(savedInstanceState.getInt("buttonTrue"));
            buttonFalse.setVisibility(savedInstanceState.getInt("buttonFalse"));
            buttonNext.setVisibility(savedInstanceState.getInt("buttonNext"));
            buttonPrev.setVisibility(savedInstanceState.getInt("buttonPrev"));
            buttonRestart.setVisibility(savedInstanceState.getInt("buttonRestart"));

            textView.setText(savedInstanceState.getString("textView"));

            questionCount = savedInstanceState.getInt("questionCount");
            answeredCount = savedInstanceState.getInt("answeredCount");
            points = savedInstanceState.getInt("points");

            for (int i = 0; i < questions.length; i++) {
                boolean question = savedInstanceState.getBoolean(String.format("question%s", i));
                questions[i].setAnswered(question);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("buttonTrue", buttonTrue.getVisibility());
        outState.putInt("buttonFalse", buttonFalse.getVisibility());
        outState.putInt("buttonNext", buttonNext.getVisibility());
        outState.putInt("buttonPrev", buttonPrev.getVisibility());
        outState.putInt("buttonRestart", buttonRestart.getVisibility());

        outState.putString("textView", textView.getText().toString());

        outState.putInt("questionCount", questionCount);
        outState.putInt("answeredCount", answeredCount);
        outState.putInt("points", points);

        for (int i = 0; i < questions.length; i++) {
            outState.putBoolean(String.format("question%s", i), questions[i].isAnswered());
        }
    }

    public void answerTrue(View view) {
//      jeżeli jeszcze nie udzielono odpowiedzi na pytanie
        if (!questions[questionCount].isAnswered()){
            if (questions[questionCount].isAnswer()){
                points++;
            }
            questions[questionCount].setAnswered(true);
            answeredCount++;
            buttonTrue.setVisibility(View.INVISIBLE);
            buttonFalse.setVisibility(View.INVISIBLE);
        }
//      po odpowiedzeniu na ostatnie pytanie wyświetlane są statystyki
        if (answeredCount == questions.length) {
            finalStats(view);
        }
    }

    public void answerFalse(View view) {
        if (!questions[questionCount].isAnswered()){
            if (!questions[questionCount].isAnswer()) {
                points++;
            }
            questions[questionCount].setAnswered(true);
            answeredCount++;
            buttonTrue.setVisibility(View.INVISIBLE);
            buttonFalse.setVisibility(View.INVISIBLE);
        }
        if (answeredCount == questions.length) {
            finalStats(view);
        }
    }

    public void prevQuestion(View view) {
        questionCount--;
        if (questionCount < 0){
            questionCount = questions.length-1;
        }

        textView.setText(questions[questionCount].getTextId());
        showhideButton(view);
    }

    public void nextQuestion(View view) {
        questionCount++;
        if (questionCount >= questions.length){
            questionCount = 0;
        }

        textView.setText(questions[questionCount].getTextId());
        showhideButton(view);
    }

    public void restart(View view){
        points = 0;
        answeredCount = 0;
        questionCount = 0;

        buttonTrue.setVisibility(View.VISIBLE);
        buttonFalse.setVisibility(View.VISIBLE);
        buttonNext.setVisibility(View.VISIBLE);
        buttonPrev.setVisibility(View.VISIBLE);
        buttonRestart.setVisibility(View.INVISIBLE);

        for (int i = 0; i < questions.length; i++) {
            questions[i].setAnswered(false);
        }

        textView.setText(questions[questionCount].getTextId());

    }

    public void finalStats(View view){
        buttonTrue.setVisibility(View.INVISIBLE);
        buttonFalse.setVisibility(View.INVISIBLE);
        buttonNext.setVisibility(View.INVISIBLE);
        buttonPrev.setVisibility(View.INVISIBLE);
        buttonRestart.setVisibility(View.VISIBLE);

        textView.setText(String.format("You scored %s points \nGood answers: %s " +
                "\nBad answers: %s", points, points, questions.length - points));
    }

    public void showhideButton(View view){
//      ukrywa przyciski przy pytaniach ktore zostaly odpowiedziane
//      pokazuje przyciski przy pytaniach ktore nie zostaly odpowiedziane
        if (questions[questionCount].isAnswered()){
            buttonTrue.setVisibility(View.INVISIBLE);
            buttonFalse.setVisibility(View.INVISIBLE);
        }
        else{
            buttonTrue.setVisibility(View.VISIBLE);
            buttonFalse.setVisibility(View.VISIBLE);
        }
    }
}