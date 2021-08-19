package com.w8india.w8;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

//import android.support.design.widget.BottomSheetBehavior;

public class Home extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener, ActivityCompat.OnRequestPermissionsResultCallback {
    private GoogleMap mMap;
    Button menu, go, yellowbus;
    LinearProgressIndicator prg;
    BottomSheetBehavior sheetBehavior;
    FloatingActionButton drawebtn, locationbtn;
    AccountHeader headerResult;
    String locality, name, number;
    String selectedbus;
    private Geocoder geocoder;
    private static final String TAG = "Home";
    private final int ACCESS_LOCATION_REQUEST_CODE = 10001;
    Marker userLocationMarker;
    Circle userLocationAccuracyCircle;
    boolean firsttime = true;
    Drawer result;
    double lat;
    double lon;
    LatLng bus;
    float bear;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;

    private static final String KEY_CAMERA_POSITION = "camera_position";
    CameraPosition cameraPosition;
    DocumentReference reference;
    int LOCATION_REQUEST_CODE = 10001;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    private FirebaseAuth auth;
    FirebaseUser user;
    Button callbtn, whatsappbtn;
    TextView busname, busnumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        RelativeLayout layoutBottomSheet = findViewById(R.id.bottom_sheet);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_HIGH));
        }
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]

        prg = findViewById(R.id.mapprg);
        prg.setIndeterminate(true);
        SharedPreferences preferences = getSharedPreferences("busno", Context.MODE_PRIVATE);

        /*TODO Bus shared pref*/
        selectedbus = "Bus No. " + preferences.getInt("bus", 0);


        reference = FirebaseFirestore.getInstance().document("buses/bus" + preferences.getInt("bus", 0));

        //NAVI BUTTON LOGIC
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        callbtn = findViewById(R.id.callbtn);
        whatsappbtn = findViewById(R.id.whatsappbtn);
        busname = findViewById(R.id.busname);
        busnumber = findViewById(R.id.busnumber);


        if (savedInstanceState != null) {
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this, R.style.Theme_MaterialComponents_Dialog);

                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onCallBtnClick();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setTitle("Do you want to call?");
                builder.setMessage("Please note that this may distract the driver so please use this carefully and only if its very urgent!");
                builder.create();
                builder.show();

            }
        });
        whatsappbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Uri uri = Uri.parse("https://wa.link/ghug2k");
                Uri uri = Uri.parse("https://wa.me/91" + number);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });


        switch (preferences.getInt("bus", 0)) {
            case 1:
                name = "Mr. NASEER";
                number = "9966255198";

                busname.setText(name);
                busnumber.setText(number);
                break;
            case 2:
                name = "Mr. SHAKEEL";
                number = "9959707274";

                busname.setText(name);
                busnumber.setText(number);
                break;
            case 3:
                name = "Mr. BASAVA RAJ";
                number = "9392413957";

                busname.setText(name);
                busnumber.setText(number);
                break;

            case 4:
                name = "Mr. SRINIVAS";
                number = "7995726523";

                busname.setText(name);
                busnumber.setText(number);
                break;
            case 5:
                name = "Mr. HANEEF";
                number = "9581991734";

                busname.setText(name);
                busnumber.setText(number);
                break;
            case 6:
                name = "Not Available";
                number = "9618211626";
                busname.setText(name);
                busnumber.setText("-");
                break;
            case 7:
                name = "Mr. SALEEM";
                number = "7095175669";

                busname.setText(name);
                busnumber.setText(number);
                break;
            case 8:
                name = "Mr. YOUNUS";
                number = "9912235254";

                busname.setText(name);
                busnumber.setText(number);
                break;

            default:
                busname.setText("name");
                busnumber.setText("number");

        }


        /**
         *
         * bottom sheet state change listener
         * we are changing button text when sheet changed state
         * */
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                    case BottomSheetBehavior.STATE_COLLAPSED:
                    case BottomSheetBehavior.STATE_SETTLING:
                        locationbtn.setVisibility(View.VISIBLE);

                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                    case BottomSheetBehavior.STATE_DRAGGING:
                        locationbtn.setVisibility(View.INVISIBLE);
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


        //create the drawer and remember the `Drawer` result object

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
                        //new PrimaryDrawerItem().withName("Add Your Bus").withIcon(R.drawable.addbus).withIdentifier(7).withSelectable(false),
                        new PrimaryDrawerItem().withName("Request Wait").withIcon(R.drawable.timer).withIdentifier(9).withSelectable(false),
                        new PrimaryDrawerItem().withName("The Team").withIcon(R.drawable.team).withIdentifier(4).withSelectable(false),

                        //new PrimaryDrawerItem().withName("Request (W8)").withIcon(R.drawable.wait).withIdentifier(16).withSelectable(false),
                        new PrimaryDrawerItem().withName("Join Our Discord").withIcon(R.drawable.join).withIdentifier(5).withSelectable(false),
                        new PrimaryDrawerItem().withName("About").withIcon(R.drawable.aboutus).withIdentifier(6).withSelectable(false),
                        new ExpandableDrawerItem().withName("Follow Us ").withIcon(R.drawable.follow).withIdentifier(8).withSelectable(false).withSubItems
                                (
                                        new SecondaryDrawerItem().withName("Instagram").withIcon(R.drawable.instaico).withLevel(2).withIdentifier(2000).withSelectable(false)
//                                        new SecondaryDrawerItem().withName("Twitter").withIcon(R.drawable.t).withLevel(2).withIdentifier(2001).withSelectable(false),
//                                        new SecondaryDrawerItem().withName("YouTube").withIcon(R.drawable.youtube).withLevel(2).withIdentifier(2003).withSelectable(false)

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
                                intent = new Intent(Home.this, Support_us.class);

                            } else if (drawerItem.getIdentifier() == 3) {
                                intent = new Intent(Home.this, Friend.class);
                            }  else if (drawerItem.getIdentifier() == 4) {
                                intent = new Intent(Home.this, Team.class);
                            } else if (drawerItem.getIdentifier() == 9) {
                                intent = new Intent(Home.this, Request.class);

                            } else if (drawerItem.getIdentifier() == 5) {
                                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://discord.gg/6NtGDzKTAz"));
                                //link
                            } else if (drawerItem.getIdentifier() == 6) {
                                intent = new Intent(Home.this, About.class);
                            } else if (drawerItem.getIdentifier() == 11) {
                                auth.signOut();
                                intent = new Intent(Home.this, Student_Number.class);
                                finish();
                            } else if (drawerItem.getIdentifier() == 2000) {
                                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/w8india.in/"));
                            } else if (drawerItem.getIdentifier() == 2001) {
                                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/w8india_in"));
                            } else if (drawerItem.getIdentifier() == 2003) {
                                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCZ3TnGujid32zOZ0RSLGeMA"));
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


        checkSettingsAndStartLocationUpdates();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
        }
        super.onSaveInstanceState(outState);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
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
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setTrafficEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(17.39607600582687, 78.42970583310021)));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            enableMyLocation();


        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //We Can show user why this permission is required
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
            }
        }
        zoomToLocation();
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
//
//        MarkerOptions markerOptions  = new MarkerOptions().position(latLng).title("Taj Mahal").snippet("Wonder Of The World");
//        mMap.addMarker(markerOptions);
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,16);
//        mMap.animateCamera(cameraUpdate);

    }

    private void enableMyLocation() {
        // [START maps_check_location_permission]
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
        // [END maps_check_location_permission]
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


    //BUS ICON LOGIC17.449616397619273, 78.4230210144581
    private void setUserLocationMarker(Location location) {
        //LatLng latLng = new LatLng(lat, lon);
        prg.setVisibility(View.GONE);
        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData());
                    GeoPoint geoPoint = snapshot.getGeoPoint("location");


                    if(geoPoint.getLatitude()==10){
                        bus = new LatLng(17.341987000671335,78.36856298594809);
                        TextView tv = findViewById(R.id.buslocality);
                        tv.setText("The bus driver is offline");
                    } else {
                        bus = new LatLng(geoPoint.getLatitude(),geoPoint.getLongitude());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(geoPoint.getLatitude(),geoPoint.getLongitude(), 1);
                            Address address = addresses.get(0);
                            locality = address.getAddressLine(0);
                            TextView tv = findViewById(R.id.buslocality);
                            tv.setText(locality);


                        } catch (Exception er) {
                            er.printStackTrace();
                        }
                    }
                    bear = snapshot.getLong("bearing").floatValue();

                    if (userLocationMarker == null) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(bus);
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.yellowbus));

                        if (bear == 0.0) {
                            markerOptions.rotation(location.getBearing());
                        } else {
                            markerOptions.rotation(bear);
                        }

                        markerOptions.title(selectedbus);


//            behavior.setPeekHeight(100);
                        markerOptions.anchor((float) 0.5, (float) 0.5);
                        //We create a new marker
                        userLocationMarker = mMap.addMarker(markerOptions);



                    } else {
                        userLocationMarker.setPosition(bus);
                        userLocationMarker.setRotation(bear);

//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                    }

//get the latLngbuilder from the marker list
                   LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(bus);
                    builder.include(new LatLng(location.getLatitude(),location.getLongitude()));

//Bounds padding here
                    int padding = 75;

                    //Create bounds here
                    LatLngBounds bounds = builder.build();

//Create camera with bounds
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                    mMap.animateCamera(cu);
//Check map is loaded
                    mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                        @Override
                        public void onMapLoaded() {
                            //animate camera here
                            mMap.animateCamera(cu);

                        }
                    });
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }

        });


//        LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
//        if (firsttime) {
//
//
////            Toast.makeText(this, "The Place You Live", Toast.LENGTH_SHORT).show();
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 20));
//            firsttime = false;
//        } else {
////            Toast.makeText(Home.this, "Your Bus Is Ready On Map ", Toast.LENGTH_LONG).show();
////            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
//        }


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
            userLocationAccuracyCircle.setCenter(bus);
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
        NetworkHelper.checkNetworkInfo(this, new NetworkHelper.OnConnectionStatusChange() {
            @Override
            public void onChange(boolean type) {
                if (!type) {
                    startActivity(new Intent(Home.this, Internet_loss.class));
                }
            }
        });
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }

    private void zoomToLocation() {


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
        Task<Location> locationTask = fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
            @Override
            public boolean isCancellationRequested() {
                return false;
            }

            @NonNull
            @NotNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull @NotNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }
        });
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
//                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                    setUserLocationMarker(location);
                } else {
                    if (ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
//                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                            setUserLocationMarker(location);
                        }
                    });
                }


//                 mMap.addMarker(new MarkerOptions().position(latLng));
            }
        });

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
//        Task<Location> locationTask = fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
//            @Override
//            public boolean isCancellationRequested() {
//                return false;
//            }
//
//            @NonNull
//            @NotNull
//            @Override
//            public CancellationToken onCanceledRequested(@NonNull @NotNull OnTokenCanceledListener onTokenCanceledListener) {
//                return null;
//            }
//        });
//        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if (location != null) {
//                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
//                   //setUserLocationMarker(location);
//                } else {
//                    if (ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        // TODO: Consider calling
//                        //    ActivityCompat#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for ActivityCompat#requestPermissions for more details.
//                        return;
//                    }
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                            //setUserLocationMarker(location);
                        }
                    });



//                 mMap.addMarker(new MarkerOptions().position(latLng));
//            }
//        });

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



    private void checkSettingsAndStartLocationUpdates() {
        LocationSettingsRequest request = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).build();
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(locationSettingsResponse -> {
            //Settings of device are satisfied and we can start location updates
            startLocationUpdates();
        });
        locationSettingsResponseTask.addOnFailureListener(e -> {
            if (e instanceof ResolvableApiException) {
                ResolvableApiException apiException = (ResolvableApiException) e;
                try {
                    apiException.startResolutionForResult(Home.this, 1001);
                } catch (IntentSender.SendIntentException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Permission was denied. Display an error message
            // [START_EXCLUDE]
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
            showMissingPermissionError();
            // [END_EXCLUDE]
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            permissionDenied = false;
        }
    }



    private void onCallBtnClick(){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("tel:"+number));
        Home.this.startActivity(i);

    }


    @Override
    public void onMapClick(LatLng latLng) {
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void onBackPressed() {
        if(sheetBehavior.getState()!=BottomSheetBehavior.STATE_EXPANDED) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
            builder.setTitle("Are you sure you want to exit?");
            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Home.super.onBackPressed();
                }
            });
            builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create();
            builder.show();
        }else{
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        }
    }
}