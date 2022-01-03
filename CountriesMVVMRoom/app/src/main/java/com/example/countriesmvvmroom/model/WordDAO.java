package com.example.countriesmvvmroom.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

// Data access object
@Dao
public interface WordDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(WordEntity word);

    @Query("DELETE FROM word_table")
    void deleteAll();

    @Query("SELECT * FROM word_table ORDER BY name ASC")
    LiveData<List<WordEntity>> getSortedWords();
}
