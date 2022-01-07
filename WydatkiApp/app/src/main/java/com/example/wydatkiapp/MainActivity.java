package com.example.wydatkiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private EditText natalia_value;
    private EditText hubert_value;
    private Button addExpense;
    private Button showExpenses;
    private Button showSummary;

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        natalia_value = findViewById(R.id.natalia_value);
        natalia_value.setTransformationMethod(null);
        hubert_value = findViewById(R.id.hubert_value);
        hubert_value.setTransformationMethod(null);
        addExpense = findViewById(R.id.button_add);
        showExpenses = findViewById(R.id.button_recycler);
        showSummary = findViewById(R.id.button_sum);

        dbHandler = new DBHandler(this);

        addExpense.setOnClickListener(v -> {
            addExpense(natalia_value.getText().toString(), hubert_value.getText().toString());
        });
    }

    @Override
    protected void onDestroy() {
        dbHandler.close();
        super.onDestroy();
    }

    private void addExpense(String natalia, String hubert) {
        if (!hubert.isEmpty()) {
            if (Integer.parseInt(hubert) > 0) {
                dbHandler.addExpenses(new Expense("hubert", Integer.parseInt(hubert), new Date()));
                Toast.makeText(this, "Dodano hubert, wartość: " +
                        Integer.parseInt(hubert) + "zł", Toast.LENGTH_SHORT).show();
            }
        }

        if (!natalia.isEmpty()){
            if (Integer.parseInt(natalia) > 0) {
                dbHandler.addExpenses(new Expense("natalia", Integer.parseInt(natalia), new Date()));
                Toast.makeText(this, "Dodano natalia, wartość: " +
                        Integer.parseInt(natalia) + "zł", Toast.LENGTH_SHORT).show();
            }
        }

    }
}