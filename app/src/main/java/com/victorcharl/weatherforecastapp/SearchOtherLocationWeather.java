package com.victorcharl.weatherforecastapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class SearchOtherLocationWeather extends Fragment implements OnMapReadyCallback {


    private double latLaT;
    private double longLat;

    private final static int CAMERA_ZOOM = 3;

    private static final String LONGITUDE_KEY = "longitude";
    private static final String LATITUDE_KEY = "latitude";

    private SupportMapFragment mapFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search_other_location_weather, container, false);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(SearchOtherLocationWeather.this);

        final FloatingActionButton lookThisPlaceWeather = view.findViewById(R.id.look_this_place_weather);
        lookThisPlaceWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(longLat != 0.0 && latLaT != 0.0) {
                    SharedPreferences myPrefs = getActivity().getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = myPrefs.edit();
                    editor.putString(LATITUDE_KEY, String.valueOf(latLaT));
                    editor.putString(LONGITUDE_KEY, String.valueOf(longLat));
                    editor.commit();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("page", 0);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    getActivity().finish();
                }
                else
                {
                    Toast.makeText(getContext(), "Please select a country first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        /*https://www.youtube.com/watch?v=2ppri1ovIQA*/
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("You're here");
                latLaT = latLng.latitude;
                longLat = latLng.longitude;
                googleMap.clear();
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, CAMERA_ZOOM));
                googleMap.addMarker(markerOptions);
            }
        });
    }

}
