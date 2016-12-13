package com.yuly.elaundry.konsumen.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import com.yuly.elaundry.konsumen.R;
//import com.yuly.elaundry.konsumen.fragment.AboutFragment;

import com.yuly.elaundry.konsumen.fragment.TransaksiFragment;
import com.yuly.elaundry.konsumen.fragment.ProfileFragment;
import com.yuly.elaundry.konsumen.helper.KonsumenDbHandler;
import com.yuly.elaundry.konsumen.helper.SessionManager;
import com.yuly.elaundry.konsumen.util.HelpUtils;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import static com.yuly.elaundry.konsumen.R.id.fragment;


public class MainActivity extends AppCompatActivity   implements NavigationView.OnNavigationItemSelectedListener {

	ColorGenerator generator = ColorGenerator.MATERIAL;

	String api_key;

	private KonsumenDbHandler db;
	private SessionManager session;

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] navMenuTitles;
	private ImageView myNamaHuruf;
	private TextView txtName, txtEmail;
	private TextDrawable txDrawable;

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
		db = new KonsumenDbHandler(getApplicationContext());

		// session manager
		session = new SessionManager(getApplicationContext());

		if (!session.isLoggedIn()) {
			logOutUser();
		} else {



		//TypedArray navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

//		ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<NavDrawerItem>();
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));
//
//		navMenuIcons.recycle();
//
//		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
//
//		NavDrawerListAdapter adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
//		mDrawerList.setAdapter(adapter);

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
		//ProgressDialog pDialog = new ProgressDialog(this);
		//pDialog.setCancelable(false);

			// Fetching user details from SQLite
			HashMap<String, String> user = db.getUserDetails();

			if (user!=null) {

				String nama = user.get("konsumen_nama");
				//String alamat = user.get("alamat");
				//String telepon = user.get("telepon");
				String email = user.get("konsumen_email");
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
					txDrawable = TextDrawable.builder()
							.buildRound(letter, generator.getRandomColor());

					if (myNamaHuruf != null) {
						myNamaHuruf.setImageDrawable(txDrawable);
					}
				}
			} else {
				Log.d("Main Activity","User not found");
			}

		}


	}

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


	private void logOutUser() {

		session.setLogin(false);
		db.deleteUsers();

		// Launching the login activity
		Intent intent = new Intent(MainActivity.this, LoginActivityNew.class);
		startActivity(intent);
		finish();

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
					.replace(R.id.frame_container, fragment = new TransaksiFragment()).commit();

			// Handle the camera action
			;//Get Fragment Instance
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
			fragment = new TransaksiFragment();//Get Fragment Instance
			Bundle data = new Bundle();//Use bundle to pass data
			data.putString("TEXT_TOMBOL", "mengambil laundry");//put string, int, etc in bundle with a key value
			data.putString("STATUS_SEBELUMNYA", "baru memesan");
			data.putString("UPDATE_STATUS", "pengambilan laundry");

			fragment.setArguments(data);//Finally set argument bundle to fragment
		} else if (id == R.id.nav_penjemputan) {

			fragment = new TransaksiFragment();//Get Fragment Instance
			Bundle data1 = new Bundle();//Use bundle to pass data
			data1.putString("TEXT_TOMBOL", "menyerahkan ke agent");//put string, int, etc in bundle with a key value
			data1.putString("STATUS_SEBELUMNYA", "pengambilan laundry");
			data1.putString("UPDATE_STATUS", "diserahkan ke agent");

			fragment.setArguments(data1);//Finally set argument bundle to fragment

		} else if (id == R.id.nav_pengambilan) {

			fragment = new TransaksiFragment();//Get Fragment Instance
			Bundle data2 = new Bundle();//Use bundle to pass data
			data2.putString("TEXT_TOMBOL", "mengambil dari agent");//put string, int, etc in bundle with a key value
			data2.putString("STATUS_SEBELUMNYA", "diserahkan ke agent");
			data2.putString("UPDATE_STATUS", "mengambil dari agent");


			//intent.putExtra("TEXT_TOMBOL", "mengantarkan laundry");
			//intent.putExtra("STATUS_SEBELUMNYA", "mengambil dari agent");
			//intent.putExtra("UPDATE_STATUS", "mengantarkan laundry");

			fragment.setArguments(data2);//Finally set argument bundle to fragment

		} else if (id == R.id.nav_pengantaran) {

			fragment = new TransaksiFragment();//Get Fragment Instance
			Bundle data3 = new Bundle();//Use bundle to pass data
			data3.putString("TEXT_TOMBOL", "mengantarkan laundry");//put string, int, etc in bundle with a key value
			data3.putString("STATUS_SEBELUMNYA", "mengambil dari agent");
			data3.putString("UPDATE_STATUS", "mengantarkan laundry");

			fragment.setArguments(data3);//Finally set argument bundle to fragment

		} else if (id == R.id.nav_profile) {

			fragment = new ProfileFragment();

		} else if (id == R.id.nav_tema) {

			//	themes
			HelpUtils.showThemes(this);

		} else if (id == R.id.nav_about) {

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