package com.darkdesign.constitution.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.darkdesign.constitution.R;
import com.darkdesign.constitution.fragments.DetailsFragment;
import com.darkdesign.constitution.utils.Globals;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends FragmentActivity {

    private int mItemTypeCount;
    private int mItemPosition;

    private ViewPager mPager;

    @SuppressWarnings("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        // TODO Content view
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mItemTypeCount = getIntent().getExtras().getInt("itemTypeCount");
        mItemPosition = getIntent().getExtras().getInt("itemPosition");

        ViewPagerAdapter adapter = new ViewPagerAdapter(this,
                mItemTypeCount);
        ViewPager myPager = (ViewPager) findViewById(R.id.reviewpager);
        myPager.setAdapter(adapter);
        myPager.setCurrentItem(mItemPosition - 1);
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

    private class ViewPagerAdapter extends PagerAdapter {

        private Activity mActivity;
        private WebView mContent;

        int size;
        View layout;

        public ViewPagerAdapter(DetailsActivity mainActivity, int numberOfPages) {
            mActivity = mainActivity;
            size = numberOfPages;
        }

        @Override
        public int getCount() {
            return size;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater inflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.view_information, null);
            mContent = (WebView) layout.findViewById(R.id.content_frame);

            getActionBar().setTitle(Globals.data.get(position).getTitle());
            getActionBar().setSubtitle(Globals.data.get(position).getSubtitle());

            mContent.loadUrl("file:///android_asset/www/" + (position) + ".html");

            // Enable pinch to zoom without the zoom buttons
            mContent.getSettings().setBuiltInZoomControls(true);

            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                // Hide the zoom controls for HONEYCOMB+
                mContent.getSettings().setDisplayZoomControls(false);
            }

            ((ViewPager) container).addView(layout);
            return layout;
        }



        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View container, Object object) {
            return object == ((View) container);
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }
}