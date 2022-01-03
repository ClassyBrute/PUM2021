package com.example.countriesmvvmroom.model;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CountriesAPI {
    @GET("all")
//    Single<List<WordEntity>> getCountries();
    Call<List<WordEntity>> getCountries();
}
