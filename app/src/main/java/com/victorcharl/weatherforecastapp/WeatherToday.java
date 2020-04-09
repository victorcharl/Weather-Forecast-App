package com.victorcharl.weatherforecastapp;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.androdocs.httprequest.HttpRequest;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class WeatherToday extends Fragment {


    private static String API_KEY = "bd154dbe4caf8a49b5926c34fcfa6dcd";

    private String currentLocation = "calgary";

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

    ProgressBar loader;

    TextView day1, day2, day3, day4, day5, day6, day7;
    private RelativeLayout relativeLayout;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_today, container, false);

        day1 = view.findViewById(R.id.day1);
        day2 = view.findViewById(R.id.day2);
        day3 = view.findViewById(R.id.day3);
        day4 = view.findViewById(R.id.day4);
        day5 = view.findViewById(R.id.day5);
        day6 = view.findViewById(R.id.day6);
        day7 = view.findViewById(R.id.day7);

        relativeLayout = (RelativeLayout)view.findViewById(R.id.mainContainer);

        errorMessage = view.findViewById(R.id.txVw_error);
        loader = view.findViewById(R.id.loader);

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

        day1.setText("Today");

        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        TextView[] TV_days = new TextView[]{ day2,day3, day4, day5, day6, day7};
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        for (int i = 0; i < 6; i++) {
            TV_days[i].setText(days[day]);
        }

        new getWeather().execute();

        return view;
    }

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

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                String updateTime = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(jsonObj.getLong("dt") * 1000));
                String temp = main.getString("temp");
                String tempFeelsLike = main.getString("feels_like");
                String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
                String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
                String pressure = main.getString("pressure");
                String humidity = main.getString("humidity");

                Long sunrise = sys.getLong("sunrise");
                Long sunset = sys.getLong("sunset");
                String windSpeed = wind.getString("speed");
                String weatherDescription = weather.getString("description");

                String address = jsonObj.getString("name") + ", " + sys.getString("country");

                relativeLayout.setVisibility(View.VISIBLE);
                loader.setVisibility(View.GONE);

                location_txtVw.setText(address);
                dateAndTime_txtVw.setText("Updated at: " + updateTime);
                status_txtVw.setText(weatherDescription);
                temperature_txtVw.setText(temp  + "°C");
                feelsLike_txtVw.setText("Feels Like : " + tempFeelsLike  + "°C");
                sunrise_txtVw.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
                sunset_txtVw.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000)));
                pressure_txtVw.setText(pressure + " hPa");
                humidity__txtVw.setText(humidity + " %");
                visibility__txtVw.setText("TODO");
                wind_txtVw.setText(windSpeed + " km/hr");

            } catch (JSONException e) {
                relativeLayout.setVisibility(View.GONE);
                errorMessage.setVisibility(View.VISIBLE);


            }

        }
    }

}
