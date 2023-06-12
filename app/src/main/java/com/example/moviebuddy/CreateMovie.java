package com.example.moviebuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class CreateMovie extends AppCompatActivity {

    private MovieDatabase movieDbManager;
    private TextView response;
    private EditText mid, mtitle, directors, casts, rdate;
    private Button addBtn, cancelBtn, viewBtn;
    private boolean recInserted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_movie);

        insertRec();

        viewBtn = findViewById(R.id.viewBtn);
        viewBtn.setOnClickListener(v -> {
            Intent backToMPage = new Intent(CreateMovie.this, MoviePage.class);
            startActivity(backToMPage);
        });
    }

    public boolean insertRec() {
        //declare database and form variable
        movieDbManager = new MovieDatabase(CreateMovie.this);
        response = findViewById(R.id.response);
        mid = findViewById(R.id.id_input);
        mtitle = findViewById(R.id.title_input);
        directors = findViewById(R.id.directors_input);
        casts = findViewById(R.id.casts_input);
        rdate = findViewById(R.id.rdate_input);

        //when add button on the form is clicked
        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(v -> {
            //insert input taken from the form the database
            recInserted = movieDbManager.addRow(Integer.parseInt(mid.getText().toString()), mtitle.getText().toString(), directors.getText().toString(), casts.getText().toString(), rdate.getText().toString());

            if (recInserted) {
                response.setText("A new movie is inserted into the listing");
            }
            else {
                response.setText("Sorry, errors when inserting to DB");
            }
            //clear the form
            mid.setText("");
            mtitle.setText("");
            directors.setText("");
            casts.setText("");
            rdate.setText("");
        });

        //when cancel button is clicked, clear the form.
        cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(v->{
            mid.setText("");
            mtitle.setText("");
            directors.setText("");
            casts.setText("");
            rdate.setText("");
        });
        return true;
    }
}