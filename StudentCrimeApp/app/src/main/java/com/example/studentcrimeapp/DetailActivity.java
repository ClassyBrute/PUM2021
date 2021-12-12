package com.example.studentcrimeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Calendar;

public class DetailActivity extends AppCompatActivity {

    Intent intent;
    DBHandler dbHandler;

    int crime_id;
    int position;
    String title;
    boolean solved;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView crimeText = findViewById(R.id.editTextTitle);
        CheckBox crimeSolved = findViewById(R.id.checkBox);
        Button dateTime = findViewById(R.id.dateTime);
        Button deleteCrime = findViewById(R.id.delete);

        dbHandler = new DBHandler(this);

        intent = getIntent();
        crime_id = intent.getIntExtra("id", 0);
        position = intent.getIntExtra("position", 0);
        title = intent.getStringExtra("title");
        solved = intent.getBooleanExtra("solved", false);
        date = intent.getStringExtra("date");

        crimeText.setText(title);
        crimeSolved.setChecked(solved);
        dateTime.setText(date);

        crimeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dbHandler.updateCrime(crime_id, s.toString(), null, solved ? 1 : 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        crimeSolved.setOnCheckedChangeListener((buttonView, isChecked) -> {
            solved = crimeSolved.isChecked();
            dbHandler.updateCrime(crime_id, title, null, solved ? 1 : 0);
        });

        dateTime.setOnClickListener(v -> {
            final Calendar currentDate = Calendar.getInstance();
            Calendar date_c = Calendar.getInstance();
            new DatePickerDialog(DetailActivity.this, (view, year, monthOfYear, dayOfMonth) -> {
                date_c.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(DetailActivity.this, (view1, hourOfDay, minute) -> {
                    date_c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    date_c.set(Calendar.MINUTE, minute);
                    dateTime.setText(date_c.getTime().toString());
                    dbHandler.updateCrime(crime_id, title, date_c.getTime(), solved ? 1 : 0);
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();

        });

        deleteCrime.setOnClickListener(v -> {
            dbHandler.deleteCrime(title);
            dbHandler.getCrimes();
            finish();
        });
    }
}