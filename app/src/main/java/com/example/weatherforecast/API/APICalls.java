package com.example.weatherforecast.API;

import com.example.weatherforecast.Model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APICalls {

    @GET("forecast")
    Call<WeatherResponse> weather(@Query("q") String cityName
                                ,@Query("apikey")String apiKey,
                                  @Query("units")String units);

    @GET("forecast")
    Call<WeatherResponse> weatherGPS(@Query("lat") String lat
                                     ,@Query("lon") String lon
                                     ,@Query("apikey")String apiKey
                                     ,@Query("units")String units);

}
