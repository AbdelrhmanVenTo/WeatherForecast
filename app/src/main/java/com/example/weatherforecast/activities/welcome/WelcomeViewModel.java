package com.example.weatherforecast.activities.welcome;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.weatherforecast.API.ApiManager;
import com.example.weatherforecast.Model.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeViewModel extends AndroidViewModel {

    protected MutableLiveData<WeatherResponse> weatherLiveDatal;
    protected MutableLiveData<Integer> errorMassage;


    public WelcomeViewModel(@NonNull Application application) {
        super(application);
        weatherLiveDatal = new MutableLiveData<>();
        errorMassage =  new MutableLiveData<>();
    }

    public MutableLiveData<WeatherResponse> getWeatherLiveDatal() {
        return weatherLiveDatal;
    }

    public MutableLiveData<Integer> getErrorMassage() {
        return errorMassage;
    }

    public void getWeather (String cityName, String apiKey , String units){
        ApiManager.getAPIs().weather(cityName, apiKey , units).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()){
                    weatherLiveDatal.postValue(response.body());
                }else {
                    errorMassage.postValue(0);

                }

            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                errorMassage.postValue(1);

            }
        });
    }


    public void getWeatherGPS(String lat ,String lang , String key, String units){
        ApiManager.getAPIs().weatherGPS(lat, lang, key,units).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()){
                    weatherLiveDatal.postValue(response.body());
                }else {
                    errorMassage.postValue(0);

                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                errorMassage.postValue(1);
            }
        });

    }
}
