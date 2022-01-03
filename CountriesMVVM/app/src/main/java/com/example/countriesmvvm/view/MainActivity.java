package com.example.countriesmvvm.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.countriesmvvm.R;
import com.example.countriesmvvm.viewmodel.CountriesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> listCountries = new ArrayList<>();
    private RecyclerView recyclerView;
    private CountriesAdapter adapter;
    private CountriesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(CountriesViewModel.class);

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CountriesAdapter();
        recyclerView.setAdapter(adapter);

        observeViewModel();
    }

    private void observeViewModel(){
        viewModel.getCountries().observe(this, countries -> {
            if (listCountries != null){
                listCountries.clear();
                listCountries.addAll(countries);
                adapter.notifyDataSetChanged();
            }
        });

        viewModel.getCountryError().observe(this, error -> {
            if (error){
                Toast.makeText(this, "FAILED TO CONNECT", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        viewModel.onDispose();
        super.onStop();
    }


    public class CountriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        public CountriesAdapter(){}

        private class ViewHolder extends RecyclerView.ViewHolder{
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(
                    LayoutInflater.from(MainActivity.this).inflate(
                            R.layout.item_view,
                            parent,
                            false
                    )
            );
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            String element;
            String element1;

            if (position % 2 == 0){
                element = listCountries.get(position);
                element1 = listCountries.get(position+1);
            } else {
                element = listCountries.get(position+1);
                element1 = listCountries.get(position+2);
            }

            TextView title = holder.itemView.findViewById(R.id.text_view);
            title.setText(element);

            TextView capital = holder.itemView.findViewById(R.id.capital_view);
            capital.setText(element1);
        }

        @Override
        public int getItemCount() {
            return listCountries.size();
        }
    }
}