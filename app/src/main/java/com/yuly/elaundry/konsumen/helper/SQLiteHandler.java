/**
 * Author: Yuly Nurhidayati
 * URL: elaundry.pe.hu
 * */

package com.yuly.elaundry.konsumen.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.yuly.elaundry.konsumen.R;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

	private static final String TAG = SQLiteHandler.class.getSimpleName();

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "android_api";

	// Login table name
	private static final String TABLE_KONSUMEN = "konsumen";

	// Login Table Columns names
	private static final String KEY_ID = "konsumen_no";
	private static final String KEY_NAMA = "konsumen_nama";
	private static final String KEY_ALAMAT = "konsumen_alamat";
	private static final String KEY_NOHP = "konsumen_nohp";
	private static final String KEY_EMAIL = "konsumen_email";
	private static final String KEY_API = "konsumen_kunci_api";
	private static final String KEY_KONSUMEN_ID = "konsumen_id";
	private static final String KEY_DIBUAT_PADA = "konsumen_dibuat_pada";

	public SQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_KONSUMEN + "("
				+ KEY_ID + " INTEGER PRIMARY KEY,"  + KEY_NAMA + " TEXT,"+ KEY_ALAMAT + " TEXT," + KEY_NOHP + " TEXT,"
				+ KEY_EMAIL + " TEXT UNIQUE," + KEY_API + " TEXT,"+ KEY_KONSUMEN_ID + " TEXT,"
				+ KEY_DIBUAT_PADA + " TEXT" + ")";
		db.execSQL(CREATE_LOGIN_TABLE);

		Log.d(TAG, String.valueOf(R.string.table_database_berhasil_dibuat));
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_KONSUMEN);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Storing user details in database
	 * */
	public void addUser(String konsumen_nama, String konsumen_alamat, String konsumen_telepon,  String konsumen_email, String konsumen_kunci_api, String konsumen_id, String konsumen_dibuat_pada) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAMA, konsumen_nama); // Name
		values.put(KEY_ALAMAT, konsumen_alamat); // Name
		values.put(KEY_NOHP, konsumen_telepon); // Name
		values.put(KEY_EMAIL, konsumen_email); // Email
		values.put(KEY_API, konsumen_kunci_api); // API
		values.put(KEY_KONSUMEN_ID, konsumen_id); // Email
		values.put(KEY_DIBUAT_PADA, konsumen_dibuat_pada); // Created At

		// Inserting Row
		long id = db.insert(TABLE_KONSUMEN, null, values);
		db.close(); // Closing database connection

		Log.d(TAG, String.valueOf((R.string.mengambil_data_dari_sqlite) + id));
	}

	/**
	 * Storing user details in database
	 * */
	public int updatePassword(String konsumen_kunci_api, String konsumen_id, String konsumen_dibuat_pada) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_API, konsumen_kunci_api); // API
		values.put(KEY_DIBUAT_PADA, konsumen_dibuat_pada); // Created At

		// updating row
		return db.update(TABLE_KONSUMEN, values, KEY_KONSUMEN_ID + " = ?",
				new String[] {konsumen_id });
	}

	/**
	 * Storing user details in database
	 * */
	public int updateProfile(String konsumen_nama,String konsumen_alamat,String konsumen_telepon,String konsumen_email, String konsumen_kunci_api, String konsumen_id, String konsumen_dibuat_pada) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_NAMA, konsumen_nama); // API
		values.put(KEY_ALAMAT, konsumen_alamat); // API
		values.put(KEY_NOHP, konsumen_telepon); // API
		values.put(KEY_EMAIL, konsumen_email); // API
		values.put(KEY_API, konsumen_kunci_api); // API
		values.put(KEY_DIBUAT_PADA, konsumen_dibuat_pada); // Created At

		// updating row
		return db.update(TABLE_KONSUMEN, values, KEY_KONSUMEN_ID + " = ?",
				new String[] {konsumen_id });
	}

	/**
	 * Getting user data from database
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<>();
		String selectQuery = "SELECT  * FROM " + TABLE_KONSUMEN;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("konsumen_nama", cursor.getString(1));
			user.put("konsumen_alamat", cursor.getString(2));
			user.put("konsumen_nohp", cursor.getString(3));
			user.put("konsumen_email", cursor.getString(4));
			user.put("konsumen_kunci_api", cursor.getString(5));
			user.put("konsumen_id", cursor.getString(6));
			user.put("konsumen_dibuat_pada", cursor.getString(7));
		}
		cursor.close();
		db.close();
		// return user
		Log.d(TAG, (R.string.mengambil_data_dari_sqlite) + " ini " + user.toString());

		return user;
	}


	/**
	 * Getting user data from database
	 * */
	public String getUserApi() {
		String userapi = null;
		String selectQuery = "SELECT  * FROM " + TABLE_KONSUMEN;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			userapi = cursor.getString(5);
		}
		cursor.close();
		db.close();
		// return user
		Log.d(TAG, (R.string.mengambil_data_dari_sqlite) + userapi);

		return userapi;
	}



	/**
	 * Re crate database Delete all tables and create them again
	 * */
	public void deleteUsers() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_KONSUMEN, null, null);
		db.close();

		Log.d(TAG, "Menghapus semua data konsumen dari database SQLite");
	}

}
