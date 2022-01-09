package com.example.wydatkiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailExpense extends AppCompatActivity {

    Intent intent;
    DBHandler dbHandler;

    EditText amount_;
    TextView date_;
    TextView owner_;
    Button delete;
    int expense_id;
    int position;
    int amount;
    String date;
    String owner;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_expense);

        dbHandler = new DBHandler(this);

        date_ = findViewById(R.id.detail_date);
        owner_ = findViewById(R.id.detail_owner);
        amount_ = findViewById(R.id.detail_amount);
        amount_.setTransformationMethod(null);
        delete = findViewById(R.id.delete);

        intent = getIntent();
        expense_id = intent.getIntExtra("id", 0);
        position = intent.getIntExtra("position", 0);
        amount = intent.getIntExtra("amount", 0);
        date = intent.getStringExtra("date");
        owner = intent.getStringExtra("owner");


        date_.setText(date);
        owner_.setText(owner);
        amount_.setText(String.valueOf(amount));


        delete.setOnClickListener(v -> {
            dbHandler.deleteExpense(String.valueOf(expense_id));
            dbHandler.getExpenses();
            finish();
        });



    }

}
