package com.example.moviebuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieDetails extends AppCompatActivity {

    private MovieDatabase mDetailsDbManager;
    private TextView mTitle, directors, casts, rdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ImageButton backMPageBtn = findViewById(R.id.backMPageBtn);
        backMPageBtn.setOnClickListener(v -> {
            Intent backToMPage = new Intent(MovieDetails.this, MoviePage.class);
            startActivity(backToMPage);
        });

        Bundle extras = getIntent().getExtras();
        int moviePos = extras.getInt("moviePos");

        mDetailsDbManager = new MovieDatabase(this);
        mDetailsDbManager.openReadable();

        //display selected movie
        ArrayList<String> mIdList = mDetailsDbManager.retrieveId();
        ArrayList<String> mTitleList = mDetailsDbManager.retrieveTitle();
        ArrayList<String> directorsList = mDetailsDbManager.retrieveDirectors();
        ArrayList<String> castsList = mDetailsDbManager.retrieveCasts();
        ArrayList<String> rDateList = mDetailsDbManager.retrieveRDate();

        mTitle = findViewById(R.id.details_mtitle);
        directors = findViewById(R.id.details_directors);
        casts = findViewById(R.id.details_casts);
        rdate = findViewById(R.id.details_rdate);

        mTitle.setText(mTitleList.get(moviePos));
        directors.setText("Directors: " + directorsList.get(moviePos));
        casts.setText("Casts: " + castsList.get(moviePos));
        rdate.setText("Release date: " + rDateList.get(moviePos));

        Button mDeleteBtn = findViewById(R.id.mDeleteBtn);
        mDeleteBtn.setOnClickListener(v -> {
            mDetailsDbManager.clearRow(mDetailsDbManager.retrieveTitle().get(moviePos));
            Intent backToCPage = new Intent(MovieDetails.this, MoviePage.class);
            startActivity(backToCPage);
        });

        //get data to Edit Page
        Button mEditBtn = findViewById(R.id.mEditBtn);
        mEditBtn.setOnClickListener(v -> {
            Intent toMEditPage = new Intent(MovieDetails.this, EditMovie.class);
            toMEditPage.putExtra("moviePos", moviePos);
            toMEditPage.putExtra("cur_mid", mIdList.get(moviePos));
            toMEditPage.putExtra("cur_mtitle", mTitleList.get(moviePos));
            toMEditPage.putExtra("cur_directors", directorsList.get(moviePos));
            toMEditPage.putExtra("cur_casts", castsList.get(moviePos));
            toMEditPage.putExtra("cur_rdate", rDateList.get(moviePos));
            startActivity(toMEditPage);
        });
    }
}