package com.example.moviebuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class CheckboxAdapter extends ArrayAdapter<String> {
    private final Context context;
    private MovieDatabase dbAdapter;
    private ArrayList<String> arrayList, selectedMovies;

    public CheckboxAdapter(Context context, ArrayList<String> title_rdateList){
        super(context, R.layout.cinema_checkbox_list, title_rdateList);
        this.context = context;
        this.arrayList = title_rdateList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        dbAdapter = new MovieDatabase(this.context);
        dbAdapter.openReadable();
        arrayList = dbAdapter.retrieveTitle_RDate();

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.cinema_checkbox_list, parent, false);

        //ImageView imageView = rowView.findViewById(R.id.list_poster);
        TextView title_rdate = rowView.findViewById(R.id.checkbox_text);

        title_rdate.setText(arrayList.get(position));

        selectedMovies = new ArrayList<>();
//        title_rdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    selectedMovies.add(title_rdate.getText().toString());
//                }else{
//                    selectedMovies.remove(title_rdate.getText().toString());
//                }
//            }
//        });

        return rowView;
    }

//    ArrayList<String> getSelectedMovies(){
//        return selectedMovies;
//    }
}
