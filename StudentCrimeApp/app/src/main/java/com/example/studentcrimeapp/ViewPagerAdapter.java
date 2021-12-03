package com.example.studentcrimeapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.LinkedList;


public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder> {

    private LinkedList<Crime> crimeList;
    private Context context;
    private LayoutInflater inflater;

    public ViewPagerAdapter(Context context){
        inflater = LayoutInflater.from(context);
        crimeList = CrimeLab.mCrimes;
        this.context = context;
    }

    public class PagerViewHolder extends RecyclerView.ViewHolder {

        public TextView crimeText;
        public TextView crimeDate;
        public CheckBox crimeSolved;

        public PagerViewHolder(@NonNull View itemView) {
            super(itemView);
            crimeText = itemView.findViewById(R.id.editTextTitle);
            crimeDate = itemView.findViewById(R.id.dateTime);
            crimeSolved = itemView.findViewById(R.id.checkBox);
        }
    }

    @NonNull
    @Override
    public ViewPagerAdapter.PagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PagerViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.activity_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerAdapter.PagerViewHolder holder, int position) {
        Crime current = crimeList.get(position);
        holder.crimeText.setText(current.getTitle());
        holder.crimeDate.setText(current.getDate().toString());
        holder.crimeSolved.setChecked(current.isSolved());
    }

    @Override
    public int getItemCount() {
        return crimeList.size();
    }
}