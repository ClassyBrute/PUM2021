package com.example.countriesmvvmroom.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countriesmvvmroom.R;
import com.example.countriesmvvmroom.model.WordEntity;

public class WordRecyclerViewAdapter extends ListAdapter <WordEntity, WordRecyclerViewAdapter.WordViewHolder> {
    protected WordRecyclerViewAdapter(@NonNull DiffUtil.ItemCallback<WordEntity> diffCallback) {
        super(diffCallback);
    }

    static class WordViewHolder extends RecyclerView.ViewHolder {

        private final TextView wordItemView;
        private final TextView wordItemView_cap;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.text_name);
            wordItemView_cap = itemView.findViewById(R.id.text_capital);
        }
    }

    @NonNull
    @Override
    public WordRecyclerViewAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WordViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_view,
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull WordRecyclerViewAdapter.WordViewHolder holder, int position) {
        WordEntity current = getItem(position);
        holder.wordItemView.setText(current.getWord());
        holder.wordItemView_cap.setText(current.getCapital());
    }

    static class WordDiff extends DiffUtil.ItemCallback<WordEntity> {

        @Override
        public boolean areItemsTheSame(@NonNull WordEntity oldItem, @NonNull WordEntity newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull WordEntity oldItem, @NonNull WordEntity newItem) {
            return oldItem.getWord().equals(newItem.getWord());
        }
    }
}
