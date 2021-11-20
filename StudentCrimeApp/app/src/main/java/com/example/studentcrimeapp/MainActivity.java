package com.example.studentcrimeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    CrimeLab Crimes = CrimeLab.get(this);
    LinkedList<Crime> CrimesList = Crimes.getCrimes();

    private RecyclerView recyclerView;
    private CrimeAdapter crimeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        crimeAdapter = new CrimeAdapter(this, CrimesList);
        recyclerView.setAdapter(crimeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}

