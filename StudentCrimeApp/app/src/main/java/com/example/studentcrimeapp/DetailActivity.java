package com.example.studentcrimeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.LinkedList;
import java.util.UUID;

public class DetailActivity extends AppCompatActivity {

    CrimeLab Crimes = CrimeLab.get(this);
    private LinkedList<Crime> crimeList;

    Intent intent;
    UUID crime_id;
    Crime crime;

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