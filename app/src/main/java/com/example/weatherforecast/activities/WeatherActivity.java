package com.example.weatherforecast.activities;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weatherforecast.Base.BaseActivity;
import com.example.weatherforecast.Model.WeatherResponse;
import com.example.weatherforecast.R;
import com.example.weatherforecast.WeatherItemAdapter;
import com.example.weatherforecast.activities.welcome.WelcomeViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WeatherActivity extends BaseActivity {

    ScrollView view;
    public static String API_KEY = "21fee46506ddd1641aa66f828929541a";
    protected TextView temp;
    protected TextView description;
    protected TextView pressure;
    protected TextView fellTemp;
    protected TextView humidity;
    protected TextView windSpeed;
    protected RecyclerView weatherList;
    protected TextView dayName;
    protected ImageView imageView;
    protected TextView tempMin;
    protected TextView tempMax;
    protected TextView cityNameTV;
    WelcomeViewModel viewModel;
    WeatherItemAdapter adapter;


    String cityName, lat, lang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_weather);
        initView();
        init();
        onClickListeners();
    }

    public void init() {
        viewModel = ViewModelProviders.of(this)
                .get(WelcomeViewModel.class);
        cityName = getIntent().getStringExtra("cityName");
        lat = getIntent().getStringExtra("lat");
        lang = getIntent().getStringExtra("lang");
        adapter = new WeatherItemAdapter(null, activity);
        weatherList.setAdapter(adapter);
        weatherList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        if (cityName.equals("")) {
            viewModel.getWeatherGPS(lat, lang, API_KEY, "metric");
        } else {
            viewModel.getWeather(cityName, API_KEY, "metric");
        }

    }


    private void initView() {
        view = findViewById(R.id.screen);
        temp = (TextView) findViewById(R.id.temp);
        description = (TextView) findViewById(R.id.description);
        pressure = (TextView) findViewById(R.id.pressure);
        fellTemp = (TextView) findViewById(R.id.fell_temp);
        humidity = (TextView) findViewById(R.id.humidity);
        windSpeed = (TextView) findViewById(R.id.wind_speed);
        weatherList = (RecyclerView) findViewById(R.id.weatherList);
        dayName = (TextView) findViewById(R.id.dayName);
        imageView = (ImageView) findViewById(R.id.imageView);
        tempMin = (TextView) findViewById(R.id.tempMin);
        tempMax = (TextView) findViewById(R.id.tempMax);
        cityNameTV = findViewById(R.id.cityName);
    }

    private void onClickListeners() {
        viewModel.getWeatherLiveDatal().observe(activity, new Observer<WeatherResponse>() {
            @Override
            public void onChanged(@Nullable WeatherResponse weatherResponse) {

                dayName.setText(dayName(weatherResponse.getList().get(0).getDtTxt(),"yyyy-MM-DD HH:MM:ss"));
                Log.e("TAG", "onChanged: "+weatherResponse.getList().get(0).getDtTxt() );
                cityNameTV.setText(weatherResponse.getCity().getName());
                Glide
                        .with(activity)
                        .load("http://openweathermap.org/img/wn/"+weatherResponse.getList().get(0).
                                getWeather().get(0).getIcon()+"@2x.png")
                        .into(imageView);
                temp.setText(weatherResponse.getList().get(0).getMain().getTemp()+"°C");
                tempMin.setText("Min : "+weatherResponse.getList().get(0).getMain().getTempMin()+" °C");
                tempMax.setText("Max : "+weatherResponse.getList().get(0).getMain().getTempMax()+" °C");
                description.setText(weatherResponse.getList().get(0).getWeather().get(0).getDescription());
                pressure.setText(weatherResponse.getList().get(0).getMain().getPressure()+" hPa");
                humidity.setText(weatherResponse.getList().get(0).getMain().getHumidity()+ "%");
                fellTemp.setText(weatherResponse.getList().get(0).getMain().getFeels_like()+ "\u2103\n"+"(Feels Like)");
                windSpeed.setText(weatherResponse.getList().get(0).getWind().getSpeed()+" km/h");

                adapter = new WeatherItemAdapter(weatherResponse.getList(),activity);
                weatherList.setAdapter(adapter);




            }
        });
    }

    public static String dayName(String inputDate, String format) {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleformat = new SimpleDateFormat("E, dd-MMM-yyyy");
        System.out.println("Today's date and time = "+simpleformat.format(cal.getTime()));

        return simpleformat.format(cal.getTime());
    }
}
