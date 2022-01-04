package com.example.countriesmvvmroom.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.countriesmvvmroom.R;
import com.example.countriesmvvmroom.viewmodel.WordViewModel;

public class MainActivity extends AppCompatActivity {

    private WordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        final WordRecyclerViewAdapter adapter = new WordRecyclerViewAdapter(
                new WordRecyclerViewAdapter.WordDiff()
        );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this, ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(this.getApplication())).get(WordViewModel.class);

        viewModel.getAllWords().observe(this, adapter::submitList);
    }
}