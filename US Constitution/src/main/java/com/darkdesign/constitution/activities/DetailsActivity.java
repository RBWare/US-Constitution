package com.darkdesign.constitution.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.darkdesign.constitution.R;
import com.darkdesign.constitution.utils.Globals;

public class DetailsActivity extends FragmentActivity {

    private int mItemType;
    private int mItemPosition;

    @SuppressWarnings("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        // TODO Content view
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mItemType = getIntent().getExtras().getInt("itemType");
        mItemPosition = getIntent().getExtras().getInt("itemPosition");

        getActionBar().setTitle(Globals.data.get(mItemPosition).getTitle());
        getActionBar().setSubtitle(Globals.data.get(mItemPosition).getSubtitle());

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
        Intent i;
        String url;
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_options_google:
                url = Globals.data.get(mItemPosition).getGoogleUrl();
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            case R.id.menu_options_official_website:
                url = Globals.data.get(mItemPosition).getOfficialUrl();
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            case R.id.menu_options_wikipedia:
                url = Globals.data.get(mItemPosition).getWikipediaUrl();
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            case R.id.action_item_search:

                break;
            default:
                // Nothing
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return super.onMenuItemSelected(featureId, item);
    }
}