package com.example.week2;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;


import com.example.week2.control.Router;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Router.start(this);




        /*
        *
        * For Toolbar
        *
        * */
        DrawerLayout activityPane = findViewById(R.id.mainActivityDrawer);
        ActionBarDrawerToggle actionBar = new ActionBarDrawerToggle(this, activityPane, R.string.open, R.string.close);
        activityPane.addDrawerListener(actionBar);
        actionBar.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
    }
}
