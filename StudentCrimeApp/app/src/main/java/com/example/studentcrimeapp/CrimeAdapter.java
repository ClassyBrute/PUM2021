package com.example.studentcrimeapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.LinkedList;

public class CrimeAdapter extends RecyclerView.Adapter<CrimeAdapter.CrimeViewHolder> {

    private final LinkedList<Crime> crimeList;
    private LayoutInflater inflater;

    // constructor
    public CrimeAdapter(Context context, LinkedList<Crime> crimeList) {
        inflater = LayoutInflater.from(context);
        this.crimeList = crimeList;
    }

    class CrimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView crimeText;
        public TextView crimeDate;
        public TextView crimeSolved;
        final CrimeAdapter adapter;

        public CrimeViewHolder(@NonNull View itemView, CrimeAdapter adapter) {
            super(itemView);
            crimeText = itemView.findViewById(R.id.crime_text);
            crimeDate = itemView.findViewById(R.id.crime_date);
            crimeSolved = itemView.findViewById(R.id.crime_solved);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            Crime element = crimeList.get(position);

            Intent intent = new Intent(inflater.getContext(), DetailActivity.class);

            intent.putExtra("id", element.getId());
            intent.putExtra("position", position);

            inflater.getContext().startActivity(intent);
        }
    }

    @NonNull
    @Override
    public CrimeAdapter.CrimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.crime_list , parent, false);
        return new CrimeViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CrimeAdapter.CrimeViewHolder holder, int position) {
        Crime current = crimeList.get(position);
        holder.crimeText.setText(current.getTitle());
        holder.crimeDate.setText(current.getDate().toString());
        if (current.isSolved()){
            holder.crimeSolved.setText("Crime is solved");
        }
        else holder.crimeSolved.setText("Crime is not solved");
    }

    @Override
    public int getItemCount() {
        return crimeList.size();
    }
}
