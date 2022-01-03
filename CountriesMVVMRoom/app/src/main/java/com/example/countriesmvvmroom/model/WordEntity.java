package com.example.countriesmvvmroom.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "word_table")
public class WordEntity {

//    @PrimaryKey(autoGenerate = true)
//    private int id;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String word;

    @NonNull
    @ColumnInfo(name = "capital")
    @SerializedName("capital")
    private String capital;

    public WordEntity(@NonNull String word, @NonNull String capital){
        this.word = word;
        this.capital = capital;
    }

    public String getWord(){
        return this.word;
    }

    public String getCapital(){
        return this.capital;
    }
}
