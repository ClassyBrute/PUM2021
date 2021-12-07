package com.example.studentcrimeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

//    CrimeLab Crimes = CrimeLab.get(this);
//    LinkedList<Crime> CrimesList = Crimes.getCrimes();

    private RecyclerView recyclerView;
    private CrimeAdapter crimeAdapter;
    private FloatingActionButton addCrime;


    private DBHandler dbHandler;
    private ArrayList<Crime> crimes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addCrime = findViewById(R.id.addCrime);
        recyclerView = findViewById(R.id.recycler_view);

        dbHandler = new DBHandler(this);

        getCrimes();

        crimeAdapter = new CrimeAdapter(this, crimes);
        recyclerView.setAdapter(crimeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addCrime.setOnClickListener(v -> {
            addCrimes();
            getCrimes();
            recyclerView.getAdapter().notifyDataSetChanged();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        crimeAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        dbHandler.close();
        super.onDestroy();
    }

    public void getCrimes() {
        crimes.clear();
        Cursor cursor = dbHandler.getCrimes();

        if (cursor.getCount() == 0)
            Toast.makeText(this, "EMPTY", Toast.LENGTH_SHORT).show();
        else {
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                int index = cursor.getInt(2);
                boolean solved = (cursor.getInt(3) == 1);
                Date date = new Date(cursor.getInt(4));

                crimes.add(new Crime(id, index, title, date, solved));
            }
        }
    }

    public void addCrimes() {
        String title = "New crime!";
        int index = 0;
        Date date = new Date();

        dbHandler.addCrimes(new Crime(index, title, date, false));
    }

//    private class CrimeAdapter extends RecyclerView.Adapter<MainActivity.CrimeAdapter.ViewHolder>{
//        public CrimeAdapter(){
//
//        }
//
//        private class ViewHolder extends RecyclerView.ViewHolder{
//            super(itemView);
//
//        }
//
//        @NonNull
//        @Override
//        public CrimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            return null;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull CrimeAdapter.ViewHolder holder, int position) {
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return 0;
//        }
//    }
}