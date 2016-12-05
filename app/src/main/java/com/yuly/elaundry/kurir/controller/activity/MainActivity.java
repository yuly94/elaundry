package com.yuly.elaundry.kurir.controller.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
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



import com.yuly.elaundry.kurir.R;
//import com.yuly.elaundry.kurir.controller.fragment.AboutFragment;

import com.yuly.elaundry.kurir.controller.fragment.LaundryPengambilanAgentFragment;
import com.yuly.elaundry.kurir.controller.fragment.LaundryPengantaranFragment;
import com.yuly.elaundry.kurir.controller.fragment.LaundryPemesananFragment;
import com.yuly.elaundry.kurir.controller.fragment.ProfileFragment;
import com.yuly.elaundry.kurir.controller.fragment.LaundryPenjemputanFragment;
import com.yuly.elaundry.kurir.model.database.KurirDbHandler;
import com.yuly.elaundry.kurir.model.helper.SessionManager;
import com.yuly.elaundry.kurir.view.navigation.NavDrawerItem;
import com.yuly.elaundry.kurir.view.navigation.NavDrawerListAdapter;
import com.yuly.elaundry.kurir.view.util.HelpUtils;

import com.yuly.elaundry.kurir.view.widgets.ColorGenerator;
import com.yuly.elaundry.kurir.view.widgets.TextDrawable;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

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
		db = new KurirDbHandler(getApplicationContext());

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
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons.getResourceId(8, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], navMenuIcons.getResourceId(9, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[10], navMenuIcons.getResourceId(10,-1)));

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
		getMenuInflater().inflate(R.menu.menu_main_ori, menu);
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
				fragment = new LaundryPemesananFragment();
				break;
			case 1:

				//  fragment pesanan
				fragment = new LaundryPenjemputanFragment();

				break;

			case 2:
				//  fragment pesanan
				fragment = new LaundryPengambilanAgentFragment();
				break;
			case 3:

				//  fragment pesanan
				fragment = new LaundryPengantaranFragment();

				break;

			case 4:
			//  fragment tempa
				// fragment = new AlamatFragment();

				Intent intentMap = new Intent(this, MainActivityPeta.class);
				startActivity(intentMap);

				break;
			case 5:
			  //	fragment = new MapsFragmentLocation();
			//	fragment =new Example3Fragment();

				Intent intent = new Intent(this, CariRuteActivity.class);
				startActivity(intent);

				break;
			case 6:
				fragment = new ProfileFragment();
				break;
			case 7:
			 	//fragment = new SettingsFragment();

				Intent intentSet = new Intent(this, AppSettingsActivity.class);
				startActivity(intentSet);
				break;
			case 8:
				//	themes
				HelpUtils.showThemes(this);
				break;
			case 9:

			//	Intent intentx = new Intent(this, PetaActivity.class);
			///	startActivity(intentx);

		 		//	About
				HelpUtils.showAbout(this);

/*				Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
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

                fragment = AboutFragment;
*/
            //    fragment = new AboutFragment();

				break;
			case 10:
				// Close
				closeUser();

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

	private void closeUser() {
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
}