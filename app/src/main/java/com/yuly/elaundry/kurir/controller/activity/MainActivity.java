package com.yuly.elaundry.kurir.controller.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;


import com.yuly.elaundry.kurir.R;
//import com.yuly.elaundry.kurir.controller.fragment.AboutFragment;

import com.yuly.elaundry.kurir.controller.fragment.DialogDownload;
import com.yuly.elaundry.kurir.controller.fragment.LaundryPemesananFragment;
import com.yuly.elaundry.kurir.controller.fragment.ProfileFragment;
import com.yuly.elaundry.kurir.controller.peta.CariRuteActivity;
import com.yuly.elaundry.kurir.controller.peta.PetaSayaActivity;
import com.yuly.elaundry.kurir.model.database.KurirDbHandler;
import com.yuly.elaundry.kurir.model.helper.SessionManager;
import com.yuly.elaundry.kurir.model.util.Variable;
import com.yuly.elaundry.kurir.model.util.HelpUtils;

import com.yuly.elaundry.kurir.view.widgets.ColorGenerator;
import com.yuly.elaundry.kurir.view.widgets.TextDrawable;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

	ColorGenerator generator = ColorGenerator.MATERIAL;

	String api_key;

	private KurirDbHandler db;
	private SessionManager session;

	private DrawerLayout mDrawerLayout;
	private LinearLayout mLiearLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] navMenuTitles;
	private ImageView myNamaHuruf;
	private TextView txtName, txtEmail;
	private TextDrawable txDrawable;

	private File mapsFolder;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();
		//navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		//mLiearLayout = (LinearLayout) findViewById(R.id.drawer_view);
		//mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		// SqLite database handler
		db = new KurirDbHandler(getApplicationContext());

		// session manager
		session = new SessionManager(getApplicationContext());

		if (!session.isLoggedIn()) {
			logoutUser();
		} else {


			//greater Or Equal to Kitkat
			//Mensetting folder peta
			if (Build.VERSION.SDK_INT >= 19) {
				if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					Toast.makeText(this, "Elaundry tidak bisa digunakan tanpa memori penyimpanan peta!", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				Variable.getVariable().setMapsFolder(
						new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
								Variable.getVariable().getMapDirectory()));

			} else Variable.getVariable().setMapsFolder(
					new File(Environment.getExternalStorageDirectory(), Variable.getVariable().getMapDirectory()));

		//muat ulang settingan
		Variable.getVariable().loadVariables();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		View header=navigationView.getHeaderView(0);

		Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

		setSupportActionBar(mToolbar);

		// Thema
	 	themeUtils.onActivityCreateSetTheme(this,getSupportActionBar(),this);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				mToolbar,
				R.string.app_name,
				R.string.app_name
		) {
			public void onDrawerClosed(View view) {
				if (getSupportActionBar()!=null) {
					getSupportActionBar().setTitle(mTitle);
				}
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				if (getSupportActionBar()!=null) {
					getSupportActionBar().setTitle(mDrawerTitle);
				}
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

			if (savedInstanceState == null) {

				fragmentSatu();
			}

			txtName = (TextView) header.findViewById(R.id.text_nama);
			txtEmail = (TextView) header.findViewById(R.id.text_email);
			myNamaHuruf = (ImageView) header.findViewById(R.id.draw_nama);

		// Progress dialog
		ProgressDialog pDialog = new ProgressDialog(this);
		pDialog.setCancelable(false);

			// Fetching user details from SQLite
			HashMap<String, String> user = db.getUserDetails();

			if (user!=null) {

				Log.d("TAG","tidak null");

				String nama = user.get("kurir_nama");
				//String alamat = user.get("alamat");
				//String telepon = user.get("telepon");
				String email = user.get("kurir_email");
				api_key = user.get("api");

				// Displaying the user details on the screen
				if (txtName != null) {
					txtName.setText(nama);
				}
				if (txtEmail != null) {
					txtEmail.setText(email);
				}

				if(nama!=null) {
					String letter = String.valueOf(nama.charAt(0));

					// Create a new TextDrawable for our image's background
					TextDrawable drawable = TextDrawable.builder()
							.buildRound(letter, generator.getRandomColor());

					if (myNamaHuruf != null) {
						myNamaHuruf.setImageDrawable(drawable);
					}
				}
			} else {
				Log.d("Main Activity","User not found");
			}

		}

	}


/*
	// memanggil menu drawer
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main_ori, menu);
		return true;
	}
*/


	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}


	// memanggil menu drawer
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {

			case android.R.id.home:
				this.finish();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}

	}

	// memanggil menu drawer
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Logging out the user. Will set isLoggedIn flag to false in shared
	 * preferences Clears the user data from sqlite users table
	 * */
	private void closeUser() {

		this.finish();
		System.exit(0);

	}



	private void logoutUser() {
		session.setLogin(false);
		db.deleteUsers();

		// Launching the login activity
 		Intent intent = new Intent(MainActivity.this, LoginActivityNew.class);
		startActivity(intent);
		finish();

		//



	/*		android.app.Fragment fragment;

			fragment = new LoginFragment();

			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.frame_container,fragment);
			ft.commit();

*/

		//

	}



	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		if (getSupportActionBar()!=null) {
			getSupportActionBar().setTitle(mTitle);
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}



	private void fragmentSatu(){
		Fragment fragment;

		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.frame_container, fragment = new LaundryPemesananFragment()).commit();

		// Handle the camera action
		//Get Fragment Instance
		Bundle data = new Bundle();//Use bundle to pass data
		data.putString("TEXT_TOMBOL", "mengambil laundry");//put string, int, etc in bundle with a key value
		data.putString("STATUS_SEBELUMNYA", "baru memesan");
		data.putString("UPDATE_STATUS", "pengambilan laundry");

		fragment.setArguments(data);//Finally set argument bundle to fragment


		Log.e(String.valueOf(getApplicationContext()), String.valueOf(R.string.error_in_creating_fragment));

	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		Fragment fragment = null;
		int id = item.getItemId();

		if (id == R.id.nav_pemesanan) {
			// Handle the camera action
			fragment = new LaundryPemesananFragment();//Get Fragment Instance
			Bundle data = new Bundle();//Use bundle to pass data
			data.putString("TEXT_TOMBOL", "mengambil laundry");//put string, int, etc in bundle with a key value
			data.putString("STATUS_SEBELUMNYA", "baru memesan");
			data.putString("UPDATE_STATUS", "pengambilan laundry");

			fragment.setArguments(data);//Finally set argument bundle to fragment
		} else if (id == R.id.nav_penjemputan) {

			fragment = new LaundryPemesananFragment();//Get Fragment Instance
			Bundle data1 = new Bundle();//Use bundle to pass data
			data1.putString("TEXT_TOMBOL", "menyerahkan ke agent");//put string, int, etc in bundle with a key value
			data1.putString("STATUS_SEBELUMNYA", "pengambilan laundry");
			data1.putString("UPDATE_STATUS", "diserahkan ke agent");

			fragment.setArguments(data1);//Finally set argument bundle to fragment

		} else if (id == R.id.nav_pengambilan) {

			fragment = new LaundryPemesananFragment();//Get Fragment Instance
			Bundle data2 = new Bundle();//Use bundle to pass data
			data2.putString("TEXT_TOMBOL", "mengambil dari agent");//put string, int, etc in bundle with a key value
			data2.putString("STATUS_SEBELUMNYA", "diserahkan ke agent");
			data2.putString("UPDATE_STATUS", "mengambil dari agent");


			//intent.putExtra("TEXT_TOMBOL", "mengantarkan laundry");
			//intent.putExtra("STATUS_SEBELUMNYA", "mengambil dari agent");
			//intent.putExtra("UPDATE_STATUS", "mengantarkan laundry");

			fragment.setArguments(data2);//Finally set argument bundle to fragment

		} else if (id == R.id.nav_pengantaran) {

			fragment = new LaundryPemesananFragment();//Get Fragment Instance
			Bundle data3 = new Bundle();//Use bundle to pass data
			data3.putString("TEXT_TOMBOL", "mengantarkan laundry");//put string, int, etc in bundle with a key value
			data3.putString("STATUS_SEBELUMNYA", "mengambil dari agent");
			data3.putString("UPDATE_STATUS", "mengantarkan laundry");

			fragment.setArguments(data3);//Finally set argument bundle to fragment

		}	else if (id == R.id.nav_peta_saya) {

			if (!MendownloadPeta.getMendownloadPeta().checkFilePetaAda()){

				//MendownloadPeta.getMendownloadPeta().dialogDownloadPeta();
				//panggilDialogDownload();
				DialogDownload.showDownloadPetaDialog(this);
			} else {

			Intent intentMap = new Intent(this, PetaSayaActivity.class);
			startActivity(intentMap);
			}

		}  else if (id == R.id.nav_peta_rute) {

			if (!MendownloadPeta.getMendownloadPeta().checkFilePetaAda()){

				//MendownloadPeta.getMendownloadPeta().dialogDownloadPeta();
				//panggilDialogDownload();

				DialogDownload.showDownloadPetaDialog(this);
			} else {
				Intent intent = new Intent(this, CariRuteActivity.class);
				startActivity(intent);
			}

		} else if (id == R.id.nav_profile) {

			fragment = new ProfileFragment();

		} 	else if (id == R.id.nav_pengaturan) {

			Intent intentSet = new Intent(this, AppSettingsActivity.class);
			startActivity(intentSet);

		}  else if (id == R.id.nav_tema) {

			//	themes
			HelpUtils.showThemes(this);

		}

		else if (id == R.id.nav_about) {

			//	About
			HelpUtils.showAbout(this);

		} else if (id == R.id.nav_exit) {
			// Logout
			closeUser();

		}

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

//			mDrawerList.setItemChecked(position, true);
//			mDrawerList.setSelection(position);
			//setTitle(navMenuTitles[id]);
		} else {

			Log.e(String.valueOf(getApplicationContext()), String.valueOf(R.string.error_in_creating_fragment));
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}


}