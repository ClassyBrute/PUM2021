package com.example.physicsquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button buttonTrue;
    private Button buttonFalse;
    private Button buttonNext;
    private Button buttonPrev;
    private Button buttonRestart;
    private Button buttonCheat;
    private Button buttonSearch;
    private int questionCount = 0;
    private int answeredCount = 0;
    private float points = 0;
    private int goodAnswers = 0;
    private int cheatedCount = 0;

    public static final String CHEAT_QUESTION = "com.example.physicsquiz.CHEATQ";
    public static final String CHEAT_ANSWER = "com.example.physicsquiz.CHEATA";

    private final Question[] questions = new Question[]{
            new Question(R.string.question1, true, false),
            new Question(R.string.question2, true, false),
            new Question(R.string.question3, false, false),
            new Question(R.string.question4, false, false),
            new Question(R.string.question5, false, false),
            new Question(R.string.question6, true, false),
            new Question(R.string.question7, true, false),
            new Question(R.string.question8, true, false),
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
        buttonCheat = findViewById(R.id.buttonCheat);
        buttonSearch = findViewById(R.id.buttonSearch);

        textView.setText(questions[questionCount].getTextId());
    }

//  przywracanie stanu widoczności przycisków oraz wartości zmiennych
//  zapamiętywanie czy pytanie zostało już odpowiedziane niezależnie od ilości pytań
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {

            buttonTrue.setVisibility(savedInstanceState.getInt("buttonTrue"));
            buttonFalse.setVisibility(savedInstanceState.getInt("buttonFalse"));
            buttonNext.setVisibility(savedInstanceState.getInt("buttonNext"));
            buttonPrev.setVisibility(savedInstanceState.getInt("buttonPrev"));
            buttonRestart.setVisibility(savedInstanceState.getInt("buttonRestart"));
            buttonCheat.setVisibility(savedInstanceState.getInt("buttonCheatV"));
            buttonSearch.setVisibility(savedInstanceState.getInt("buttonSearch"));

            buttonCheat.setText(savedInstanceState.getString("buttonCheat"));
            textView.setText(savedInstanceState.getString("textView"));

            questionCount = savedInstanceState.getInt("questionCount");
            answeredCount = savedInstanceState.getInt("answeredCount");
            points = savedInstanceState.getFloat("points");
            goodAnswers = savedInstanceState.getInt("goodAnswers");
            cheatedCount = savedInstanceState.getInt("cheatedCount");

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
        outState.putInt("buttonCheatV", buttonCheat.getVisibility());
        outState.putInt("buttonSearch", buttonSearch.getVisibility());

        outState.putString("buttonCheat", buttonCheat.getText().toString());
        outState.putString("textView", textView.getText().toString());

        outState.putInt("questionCount", questionCount);
        outState.putInt("answeredCount", answeredCount);
        outState.putFloat("points", points);
        outState.putInt("goodAnswers", goodAnswers);
        outState.putInt("cheatedCount", cheatedCount);

        for (int i = 0; i < questions.length; i++) {
            outState.putBoolean(String.format("question%s", i), questions[i].isAnswered());
        }
    }

    public void answerTrue(View view) {
//      jeżeli jeszcze nie udzielono odpowiedzi na pytanie
        if (!questions[questionCount].isAnswered()) {
            if (questions[questionCount].isAnswer()) {
                points++;
                goodAnswers++;
            }
            questions[questionCount].setAnswered(true);
            answeredCount++;
            buttonTrue.setVisibility(View.INVISIBLE);
            buttonFalse.setVisibility(View.INVISIBLE);
            buttonCheat.setText("ANSWER");
        }
//      po odpowiedzeniu na ostatnie pytanie wyświetlane są statystyki
        if (answeredCount == questions.length) {
            finalStats(view);
        }
    }

    public void answerFalse(View view) {
        if (!questions[questionCount].isAnswered()) {
            if (!questions[questionCount].isAnswer()) {
                points++;
                goodAnswers++;
            }
            questions[questionCount].setAnswered(true);
            answeredCount++;
            buttonTrue.setVisibility(View.INVISIBLE);
            buttonFalse.setVisibility(View.INVISIBLE);
            buttonCheat.setText("ANSWER");
        }
        if (answeredCount == questions.length) {
            finalStats(view);
        }
    }

    public void prevQuestion(View view) {
        questionCount--;
        if (questionCount < 0) {
            questionCount = questions.length - 1;
        }

        textView.setText(questions[questionCount].getTextId());
        showhideButton(view);
    }

    public void nextQuestion(View view) {
        questionCount++;
        if (questionCount >= questions.length) {
            questionCount = 0;
        }

        textView.setText(questions[questionCount].getTextId());
        showhideButton(view);
    }

    public void restart(View view) {
        points = 0;
        answeredCount = 0;
        questionCount = 0;
        cheatedCount = 0;
        goodAnswers = 0;

        buttonTrue.setVisibility(View.VISIBLE);
        buttonFalse.setVisibility(View.VISIBLE);
        buttonNext.setVisibility(View.VISIBLE);
        buttonPrev.setVisibility(View.VISIBLE);
        buttonCheat.setVisibility(View.VISIBLE);
        buttonSearch.setVisibility(View.VISIBLE);
        buttonRestart.setVisibility(View.INVISIBLE);
        buttonCheat.setText("CHEAT");

        for (int i = 0; i < questions.length; i++) {
            questions[i].setAnswered(false);
        }

        textView.setText(questions[questionCount].getTextId());
    }

    public void cheat(View view) {
        if (!questions[questionCount].isAnswered()){
            cheatedCount++;
        }

        String cheatQuestion = textView.getText().toString();
        String cheatAnswer = String.valueOf(questions[questionCount].isAnswer());

        Intent intent = new Intent(this, Cheat.class);
        intent.putExtra(CHEAT_QUESTION, cheatQuestion);
        intent.putExtra(CHEAT_ANSWER, cheatAnswer);

        startActivity(intent);
    }

    public void search(View view){
        String url = textView.getText().toString();
        Uri webpage = Uri.parse(String.format("https://www.google.com/search?q=%s", url));

        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        try {
            startActivity(intent);
        }
        catch (ActivityNotFoundException e){
            Log.d("error", "no activity");
        }
    }

    public void finalStats(View view) {
        buttonTrue.setVisibility(View.INVISIBLE);
        buttonFalse.setVisibility(View.INVISIBLE);
        buttonNext.setVisibility(View.INVISIBLE);
        buttonPrev.setVisibility(View.INVISIBLE);
        buttonCheat.setVisibility(View.INVISIBLE);
        buttonSearch.setVisibility(View.INVISIBLE);
        buttonRestart.setVisibility(View.VISIBLE);

        double pointsFinal = points*(1-(cheatedCount*0.15));

        if (pointsFinal <= 0){
            pointsFinal = 0;
        }

        textView.setText(String.format("You scored %s points \nGood answers: %s " +
            "\nBad answers: %s \nCheated %s times", String.format("%.2f", pointsFinal),
            goodAnswers, questions.length - goodAnswers, cheatedCount));
    }

    public void showhideButton(View view) {
//      ukrywa przyciski przy pytaniach ktore zostaly odpowiedziane
//      pokazuje przyciski przy pytaniach ktore nie zostaly odpowiedziane
        if (questions[questionCount].isAnswered()) {
            buttonTrue.setVisibility(View.INVISIBLE);
            buttonFalse.setVisibility(View.INVISIBLE);
            buttonCheat.setText("ANSWER");
        } else {
            buttonTrue.setVisibility(View.VISIBLE);
            buttonFalse.setVisibility(View.VISIBLE);
            buttonCheat.setText("CHEAT");
        }
    }
}