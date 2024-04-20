package com.example.lab3_20203607.services;
import com.example.lab3_20203607.dto.Pelicula;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OMDBService {

    @GET("/")
    Call<Pelicula> getPelicula(@Query("apikey") String apiKey, @Query("i") String imdbID);


}