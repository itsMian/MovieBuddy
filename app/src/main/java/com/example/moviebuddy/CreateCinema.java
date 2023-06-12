package com.example.moviebuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateCinema extends AppCompatActivity {

    private CinemaDatabase cinemaDbManager;
    private TextView response;
    private EditText cid, cname, location;
    private Button addBtn, cancelBtn, viewBtn;
    private boolean cRecInserted;
    private MovieDatabase movieDbManager;
    private ListView cBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cinema);

        insertRec();

        viewBtn = findViewById(R.id.viewBtn);
        viewBtn.setOnClickListener(v -> {
            Intent backToCPage = new Intent(CreateCinema.this, CinemaPage.class);
            startActivity(backToCPage);
        });

        movieDbManager = new MovieDatabase(this);
        movieDbManager.openReadable();

        //display Title for each listing
        ArrayList<String> title_rdateList = movieDbManager.retrieveTitle_RDate();
        cBox = (ListView) findViewById(R.id.cbox_listview);
        CheckboxAdapter titleAdpt = new CheckboxAdapter(this, title_rdateList);
        cBox.setAdapter(titleAdpt);

        cBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                Toast.makeText(getApplicationContext(),
                                "Click ListItem Number " + title_rdateList.get(position), Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    public boolean insertRec() {
        //declare database and form variable
        cinemaDbManager = new CinemaDatabase(CreateCinema.this);
        response = findViewById(R.id.response);
        cid = findViewById(R.id.c_id_input);
        cname = findViewById(R.id.c_name_input);
        location = findViewById(R.id.location_input);

        //when add button on the form is clicked
        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(v -> {
            //insert input taken from the form the database
            cRecInserted = cinemaDbManager.addRow(Integer.parseInt(cid.getText().toString()), cname.getText().toString(), location.getText().toString());

            if (cRecInserted) {
                response.setText("A new cinema is inserted into the listing");
            }
            else {
                response.setText("Sorry, errors when inserting to DB");
            }
            //clear the form
            cid.setText("");
            cname.setText("");
            location.setText("");
        });

        //when cancel button is clicked, clear the form.
        cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(v->{
            cid.setText("");
            cname.setText("");
            location.setText("");
        });
        return true;
    }
}