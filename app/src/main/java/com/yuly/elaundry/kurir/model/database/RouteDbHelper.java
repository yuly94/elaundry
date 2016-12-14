package com.yuly.elaundry.kurir.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by yuly nurhidayati on 28/11/16.
 */

public class RouteDbHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "djikstra_route.db";

    // Table Names
    private static final String TABLE_LOKASI_KONSUMEN = "lokasi_konsumen";
    private static final String TABLE_POINT_KONSUMEN = "point_konsumen";

    private static final String TABLE_PATH_KONSUMEN = "path_konsumen";
    private static final String TABLE_TEMP_PATH_KONSUMEN = "path_temp_konsumen";
    private static final String TABLE_JARAK_KONSUMEN = "jarak_konsumen";
    private static final String TABLE_HISTORY_JARAK_KONSUMEN = "history_jarak_konsumen";
    private static final String TABLE_TODO = "todos";
    private static final String TABLE_TAG = "tags";
    private static final String TABLE_TODO_TAG = "todo_tags";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // NOTES Table - column nmaes

    private static final String KEY_KONSUMEN_ID = "konsumen_id";
    private static final String KEY_PEMESANAN_ID = "pemesanan_id";
    private static final String KEY_KONSUMEN_LONGITUDE = "konsumen_longitude";
    private static final String KEY_KONSUMEN_LATITUDE = "konsumen_latitude";
    private static final String KEY_KONSUMEN_JARAK = "konsumen_jarak";

    private static final String KEY_DARI = "konsumen_dari";
    private static final String KEY_TUJUAN = "konsumen_tujuan";
    private static final String KEY_JARAK = "konsumen_jarak";

    private static final String KEY_TODO = "todo";
    private static final String KEY_STATUS = "status";

    // TAGS Table - column names
    private static final String KEY_TAG_NAME = "tag_name";

    // NOTE_TAGS Table - column names
    private static final String KEY_TODO_ID = "todo_id";
    private static final String KEY_TAG_ID = "tag_id";

    // Table Create Statements
    // Todo table create statement

    private static final String CREATE_TABLE_LOKASI_KONSUMEN = "CREATE TABLE "
            + TABLE_LOKASI_KONSUMEN + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_KONSUMEN_ID + " TEXT,"
            + KEY_PEMESANAN_ID + " TEXT,"
            + KEY_KONSUMEN_LATITUDE + " TEXT,"
            + KEY_KONSUMEN_LONGITUDE + " TEXT,"
            + KEY_KONSUMEN_JARAK + " TEXT,"
            + KEY_STATUS + " INTEGER,"
            + KEY_CREATED_AT
            + " DATETIME" + ")";

    private static final String CREATE_TABLE_POINT_KONSUMEN = "CREATE TABLE "
            + TABLE_POINT_KONSUMEN + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_KONSUMEN_ID + " TEXT,"
            + KEY_PEMESANAN_ID + " TEXT,"
            + KEY_KONSUMEN_LATITUDE + " TEXT,"
            + KEY_KONSUMEN_LONGITUDE + " TEXT,"
            + KEY_KONSUMEN_JARAK + " TEXT,"
            + KEY_STATUS + " INTEGER,"
            + KEY_CREATED_AT
            + " DATETIME" + ")";


    private static final String CREATE_TABLE_HISTORY_JARAK_KONSUMEN = "CREATE TABLE "
            + TABLE_HISTORY_JARAK_KONSUMEN + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_DARI + " TEXT,"
            + KEY_TUJUAN + " TEXT,"
            + KEY_JARAK + " INTEGER,"
            + KEY_STATUS + " INTEGER,"
            + KEY_CREATED_AT
            + " DATETIME" + ")";

    private static final String CREATE_TABLE_JARAK_KONSUMEN = "CREATE TABLE "
            + TABLE_JARAK_KONSUMEN + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_DARI + " TEXT,"
            + KEY_TUJUAN + " TEXT,"
            + KEY_JARAK + " INTEGER,"
            + KEY_STATUS + " INTEGER,"
            + KEY_CREATED_AT
            + " DATETIME" + ")";

    private static final String CREATE_TABLE_TEMP_PATH_KONSUMEN = "CREATE TABLE "
            + TABLE_TEMP_PATH_KONSUMEN + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_DARI + " TEXT,"
            + KEY_TUJUAN + " TEXT,"
            + KEY_JARAK + " TEXT,"
            + KEY_STATUS + " INTEGER,"
            + KEY_CREATED_AT
            + " DATETIME" + ")";

    private static final String CREATE_TABLE_PATH_KONSUMEN = "CREATE TABLE "
            + TABLE_PATH_KONSUMEN + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_DARI + " TEXT,"
            + KEY_TUJUAN + " TEXT,"
            + KEY_JARAK + " TEXT,"
            + KEY_STATUS + " INTEGER,"
            + KEY_CREATED_AT
            + " DATETIME" + ")";

/*    private static final String CREATE_TABLE_PATH_KONSUMEN = "CREATE TABLE "
            + TABLE_PATH_KONSUMEN + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_KONSUMEN_ID + " TEXT,"
            + KEY_KONSUMEN_LATITUDE + " TEXT,"
            + KEY_KONSUMEN_LONGITUDE + " TEXT,"
            + KEY_KONSUMEN_JARAK + " TEXT,"
            + KEY_STATUS + " INTEGER,"
            + KEY_CREATED_AT
            + " DATETIME" + ")";*/

    private static final String CREATE_TABLE_TODO = "CREATE TABLE "
            + TABLE_TODO + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TODO
            + " TEXT," + KEY_STATUS + " INTEGER," + KEY_CREATED_AT
            + " DATETIME" + ")";

    // Tag table create statement
    private static final String CREATE_TABLE_TAG = "CREATE TABLE " + TABLE_TAG
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TAG_NAME + " TEXT,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    // todo_tag table create statement
    private static final String CREATE_TABLE_TODO_TAG = "CREATE TABLE "
            + TABLE_TODO_TAG + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TODO_ID + " INTEGER," + KEY_TAG_ID + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    public RouteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_LOKASI_KONSUMEN);
        db.execSQL(CREATE_TABLE_POINT_KONSUMEN);
        db.execSQL(CREATE_TABLE_PATH_KONSUMEN);
        db.execSQL(CREATE_TABLE_JARAK_KONSUMEN);
        db.execSQL(CREATE_TABLE_HISTORY_JARAK_KONSUMEN);
       // db.execSQL(CREATE_TABLE_TODO);
       // db.execSQL(CREATE_TABLE_TAG);
       // db.execSQL(CREATE_TABLE_TODO_TAG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOKASI_KONSUMEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POINT_KONSUMEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATH_KONSUMEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JARAK_KONSUMEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY_JARAK_KONSUMEN);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO_TAG);

        // create new tables
        onCreate(db);
    }


    // ------------------------ "todos" table methods ----------------//


    /**
     * Creating a todo
     */
    public long createLokasiKonsumen(Lokasi lokasi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_KONSUMEN_ID, lokasi.getKonsumenId());
        values.put(KEY_PEMESANAN_ID, lokasi.getPemesananId());
        values.put(KEY_KONSUMEN_LATITUDE, lokasi.getLatitude());
        values.put(KEY_KONSUMEN_LONGITUDE, lokasi.getLongitude());
        values.put(KEY_KONSUMEN_JARAK, lokasi.getJarak());
        values.put(KEY_STATUS, lokasi.getStatus());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row

        // insert tag_ids
        //    for (long tag_id : tag_ids) {
        //      createTodoTag(todo_id, tag_id);
        //  }

        return db.insert(TABLE_LOKASI_KONSUMEN, null, values);
    }


    /**
     * Creating a todo
     */
    public long createPointKonsumen(Lokasi lokasi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_KONSUMEN_ID, lokasi.getKonsumenId());
        values.put(KEY_PEMESANAN_ID, lokasi.getPemesananId());
        values.put(KEY_KONSUMEN_LATITUDE, lokasi.getLatitude());
        values.put(KEY_KONSUMEN_LONGITUDE, lokasi.getLongitude());
        values.put(KEY_KONSUMEN_JARAK, lokasi.getJarak());
        values.put(KEY_STATUS, lokasi.getStatus());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row

        // insert tag_ids
        //    for (long tag_id : tag_ids) {
        //      createTodoTag(todo_id, tag_id);
        //  }

        return db.insert(TABLE_POINT_KONSUMEN, null, values);
    }

    /**
     * Creating a todo
     */
    public long buatJarakKonsumen(Lokasi lokasi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DARI, lokasi.getDari());
        values.put(KEY_TUJUAN, lokasi.getTujuan());
        values.put(KEY_JARAK, lokasi.getJarak());
        values.put(KEY_STATUS, lokasi.getStatus());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row

        long todo_id = db.insert(TABLE_JARAK_KONSUMEN, null, values);

        // insert tag_ids
        //    for (long tag_id : tag_ids) {
        //      createTodoTag(todo_id, tag_id);
        //  }

        return todo_id;
    }

    /**
     * Creating a todo
     */
    public long buatHistoryJarakKonsumen(Lokasi lokasi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DARI, lokasi.getDari());
        values.put(KEY_TUJUAN, lokasi.getTujuan());
        values.put(KEY_JARAK, lokasi.getJarak());
        values.put(KEY_STATUS, lokasi.getStatus());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row

        long todo_id = db.insert(TABLE_HISTORY_JARAK_KONSUMEN, null, values);

        // insert tag_ids
        //    for (long tag_id : tag_ids) {
        //      createTodoTag(todo_id, tag_id);
        //  }

        return todo_id;
    }

    public long buatPathKonsumen(Lokasi lokasi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DARI, lokasi.getDari());
        values.put(KEY_TUJUAN, lokasi.getTujuan());
        values.put(KEY_JARAK, lokasi.getJarak());
        values.put(KEY_STATUS, lokasi.getStatus());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row

        // insert tag_ids
        //    for (long tag_id : tag_ids) {
        //      createTodoTag(todo_id, tag_id);
        //  }

        return db.insert(TABLE_PATH_KONSUMEN, null, values);
    }

    // Getting single lokasi
    public Lokasi getLokasi(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.query(TABLE_LOKASI_KONSUMEN, new String[]{KEY_ID, KEY_KONSUMEN_ID,
                        KEY_KONSUMEN_LATITUDE, KEY_KONSUMEN_LONGITUDE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        // looping through all rows and adding to list
        if (mCursor.getCount() >= 1 && mCursor.moveToFirst()) {

            Lokasi lokasi = new Lokasi();
            lokasi.setId(Integer.parseInt(mCursor.getString(0)));
            lokasi.setKonsumenId(mCursor.getString(1));
            lokasi.setLatitude(mCursor.getString(2));
            lokasi.setLongitude(mCursor.getString(3));

            // Adding contact to list
            // return contact list
            mCursor.close();
            return lokasi;
        } else {

            return null;
        }
    }


    // Getting single lokasi
    public Lokasi getTujuan(int dariJarak) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.query(TABLE_JARAK_KONSUMEN, new String[] { KEY_ID, KEY_DARI,
                        KEY_TUJUAN, KEY_JARAK }, KEY_DARI+ "=?",
                new String[] {String.valueOf(dariJarak)}, null, null, KEY_JARAK +" ASC", "1");

        // looping through all rows and adding to list
        if (mCursor.getCount() >= 1 &&  mCursor.moveToFirst()){

            Lokasi lokasi = new Lokasi();
            lokasi.setId(Integer.parseInt(mCursor.getString(0)));
            lokasi.setDari(Integer.parseInt(mCursor.getString(1)));
            lokasi.setTujuan(Integer.parseInt(mCursor.getString(2)));
            lokasi.setJarakAb(Integer.parseInt(mCursor.getString(3)));


            // Adding contact to list
            // return contact list
            if (mCursor.getCount() >= 1 && !mCursor.isClosed()) {

                mCursor.close();
            }

            return lokasi;

        } else return null;

    }


    // Getting single lokasi
    public Lokasi getLastPath() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.query(TABLE_PATH_KONSUMEN, new String[] { KEY_ID, KEY_DARI,
                        KEY_TUJUAN, KEY_JARAK }, null,
                null, null, null, KEY_ID +" DESC", "1");

        // looping through all rows and adding to list
        if(mCursor.getCount() >= 1 &&  mCursor.moveToFirst()){

            Lokasi lokasi = new Lokasi();
            lokasi.setId(Integer.parseInt(mCursor.getString(0)));
            lokasi.setDari(Integer.parseInt(mCursor.getString(1)));
            lokasi.setTujuan(Integer.parseInt(mCursor.getString(2)));
            lokasi.setJarakAb(Integer.parseInt(mCursor.getString(3)));

            // Adding contact to list
            // return contact list
            if (mCursor.getCount() >= 1 && !mCursor.isClosed()) {
                mCursor.close();
            }

            return lokasi;
        } else {
            Lokasi lokasiNull = new Lokasi();
            lokasiNull.setDari(0);
            lokasiNull.setTujuan(0);

            return lokasiNull;
        }

    }



    // Lokasi Pesanan
    public List<Lokasi> getAllLokasi() {
        List<Lokasi> listlokasi = new ArrayList<Lokasi>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LOKASI_KONSUMEN;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Lokasi lokasi = new Lokasi();
                lokasi.setId(Integer.parseInt(cursor.getString(0)));
                lokasi.setKonsumenId(cursor.getString(1));
                lokasi.setLatitude(cursor.getString(2));
                lokasi.setLongitude(cursor.getString(3));
                // Adding contact to list
                listlokasi.add(lokasi);
            } while (cursor.moveToNext());
        }

        // return contact list
        cursor.close();
        return listlokasi;
    }


    // Lokasi Pesanan
    public List<Lokasi> getAllJarak() {
        List<Lokasi> listlokasi = new ArrayList<Lokasi>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_JARAK_KONSUMEN;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.getCount() >= 1 &&  cursor.moveToFirst()) {

            do {
                Lokasi jarak = new Lokasi();

                jarak.setId(Integer.parseInt(cursor.getString(0)));
                jarak.setDari(Integer.parseInt(cursor.getString(1)));
                jarak.setTujuan(Integer.parseInt(cursor.getString(2)));
                jarak.setJarakAb(Integer.parseInt(cursor.getString(3)));
                // Adding contact to list
                listlokasi.add(jarak);
            } while (cursor.moveToNext());
        }

        // return contact list
        cursor.close();
        return listlokasi;
    }



    // Lokasi Pesanan
    public List<Lokasi> getAllHistoryJarak() {
        List<Lokasi> listlokasi = new ArrayList<Lokasi>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_HISTORY_JARAK_KONSUMEN;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.getCount() >= 1 &&  cursor.moveToFirst()) {

            do {
                Lokasi jarak = new Lokasi();

                jarak.setId(Integer.parseInt(cursor.getString(0)));
                jarak.setDari(Integer.parseInt(cursor.getString(1)));
                jarak.setTujuan(Integer.parseInt(cursor.getString(2)));
                jarak.setJarakAb(Integer.parseInt(cursor.getString(3)));
                // Adding contact to list
                listlokasi.add(jarak);
            } while (cursor.moveToNext());
        }

        // return contact list
        cursor.close();
        return listlokasi;
    }



    // Lokasi Pesanan
    public List<Lokasi> getAllPath() {
        List<Lokasi> listlokasi = new ArrayList<Lokasi>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PATH_KONSUMEN;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.getCount() >= 1 &&  cursor.moveToFirst()) {
            do {
                Lokasi jarak = new Lokasi();

                jarak.setId(Integer.parseInt(cursor.getString(0)));
                jarak.setDari(Integer.parseInt(cursor.getString(1)));
                jarak.setTujuan(Integer.parseInt(cursor.getString(2)));
                jarak.setJarakAb(Integer.parseInt(cursor.getString(3)));
                // Adding contact to list
                listlokasi.add(jarak);
            } while (cursor.moveToNext());
        }

        // return contact list
        cursor.close();
        return listlokasi;
    }



    /**
     * Deleting a jarak
     */
    public long deleteJarak(long tado_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_JARAK_KONSUMEN, KEY_ID + " = ?",
                new String[] { String.valueOf(tado_id) });
        return tado_id;
    }


    /**
     * Deleting a jarak
     */
    public long deleteHistoryJarak(long tado_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HISTORY_JARAK_KONSUMEN, KEY_ID + " = ?",
                new String[] { String.valueOf(tado_id) });
        return tado_id;
    }


    /**
     * Deleting a jarak
     */
    public long deleteJarakAB(long dari_id,long tujuan_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_JARAK_KONSUMEN, KEY_TUJUAN + "=?" + " and "  +
                        KEY_DARI+ "=?",
                new String[] { String.valueOf(tujuan_id), String.valueOf(dari_id)  });

        return tujuan_id;
    }

    // Lokasi Pesanan
    public List<Lokasi> getAllJarakB(long tujuan_id) {

        List<Lokasi> listlokasi = new ArrayList<Lokasi>();
        SQLiteDatabase db = this.getWritableDatabase();
        // Select All Query
        Cursor mCursor = db.query(TABLE_JARAK_KONSUMEN, new String[]{KEY_ID, KEY_DARI,
                       KEY_TUJUAN, KEY_JARAK}, KEY_TUJUAN + "=?",
                new String[]{String.valueOf(tujuan_id)}, null, null, null, null);

        // looping through all rows and adding to list

        if(mCursor.getCount() >= 1 &&  mCursor.moveToFirst()){

            do {
                Lokasi jarak = new Lokasi();

                jarak.setId(Integer.parseInt(mCursor.getString(0)));
                jarak.setDari(Integer.parseInt(mCursor.getString(1)));
                jarak.setTujuan(Integer.parseInt(mCursor.getString(2)));
                jarak.setJarakAb(Integer.parseInt(mCursor.getString(3)));
                // Adding contact to list
                listlokasi.add(jarak);
            } while (mCursor.moveToNext());
        }

        // return contact list
        mCursor.close();
        return listlokasi;
    }

    /**
     * Deleting a jarak
     */
    public long deleteJarakB(long tado_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_JARAK_KONSUMEN, KEY_TUJUAN + " = ?",
                new String[] { String.valueOf(tado_id) });
        return tado_id;
    }





    // Lokasi Pesanan
    public List<Lokasi> getAllPoint() {
        List<Lokasi> listlokasi = new ArrayList<Lokasi>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_POINT_KONSUMEN;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.getCount() >= 1 &&  cursor.moveToFirst()) {
            do {
                Lokasi lokasi = new Lokasi();
                lokasi.setId(Integer.parseInt(cursor.getString(0)));
                lokasi.setKonsumenId(cursor.getString(1));
                lokasi.setPemesananId(cursor.getString(2));
                lokasi.setLatitude(cursor.getString(3));
                lokasi.setLongitude(cursor.getString(4));
                // Adding contact to list
                listlokasi.add(lokasi);
            } while (cursor.moveToNext());
        }

        // return contact list
        cursor.close();
        return listlokasi;
    }


    /**
     * Deleting a todo
     */
    public long deletePoint(long tado_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_POINT_KONSUMEN, KEY_ID + " = ?",
                new String[] { String.valueOf(tado_id) });
        return tado_id;
    }



    /**
     * Deleting a todo
     */
    public long deleteLokasi(long lokasi_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOKASI_KONSUMEN, KEY_ID + " = ?",
                new String[] { String.valueOf(lokasi_id) });
        return lokasi_id;
    }


    /**
     * Deleting a todo
     */
    public long deletePath(long path_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PATH_KONSUMEN, KEY_ID + " = ?",
                new String[] { String.valueOf(path_id) });
        return path_id;
    }


    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /**
     * get datetime
     * */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
