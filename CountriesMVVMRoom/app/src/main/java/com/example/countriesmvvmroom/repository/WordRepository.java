package com.example.countriesmvvmroom.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.countriesmvvmroom.database.WordRoom;
import com.example.countriesmvvmroom.model.WordDAO;
import com.example.countriesmvvmroom.model.WordEntity;

import java.util.List;

public class WordRepository {

    private WordDAO mWordDao;
    private LiveData<List<WordEntity>> mAllWords;

    public WordRepository(Application application){
        WordRoom db = WordRoom.getDatabase(application);

        mWordDao = db.wordDAO();
        mAllWords = mWordDao.getSortedWords();
    }

    public LiveData<List<WordEntity>> getAllWords() {
        return mAllWords;
    }

    public void insert(WordEntity word){
        WordRoom.databaseWriteExecutor.execute(() ->{
            mWordDao.insert(word);
        });
    }
}
