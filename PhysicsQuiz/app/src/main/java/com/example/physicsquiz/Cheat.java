package com.example.physicsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Cheat extends AppCompatActivity {

    private TextView questionView;
    private TextView answerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        questionView = findViewById(R.id.cheatQuestion);
        answerView = findViewById(R.id.cheatAnswer);

        Intent intent = getIntent();
        String question = intent.getStringExtra(MainActivity.CHEAT_QUESTION);
        String answer = intent.getStringExtra(MainActivity.CHEAT_ANSWER);

        questionView.setText(question);
        answerView.setText(answer);

    }
}