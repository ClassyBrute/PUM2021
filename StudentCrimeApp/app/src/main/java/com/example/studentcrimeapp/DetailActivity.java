package com.example.studentcrimeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.UUID;

public class DetailActivity extends AppCompatActivity {

    CrimeLab Crimes = CrimeLab.get(this);

    TextView textView;
    EditText editText;
    CheckBox checkbox;
    Intent intent;
    UUID crime_id;
    Crime crime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textView = findViewById(R.id.textView1);
        editText = findViewById(R.id.editTextTitle);
        checkbox = findViewById(R.id.checkBox);

        intent = getIntent();
        // czy mozna to jakos ladniej zrobic, np nie uzywajac serializable
        crime_id = (UUID) intent.getSerializableExtra("id");

        // dlaczego musze nowy obiekt Crimes tworzyc?
        crime = Crimes.getCrime(crime_id);

        // if crime solved, set checkbox to true
        checkbox.setChecked(crime.isSolved());
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