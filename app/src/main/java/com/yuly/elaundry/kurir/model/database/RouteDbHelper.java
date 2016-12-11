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


    private static final String CREATE_TABLE_JARAK_KONSUMEN = "CREATE TABLE "
            + TABLE_JARAK_KONSUMEN + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_DARI + " TEXT,"
            + KEY_TUJUAN + " TEXT,"
            + KEY_JARAK + " TEXT,"
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
        db.execSQL(CREATE_TABLE_TODO);
        db.execSQL(CREATE_TABLE_TAG);
        db.execSQL(CREATE_TABLE_TODO_TAG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOKASI_KONSUMEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POINT_KONSUMEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATH_KONSUMEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JARAK_KONSUMEN);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO_TAG);

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

        long todo_id = db.insert(TABLE_LOKASI_KONSUMEN, null, values);

        // insert tag_ids
    //    for (long tag_id : tag_ids) {
      //      createTodoTag(todo_id, tag_id);
      //  }

        return todo_id;
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

        long todo_id = db.insert(TABLE_POINT_KONSUMEN, null, values);

        // insert tag_ids
        //    for (long tag_id : tag_ids) {
        //      createTodoTag(todo_id, tag_id);
        //  }

        return todo_id;
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

    public long buatPathKonsumen(Lokasi lokasi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DARI, lokasi.getDari());
        values.put(KEY_TUJUAN, lokasi.getTujuan());
        values.put(KEY_JARAK, lokasi.getJarak());
        values.put(KEY_STATUS, lokasi.getStatus());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row

        long todo_id = db.insert(TABLE_PATH_KONSUMEN, null, values);

        // insert tag_ids
        //    for (long tag_id : tag_ids) {
        //      createTodoTag(todo_id, tag_id);
        //  }

        return todo_id;
    }

    // Getting single lokasi
    public Lokasi getLokasi(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LOKASI_KONSUMEN, new String[] { KEY_ID, KEY_KONSUMEN_ID,
                        KEY_KONSUMEN_LATITUDE, KEY_KONSUMEN_LONGITUDE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor != null)
            cursor.moveToFirst();

        Lokasi lokasi = new Lokasi();
                lokasi.setId(Integer.parseInt(cursor.getString(0)));
                lokasi.setKonsumenId(cursor.getString(1));
                lokasi.setLatitude(cursor.getString(2));
                lokasi.setLongitude(cursor.getString(3));

                // Adding contact to list
        // return contact list
        cursor.close();
        return lokasi;

        }


    // Getting single lokasi
    public Lokasi getJarak(long jarak_dari, long jarak_tujuan) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.query(TABLE_JARAK_KONSUMEN, new String[] { KEY_ID, KEY_DARI,
                        KEY_TUJUAN, KEY_JARAK }, KEY_DARI+ "=?" + " and "  +
                        KEY_TUJUAN+ "=?",
                new String[] {String.valueOf(jarak_dari),String.valueOf(jarak_tujuan) }, null, null, KEY_JARAK +" DESC", "1");

        // looping through all rows and adding to list
        if (mCursor != null)
            mCursor.moveToFirst();

        Lokasi lokasi = new Lokasi();
        lokasi.setId(Integer.parseInt(mCursor.getString(0)));
        lokasi.setDari(Integer.parseInt(mCursor.getString(1)));
        lokasi.setTujuan(Integer.parseInt(mCursor.getString(2)));
        lokasi.setJarakAb(Integer.parseInt(mCursor.getString(3)));

        // Adding contact to list
        // return contact list
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }

        return lokasi;

    }


    // Getting single lokasi
    public Lokasi getJarak(int jarak_dari) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.query(TABLE_JARAK_KONSUMEN, new String[] { KEY_ID, KEY_DARI,
                        KEY_TUJUAN, KEY_JARAK }, null,
                new String[] {String.valueOf(jarak_dari) }, null, null, KEY_JARAK +" DESC", "1");

        // looping through all rows and adding to list
        if (mCursor != null)
            mCursor.moveToFirst();

        Lokasi lokasi = new Lokasi();
        lokasi.setId(Integer.parseInt(mCursor.getString(0)));
        lokasi.setDari(Integer.parseInt(mCursor.getString(1)));
        lokasi.setTujuan(Integer.parseInt(mCursor.getString(2)));
        lokasi.setJarakAb(Integer.parseInt(mCursor.getString(3)));

        // Adding contact to list
        // return contact list
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }

        return lokasi;

    }


    // Getting single lokasi
    public Lokasi getPath(long jarak_dari, long jarak_tujuan) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.query(TABLE_JARAK_KONSUMEN, new String[] { KEY_ID, KEY_DARI,
                        KEY_TUJUAN, KEY_JARAK }, KEY_DARI+ "=?" + " and "  +
                        KEY_TUJUAN+ "=?",
                new String[] {String.valueOf(jarak_dari),String.valueOf(jarak_tujuan) }, null, null, KEY_JARAK +" DESC", "1");

        // looping through all rows and adding to list
        if (mCursor != null)
            mCursor.moveToFirst();

        Lokasi lokasi = new Lokasi();
        lokasi.setId(Integer.parseInt(mCursor.getString(0)));
        lokasi.setDari(Integer.parseInt(mCursor.getString(1)));
        lokasi.setTujuan(Integer.parseInt(mCursor.getString(2)));
        lokasi.setJarakAb(Integer.parseInt(mCursor.getString(3)));

        // Adding contact to list
        // return contact list
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }

        return lokasi;

    }


    // Getting single lokasi
    public Lokasi getJarak() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.query(TABLE_JARAK_KONSUMEN, new String[] { KEY_ID, KEY_DARI,
                        KEY_TUJUAN, KEY_JARAK }, null,
                null, null, null, KEY_ID +" DESC", "1");

        // looping through all rows and adding to list
        if (mCursor != null)
            mCursor.moveToFirst();

        Lokasi lokasi = new Lokasi();
        lokasi.setId(Integer.parseInt(mCursor.getString(0)));
        lokasi.setDari(Integer.parseInt(mCursor.getString(1)));
        lokasi.setTujuan(Integer.parseInt(mCursor.getString(2)));
        lokasi.setJarakAb(Integer.parseInt(mCursor.getString(3)));

        // Adding contact to list
        // return contact list
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }

        return lokasi;

    }

    // Getting single lokasi
    public Lokasi getTujuan(int dariJarak) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.query(TABLE_JARAK_KONSUMEN, new String[] { KEY_ID, KEY_DARI,
                        KEY_TUJUAN, KEY_JARAK }, KEY_DARI+ "=?",
                new String[] {String.valueOf(dariJarak)}, null, null, KEY_JARAK +" ASC", "1");

        // looping through all rows and adding to list
        if (mCursor != null)
            mCursor.moveToFirst();

        Lokasi lokasi = new Lokasi();
        lokasi.setId(Integer.parseInt(mCursor.getString(0)));
        lokasi.setDari(Integer.parseInt(mCursor.getString(1)));
        lokasi.setTujuan(Integer.parseInt(mCursor.getString(2)));
        lokasi.setJarakAb(Integer.parseInt(mCursor.getString(3)));

        // Adding contact to list
        // return contact list
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }

        return lokasi;

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



    /**
     * Deleting a todo
     */
    public long deleteLokasxi(long tado_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOKASI_KONSUMEN, KEY_ID + " = ?",
                new String[] { String.valueOf(tado_id) });
        return tado_id;
    }

    /**
     * getting all lokasi
     * */


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
        if (cursor.moveToFirst()) {
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
        if (cursor.moveToFirst()) {
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
     * get single todo
     */

    public Lokasi getJarakx(long jarak_dari, long jarak_tujuan) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_JARAK_KONSUMEN + " WHERE "
                + KEY_DARI + " = " + jarak_dari +" AND " + KEY_TUJUAN + " = " + jarak_tujuan +" ORDER BY " + KEY_JARAK +" ASC LIMIT 1";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Lokasi lok = new Lokasi();
        lok.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        lok.setNote((c.getString(c.getColumnIndex(KEY_DARI))));
        lok.setCreatedAt(c.getString(c.getColumnIndex(KEY_TUJUAN)));

        return lok;
    }


    /**
     * Deleting a todo
     */
    public long deleteJarak(long tado_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_JARAK_KONSUMEN, KEY_ID + " = ?",
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
        if (cursor.moveToFirst()) {
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


    private Lokasi cursorToComment(Cursor cursor) {
        Lokasi lokasi = new Lokasi();
        lokasi.setLatitude(cursor.getString(1));
        lokasi.setLongitude(cursor.getString(2));

        //Log.d("LOKASI DB", lokasi[1]);

        return lokasi;
    }

    /**
     * get single todo
     */
    public Todo getTodo(long todo_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_TODO + " WHERE "
                + KEY_ID + " = " + todo_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Todo td = new Todo();
        td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
        td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

        return td;
    }




    /**
     * getting all todos
     * */
    public List<Todo> getAllToDos() {
        List<Todo> todos = new ArrayList<Todo>();
        String selectQuery = "SELECT  * FROM " + TABLE_TODO;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Todo td = new Todo();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
                td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to todo list
                todos.add(td);
            } while (c.moveToNext());
        }

        return todos;
    }

    /**
     * getting all todos under single tag
     * */
    public List<Todo> getAllToDosByTag(String tag_name) {
        List<Todo> todos = new ArrayList<Todo>();

        String selectQuery = "SELECT  * FROM " + TABLE_TODO + " td, "
                + TABLE_TAG + " tg, " + TABLE_TODO_TAG + " tt WHERE tg."
                + KEY_TAG_NAME + " = '" + tag_name + "'" + " AND tg." + KEY_ID
                + " = " + "tt." + KEY_TAG_ID + " AND td." + KEY_ID + " = "
                + "tt." + KEY_TODO_ID;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Todo td = new Todo();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
                td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to todo list
                todos.add(td);
            } while (c.moveToNext());
        }

        return todos;
    }

    /**
     * getting todo count
     */
    public int getToDoCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TODO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    /**
     * Updating a todo
     */
    public int updateToDo(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TODO, todo.getNote());
        values.put(KEY_STATUS, todo.getStatus());

        // updating row
        return db.update(TABLE_TODO, values, KEY_ID + " = ?",
                new String[] { String.valueOf(todo.getId()) });
    }

    /**
     * Deleting a todo
     */
    public void deleteToDo(long tado_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, KEY_ID + " = ?",
                new String[] { String.valueOf(tado_id) });
    }

    // ------------------------ "tags" table methods ----------------//

    /**
     * Creating tag
     */
    public long createTag(Tag tag) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TAG_NAME, tag.getTagName());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long tag_id = db.insert(TABLE_TAG, null, values);

        return tag_id;
    }

    /**
     * getting all tags
     * */
    public List<Tag> getAllTags() {
        List<Tag> tags = new ArrayList<Tag>();
        String selectQuery = "SELECT  * FROM " + TABLE_TAG;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Tag t = new Tag();
                t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                t.setTagName(c.getString(c.getColumnIndex(KEY_TAG_NAME)));

                // adding to tags list
                tags.add(t);
            } while (c.moveToNext());
        }
        return tags;
    }

    /**
     * Updating a tag
     */
    public int updateTag(Tag tag) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TAG_NAME, tag.getTagName());

        // updating row
        return db.update(TABLE_TAG, values, KEY_ID + " = ?",
                new String[] { String.valueOf(tag.getId()) });
    }

    /**
     * Deleting a tag
     */
    public void deleteTag(Tag tag, boolean should_delete_all_tag_todos) {
        SQLiteDatabase db = this.getWritableDatabase();

        // before deleting tag
        // check if todos under this tag should also be deleted
        if (should_delete_all_tag_todos) {
            // get all todos under this tag
            List<Todo> allTagToDos = getAllToDosByTag(tag.getTagName());

            // delete all todos
            for (Todo todo : allTagToDos) {
                // delete todo
                deleteToDo(todo.getId());
            }
        }

        // now delete the tag
        db.delete(TABLE_TAG, KEY_ID + " = ?",
                new String[] { String.valueOf(tag.getId()) });
    }

    // ------------------------ "todo_tags" table methods ----------------//

    /**
     * Creating todo_tag
     */
    public long createTodoTag(long todo_id, long tag_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TODO_ID, todo_id);
        values.put(KEY_TAG_ID, tag_id);
        values.put(KEY_CREATED_AT, getDateTime());

        long id = db.insert(TABLE_TODO_TAG, null, values);

        return id;
    }

    /**
     * Updating a todo tag
     */
    public int updateNoteTag(long id, long tag_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TAG_ID, tag_id);

        // updating row
        return db.update(TABLE_TODO, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    /**
     * Creating a todo
     */
    public long createToDo(Todo todo, long[] tag_ids) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TODO, todo.getNote());
        values.put(KEY_STATUS, todo.getStatus());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long todo_id = db.insert(TABLE_TODO, null, values);

        // insert tag_ids
        for (long tag_id : tag_ids) {
            createTodoTag(todo_id, tag_id);
        }

        return todo_id;
    }


    /**
     * Deleting a todo tag
     */
    public void deleteToDoTag(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
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
