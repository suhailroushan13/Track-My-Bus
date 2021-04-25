package com.w8india.w8;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.io.IOException;
import java.util.List;

//import android.support.design.widget.BottomSheetBehavior;

public class Home extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerDragListener {
    private GoogleMap mMap;
    private DrawerLayout drawer;
    Button menu, go, yellowbus;
    BottomSheetBehavior sheetBehavior;
    FloatingActionButton drawebtn, locationbtn;
    AccountHeader headerResult;
    String locality, name, number;
    int selectedbus;
    private Geocoder geocoder;
    private static final String TAG = "Home";
    private final int ACCESS_LOCATION_REQUEST_CODE = 10001;
    Marker userLocationMarker;
    Circle userLocationAccuracyCircle;
    boolean firsttime = true;
    Drawer result;
    int LOCATION_REQUEST_CODE = 10001;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    private FirebaseAuth auth;
    FirebaseUser user;
    TextView busname,four;
    ImageView one,three;

     String num = "9618211626";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ConstraintLayout layoutBottomSheet = findViewById(R.id.bottom_sheet);
/*TODO Bus shared pref*/
        selectedbus = 5;


        //NAVI BUTTON LOGIC
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        busname=findViewById(R.id.busname);
        one=findViewById(R.id.one);
        three=findViewById(R.id.three);
        four=findViewById(R.id.four);
        busname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onCallBtnClick();

            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://wa.link/ghug2k"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onCallBtnClick();


            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://wa.link/ghug2k"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        switch (selectedbus){
            case 1:
                name = "NASEER";
//                number="9966255198";
                number="9618211626";
                busname.setText(name);
                four.setText(number);
                break;
            case 2:
                name = "SHAKEEL";
//                number="9959707274";
                number="9618211626";
                busname.setText(name);
                four.setText(number);
                break;
            case 3:
                name = "RAJU";
//                number="9392413957";
                number="9618211626";
                busname.setText(name);
                four.setText(number);
                break;

            case 4:
                name = "ALEEM";
//                number="7995726523";
                number="9618211626";
                busname.setText(name);
                four.setText(number);
                break;
            case 5:
                name = "HANEEF";
//                number="9581991734";
                number="9618211626";
                busname.setText(name);
                four.setText(number);
                break;
            case 6:
                name = "Not Available";
                number="-";
                busname.setText(name);
                four.setText(number);
                break;
            case 7:
                name = "SALEEM";
//                number="7095175669";
                number="9618211626";
                busname.setText(name);
                four.setText(number);
                break;

            default:
                busname.setText(name);
                four.setText(number);

        }
















        /**
         *
         * bottom sheet state change listener
         * we are changing button text when sheet changed state
         * */
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        locationbtn = findViewById(R.id.locationbtn);
        locationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomToUserLocation();

            }
        });
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (auth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Student_Number.class));
        }
        drawebtn = findViewById(R.id.drawerBtn);
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Home");
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("Settings");
        //create the drawer and remember the `Drawer` result object
        final IProfile profile = new ProfileDrawerItem().withName("usernames")
                .withIdentifier(100);
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.header1)
                .withSavedInstance(savedInstanceState)
                .build();
        result = new DrawerBuilder()
                .withActivity(this)
                .withHasStableIds(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Switch Bus").withIcon(R.drawable.bus).withIdentifier(1).withSelectable(false),
                        new PrimaryDrawerItem().withName("Support Us").withIcon(R.drawable.rateus).withIdentifier(2).withSelectable(false),
                        new PrimaryDrawerItem().withName("Tell a Friend").withIcon(R.drawable.friend).withIdentifier(3).withSelectable(false),
                        new PrimaryDrawerItem().withName("The Team").withIcon(R.drawable.team).withIdentifier(4).withSelectable(false),
                        //new PrimaryDrawerItem().withName("Request (W8)").withIcon(R.drawable.wait).withIdentifier(16).withSelectable(false),
                        new PrimaryDrawerItem().withName("Join our Cult").withIcon(R.drawable.join).withIdentifier(5).withSelectable(false),
                        new PrimaryDrawerItem().withName("About").withIcon(R.drawable.aboutus).withIdentifier(6).withSelectable(false),
                        new ExpandableDrawerItem().withName("Follow Us ").withIcon(R.drawable.follow).withIdentifier(8).withSelectable(false).withSubItems
                                (
                                        new SecondaryDrawerItem().withName("Instagram").withIcon(R.drawable.insta).withLevel(2).withIdentifier(2000).withSelectable(false),
                                        new SecondaryDrawerItem().withName("Twitter").withIcon(R.drawable.twitter).withLevel(2).withIdentifier(2001).withSelectable(false)
                                ),
                        new PrimaryDrawerItem().withName("Sign Off").withIcon(R.drawable.logout).withIdentifier(11).withSelectable(false)
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        //check if the drawerItem is set.
                        //there are different reasons for the drawerItem to be null
                        //--> click on the header
                        //--> click on the footer
                        //those items don't contain a drawerItem
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                intent = new Intent(Home.this, Select_Bus.class);
                            } else if (drawerItem.getIdentifier() == 2) {
                                intent = new Intent(Home.this, Rate_us.class);

                            } else if (drawerItem.getIdentifier() == 3) {
                                intent = new Intent(Home.this, Share.class);
                            } else if (drawerItem.getIdentifier() == 4) {
                                intent = new Intent(Home.this, Team.class);
                            } else if (drawerItem.getIdentifier() == 16) {
                                intent = new Intent(Home.this, Request.class);
                            } else if (drawerItem.getIdentifier() == 5) {
                                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/joinchat/5j2CHowTT3M0ZmM1"));
                                //link
                            } else if (drawerItem.getIdentifier() == 6) {
                                intent = new Intent(Home.this, About.class);
                            } else if (drawerItem.getIdentifier() == 11) {
                                auth.signOut();
                                intent = new Intent(Home.this, Login.class);
                                finish();
                            } else if (drawerItem.getIdentifier() == 2000) {
                                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/suhailroushan"));
                            } else if (drawerItem.getIdentifier() == 2001) {
                                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/suhailroushan13"));
                            }
                            if (intent != null) {
                                Home.this.startActivity(intent);
                            }
                        }
                        return false;
                    }
                }).build();

        result.setSelection(0);
        drawebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.openDrawer();
            }
        });
        // Obn the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(15000);
        locationRequest.setFastestInterval(15000);
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
        //MAP TYPE LAYERS
//        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMapLongClickListener(this);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.isTrafficEnabled();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMarkerDragListener(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            enableUserLocation();
//          zoomToUserLocation();

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //We Can show user why this permission is required
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
            }
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                return false;
            }
        });
//      mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

//        // Add a marker in Sydney and move the camera
//        LatLng latLng = new LatLng(27.1751,78.0421);
//        MarkerOptions markerOptions  = new MarkerOptions().position(latLng).title("Taj Mahal").snippet("Wonder Of The World");
//        mMap.addMarker(markerOptions);
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,16);
//        mMap.animateCamera(cameraUpdate);
        try {
            List<Address> addresses = geocoder.getFromLocationName("Lords Institute Of Engineering And Technology", 1);
            Address address = addresses.get(0);
            LatLng london = (new LatLng(address.getLatitude(), address.getLongitude()));
//            MarkerOptions markerOptions = new MarkerOptions()
//                   .position(new LatLng(address.getLatitude(), address.getLongitude()))
//                    .title(address.getLocality());
//            mMap.addMarker(markerOptions);
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(london, 16));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Log.d(TAG, "onLocationResult: " + locationResult.getLastLocation());
            if (mMap != null) {
                setUserLocationMarker(locationResult.getLastLocation());

            }
        }
    };

    //BUS ICON LOGIC
    private void setUserLocationMarker(Location location) {
        double lati = 17.39499650523271;
        double longi = 78.43981142071546;
        LatLng latLng = new LatLng(lati, longi);
        try {
            List<Address> addresses = geocoder.getFromLocation(lati, longi, 1);
            Address address = addresses.get(0);
            String streetAddress = address.getAddressLine(0);
            locality = address.getAddressLine(0);
        } catch (Exception er) {
            er.printStackTrace();
        }
        LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
        if (firsttime) {
//            Toast.makeText(this, "The Place You Live", Toast.LENGTH_SHORT).show();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 20));
            firsttime = false;
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        }

        if (userLocationMarker == null) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.yellowbus));
            markerOptions.rotation(location.getBearing());

            markerOptions.title("Bus No.1");

            markerOptions.snippet(locality);


//            behavior.setPeekHeight(100);
            markerOptions.anchor((float) 0.5, (float) 0.5);
            //We create a new marker
            userLocationMarker = mMap.addMarker(markerOptions);

        } else {
            userLocationMarker.setPosition(latLng);
            userLocationMarker.setRotation(location.getBearing());

//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        }
        if (userLocationAccuracyCircle == null) {
            /// IZHAN CHECK THIS COLORS AND ACCURACY AND SIZE OF IT ......
//             CircleOptions circleOptions = new CircleOptions();
//             circleOptions.center(latLng);
//             circleOptions.strokeWidth(4);
//             circleOptions.strokeColor(Color.argb(255, 0,0, 255));
//             circleOptions.fillColor(Color.argb(0,0,0,0));
//             circleOptions.radius(location.getAccuracy());
//             mMap.addCircle(circleOptions);
//             userLocationAccuracyCircle = mMap.addCircle(circleOptions);
        } else {
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

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(Home.this, Student_OTP.class));
            finish();
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        } else {
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
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));

//                 mMap.addMarker(new MarkerOptions().position(latLng));
            }
        });
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        //        Log.d(TAG, "onMapLongClick: " + latLng.toString());
//        try {
//            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
//            if (addresses.size() > 0) {
//                Address address = addresses.get(0);
//                String streertAddress = address.getAddressLine(0);
//                mMap.addMarker(new MarkerOptions().position(latLng).title(streertAddress).draggable(true));
//
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
//        LatLng latLng = marker.getPosition();
//        try {
//            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
//            if (addresses.size() > 0) {
//                Address address = addresses.get(0);
//                String streertAddress = address.getAddressLine(0);
//                marker.setTitle(streertAddress);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void checkSettingsAndStartLocationUpdates() {
        LocationSettingsRequest request = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).build();
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                //Settings of device are satisfied and we can start location updates
                startLocationUpdates();
            }
        });
        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try {
                        apiException.startResolutionForResult(Home.this, 1001);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void getLastLocation() {
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
                if (location != null) {
                    //We have a location
                    Log.d(TAG, "onSuccess: " + location.toString());
                    Log.d(TAG, "onSuccess: " + location.getLatitude());
                    Log.d(TAG, "onSuccess: " + location.getLongitude());
                } else {
                    Log.d(TAG, "onSuccess: Location was null...");
                }
            }
        });
        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: " + e.getLocalizedMessage());
            }
        });
    }
    private void askLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.d(TAG, "askLocationPermission: you should show an alert dialog...");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
//                getLastLocation();
                checkSettingsAndStartLocationUpdates();
            } else {
                //Permission not granted
            }
        }
        if (requestCode == ACCESS_LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableUserLocation();
            } else {
                //why we want to enable user location
            }
        }
        boolean permissionGranted = false;
        switch(requestCode){
            case 9:
                permissionGranted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                break;
        }
        if(permissionGranted){
            phoneCall();
        }else {
            Toast.makeText(Home.this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }





    private void onCallBtnClick(){
        if (Build.VERSION.SDK_INT < 23) {
            phoneCall();
        }else {

            if (ActivityCompat.checkSelfPermission(Home.this,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                phoneCall();
            }else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions(Home.this, PERMISSIONS_STORAGE, 9);
            }
        }
    }



    private void phoneCall(){
        if (ActivityCompat.checkSelfPermission(Home.this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+num));
            Home.this.startActivity(callIntent);
        }else{
            Toast.makeText(Home.this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }



}