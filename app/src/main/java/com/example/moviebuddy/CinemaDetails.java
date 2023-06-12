package com.example.moviebuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class CinemaDetails extends AppCompatActivity {

    private CinemaDatabase cDetailsDbManager;
    private TextView cName, location, selMovies;
    private CheckboxAdapter cbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_details);

        ImageButton backCPageBtn = findViewById(R.id.backCPageBtn);
        backCPageBtn.setOnClickListener(v -> {
            Intent backToCPage = new Intent(CinemaDetails.this, CinemaPage.class);
            startActivity(backToCPage);
        });

        Bundle extras = getIntent().getExtras();
        int cinemaPos = extras.getInt("cinemaPos");

        cDetailsDbManager = new CinemaDatabase(this);
        cDetailsDbManager.openReadable();

        //display selected cinema
        ArrayList<String> cIdList = cDetailsDbManager.retrieveCId();
        ArrayList<String> cNameList = cDetailsDbManager.retrieveCName();
        ArrayList<String> cLocationList = cDetailsDbManager.retrieveLocation();
        cName = findViewById(R.id.details_cname);
        location = findViewById(R.id.details_location);
        cName.setText(cNameList.get(cinemaPos));
        location.setText(cLocationList.get(cinemaPos));

        Button cDeleteBtn = findViewById(R.id.cDeleteBtn);
        cDeleteBtn.setOnClickListener(v -> {
            cDetailsDbManager.clearRow(cDetailsDbManager.retrieveCName().get(cinemaPos));
            Intent backToCPage = new Intent(CinemaDetails.this, CinemaPage.class);
            startActivity(backToCPage);
        });

        Button cEditBtn = findViewById(R.id.cEditBtn);
        cEditBtn.setOnClickListener(v -> {
            Intent toCEditPage = new Intent(CinemaDetails.this, EditCinema.class);
            toCEditPage.putExtra("cinemaPos", cinemaPos);
            toCEditPage.putExtra("cur_cid", cIdList.get(cinemaPos));
            toCEditPage.putExtra("cur_cname", cNameList.get(cinemaPos));
            toCEditPage.putExtra("cur_location", cLocationList.get(cinemaPos));
            startActivity(toCEditPage);
        });
    }
}