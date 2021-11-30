package com.example.studentcrimeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    CrimeLab Crimes = CrimeLab.get(this);
    LinkedList<Crime> CrimesList = Crimes.getCrimes();

    private RecyclerView recyclerView;
    private CrimeAdapter crimeAdapter;

    private FloatingActionButton addCrime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addCrime = findViewById(R.id.addCrime);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        crimeAdapter = new CrimeAdapter(this, CrimesList);
        recyclerView.setAdapter(crimeAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        crimeAdapter.notifyDataSetChanged();
    }

    public void addCrime(View view){
        Crime new_crime = new Crime();
        new_crime.setTitle("New crime!");
        CrimesList.add(new_crime);
        crimeAdapter.notifyDataSetChanged();
    }
}



