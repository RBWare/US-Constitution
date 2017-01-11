package com.darkdesign.constitution.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.view.ViewPager;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import com.darkdesign.constitution.fragments.ListItemFragment;
import com.darkdesign.constitution.R;
import com.darkdesign.constitution.sql.DatabaseHelper;

import java.util.ArrayList;

public class ListActivity extends ActionBarActivity {

    private ActionBar mActionBar;
    private ViewPager mPager;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mPager = (ViewPager) findViewById(R.id.pager);

        String[] navItems = getResources().getStringArray(R.array.nav_drawer_items);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, navItems);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer_dark,  /* nav drawer image to replace 'Up' caret */
                R.string.app_name,  /* "open drawer" description for accessibility */
                R.string.app_name  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Activate Fragment Manager
        FragmentManager fm = getSupportFragmentManager();

        // Locate the adapter class called ViewPagerAdapter.java
        ViewPagerAdapter viewpageradapter = new ViewPagerAdapter(fm);
        // Set the View Pager Adapter into ViewPager
        mPager.setAdapter(viewpageradapter);
        mPager.invalidate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
            case R.id.action_item_search:
                // TODO

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void selectItem(int position) {

        switch(position){
            case 0:
                showSettingsDialog();
                break;
            case 1:
                showAboutDialog();
                break;
            default:
                // Nothing
                break;
        }

        // Close after selection
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private void showSettingsDialog(){
        AlertDialog.Builder settingsDialogBuilder = new AlertDialog.Builder(this);
        View settingsDialogView = getLayoutInflater().inflate(R.layout.settings_dialog, null);
        final CheckBox checkboxLightTheme = (CheckBox)settingsDialogView.findViewById(R.id.settings_dialog_switch_theme);
        final CheckBox checkboxSplashScreen = (CheckBox)settingsDialogView.findViewById(R.id.settings_dialog_switch_splash);

        SharedPreferences prefs = getSharedPreferences(getString(R.string.settings_shared_prefs), Context.MODE_PRIVATE);

        checkboxLightTheme.setChecked(prefs.getBoolean(getString(R.string.settings_pref_theme_light), false));
        checkboxSplashScreen.setChecked(prefs.getBoolean(getString(R.string.settings_pref_splash_enabled), true));

        settingsDialogBuilder.setView(settingsDialogView);
        settingsDialogBuilder.setTitle(getString(R.string.settings_header));

        settingsDialogBuilder.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                SharedPreferences prefs = getSharedPreferences(getString(R.string.settings_shared_prefs), Context.MODE_PRIVATE);

                prefs.edit().putBoolean(getString(R.string.settings_pref_splash_enabled), checkboxSplashScreen.isChecked()).commit();
                prefs.edit().putBoolean(getString(R.string.settings_pref_theme_light), checkboxLightTheme.isChecked()).commit();

                Log.i("Settings", "Saved");

                // TODO - Switch themes

            } });

        settingsDialogBuilder.setNegativeButton(getString(R.string.cancel), null); // Nothing to do
        settingsDialogBuilder.show();
    }

    private void showAboutDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog aboutDialog;
        builder.setTitle(getString(R.string.about_header));
        builder.setMessage(getString(R.string.about_application));
        builder.setPositiveButton(getString(R.string.dismiss), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        aboutDialog = builder.create();

        if (!aboutDialog.isShowing())
            aboutDialog.show();
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<String> sectionTitles = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager fm) {

            super(fm);
            DatabaseHelper dbHelper = new DatabaseHelper(ListActivity.this);
            sectionTitles = dbHelper.getCategories();
        }

        @Override
        public Fragment getItem(int position) {

            Bundle bundle = new Bundle();
            ListItemFragment listFragment = new ListItemFragment();
            bundle.putInt("tabIndex", (position + 1));
            listFragment.setArguments(bundle);
            return listFragment;

        }

        @Override
        public int getCount() {
            return sectionTitles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return sectionTitles.get(position);
        }

    }
}