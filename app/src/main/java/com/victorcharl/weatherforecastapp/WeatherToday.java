package com.victorcharl.weatherforecastapp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.IpSecManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.androdocs.httprequest.HttpRequest;
import com.squareup.picasso.Picasso;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class WeatherToday extends Fragment {

    private static final String API_KEY = "bd154dbe4caf8a49b5926c34fcfa6dcd";
    private static final String currentLocation = "Airdrie, Ca";

    private static final String iconLocation = "https://openweathermap.org/img/wn/";

    /*TODO
    *  get latitude and longitude*/

    private TextView location_txtVw;
    private TextView dateAndTime_txtVw;
    private TextView temperature_txtVw;
    private TextView feelsLike_txtVw;
    private TextView sunrise_txtVw;
    private TextView sunset_txtVw;
    private TextView status_txtVw;
    private TextView wind_txtVw;
    private TextView humidity__txtVw;
    private TextView visibility__txtVw;
    private TextView pressure_txtVw;

    private TextView errorMessage;

    private ProgressBar loader;

    private TextView day1min;
    private TextView day1max;
    private TextView day1status;
    private TextView day2min;
    private TextView day2max;
    private TextView day2status;
    private TextView day3min;
    private TextView day3max;
    private TextView day3status;
    private TextView day4min;
    private TextView day4max;
    private TextView day4status;
    private TextView day5min;
    private TextView day5max;
    private TextView day5status;

    private ImageView day1photo, day2photo, day3photo, day4photo, day5photo;

    private RelativeLayout relativeLayout;

    private ArrayList<String> temp_icon_stat;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_today, container, false);

        relativeLayout = view.findViewById(R.id.mainContainer);

        errorMessage = view.findViewById(R.id.txVw_error);
        loader = view.findViewById(R.id.loader);

        /*TODAY'S WEATHER DETAILS*/
        location_txtVw = view.findViewById(R.id.location);
        dateAndTime_txtVw = view.findViewById(R.id.dataAndTime);
        temperature_txtVw = view.findViewById(R.id.temperature);
        feelsLike_txtVw = view.findViewById(R.id.fellsLike);
        sunrise_txtVw = view.findViewById(R.id.sunrise);
        sunset_txtVw = view.findViewById(R.id.sunset);
        status_txtVw = view.findViewById(R.id.status);
        humidity__txtVw = view.findViewById(R.id.humidity);
        wind_txtVw = view.findViewById(R.id.wind);
        visibility__txtVw = view.findViewById(R.id.visibility);
        pressure_txtVw = view.findViewById(R.id.pressure);

        /*WEEKLY WEATHER DETAILS*/
        TextView day1 = view.findViewById(R.id.day1);
        day1photo = view.findViewById(R.id.day1photo);
        day1min = view.findViewById(R.id.day1_temp_min);
        day1max = view.findViewById(R.id.day1_temp_max);
        day1status = view.findViewById(R.id.day1status);
        TextView day2 = view.findViewById(R.id.day2);
        day2photo = view.findViewById(R.id.day2photo);
        day2min = view.findViewById(R.id.day2_temp_min);
        day2max = view.findViewById(R.id.day2_temp_max);
        day2status = view.findViewById(R.id.day2status);
        TextView day3 = view.findViewById(R.id.day3);
        day3photo = view.findViewById(R.id.day3photo);
        day3min = view.findViewById(R.id.day3_temp_min);
        day3max = view.findViewById(R.id.day3_temp_max);
        day3status = view.findViewById(R.id.day3status);
        TextView day4 = view.findViewById(R.id.day4);
        day4photo = view.findViewById(R.id.day4photo);
        day4min = view.findViewById(R.id.day4_temp_min);
        day4max = view.findViewById(R.id.day4_temp_max);
        day4status = view.findViewById(R.id.day4status);
        TextView day5 = view.findViewById(R.id.day5);
        day5photo = view.findViewById(R.id.day5photo);
        day5min = view.findViewById(R.id.day5_temp_min);
        day5max = view.findViewById(R.id.day5_temp_max);
        day5status = view.findViewById(R.id.day5status);

        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        TextView[] TV_days = new TextView[]{day1, day2, day3, day4, day5};
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        for (int i = 1; i < TV_days.length; i++) {
            day++;
            /*https://stackoverflow.com/questions/1616854/array-iteration-starting-from-the-middle*/
            for (int j = day; j < days.length + day; j++)
            {
                TV_days[i].setText(days[(j + days.length) % days.length]);
            }
        }

        new getTodayWeather().execute();
        new getFourDayWeather().execute();

        return view;
    }

    @SuppressLint("StaticFieldLeak")
    class getTodayWeather extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            relativeLayout.setVisibility(View.GONE);
            loader.setVisibility(View.VISIBLE);

        }

        /*https://github.com/androdocs/Simple-HTTP-Request*/
        protected String doInBackground(String... args) {
            return HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + currentLocation + "&units=metric&appid=" + API_KEY);
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result) {
            /*https://abhiandroid.com/programming/json*/
            try {
                /*get JSONObject from JSON file*/
                JSONObject jsonObj = new JSONObject(result);
                /*fetch JSONObject named main, sys, wind*/
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                double visibility = Double.parseDouble(jsonObj.getString("visibility")) * .001;

                String address = jsonObj.getString("name") + ", " + sys.getString("country");

                /*Getting value from JSON object in "main" object*/
                String updateTime = new SimpleDateFormat("MMM/dd/yyyy hh:mm a", Locale.ENGLISH).format(new Date(jsonObj.getLong("dt") * 1000));
                String temp = main.getString("temp");
                String tempFeelsLike = main.getString("feels_like");
                String tempMin = main.getString("temp_min");
                String tempMax = main.getString("temp_max");
                String pressure = main.getString("pressure");
                String humidity = main.getString("humidity");

                /*Getting value from JSON object in "sys" object*/
                long sunrise = sys.getLong("sunrise");
                long sunset = sys.getLong("sunset");

                /*Getting value from JSON object in "wind" object*/
                String windSpeed = wind.getString("speed");

                /*Getting value from JSON object in "weather" object*/
                String weatherMain = weather.getString("main");
                String weatherDescription = weather.getString("description");
                String weatherIcon = weather.getString("icon");

                relativeLayout.setVisibility(View.VISIBLE);
                loader.setVisibility(View.GONE);

                /*populating today's weather and it's details*/
                location_txtVw.setText(address);
                dateAndTime_txtVw.setText("Updated at: " + updateTime);
                status_txtVw.setText(weatherDescription.substring(0, 1).toUpperCase() + weatherDescription.substring(1).toLowerCase());
                temperature_txtVw.setText(roundingOff(temp) + "°C");
                feelsLike_txtVw.setText("Feels Like : " + roundingOff(tempFeelsLike)  + "°C");
                sunrise_txtVw.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
                sunset_txtVw.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000)));
                pressure_txtVw.setText(pressure + " hPa");
                humidity__txtVw.setText(humidity + " %");
                visibility__txtVw.setText(String.valueOf(visibility).substring(0,4) + " km");
                wind_txtVw.setText(windSpeed + " km/hr");

            } catch (JSONException e) {
                relativeLayout.setVisibility(View.GONE);
                loader.setVisibility(View.GONE);
                errorMessage.setVisibility(View.VISIBLE);
            }
        }
    }

    class getFourDayWeather extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            relativeLayout.setVisibility(View.GONE);
            loader.setVisibility(View.VISIBLE);

        }

        //https://github.com/androdocs/Simple-HTTP-Request
        protected String doInBackground(String... args) {
            return HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/forecast?q=" + currentLocation + "&appid=" + API_KEY + "&units=metric");
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result) {

            try {

                relativeLayout.setVisibility(View.VISIBLE);
                loader.setVisibility(View.GONE);

                ArrayList<String> temp_min = new ArrayList<>();
                ArrayList<String> temp_max = new ArrayList<>();
                ArrayList<String> temp_stat = new ArrayList<>();
                temp_icon_stat = new ArrayList<>();

                JSONObject jsonObject = new JSONObject(result);
                JSONArray fiveDaysWeatherInformation = jsonObject.getJSONArray("list");
                for(int i = 0; i < fiveDaysWeatherInformation.length(); i+=8 ) {
                    JSONObject myList = fiveDaysWeatherInformation.getJSONObject(i);
                    JSONObject main = myList.getJSONObject("main");

                    JSONArray weather = myList.getJSONArray("weather");
                    JSONObject stat = weather.getJSONObject(0);

                    temp_min.add(main.getString("temp_min"));
                    temp_max.add(main.getString("temp_max"));
                    temp_stat.add(stat.getString("main"));
                    temp_icon_stat.add(iconLocation + stat.getString("icon")+ "@2x.png");

                }

                TextView[] TV_temp_min = new TextView[]{day1min, day2min, day3min, day4min, day5min};
                TextView[] TV_temp_max = new TextView[]{day1max, day2max, day3max, day4max, day5max};
                TextView[] TV_temp_stat = new TextView[]{day1status, day2status, day3status, day4status, day5status};

                for(int j = 0;  j < TV_temp_max.length; j++) {
                    TV_temp_min[j].setText(roundingOff(temp_min.get(j)) + "°");
                    TV_temp_max[j].setText(roundingOff(temp_max.get(j)) + "°");
                    TV_temp_stat[j].setText(temp_stat.get(j));
                }

                displayImage();

            } catch (JSONException e) {
                relativeLayout.setVisibility(View.GONE);
                loader.setVisibility(View.GONE);
                errorMessage.setVisibility(View.VISIBLE);
            }

        }
    }

    private int roundingOff(String value){
        return (int)Math.round(Double.parseDouble(value));
    }

    private void displayImage(){

        ImageView[] photoStat = new ImageView[]{day1photo, day2photo, day3photo, day4photo, day5photo};

        /*https://stackoverflow.com/questions/6407324/how-to-display-image-from-url-on-android*/
        for (int i = 0; i < photoStat.length; i++){
            Picasso.get().load(temp_icon_stat.get(i)).into(photoStat[i]);
        }
    }

}
