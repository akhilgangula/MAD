package com.example.inclass10;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.inclass10.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, GoogleMap.OnPolylineClickListener, GoogleMap.OnPolygonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Gson gson = new Gson();
        Trip trip = null;
        try {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(getAssets().open(getString(R.string.file_name))));
            trip = gson.fromJson(jsonReader, Trip.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (trip == null) {
            return;
        }
        PolylineOptions options = new PolylineOptions()
                .clickable(true);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Coordinates coordinates : trip.points) {
            LatLng latLng = new LatLng(coordinates.latitude, coordinates.longitude);
            options.add(latLng);
            builder.include(latLng);
        }
        googleMap.addMarker(new MarkerOptions()
                .position(options.getPoints().get(0))
                .title(getString(R.string.start_location)));
        googleMap.addMarker(new MarkerOptions()
                .position(options.getPoints().get(trip.points.size() - 1))
                .title(getString(R.string.end_location)));
        LatLngBounds bounds = builder.build();
        googleMap.addPolyline(options);
        googleMap.setOnMapLoadedCallback(() -> {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
        });
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
    }


    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    public void onPolygonClick(@NonNull Polygon polygon) {

    }

    @Override
    public void onPolylineClick(@NonNull Polyline polyline) {

    }
}