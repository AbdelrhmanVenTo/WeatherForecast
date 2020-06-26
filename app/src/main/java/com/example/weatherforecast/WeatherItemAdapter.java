package com.example.weatherforecast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.weatherforecast.Model.ListItem;
import com.example.weatherforecast.Model.WeatherItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherItemAdapter extends RecyclerView.Adapter<WeatherItemAdapter.ViewHolder> {

    List<ListItem> weatherList;
    Context context;
    OnItemClickListener onItemClickListener;


    public WeatherItemAdapter(List<ListItem> weatherList, Context context) {
        this.weatherList = weatherList;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final ListItem item = weatherList.get(position);
        WeatherItem weatherItem = item.getWeather().get(0);
        viewHolder.day.setText(dayName(item.getDtTxt(),"yyyy-MM-DD HH:MM:ss"));
        viewHolder.tempDay.setText(item.getMain().getTemp()+"°C");


        Glide
                .with(context)
                .load("http://openweathermap.org/img/wn/"+weatherItem.getIcon()+"@2x.png")
                .into(viewHolder.icon);



    }

    @Override
    public int getItemCount() {
        if (weatherList == null)
            return 0;
        return weatherList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView day, tempDay ,seeMore;
        ImageView icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day);
            tempDay = itemView.findViewById(R.id.tempDay);
            seeMore = itemView.findViewById(R.id.seeMore);
            icon = itemView.findViewById(R.id.icon);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int pos,ListItem item);
    }

    public static String dayName(String inputDate, String format){
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
    }

}
