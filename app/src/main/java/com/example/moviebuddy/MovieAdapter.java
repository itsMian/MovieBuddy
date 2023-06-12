package com.example.moviebuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<String> {

    private final Context context;
    private MovieDatabase dbAdapter;
    private ArrayList<String> arrayList;
    private ArrayList<String> rdateList;

    public MovieAdapter(Context context, ArrayList<String> movieArrayList){
        super(context, R.layout.movie_listview, movieArrayList);
        this.context = context;
        this.arrayList = movieArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        dbAdapter = new MovieDatabase(this.context);
        dbAdapter.openReadable();
        arrayList = dbAdapter.retrieveTitle();
        rdateList = dbAdapter.retrieveRDate();

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.movie_listview, parent, false);

        //ImageView imageView = rowView.findViewById(R.id.list_poster);
        TextView title = rowView.findViewById(R.id.list_title);
        TextView rdate = rowView.findViewById(R.id.list_rdate);

        title.setText(arrayList.get(position));
        rdate.setText("Release date: " + rdateList.get(position));

        return rowView;
    }
}
