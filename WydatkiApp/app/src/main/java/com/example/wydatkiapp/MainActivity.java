package com.example.wydatkiapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {

    private EditText natalia_value;
    private EditText hubert_value;
    private Button addExpense;
    private Button showExpenses;
    private Button showSummary;

    private DBHandler dbHandler;

    @RequiresApi(api = Build.VERSION_CODES.O)
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

        showExpenses.setOnClickListener(v -> {
            Intent intent = new Intent(this, RecyclerActivity.class);

            this.startActivity(intent);
        });

        showSummary.setOnClickListener(v -> {
            String[] lista = {"Dzienny", "Miesięczny", "Roczny"};

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("Wybierz zakres podsumowania")
                    .setItems(lista, (dialog, which) -> {
                        Intent intent = new Intent(this, SummaryActivity.class);
                        intent.putExtra("which", which);
                        this.startActivity(intent);
                    });

            builder.create().show();
        });
    }

    @Override
    protected void onDestroy() {
        dbHandler.close();
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addExpense(String natalia, String hubert) {
        if (!hubert.isEmpty()) {
            if (Integer.parseInt(hubert) > 0) {
                dbHandler.addExpenses(new Expense("hubert", Integer.parseInt(hubert), LocalDate.now()));
                Toast.makeText(this, "Dodano hubert, wartość: " +
                        Integer.parseInt(hubert) + "zł", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Wprowadź poprawne dane", Toast.LENGTH_SHORT).show();
            }
        }

        if (!natalia.isEmpty()){
            if (Integer.parseInt(natalia) > 0) {
                dbHandler.addExpenses(new Expense("natalia", Integer.parseInt(natalia), LocalDate.now()));
                Toast.makeText(this, "Dodano natalia, wartość: " +
                        Integer.parseInt(natalia) + "zł", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Wprowadź poprawne dane", Toast.LENGTH_SHORT).show();
            }
        }

        if (natalia.isEmpty() && hubert.isEmpty()){
            Toast.makeText(this, "Wprowadź poprawne dane", Toast.LENGTH_SHORT).show();
        }
    }
}