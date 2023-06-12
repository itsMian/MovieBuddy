package com.example.moviebuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class EditMovie extends AppCompatActivity {

    private MovieDatabase movieDbManager;
    private TextView response;
    private EditText mid, mtitle, directors, casts, rdate;
    private Button editBtn, cancelBtn, viewBtn;
    private String cur_mid, cur_mtitle, cur_directors, cur_casts, cur_rdate;
    private boolean recInserted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        Bundle extras = getIntent().getExtras();
        int moviePos = extras.getInt("moviePos");

        ImageButton backBtn = findViewById(R.id.edit_backBtn);
        backBtn.setOnClickListener(v -> {
            Intent backToMPage = new Intent(EditMovie.this, MovieDetails.class);
            backToMPage.putExtra("moviePos", moviePos);
            startActivity(backToMPage);
        });

        editRec();

        viewBtn = findViewById(R.id.edit_viewBtn);
        viewBtn.setOnClickListener(v -> {
            Intent backToMPage = new Intent(EditMovie.this, MoviePage.class);
            startActivity(backToMPage);
        });

        movieDbManager = new MovieDatabase(EditMovie.this);

        cur_mid = getIntent().getStringExtra("cur_mid");
        cur_mtitle = getIntent().getStringExtra("cur_mtitle");
        cur_directors = getIntent().getStringExtra("cur_directors");
        cur_casts = getIntent().getStringExtra("cur_casts");
        cur_rdate = getIntent().getStringExtra("cur_rdate");

        response = findViewById(R.id.edit_response);
        mid = findViewById(R.id.edit_id_input);
        mtitle = findViewById(R.id.edit_title_input);
        directors = findViewById(R.id.edit_directors_input);
        casts = findViewById(R.id.edit_casts_input);
        rdate = findViewById(R.id.edit_rdate_input);

        mid.setText(cur_mid);
        mtitle.setText(cur_mtitle);
        directors.setText(cur_directors);
        casts.setText(cur_casts);
        rdate.setText(cur_rdate);
    }

    public boolean editRec() {
        //declare database and form variable
        //movieDbManager = new MovieDatabase(EditMovie.this);

        //when add button on the form is clicked
        editBtn = findViewById(R.id.editBtn);
        editBtn.setOnClickListener(v -> {
            //insert input taken from the form the database
            recInserted = movieDbManager.updateRow(cur_mtitle, Integer.parseInt(mid.getText().toString()), mtitle.getText().toString(), directors.getText().toString(), casts.getText().toString(), rdate.getText().toString());

            if (recInserted) {
                response.setText("The movie is updated");
            }
            else {
                response.setText("Sorry, errors when updating to DB");
            }
            //clear the form
            mid.setText("");
            mtitle.setText("");
            directors.setText("");
            casts.setText("");
            rdate.setText("");
        });

        //when cancel button is clicked, clear the form.
        cancelBtn = findViewById(R.id.edit_cancelBtn);
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