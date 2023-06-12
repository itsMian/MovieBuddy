package com.example.moviebuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CinemaAdapter extends ArrayAdapter<String> {
    private final Context context;
    private CinemaDatabase dbAdapter;
    private ArrayList<String> cnameList;

    public CinemaAdapter(Context context, ArrayList<String> movieArrayList){
        super(context, R.layout.cinema_listview, movieArrayList);
        this.context = context;
        this.cnameList = movieArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        dbAdapter = new CinemaDatabase(this.context);
        dbAdapter.openReadable();
        cnameList = dbAdapter.retrieveCName();

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.cinema_listview, parent, false);

        TextView cname = rowView.findViewById(R.id.list_cname);

        cname.setText(cnameList.get(position));

        return rowView;
    }
}
