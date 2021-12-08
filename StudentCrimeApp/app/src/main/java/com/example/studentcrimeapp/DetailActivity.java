package com.example.studentcrimeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

public class DetailActivity extends AppCompatActivity {

    private ArrayList<Crime> crimeList = new ArrayList<>();

    Intent intent;
    Crime crime;
    Cursor cursor;
    Cursor cursor_crimes;
    DBHandler dbHandler;

    private ViewPager2 viewPager2;
    int crime_id;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView crimeText = findViewById(R.id.editTextTitle);
        CheckBox crimeSolved = findViewById(R.id.checkBox);
        Button dateTime = findViewById(R.id.dateTime);

//        viewPager2 = findViewById(R.id.pager);

        dbHandler = new DBHandler(this);

        cursor_crimes = dbHandler.getCrimes();

        intent = getIntent();

        crime_id = intent.getIntExtra("id", 0);
        position = intent.getIntExtra("position", 0);

//        cursor = dbHandler.getCrime(crime_id);
        cursor = dbHandler.getCrimes();

        crimeCursor(cursor);

        System.out.println(crimeList.get(position).getTitle());

//        try {
//            ViewPagerAdapter adapter = new ViewPagerAdapter(this);
//            viewPager2.setAdapter(adapter);
//            System.out.println("Try1");
//            viewPager2.setCurrentItem(position);
//        }catch (NullPointerException e){
//            System.out.println("Try3");
//
//        }

        crimeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                crime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

//    public void first(View view){
//        viewPager2.setCurrentItem(0);
//    }

//    public void last(View view){
//        viewPager2.setCurrentItem(crimeList.size()-1);
//    }

    public void crimeCursor(Cursor cursor){
        if (cursor.getCount() == 0)
            Toast.makeText(this, "EMPTY", Toast.LENGTH_SHORT).show();
        else {
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                boolean solved = (cursor.getInt(2) == 1);
                Date date = new Date(cursor.getInt(3));

                crimeList.add(new Crime(id, title, date, solved));
            }
        }
    }

//    public void deleteCrime(View view){
//        Crimes.getCrimes().remove(viewPager2.getCurrentItem());
//        finish();
//    }
}