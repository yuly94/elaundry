/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.yuly.elaundry.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
	private static final String KEY_TELEPON = "konsumen_telepon";
	private static final String KEY_EMAIL = "konsumen_email";
	private static final String KEY_API = "konsumen_kunci_api";
	private static final String KEY_UID = "konsumen_id";
	private static final String KEY_CREATED_AT = "konsumen_dibuat_pada";

	public SQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_KONSUMEN + "("
				+ KEY_ID + " INTEGER PRIMARY KEY,"  + KEY_NAMA + " TEXT,"+ KEY_ALAMAT + " TEXT," + KEY_TELEPON + " TEXT,"
				+ KEY_EMAIL + " TEXT UNIQUE," + KEY_API + " TEXT,"+ KEY_UID + " TEXT,"
				+ KEY_CREATED_AT + " TEXT" + ")";
		db.execSQL(CREATE_LOGIN_TABLE);

		Log.d(TAG, "Database tables created");
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
		values.put(KEY_TELEPON, konsumen_telepon); // Name
		values.put(KEY_EMAIL, konsumen_email); // Email
		values.put(KEY_API, konsumen_kunci_api); // API
		values.put(KEY_UID, konsumen_id); // Email
		values.put(KEY_CREATED_AT, konsumen_dibuat_pada); // Created At

		// Inserting Row
		long id = db.insert(TABLE_KONSUMEN, null, values);
		db.close(); // Closing database connection

		Log.d(TAG, "User baru dimasukkan ke db sqlite : " + id);
	}

	/**
	 * Storing user details in database
	 * */
	public int updatePassword(String api, String uid, String created_at) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_API, api); // API
		values.put(KEY_CREATED_AT, created_at); // Created At

		// updating row
		return db.update(TABLE_KONSUMEN, values, KEY_UID + " = ?",
				new String[] {uid });
	}

	/**
	 * Storing user details in database
	 * */
	public int updateProfile(String konsumen_nama,String konsumen_alamat,String konsumen_telepon,String konsumen_email, String konsumen_id, String konsumen_dibuat_pada) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_NAMA, konsumen_nama); // API
		values.put(KEY_ALAMAT, konsumen_alamat); // API
		values.put(KEY_TELEPON, konsumen_telepon); // API
		values.put(KEY_EMAIL, konsumen_email); // API
		values.put(KEY_CREATED_AT, konsumen_dibuat_pada); // Created At

		// updating row
		return db.update(TABLE_KONSUMEN, values, KEY_UID + " = ?",
				new String[] {konsumen_id });
	}



	/**
	 * Getting user data from database
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE_KONSUMEN;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("konsumen_nama", cursor.getString(1));
			user.put("konsumen_alamat", cursor.getString(2));
			user.put("konsumen_telepon", cursor.getString(3));
			user.put("konsumen_email", cursor.getString(4));
			user.put("konsumen_kunci_api", cursor.getString(5));
			user.put("konsumen_id", cursor.getString(6));
			user.put("konsumen_dibuat_pada", cursor.getString(7));
		}
		cursor.close();
		db.close();
		// return user
		Log.d(TAG, "Mengambil data konsumen dari Sqlite: " + user.toString());

		return user;
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
