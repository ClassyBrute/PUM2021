package com.example.studentcrimeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton addCrime;
    private ImageButton deleteCrime;
    private ImageButton editCrime;

    private DBHandler dbHandler;
    private ArrayList<Crime> crimes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addCrime = findViewById(R.id.addCrime);
        deleteCrime = findViewById(R.id.delete);
        editCrime = findViewById(R.id.edit);
        recyclerView = findViewById(R.id.recycler_view);

        dbHandler = new DBHandler(this);

        getCrimes();

        CrimeAdapter crimeAdapter = new CrimeAdapter();
        recyclerView.setAdapter(crimeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addCrime.setOnClickListener(v -> {
            addCrimes();
            getCrimes();
            recyclerView.getAdapter().notifyDataSetChanged();
        });

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
                boolean solved = (cursor.getInt(2) == 1);
                Date date = new Date(cursor.getInt(3));

                crimes.add(new Crime(id, title, date, solved));
            }
        }
    }

    public void addCrimes() {
        String title = "New crime! " + crimes.size();
        Date date = new Date();

        dbHandler.addCrimes(new Crime(title, date, false));
    }

    private class CrimeAdapter extends RecyclerView.Adapter<MainActivity.CrimeAdapter.CrimeViewHolder> {

        private Context context;

        // constructor
        public CrimeAdapter() {
//            LayoutInflater inflater = LayoutInflater.from(context);
        }

        private class CrimeViewHolder extends RecyclerView.ViewHolder {

            public CrimeViewHolder(@NonNull View itemView) {
                super(itemView);

            }

            private final TextView crimeText = itemView.findViewById(R.id.crime_text);
            private final TextView crimeDate = itemView.findViewById(R.id.crime_date);
            private final TextView crimeSolved = itemView.findViewById(R.id.crime_solved);
            private final ImageButton deleteCrime = itemView.findViewById(R.id.delete);
            private final ImageButton editCrime = itemView.findViewById(R.id.edit);

//        @Override
//        public void onClick(View view) {
//            int position = getLayoutPosition();
////            Crime element = crimeList.get(position);
//
//            notifyDataSetChanged();
//
////            Intent intent = new Intent(inflater.getContext(), DetailActivity.class);
//
////            intent.putExtra("index", element.getIndex());
////            intent.putExtra("position", position);
//
////            inflater.getContext().startActivity(intent);
//        }
        }

        @NonNull
        @Override
        public CrimeAdapter.CrimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CrimeAdapter.CrimeViewHolder(LayoutInflater.from(MainActivity.this)
            .inflate(R.layout.crime_list, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeAdapter.CrimeViewHolder holder, int position) {
            Crime current = crimes.get(position);
            holder.crimeText.setText(current.getTitle());
            holder.crimeDate.setText(current.getDate().toString());
            if (current.isSolved()){
                holder.crimeSolved.setText("Crime is solved");
            }
            else holder.crimeSolved.setText("Crime is not solved");

            holder.deleteCrime.setOnClickListener(v -> {
                dbHandler.deleteCrime(current.getTitle());
                getCrimes();
                notifyDataSetChanged();
            });

            holder.editCrime.setOnClickListener(v -> {
                Crime element = crimes.get(position);

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                intent.putExtra("id", element.getId());
                intent.putExtra("position", position);

                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return crimes.size();
        }
    }
}