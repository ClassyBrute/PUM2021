package com.example.studentcrimeapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
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
        public CheckBox crimeSolved;
        public Button dateTime;

        public PagerViewHolder(@NonNull View itemView) {
            super(itemView);
            crimeText = itemView.findViewById(R.id.editTextTitle);
            crimeSolved = itemView.findViewById(R.id.checkBox);
            dateTime = itemView.findViewById(R.id.dateTime);
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
        holder.crimeSolved.setChecked(current.isSolved());
        holder.dateTime.setText(current.getDate().toString());

        holder.crimeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                current.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.crimeSolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                current.setSolved(holder.crimeSolved.isChecked());
            }
        });

        holder.dateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar currentDate = Calendar.getInstance();
                Calendar date = Calendar.getInstance();
                new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date.set(year, monthOfYear, dayOfMonth);
                        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                date.set(Calendar.MINUTE, minute);
                                current.setDate(date.getTime());
                                holder.dateTime.setText(date.getTime().toString());
                            }
                        }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
                    }
                }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return crimeList.size();
    }
}