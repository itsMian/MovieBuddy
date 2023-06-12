package com.example.moviebuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class CinemaDatabase {
    public static final String DB_NAME = "cinema_moviebuddy";
    public static final String DB_TABLE = "cinemas";
    public static final int DB_VERSION = 1;
    private static final String CREATE_CINEMA_TABLE = "CREATE TABLE " + DB_TABLE + " (c_id INTEGER, c_name TEXT, location TEXT);";
    private CinemaDatabase.SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public CinemaDatabase(Context c) {
        this.context = c;
        helper = new CinemaDatabase.SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public CinemaDatabase openReadable() throws android.database.SQLException {
        helper = new CinemaDatabase.SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public boolean addRow(Integer c, String n, String l) {
        synchronized(this.db) {

            ContentValues newCinema = new ContentValues();
            newCinema.put("c_id", c);
            newCinema.put("c_name", n);
            newCinema.put("location", l);
            try {
                db.insertOrThrow(DB_TABLE, null, newCinema);
            } catch (Exception e) {
                Log.e("Error in inserting rows", e.toString());
                e.printStackTrace();
                return false;
            }
            //db.close();
            return true;
        }
    }

    public boolean updateRow(String oldName, Integer c, String n, String l) {
        synchronized(this.db) {

            ContentValues currentCinema = new ContentValues();
            currentCinema.put("c_id", c);
            currentCinema.put("c_name", n);
            currentCinema.put("location", l);
            try {
                db.update(DB_TABLE, currentCinema, "c_name = ?", new String[]{oldName});
            } catch (Exception e) {
                Log.e("Error in updating rows", e.toString());
                e.printStackTrace();
                return false;
            }
            //db.close();
            return true;
        }
    }

    public ArrayList<String> retrieveRows() {
        ArrayList<String> cinemaRows = new ArrayList<>();
        String[] columns = new String[] {"c_id", "c_name", "location"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            cinemaRows.add(cursor.getInt(0) + ", " + cursor.getString(1) + ", " + cursor.getString(2));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return cinemaRows;
    }

    public ArrayList<String> retrieveCId() {
        ArrayList<String> cinemaRows = new ArrayList<>();
        String[] columns = new String[] {"c_id"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            cinemaRows.add(cursor.getInt(0) + "");
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return cinemaRows;
    }

    public ArrayList<String> retrieveCName() {
        ArrayList<String> cinemaRows = new ArrayList<>();
        String[] columns = new String[] {"c_name"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            cinemaRows.add(cursor.getString(0));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return cinemaRows;
    }

    public ArrayList<String> retrieveLocation() {
        ArrayList<String> cinemaRows = new ArrayList<>();
        String[] columns = new String[] {"location"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            cinemaRows.add(cursor.getString(0));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return cinemaRows;
    }

    public void clearRecords()
    {
        db = helper.getWritableDatabase();
        db.delete(DB_TABLE, null, null);
    }

    public void clearRow(String cname)
    {
        db = helper.getWritableDatabase();
        db.delete(DB_TABLE, "c_name = '" + cname + "'", null);
    }

    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper (Context c) {
            super(c, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_CINEMA_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("Cinemas table", "Upgrading database i.e. dropping table and re-creating it");
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
        }
    }
}
