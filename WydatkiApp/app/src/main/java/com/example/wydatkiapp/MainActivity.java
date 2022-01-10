package com.example.wydatkiapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private EditText natalia_value;
    private EditText hubert_value;
    private Button addExpense;
    private Button showExpenses;
    private Button showSummary;

    private DBHandler dbHandler;

    ArrayList<Expense> expenseList = new ArrayList<>();

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
                        getExpenses(which);
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
    public void getExpenses(int case_) {
        expenseList.clear();
        Cursor cursor;

        switch (case_) {
            case 0:
                cursor = dbHandler.getExpensesDay();
                break;
            case 1:
                cursor = dbHandler.getExpensesMonth();
                break;
            case 2:
                cursor = dbHandler.getExpensesYear();
                break;
            default:
                cursor = dbHandler.getExpensesMonth();
                break;
        }

        if (cursor.getCount() == 0)
            Toast.makeText(this, "EMPTY", Toast.LENGTH_SHORT).show();
        else {
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String owner = cursor.getString(1);
                int amount = cursor.getInt(2);
                LocalDate date = LocalDate.parse(cursor.getString(3));

                expenseList.add(new Expense(id, owner, amount, date));
            }
        }
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