package com.example.studentcrimeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class CrimeAdapter extends RecyclerView.Adapter<CrimeAdapter.CrimeViewHolder> {

    private final LinkedList<Crime> crimeList;
    private LayoutInflater inflater;

    public CrimeAdapter(Context context, LinkedList<Crime> crimeList) {
        inflater = LayoutInflater.from(context);
        this.crimeList = crimeList;
    }

    class CrimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView crimeText;
        final CrimeAdapter adapter;

        public CrimeViewHolder(@NonNull View itemView, CrimeAdapter adapter) {
            super(itemView);
            crimeText = itemView.findViewById(R.id.crime);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            Crime element = crimeList.get(position);
            element.setTitle("Clicked! " + element.getTitle());
            crimeList.set(position, element);
            adapter.notifyItemChanged(position);
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
    }

    @Override
    public int getItemCount() {
        return crimeList.size();
    }
}
