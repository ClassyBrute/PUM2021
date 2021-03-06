package com.example.countriesmvvmroom.model;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountriesService {

    private static final String BASE_URL = "https://restcountries.com/v2/";

    private final CountriesAPI api;

    public CountriesService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        api = retrofit.create(CountriesAPI.class);
    }

    public Call<List<WordEntity>> getCountries() {
        return api.getCountries();
    }
}
