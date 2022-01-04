package com.example.countriesmvvmroom.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.countriesmvvmroom.model.WordEntity;
import com.example.countriesmvvmroom.repository.WordRepository;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private final WordRepository mRepository;
    private final LiveData<List<WordEntity>> mAllWords;

    public WordViewModel(@NonNull Application application) {
        super(application);

        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    public LiveData<List<WordEntity>> getAllWords() {
        return mAllWords;
    }

    public void insert(WordEntity word){
        mRepository.insert(word);
    }
}
