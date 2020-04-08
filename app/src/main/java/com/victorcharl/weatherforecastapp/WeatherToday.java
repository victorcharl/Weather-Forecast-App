package com.victorcharl.weatherforecastapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.androdocs.httprequest.HttpRequest;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class WeatherToday extends Fragment {


    private static String API_KEY = "bd154dbe4caf8a49b5926c34fcfa6dcd";

    String currentLocation = "calgary";

    TextView location, dateAndTime, temperature, feelsLike;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_today, container, false);

        //SharedPreferences preferences = this.getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        location = view.findViewById(R.id.location);
        dateAndTime = view.findViewById(R.id.dataAndTime);
        temperature = view.findViewById(R.id.temperature);
        feelsLike = view.findViewById(R.id.fellsLike);

        new getWeather().execute();

        return view;
    }

    class getWeather extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            return HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + currentLocation + "&units=metric&appid=" + API_KEY);
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                Long updatedAt = jsonObj.getLong("dt");
                String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                String temp = main.getString("temp") + "°C";
                String tempFeelsLike = main.getString("feels_like") + "°C";
                String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
                String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
                String pressure = main.getString("pressure");
                String humidity = main.getString("humidity");

                Long sunrise = sys.getLong("sunrise");
                Long sunset = sys.getLong("sunset");
                String windSpeed = wind.getString("speed");
                String weatherDescription = weather.getString("description");

                String address = jsonObj.getString("name") + ", " + sys.getString("country");

               location.setText(address);


            } catch (JSONException e) {
            }

        }
    }

}
