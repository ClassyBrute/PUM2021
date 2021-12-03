package com.example.studentcrimeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.UUID;

public class DetailActivity extends AppCompatActivity {

    CrimeLab Crimes = CrimeLab.get(this);
    private LinkedList<Crime> crimeList;

    Button date_time;
    EditText editText;
    CheckBox checkbox;
    Intent intent;
    UUID crime_id;
    Crime crime;
    Calendar date;

    private ViewPager2 viewPager2;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager2);

        viewPager2 = findViewById(R.id.pager);
        crimeList = CrimeLab.mCrimes;

        intent = getIntent();
        crime_id = (UUID) intent.getSerializableExtra("id");
        position = intent.getIntExtra("position", 0);
        crime = Crimes.getCrime(crime_id);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);
        viewPager2.setCurrentItem(position);


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                crime = crimeList.get(position);

                editText = findViewById(R.id.editTextTitle);
                checkbox = findViewById(R.id.checkBox);

                crime.setTitle(editText.getText().toString());
                crime.setSolved(checkbox.isChecked());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    public void showDateTimePicker(View view) {
        date_time = findViewById(R.id.dateTime);
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(DetailActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(DetailActivity.this, new TimePickerDialog.OnTimeSetListener() {

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

        editText = findViewById(R.id.editTextTitle);
        checkbox = findViewById(R.id.checkBox);

        crime.setTitle(editText.getText().toString());
        crime.setSolved(checkbox.isChecked());
    }

    public void first(View view){
        viewPager2.setCurrentItem(0);
    }

    public void last(View view){
        viewPager2.setCurrentItem(crimeList.size());
    }

    public void deleteCrime(View view){
        Crimes.getCrimes().remove(viewPager2.getCurrentItem());
        finish();
    }
}