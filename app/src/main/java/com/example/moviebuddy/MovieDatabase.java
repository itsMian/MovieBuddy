package com.example.moviebuddy;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MovieDatabase {
    public static final String DB_NAME = "moviebuddy";
    public static final String DB_TABLE = "movies";
    public static final int DB_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (m_id INTEGER, m_title TEXT, directors TEXT, casts TEXT, rdate TEXT);";
    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public MovieDatabase(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public MovieDatabase openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public boolean addRow(Integer m, String t, String d, String c, String r) {
        synchronized(this.db) {

            ContentValues newMovie = new ContentValues();
            newMovie.put("m_id", m);
            newMovie.put("m_title", t);
            newMovie.put("directors", d);
            newMovie.put("casts", c);
            newMovie.put("rdate", r);
            try {
                db.insertOrThrow(DB_TABLE, null, newMovie);
            } catch (Exception e) {
                Log.e("Error in inserting rows", e.toString());
                e.printStackTrace();
                return false;
            }
            //db.close();
            return true;
        }
    }

    public boolean updateRow(String oldTitle, Integer m, String t, String d, String c, String r) {
        synchronized(this.db) {

            ContentValues currentMovie = new ContentValues();
            currentMovie.put("m_id", m);
            currentMovie.put("m_title", t);
            currentMovie.put("directors", d);
            currentMovie.put("casts", c);
            currentMovie.put("rdate", r);
            try {
                db.update(DB_TABLE, currentMovie, "m_title=?", new String[]{oldTitle});
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
        ArrayList<String> movieRows = new ArrayList<>();
        String[] columns = new String[] {"m_id", "m_title", "directors", "casts", "rdate"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            movieRows.add(cursor.getInt(0) + ", " + cursor.getString(1) + ", " + cursor.getString(2) + ", " + cursor.getString(3) + ", " + cursor.getString(4));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return movieRows;
    }

    public ArrayList<String> retrieveId() {
        ArrayList<String> movieRows = new ArrayList<>();
        String[] columns = new String[] {"m_id"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            movieRows.add(cursor.getInt(0) + "");
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return movieRows;
    }

    public ArrayList<String> retrieveTitle() {
        ArrayList<String> movieRows = new ArrayList<>();
        String[] columns = new String[] {"m_title"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            movieRows.add(cursor.getString(0));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return movieRows;
    }

    public ArrayList<String> retrieveDirectors() {
        ArrayList<String> movieRows = new ArrayList<>();
        String[] columns = new String[] {"directors"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            movieRows.add(cursor.getString(0));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return movieRows;
    }

    public ArrayList<String> retrieveCasts() {
        ArrayList<String> movieRows = new ArrayList<>();
        String[] columns = new String[] {"casts"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            movieRows.add(cursor.getString(0));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return movieRows;
    }

    public ArrayList<String> retrieveRDate() {
        ArrayList<String> movieRows = new ArrayList<>();
        String[] columns = new String[] {"rdate"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            movieRows.add(cursor.getString(0));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return movieRows;
    }

    public ArrayList<String> retrieveTitle_RDate() {
        ArrayList<String> movieRows = new ArrayList<>();
        String[] columns = new String[] {"m_title", "rdate"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            movieRows.add(cursor.getString(0) + ", " + cursor.getString(1));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return movieRows;
    }

    public void clearRecords()
    {
        db = helper.getWritableDatabase();
        db.delete(DB_TABLE, null, null);
    }

    public void clearRow(String mtitle)
    {
        db = helper.getWritableDatabase();
        db.delete(DB_TABLE, "m_title = '" + mtitle + "'", null);
    }

    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper (Context c) {
            super(c, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("Movies table", "Upgrading database i.e. dropping table and re-creating it");
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
        }
    }


}
