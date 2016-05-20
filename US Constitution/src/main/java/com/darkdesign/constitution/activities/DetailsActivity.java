package com.darkdesign.constitution.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.darkdesign.constitution.R;
import com.darkdesign.constitution.fragments.DetailsFragment;
import com.darkdesign.constitution.models.DataObject;
import com.darkdesign.constitution.sql.DatabaseHelper;

import java.util.ArrayList;

public class DetailsActivity extends ActionBarActivity {

    private ArrayList<DataObject> mEntryItems;
    private ViewPager mDetailsViewPager;
    private int mCurrentItem;

    @SuppressWarnings("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        mDetailsViewPager = (ViewPager) findViewById(R.id.details_viewpager);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int currentItemId = getIntent().getExtras().getInt("itemId");
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        mEntryItems = dbHelper.getFullEntriesInCategoryByItemId(currentItemId);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), mEntryItems.size());

        int currentPosition = 0;
        for (int i = 0; i < mEntryItems.size(); i++){
            if (mEntryItems.get(i).getId() == currentItemId){
                currentPosition = i;
                break;
            }
        }

//
        mDetailsViewPager.setAdapter(adapter);
        mDetailsViewPager.setCurrentItem(currentPosition);
        mDetailsViewPager.setOnPageChangeListener(viewPagerOnChangeListener);
        getSupportActionBar().setTitle(mEntryItems.get(currentPosition).getTitle());
        getSupportActionBar().setSubtitle(mEntryItems.get(currentPosition).getSubtitle());
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_dark, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = null;
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return false;
            case R.id.menu_options_google:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(mEntryItems.get(mDetailsViewPager.getCurrentItem()).getGoogleUrl()));
                break;
            case R.id.menu_options_official_website:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(mEntryItems.get(mDetailsViewPager.getCurrentItem()).getOfficialUrl()));
                break;
            case R.id.menu_options_wikipedia:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(mEntryItems.get(mDetailsViewPager.getCurrentItem()).getWikipediaUrl()));
                break;
            case R.id.action_item_search:

                break;
            default:
                return false;
        }

        startActivity(i);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
//        outState.putParcelable(); // TODO
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        int mPageCount;

        public ViewPagerAdapter(FragmentManager fm, int numberOfPages) {
            super(fm);
            mPageCount = numberOfPages;
        }

        @Override
        public int getCount() {
            return mPageCount;
        }

        @Override
        public Fragment getItem(int position) {

            DetailsFragment detailsFragment = new DetailsFragment();
            Bundle args = new Bundle();
            args.putInt("itemId", mEntryItems.get(position).getId());
            detailsFragment.setArguments(args);
            return detailsFragment;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
    }


    private ViewPager.OnPageChangeListener viewPagerOnChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            getSupportActionBar().setTitle(mEntryItems.get(position).getTitle());
            getSupportActionBar().setSubtitle(mEntryItems.get(position).getSubtitle());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}