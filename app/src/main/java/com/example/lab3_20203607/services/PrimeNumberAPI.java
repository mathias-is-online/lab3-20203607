package com.example.lab3_20203607.services;
import com.example.lab3_20203607.dto.Primo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface PrimeNumberAPI {

    @GET("/primeNumbers")
    Call<List<Primo>> getPrimeNumbers(@Query("len") int length, @Query("order") int order);

}
