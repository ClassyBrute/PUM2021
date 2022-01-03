package com.example.countriesmvvmroom.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.countriesmvvmroom.model.CountriesService;
import com.example.countriesmvvmroom.model.WordDAO;
import com.example.countriesmvvmroom.model.WordEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Database(entities = {WordEntity.class}, version = 1, exportSchema = false)
public abstract class WordRoom extends RoomDatabase {

    public abstract WordDAO wordDAO();

    private static volatile WordRoom INSTANCE;
    private static final int NUM_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUM_OF_THREADS);

    public static WordRoom getDatabase(final Context context){
        if (INSTANCE == null){
            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(),
                    WordRoom.class, "word_database_java")
                    .addCallback(sRoomDatabaseCallback)
                    .build();
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        private final CountriesService service = new CountriesService();

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

                Call<List<WordEntity>> object = service.getCountries();

                object.enqueue(new retrofit2.Callback<List<WordEntity>>() {
                    @Override
                    public void onResponse(Call<List<WordEntity>> call, Response<List<WordEntity>> response) {

                        databaseWriteExecutor.execute(() -> {
                            WordDAO dao = INSTANCE.wordDAO();
                            dao.deleteAll();

                            for (WordEntity word : response.body()) {
                            WordEntity wordEntity = new WordEntity(word.getWord(), word.getCapital());
                            dao.insert(wordEntity);
                        }});
                    }

                    @Override
                    public void onFailure (Call<List<WordEntity>> call, Throwable t){
                    }
                });
        }
    };
}