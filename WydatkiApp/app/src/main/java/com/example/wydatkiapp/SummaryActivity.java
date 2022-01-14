package com.example.wydatkiapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class SummaryActivity extends AppCompatActivity {

    Intent intent;
    DBHandler dbHandler;

    TextView natalia;
    TextView hubert;
    TextView summary;
    Button save_gallery;
    Button save_sms;

    int which;
    float hubert_value;
    float natalia_value;
    float sum;

    private final ArrayList<Expense> expenseList = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_view);

        dbHandler = new DBHandler(this);

        natalia = findViewById(R.id.natalia_amount);
        hubert = findViewById(R.id.hubert_amount);
        summary = findViewById(R.id.summary);
        save_gallery = findViewById(R.id.save_gallery);
        save_sms = findViewById(R.id.save_sms);

        Locale l = Locale.ENGLISH;

        intent = getIntent();
        which = intent.getIntExtra("which", 0);

        getExpenses(which);

        for (Expense expense : expenseList) {
            if (expense.getOwner().equals("hubert")) {
                hubert_value += expense.getAmount();
                System.out.println(hubert_value);
            } else {
                natalia_value += expense.getAmount();
            }
        }

        System.out.println("przed sum");
        sum = (hubert_value + natalia_value)/2;

        hubert.setText(String.valueOf(hubert_value));
        natalia.setText(String.valueOf(natalia_value));

        if (hubert_value > natalia_value) {
            sum = sum - natalia_value;
            summary.setText(String.format(l, "Natalia musi oddać %.2f", sum));
        } else if (natalia_value > hubert_value) {
            sum = sum - hubert_value;
            summary.setText(String.format(l, "Hubert musi oddać %.2f", sum));
        } else {
            summary.setText("Po równo :)");
        }

        save_gallery.setOnClickListener(v -> {
//
        });

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
                cursor = dbHandler.getExpenses();
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
}
