package com.victorcharl.weatherforecastapp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.androdocs.httprequest.HttpRequest;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class WeatherToday extends Fragment {

    private static final String API_KEY = "bd154dbe4caf8a49b5926c34fcfa6dcd";
    private static final String currentLocation = "airdrie,ca";

    /*TODO
    *  get latitude and longitude*/

    private TextView location_txtVw;
    private TextView dateAndTime_txtVw;
    private TextView temperature_txtVw;
    private TextView temperatureMin_txtVw;
    private TextView temperatureMax_txtVw;
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

    private TextView day1min, day1max, day1status;
    private TextView day2, day2min, day2max;
    private TextView day3, day3min, day3max;
    private TextView day4, day4min, day4max;
    private TextView day5, day5min, day5max;
    private TextView day6, day6min, day6max;
    private TextView day7, day7min, day7max;

    ImageView day1photo;

    private RelativeLayout relativeLayout;

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
        day1status = view.findViewById(R.id.day1status);
        day1photo = view.findViewById(R.id.day1photo);
        day1min = view.findViewById(R.id.day1_temp_min);
        day1max = view.findViewById(R.id.day1_temp_max);
        day2 = view.findViewById(R.id.day2);
        day2min = view.findViewById(R.id.day2_temp_min);
        day2max = view.findViewById(R.id.day2_temp_max);
        day3 = view.findViewById(R.id.day3);
        day3min = view.findViewById(R.id.day3_temp_min);
        day3max = view.findViewById(R.id.day3_temp_max);
        day4 = view.findViewById(R.id.day4);
        day4min = view.findViewById(R.id.day4_temp_min);
        day4max = view.findViewById(R.id.day4_temp_max);
        day5 = view.findViewById(R.id.day5);
        day5min = view.findViewById(R.id.day5_temp_min);
        day5max = view.findViewById(R.id.day5_temp_max);
        day6 = view.findViewById(R.id.day6);
        day6min = view.findViewById(R.id.day6_temp_min);
        day6max = view.findViewById(R.id.day6_temp_max);
        day7 = view.findViewById(R.id.day7);
        day7min = view.findViewById(R.id.day7_temp_min);
        day7max = view.findViewById(R.id.day7_temp_max);

        String[] days = {"Sat", "Fri", "Thu", "Wed", "Tue", "Mon", "Sun"};
        TextView[] TV_days = new TextView[]{day2, day3, day4, day5, day6, day7};
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 6; i++) {
            int day = calendar.get(Calendar.DAY_OF_WEEK) - i;
            TV_days[i].setText(days[day]);
        }

        new getWeather().execute();

        return view;
    }

    @SuppressLint("StaticFieldLeak")
    class getWeather extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            relativeLayout.setVisibility(View.GONE);
            loader.setVisibility(View.VISIBLE);

        }

        //https://github.com/androdocs/Simple-HTTP-Request
        protected String doInBackground(String... args) {
            return HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + currentLocation + "&units=metric&appid=" + API_KEY);
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                double visibility = Double.parseDouble(jsonObj.getString("visibility")) * .001;

                String address = jsonObj.getString("name") + ", " + sys.getString("country");

                /*Getting value from JSON object in "main" object*/
                String updateTime = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(jsonObj.getLong("dt") * 1000));
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

                /*Populating weekly weather*/
                day1status.setText(weatherMain);
                day1min.setText(roundingOff(tempMin) + "°C");
                day1max.setText(roundingOff(tempMax) + "°C");


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

}
