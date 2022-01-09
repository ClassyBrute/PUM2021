package com.example.wydatkiapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    ExpenseAdapter expenseAdapter;

    private DBHandler dbHandler;
    private final ArrayList<Expense> expenseList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_expenses);

        recyclerView = findViewById(R.id.recycler_view);

        dbHandler = new DBHandler(this);

        getExpenses();

        expenseAdapter = new ExpenseAdapter();
        recyclerView.setAdapter(expenseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        getExpenses();
        expenseAdapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getExpenses() {
        expenseList.clear();
        Cursor cursor = dbHandler.getExpenses();

        if (cursor.getCount() == 0)
            Toast.makeText(this, "EMPTY", Toast.LENGTH_SHORT).show();
        else {
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String owner = cursor.getString(1);
                int amount = cursor.getInt(2);
                LocalDate date = LocalDate.parse(cursor.getString(3));

                expenseList.add(new Expense(id, owner, amount, date));
            }
        }
    }

    private class ExpenseAdapter extends RecyclerView.Adapter<RecyclerActivity.ExpenseAdapter.ExpenseViewHolder> {

        public ExpenseAdapter() {
        }

        private class ExpenseViewHolder extends RecyclerView.ViewHolder {

            public ExpenseViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            private final TextView amount = itemView.findViewById(R.id.amount);
            private final TextView date = itemView.findViewById(R.id.date_added);
            private final TextView owner = itemView.findViewById(R.id.owner);
            private final LinearLayout detail = itemView.findViewById(R.id.detail_expense);
        }

        @NonNull
        @Override
        public ExpenseAdapter.ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ExpenseAdapter.ExpenseViewHolder(LayoutInflater.from(RecyclerActivity.this)
            .inflate(R.layout.item_view, parent, false));
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onBindViewHolder(@NonNull ExpenseAdapter.ExpenseViewHolder holder, int position) {
            Expense current = expenseList.get(position);
            holder.amount.setText(String.valueOf(current.getAmount()) + " zł");
            holder.date.setText(String.valueOf(current.getDate()));
            holder.owner.setText(current.getOwner());

            holder.detail.setOnClickListener(v -> {
                Expense element = expenseList.get(position);

                Intent intent = new Intent(RecyclerActivity.this, DetailExpense.class);

                intent.putExtra("id", element.getId());
                intent.putExtra("position", position);
                intent.putExtra("amount", element.getAmount());
                intent.putExtra("date", element.getDate().toString());
                intent.putExtra("owner", element.getOwner());

                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return expenseList.size();
        }
    }
}
