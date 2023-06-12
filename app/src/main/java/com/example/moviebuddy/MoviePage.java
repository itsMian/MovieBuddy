package com.example.moviebuddy;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.moviebuddy.databinding.ActivityMoviePageBinding;

import java.util.ArrayList;

public class MoviePage extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMoviePageBinding binding;

    private MovieDatabase movieDbManager;
    private ListView movieRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMoviePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
    /*
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_movie_page);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

        ImageButton createMovieBtn = findViewById(R.id.createMovieBtn);
        createMovieBtn.setOnClickListener(v -> {
            Intent createMovie = new Intent(MoviePage.this, CreateMovie.class);
            startActivity(createMovie);
        });

        /*
        LinearLayout viewRecordsBtn = (LinearLayout) findViewById(R.id.viewRecordsBtn);
        viewRecordsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void OnClick(View v) {
                Intent viewRecords = new Intent(MoviePage.this, MovieDetails.class);
                startActivity(viewRecords);
            }
        }); */

        movieDbManager = new MovieDatabase(MoviePage.this);
        movieDbManager.openReadable();

        ArrayList<String> titleList = movieDbManager.retrieveTitle();
        movieRecord = findViewById(R.id.movieRecord);
        MovieAdapter titleAdpt = new MovieAdapter(this, titleList);
        movieRecord.setAdapter(titleAdpt);

        //Button viewMDetailsBtn = findViewById(R.id.viewMDetailsBtn);
        movieRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                Intent toDetails = new Intent(MoviePage.this, MovieDetails.class);
                toDetails.putExtra("moviePos", position);
                startActivity(toDetails);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie, menu);
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
            case R.id.m_homeMenu:
                Intent mToHome = new Intent(MoviePage.this, Homepage.class);
                startActivity(mToHome);
                return true;
            case R.id.m_cinemaMenu:
                Intent mToCinemas = new Intent(MoviePage.this, CinemaPage.class);
                startActivity(mToCinemas);
                return true;
            case R.id.m_movieMenu:
                Intent mToMovies = new Intent(MoviePage.this, MoviePage.class);
                startActivity(mToMovies);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_movie_page);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void viewRecords(View v) {
        Intent viewRecords = new Intent(MoviePage.this, MovieDetails.class);
        startActivity(viewRecords);
    }*/
}