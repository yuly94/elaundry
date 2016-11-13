package com.yuly.elaundry.activity;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import java.util.ArrayList;
import java.util.HashMap;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import com.google.android.gms.maps.SupportMapFragment;
import com.yuly.elaundry.R;
//import com.yuly.elaundry.fragment.AboutFragment;

import com.yuly.elaundry.fragment.AlamatFragment;
import com.yuly.elaundry.fragment.ChangePassFragment;
import com.yuly.elaundry.fragment.MapsFragmentLocation;
import com.yuly.elaundry.fragment.PemesananFragment;
import com.yuly.elaundry.fragment.TransaksiFragment;
import com.yuly.elaundry.fragment.TempatFragment;
import com.yuly.elaundry.fragment.ProfileFragment;
import com.yuly.elaundry.helper.SQLiteHandler;
import com.yuly.elaundry.helper.SessionManager;
import com.yuly.elaundry.navigation.NavDrawerItem;
import com.yuly.elaundry.navigation.NavDrawerListAdapter;
import com.yuly.elaundry.util.HelpUtils;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

	ColorGenerator generator = ColorGenerator.MATERIAL;

	String api_key;

	private SQLiteHandler db;
	private SessionManager session;

	private DrawerLayout mDrawerLayout;
	private LinearLayout mLiearLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] navMenuTitles;
	private ImageView myNamaHuruf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mLiearLayout = (LinearLayout) findViewById(R.id.drawer_view);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		// SqLite database handler
		db = new SQLiteHandler(getApplicationContext());

		// session manager
		session = new SessionManager(getApplicationContext());

		if (!session.isLoggedIn()) {
			logoutUser();
		} else {

		myNamaHuruf = (ImageView) findViewById(R.id.draw_nama);

		TypedArray navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

		ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<NavDrawerItem>();
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));

		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		NavDrawerListAdapter adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
		mDrawerList.setAdapter(adapter);

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

			displayView(0);
		}

		TextView txtName = (TextView) findViewById(R.id.text_nama);
		TextView txtEmail = (TextView) findViewById(R.id.text_email);

		// Progress dialog
		ProgressDialog pDialog = new ProgressDialog(this);
		pDialog.setCancelable(false);



			// Fetching user details from SQLite
			HashMap<String, String> user = db.getUserDetails();

			if (user!=null) {

				String nama = user.get("nama");
				//String alamat = user.get("alamat");
				//String telepon = user.get("telepon");
				String email = user.get("email");
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
				Log.d("Main Activity","UserModels not found");
			}

		}
	}





	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, final int position,
								long id) {
			mDrawerLayout.closeDrawer(mLiearLayout);
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					displayView(position);
				}
			}, 300);
		}
	}

/*
	// memanggil menu drawer
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}
*/

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


	private void displayView(int position) {

		Fragment fragment = null;
		switch (position) {
			case 0:
			//  fragment pesanan
				fragment = new PemesananFragment();
				break;
			case 1:
			//  fragment tempat
				fragment = new AlamatFragment();
				break;
			case 2:
			  	fragment = new MapsFragmentLocation();
			//	fragment =new Example3Fragment();
				break;
			case 3:
				fragment = new ChangePassFragment();
				break;
			case 4:
			 	fragment = new ProfileFragment();
				break;
			case 5:
				//	themes
				HelpUtils.showThemes(this);
				break;
			case 6:

		/*		//	About
				//HelpUtils.showAbout(this);

*//*				Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
				startActivity(intent);*//*


                LibsSupportFragment AboutFragment = new LibsBuilder()
                        .withLibraries()
                        .withVersionShown(false)
                        .withLicenseShown(true)
                        .withLibraryModification("aboutlibraries", Libs.LibraryFields.LIBRARY_NAME, "_AboutLibraries")
                        .supportFragment();

*//*
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_container, AboutFragment).commit();

*//*


*/

             //   fragment = new AboutFragment();

				break;
			case 7:
				// Logout
				logoutUser();
				break;


			default:
				break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
		} else {

			Log.e(String.valueOf(getApplicationContext()), String.valueOf(R.string.error_in_creating_fragment));
		}
	}


	/**
	 * Logging out the user. Will set isLoggedIn flag to false in shared
	 * preferences Clears the user data from sqlite users table
	 * */
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
}