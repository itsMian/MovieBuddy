package com.example.moviebuddy;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.moviebuddy.databinding.ActivityCinemaPageBinding;

import java.util.ArrayList;

public class CinemaPage extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityCinemaPageBinding binding;

    private CinemaDatabase cinemaDbManager;
    private ListView cinemaRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCinemaPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
    /*
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_cinema_page);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

        ImageButton createCinemaBtn = findViewById(R.id.createCinemaBtn);
        createCinemaBtn.setOnClickListener(v -> {
            Intent createCinema = new Intent(CinemaPage.this, CreateCinema.class);
            startActivity(createCinema);
        });

        cinemaDbManager = new CinemaDatabase(this);
        cinemaDbManager.openReadable();

        ArrayList<String> cNameList = cinemaDbManager.retrieveCName();
        cinemaRecord = findViewById(R.id.cinemaRecord);
        CinemaAdapter cNameAdpt = new CinemaAdapter(this, cNameList);
        cinemaRecord.setAdapter(cNameAdpt);

        cinemaRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                Intent toDetails = new Intent(CinemaPage.this, CinemaDetails.class);
                toDetails.putExtra("cinemaPos", position);
                startActivity(toDetails);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cinema, menu);
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
            case R.id.c_homeMenu:
                Intent cToHome = new Intent(CinemaPage.this, Homepage.class);
                startActivity(cToHome);
                return true;
            case R.id.c_cinemaMenu:
                Intent cToCinemas = new Intent(CinemaPage.this, CinemaPage.class);
                startActivity(cToCinemas);
                return true;
            case R.id.c_movieMenu:
                Intent cToMovies = new Intent(CinemaPage.this, MoviePage.class);
                startActivity(cToMovies);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_cinema_page);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }*/
}