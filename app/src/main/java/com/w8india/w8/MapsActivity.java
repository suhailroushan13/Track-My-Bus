package com.w8india.w8;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    private Geocoder geocoder;
    private static final String TAG = "MapsActivity";
    private final int ACCESS_LOCATION_REQUEST_CODE = 10001;
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;
    Marker userLocationMarker;
    Circle userLocationAccuracyCircle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(500);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


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
//        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerDragListener(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            enableUserLocation();
//            zoomToUserLocation();


        } else {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //We Can show user why this permission is required
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);


            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
            }


        }


//        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

//        // Add a marker in Sydney and move the camera
//        LatLng latLng = new LatLng(27.1751,78.0421);
//        MarkerOptions markerOptions  = new MarkerOptions().position(latLng).title("Taj Mahal").snippet("Wonder Of The World");
//        mMap.addMarker(markerOptions);
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,16);
//        mMap.animateCamera(cameraUpdate);


        try {
            List<Address> addresses = geocoder.getFromLocationName("London", 1);
            Address address = addresses.get(0);

            LatLng london = (new LatLng(address.getLatitude(), address.getLongitude()));
            MarkerOptions markerOptions = new MarkerOptions()

                    .position(new LatLng(address.getLatitude(), address.getLongitude()))
                    .title(address.getLocality());
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(london, 16));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Log.d(TAG, "onLocationResult: "+locationResult.getLastLocation());
            if(mMap != null){

                setUserLocationMarker(locationResult.getLastLocation());

            }
        }
    };


    private void setUserLocationMarker(Location location)

    {
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());

        if(userLocationMarker == null){

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.yellowbus));
            markerOptions.rotation(location.getBearing());

            markerOptions.anchor((float) 0.5,(float) 0.5);
            //We create a new marker
            userLocationMarker = mMap.addMarker(markerOptions);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));


        }else{

           userLocationMarker.setPosition(latLng);
           userLocationMarker.setRotation(location.getBearing());
           mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));


        }
         if(userLocationAccuracyCircle == null)
         {
//             CircleOptions circleOptions = new CircleOptions();
//             circleOptions.center(latLng);
//             circleOptions.strokeWidth(4);
//             circleOptions.strokeColor(Color.argb(255, 0,0, 255));
//             circleOptions.fillColor(Color.argb(0,0,0,0));
//             circleOptions.radius(location.getAccuracy());
//             mMap.addCircle(circleOptions);
//             userLocationAccuracyCircle = mMap.addCircle(circleOptions);
         }else {




             userLocationAccuracyCircle.setCenter(latLng);
             userLocationAccuracyCircle.setRadius(location.getAccuracy());
         }


    }

    private void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

    }
     private void stopLocationUpdates()
     {

         fusedLocationProviderClient.removeLocationUpdates(locationCallback);


     }


    @Override
    protected void onStart() {
        super.onStart();

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){

            startLocationUpdates();
        }  else {

            //you need to request permission....
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    private void enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

    }


    private void zoomToUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                     LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                     mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
                   //  mMap.addMarker(new MarkerOptions().position(latLng));
            }
        });


    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        Log.d(TAG, "onMapLongClick: " + latLng.toString());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                String streertAddress = address.getAddressLine(0);
                mMap.addMarker(new MarkerOptions().position(latLng).title(streertAddress).draggable(true));


            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onMarkerDragStart(Marker marker) {

        Log.d(TAG, "onMarkerDragStart: ");

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        Log.d(TAG, "onMarkerDrag: ");

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Log.d(TAG, "onMarkerDragEnd: ");
        LatLng latLng = marker.getPosition();
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                String streertAddress = address.getAddressLine(0);
                marker.setTitle(streertAddress);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
      if(requestCode == ACCESS_LOCATION_REQUEST_CODE){

          if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
              enableUserLocation();

          }else{
              //why we want to enable user location
          }

      }

    }
}