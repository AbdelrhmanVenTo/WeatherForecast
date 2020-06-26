package com.example.weatherforecast.activities.welcome;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherforecast.API.ApiManager;
import com.example.weatherforecast.Base.BaseActivity;
import com.example.weatherforecast.Model.WeatherResponse;
import com.example.weatherforecast.MyLocationProvider;
import com.example.weatherforecast.R;
import com.example.weatherforecast.activities.WeatherActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomePage extends BaseActivity implements View.OnClickListener {

    protected TextInputLayout cityName;
    protected ImageView getWeather;
    protected TextView byGPS;
    protected String cityNameSelect;
    MyLocationProvider myLocationProvider;
    android.location.Location currentLocation;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION_CODE = 500;
    List<Address> addresses ;
    String languageToLoad = "en"; // your language
    Locale mLocale;
    Geocoder geocoder;
    String city  , lat , lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_welcome_page);
        initView();
    }

    @Override
    public void onClick(View view) {
       if (view.getId() == R.id.getWeather) {
            Intent intent = new Intent(this, WeatherActivity.class);
            cityNameSelect = cityName.getEditText().getText().toString();
            intent.putExtra("cityName", cityNameSelect);
            startActivity(intent);
           //getWeather(cityName.getEditText().getText().toString(),API_KEY);
        }else if (view.getId() == R.id.byGPS){
           if(isLocationPermissionsGranted()){
               //call function
               showUserLocation();

           }else {
               requestLocationPermission();

           }
       }
    }

    public boolean isLocationPermissionsGranted(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestLocationPermission() {
        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {



            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("GPS");
            alertDialogBuilder.setMessage("Please open GPS");
            alertDialogBuilder.setCancelable(false);

            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int arg1) {
                    dialog.cancel();
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION_CODE);


                }
            });
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (myLocationProvider.isGpsEnabled()){
                        dialog.cancel();
                    }

                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


        } else {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION_CODE);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    //call your function
                    showUserLocation();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Can't Get your Location", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public void showUserLocation(){

        if(myLocationProvider==null)
            myLocationProvider=new MyLocationProvider(this);

        if(!myLocationProvider.isGpsEnabled()){
            //Toast.makeText(this, "showuser=null", Toast.LENGTH_SHORT).show();
            Log.e("heereeeeee","you  are here");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("رسالة تأكيد");
            alertDialogBuilder.setMessage("من فضلك قم بتشغيل ال GPS الخاص بالهاتف");
            alertDialogBuilder.setCancelable(true);

            alertDialogBuilder.setPositiveButton("حسنا", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int arg1) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    dialog.cancel();
                    Log.e("log","you  are here");

                }
            });
            alertDialogBuilder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


        currentLocation=myLocationProvider.getUserLocation();

            try {
                lat = String.valueOf(currentLocation.getLatitude());
                lang = String.valueOf(currentLocation.getLongitude());
                mLocale = new Locale(languageToLoad);
                Locale.setDefault(mLocale);
                geocoder = new Geocoder(this, Locale.getDefault());
                addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                city = addresses.get(0).getLocality();
                Intent intent = new Intent(this, WeatherActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lang", lang);
                startActivity(intent);

            } catch (IOException e) {
                e.printStackTrace();
            }




        }


    }



    private void initView() {
        cityName =  findViewById(R.id.cityName);
        getWeather = findViewById(R.id.getWeather);
        getWeather.setOnClickListener(WelcomePage.this);
        byGPS =  findViewById(R.id.byGPS);
        byGPS.setOnClickListener(WelcomePage.this);
    }
}
