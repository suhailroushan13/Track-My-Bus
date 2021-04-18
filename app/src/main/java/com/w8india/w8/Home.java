package com.w8india.w8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

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


    Button menu;
    FloatingActionButton drawebtn;
    AccountHeader headerResult;
    private Button mLogOutBtn;
    Drawer result;
    private FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawebtn = findViewById(R.id.drawerBtn);


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (auth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Student_Number.class));
        }
        mLogOutBtn = findViewById(R.id.log_out_btn);
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Home");
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("Settings");

        //create the drawer and remember the `Drawer` result object
        final IProfile profile = new ProfileDrawerItem().withName("usernames")

                .withIdentifier(100);
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.header)


                .withSavedInstance(savedInstanceState)
                .build();


        result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Change Bus").withIcon(R.drawable.bus).withIdentifier(1).withSelectable(false),
                        new PrimaryDrawerItem().withName("Support Our Work").withIcon(R.drawable.rateus).withIdentifier(2).withSelectable(false),
                        new PrimaryDrawerItem().withName("Tell a Friend").withIcon(R.drawable.friend).withIdentifier(3).withSelectable(false),
                        new PrimaryDrawerItem().withName("Meet the Team").withIcon(R.drawable.team).withIdentifier(4).withSelectable(false),
                        new PrimaryDrawerItem().withName("Join our Circle").withIcon(R.drawable.join).withIdentifier(5).withSelectable(false),
                        new PrimaryDrawerItem().withName("About").withIcon(R.drawable.aboutus).withIdentifier(6).withSelectable(false),
                        new PrimaryDrawerItem().withName("Sign Off").withIcon(R.drawable.logout).withIdentifier(7).withSelectable(false),
                        new ExpandableDrawerItem().withName("Follow us on").withIcon(R.drawable.follow).withIdentifier(12).withSelectable(false).withSubItems(
                                new SecondaryDrawerItem().withName("Facebook").withLevel(2).withIdentifier(2000).withSelectable(false),
                                new SecondaryDrawerItem().withName("Instagram").withLevel(2).withIdentifier(2001).withSelectable(false)
                        )
                                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        // do something with the clicked item :D
                                        if (drawerItem != null) {
                                            Intent intent = null;
                                            if (drawerItem.getIdentifier() == 1) {

                                            } else if (drawerItem.getIdentifier() == 2) {

                                            } else if (drawerItem.getIdentifier() == 3) {

                                            } else if (drawerItem.getIdentifier() == 4) {

                                            } else if (drawerItem.getIdentifier() == 5) {

                                            } else if (drawerItem.getIdentifier() == 6) {

                                            } else if (drawerItem.getIdentifier() == 7) {

                                            }
                                        }
                                        return false;
                                    }
                                })

                ) .build();


        result.setSelection(0);




        drawebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.openDrawer();

            }
        });
        mLogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(Home.this, Selection.class));
                finish();
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
    }

}


