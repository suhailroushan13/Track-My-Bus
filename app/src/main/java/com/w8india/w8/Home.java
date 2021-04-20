package com.w8india.w8;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class Home extends AppCompatActivity {
    private DrawerLayout drawer;
    Button menu,go;
    FloatingActionButton drawebtn;
    AccountHeader headerResult;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;


    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            if(locationResult == null){

                return;
            }
                   for (Location location: locationResult.getLocations()){
                       Log.d(TAG, "onLocationResult: "+location.toString());

                   }
        }
    };
    Drawer result;
    private static final String TAG = "Home";
    int LOCATION_REQUEST_CODE = 10001;
    private FirebaseAuth auth;



    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawebtn = findViewById(R.id.drawerBtn);
        go=findViewById(R.id.go);
        go.setOnClickListener(v -> MapsActivity());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (auth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Student_Number.class));
        }

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
                        new PrimaryDrawerItem().withName("Change Bus").withIcon(R.drawable.bus).withIdentifier(1).withSelectable(false),
                        new PrimaryDrawerItem().withName("Support Our Work").withIcon(R.drawable.rateus).withIdentifier(2).withSelectable(false),
                        new PrimaryDrawerItem().withName("Tell a Friend").withIcon(R.drawable.friend).withIdentifier(3).withSelectable(false),
                        new PrimaryDrawerItem().withName("Meet the Team").withIcon(R.drawable.team).withIdentifier(4).withSelectable(false),
                        new PrimaryDrawerItem().withName("Tell a Friend").withIcon(R.drawable.friend).withIdentifier(16).withSelectable(false),
                        new PrimaryDrawerItem().withName("Join our Circle").withIcon(R.drawable.join).withIdentifier(5).withSelectable(false),
                        new PrimaryDrawerItem().withName("About").withIcon(R.drawable.aboutus).withIdentifier(6).withSelectable(false),
                        new ExpandableDrawerItem().withName("Follow us on").withIcon(R.drawable.follow).withIdentifier(8).withSelectable(false).withSubItems(
                                new SecondaryDrawerItem().withName("Facebook").withIcon(R.drawable.bus).withLevel(2).withIdentifier(2000).withSelectable(false),
                                new SecondaryDrawerItem().withName("Instagram").withIcon(R.drawable.follow).withLevel(2).withIdentifier(2001).withSelectable(false)
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
                                intent = new Intent(Home.this, Share.class);
                            } else if (drawerItem.getIdentifier() == 5) {
                                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/joinchat/5j2CHowTT3M0ZmM1"));
                                //link
                            } else if (drawerItem.getIdentifier() == 6) {
                                intent = new Intent(Home.this, About.class);

                            } else if (drawerItem.getIdentifier() == 11) {


                                auth.signOut();
                                intent = new Intent(Home.this, Selection.class);
                                finish();

                            } else if (drawerItem.getIdentifier() == 2000) {
                                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/suhailroushan"));


                            } else if (drawerItem.getIdentifier() == 2001) {
                                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/suhailroushan"));


                            }
                            if (intent != null) {
                                Home.this.startActivity(intent);
                            }
                        }

                        return false;
                    }
                }).build();
        /** .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
        @Override public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        // do something with the clicked item :D
        if (drawerItem != null) {
        Intent intent = null;
        if (drawerItem.getIdentifier() == 1) {
        intent = new Intent(Home.this,Select_Bus.class);
        Toast.makeText(Home.this, "Clicked", Toast.LENGTH_SHORT).show();




        } else if (drawerItem.getIdentifier() == 2) {
        Toast.makeText(Home.this, "Clicked", Toast.LENGTH_SHORT).show();
        } else if (drawerItem.getIdentifier() == 3) {

        } else if (drawerItem.getIdentifier() == 4) {

        } else if (drawerItem.getIdentifier() == 5) {

        } else if (drawerItem.getIdentifier() == 6) {

        } else if (drawerItem.getIdentifier() == 7) {

        auth.signOut();
        startActivity(new Intent(Home.this, Selection.class));
        finish();


        }
        }

        return false;
        }
        })

         )**/


        result.setSelection(0);

        drawebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.openDrawer();

            }
        });


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
            // getLastLocation();
            checkSettingsAndStartLocationUpdates();
        } else {
            askLocationPermission();
        }

    }


    @Override
    protected void onStop()
    {
        super.onStop();
        startLocationUpdates();
    }


    private void checkSettingsAndStartLocationUpdates() {


        LocationSettingsRequest request = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).build();
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                //Setting of devices are satisfied and we can start location updates
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
                    } catch (IntentSender.SendIntentException sendIntentException) {
                        sendIntentException.printStackTrace();
                    }

                }

            }
        });
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
                if(location != null){
                    Log.d(TAG, "onSuccess: "+location.toString());
                    Log.d(TAG, "onSuccess: "+location.getLatitude());
                    Log.d(TAG, "onSuccess: "+location.getLongitude());




                }
                else {

                    Log.d(TAG, "onSuccess: Location Was Null...");
                }


            }
        });

        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.getLocalizedMessage());



            }
        });
    }
    private void askLocationPermission() {


        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                Log.d(TAG, "askLocationPermission: You Should Show An Alert Dailog......");
                ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
            } else {

                ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);

            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == LOCATION_REQUEST_CODE){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permission Required

               // getLastLocation();
                checkSettingsAndStartLocationUpdates();
             } else
                 {
                     //Permission Not Granted



                 }
        }
        }


        public void MapsActivity(){

            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }
    }







