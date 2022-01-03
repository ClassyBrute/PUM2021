package com.example.countriesmvvmroom.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.countriesmvvmroom.database.WordRoom;
import com.example.countriesmvvmroom.model.CountriesService;
import com.example.countriesmvvmroom.model.WordDAO;
import com.example.countriesmvvmroom.model.WordEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
