package com.example.moviebuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class EditCinema extends AppCompatActivity {

    private CinemaDatabase cinemaDbManager;
    private TextView response;
    private EditText cid, cname, location;
    private Button editBtn, cancelBtn, viewBtn;
    private String cur_cid, cur_cname, cur_location;
    private boolean recInserted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cinema);

        Bundle extras = getIntent().getExtras();
        int cinemaPos = extras.getInt("cinemaPos");

        ImageButton backBtn = findViewById(R.id.editC_backBtn);
        backBtn.setOnClickListener(v -> {
            Intent backToCPage = new Intent(EditCinema.this, CinemaDetails.class);
            backToCPage.putExtra("cinemaPos", cinemaPos);
            startActivity(backToCPage);
        });

        editRec();

        viewBtn = findViewById(R.id.editC_viewBtn);
        viewBtn.setOnClickListener(v -> {
            Intent backToCPage = new Intent(EditCinema.this, CinemaPage.class);
            startActivity(backToCPage);
        });

        cinemaDbManager = new CinemaDatabase(EditCinema.this);

        cur_cid = getIntent().getStringExtra("cur_cid");
        cur_cname = getIntent().getStringExtra("cur_cname");
        cur_location = getIntent().getStringExtra("cur_location");

        response = findViewById(R.id.editC_response);
        cid = findViewById(R.id.editC_cid_input);
        cname = findViewById(R.id.editC_cname_input);
        location = findViewById(R.id.editC_location_input);

        cid.setText(cur_cid);
        cname.setText(cur_cname);
        location.setText(cur_location);
    }

    public boolean editRec() {
        //declare database and form variable
        //movieDbManager = new MovieDatabase(EditMovie.this);

        //when add button on the form is clicked
        editBtn = findViewById(R.id.editCBtn);
        editBtn.setOnClickListener(v -> {
            //insert input taken from the form the database
            recInserted = cinemaDbManager.updateRow(cur_cname, Integer.parseInt(cid.getText().toString()), cname.getText().toString(), location.getText().toString());

            if (recInserted) {
                response.setText("The cinema is updated");
            }
            else {
                response.setText("Sorry, errors when updating to DB");
            }
            //clear the form
            cid.setText("");
            cname.setText("");
            location.setText("");
        });

        //when cancel button is clicked, clear the form.
        cancelBtn = findViewById(R.id.editC_cancelBtn);
        cancelBtn.setOnClickListener(v->{
            cid.setText("");
            cname.setText("");
            location.setText("");
        });
        return true;
    }
}