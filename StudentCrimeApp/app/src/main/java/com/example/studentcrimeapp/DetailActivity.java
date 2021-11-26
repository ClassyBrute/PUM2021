package com.example.studentcrimeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.UUID;

public class DetailActivity extends AppCompatActivity {

    CrimeLab Crimes = CrimeLab.get(this);

    TextView textView;
    Button date_time;
    EditText editText;
    CheckBox checkbox;
    Intent intent;
    UUID crime_id;
    Crime crime;
    Calendar date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textView = findViewById(R.id.textView1);
        date_time = findViewById(R.id.dateTime);
        editText = findViewById(R.id.editTextTitle);
        checkbox = findViewById(R.id.checkBox);

        intent = getIntent();
        // czy mozna to jakos ladniej zrobic, np nie uzywajac serializable
        crime_id = (UUID) intent.getSerializableExtra("id");

        // dlaczego musze nowy obiekt Crimes tworzyc?
        crime = Crimes.getCrime(crime_id);

        // if crime solved, set checkbox to true
        checkbox.setChecked(crime.isSolved());

        editText.setText(crime.getTitle());
        date_time.setText(crime.getDate().toString());
    }

    public void showDateTimePicker(View view) {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                // nie wiem co to peekAvailableContext ale zadziałało z tym
                new TimePickerDialog(peekAvailableContext(), new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        crime.setDate(date.getTime());
                        date_time.setText(date.getTime().toString());
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (editText.getText().toString().equals("")){  }
        else {
            crime.setTitle(editText.getText().toString());
        }
        crime.setSolved(checkbox.isChecked());
    }

    public void deleteCrime(View view){
        Crimes.getCrimes().remove(crime);
        finish();
    }
}