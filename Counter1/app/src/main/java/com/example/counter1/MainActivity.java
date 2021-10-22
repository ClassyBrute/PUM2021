package com.example.counter1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView CountView;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CountView = findViewById(R.id.textView);

    }

    public void countUp(View view){
        count++;
        CountView.setText(Integer.toString(count));
    }

    public void countDown(View view){
        count--;
        CountView.setText(Integer.toString(count));
    }

    public void reset(View view){
        count = 0;
        CountView.setText(Integer.toString(count));
    }
}