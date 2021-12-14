package com.example.studentcrimeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton addCrime;
    private ImageButton deleteCrime;
    private ImageButton editCrime;
    private SearchView searchView;

    CrimeAdapter crimeAdapter;

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
        searchView = findViewById(R.id.search_bar);
        searchView.setQueryHint("Type to search...");

        dbHandler = new DBHandler(this);

        getCrimes();

        crimeAdapter = new CrimeAdapter();
        recyclerView.setAdapter(crimeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addCrime.setOnClickListener(v -> {
            addCrimes();
            getCrimes();
            recyclerView.getAdapter().notifyDataSetChanged();
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                crimeAdapter.getFilter().filter(newText);
                crimeAdapter.updateCrimes(crimes);

                return false;
            }
        });

        searchView.setOnCloseListener(() -> {
            getCrimes();
            recyclerView.getAdapter().notifyDataSetChanged();
            return false;
        });
    }

    @Override
    protected void onDestroy() {
        dbHandler.close();
        super.onDestroy();
    }

    @Override
    protected void onResume(){
        super.onResume();
        getCrimes();
        recyclerView.getAdapter().notifyDataSetChanged();
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
                Date date = new Date(cursor.getLong(3));

                crimes.add(new Crime(id, title, date, solved));
            }
        }
    }

    public void addCrimes() {
        String title = "New crime! ";

        dbHandler.addCrimes(new Crime(title, new Date(), false));
        getCrimes();
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private class CrimeAdapter extends RecyclerView.Adapter<MainActivity.CrimeAdapter.CrimeViewHolder> implements Filterable {

        ArrayList<Crime> crimeList;

        // constructor
        public CrimeAdapter() {
            this.crimeList = new ArrayList<>(crimes);
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
        }

        public void updateCrimes(ArrayList<Crime> crimesList){
            crimeList = crimesList;
            notifyDataSetChanged();
        }

        @Override
        public Filter getFilter(){
            return filter;
        }

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                ArrayList<Crime> filtered = new ArrayList<>();

                if (constraint.toString().isEmpty())
                    filtered.addAll(crimeList);
                else {
                    for (Crime crime: crimeList){
                        if (crime.getTitle().toLowerCase().contains(constraint.toString().toLowerCase()))
                            filtered.add(crime);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered;
                return filterResults;
            }

            @Override
            protected void publishResults(final CharSequence constraint, FilterResults results) {
                crimes.clear();
                crimes.addAll((Collection<? extends Crime>) results.values);
                notifyDataSetChanged();
            }
        };

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
                intent.putExtra("title", element.getTitle());
                intent.putExtra("solved", element.isSolved());
                intent.putExtra("date", element.getDate().toString());

                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return crimes.size();
        }
    }
}