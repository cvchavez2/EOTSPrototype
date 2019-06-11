package edu.utep.cs.cs4330.eotsprototype;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ViewM extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private LocationManager locationManager;
    TextView addressText;
    List<Address> addresses;
    private double lon, lat;
    private GoogleMap mMap;
    private static final int REQUEST_PERMISSION_LOCATION = 255; // int should be between 0 and 255
    private Button confirmBut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_m);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);


        addressText = findViewById(R.id.editText3);
        confirmBut = findViewById(R.id.confButton);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSION_LOCATION);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);

        confirmBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewM.this, GenerateReport.class);
                intent.putExtra("latitud", lat);
                intent.putExtra("longitud", lon);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng currLocation = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(currLocation).title("Boston, Mass"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currLocation));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d("DEBUG", "Map clicked [" + latLng.latitude +
                        " / " + latLng.longitude + "] ");
                Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
                try{
                    List<Address> addresses = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    String add = "";
                    if(addresses.size() > 0){
                        for(int i = 0; i<addresses.get(0).getMaxAddressLineIndex(); i++) {
                            add += addresses.get(0).getAddressLine(i) + "\n";
                        }
                    }
                    Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();

        LatLng currLocation = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(currLocation).title("Boston, Mass"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currLocation));

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            addresses = geocoder.getFromLocation(lat, lon, 1);
            addressText.setText(addressText.getText() + "\n"+addresses.get(0).getAddressLine(0));
            Toast.makeText(this,  "lat " + lat + " lon " + lon, Toast.LENGTH_SHORT).show();

        }catch(Exception e) {

        }
    }


}
