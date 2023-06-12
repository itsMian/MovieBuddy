package com.example.moviebuddy;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.moviebuddy.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Homepage extends AppCompatActivity {

    //private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private MovieDatabase movieDbManager;
    private ListView movieRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        /*
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        movieDbManager = new MovieDatabase(Homepage.this);
        movieDbManager.openReadable();

        ArrayList<String> titleList = movieDbManager.retrieveTitle();
        movieRecord = findViewById(R.id.home_movieRecord);
        MovieAdapter titleAdpt = new MovieAdapter(this, titleList);
        movieRecord.setAdapter(titleAdpt);

        movieRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                Intent toDetails = new Intent(Homepage.this, MovieDetails.class);
                toDetails.putExtra("moviePos", position);
                startActivity(toDetails);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id) {
            case R.id.h_homeMenu:
                Intent hToHome = new Intent(Homepage.this, Homepage.class);
                startActivity(hToHome);
                return true;
            case R.id.h_cinemaMenu:
                Intent hToCinemas = new Intent(Homepage.this, CinemaPage.class);
                startActivity(hToCinemas);
                return true;
            case R.id.h_movieMenu:
                Intent hToMovies = new Intent(Homepage.this, MoviePage.class);
                startActivity(hToMovies);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
    */
}