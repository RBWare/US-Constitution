package com.darkdesign.constitution;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;

import com.darkdesign.constitution.R;

public class Main extends SherlockFragmentActivity {

    private ActionBar mActionBar;
    private ViewPager mPager;
    private Tab mTab;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD
                | ActionBar.NAVIGATION_MODE_TABS);

        mPager = (ViewPager) findViewById(R.id.pager);

        // Activate Fragment Manager
        FragmentManager fm = getSupportFragmentManager();

        // Capture ViewPager page swipes
        ViewPager.SimpleOnPageChangeListener ViewPagerListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Find the ViewPager Position
                mActionBar.setSelectedNavigationItem(position);
            }
        };

        mPager.setOnPageChangeListener(ViewPagerListener);
        // Locate the adapter class called ViewPagerAdapter.java
        ViewPagerAdapter viewpageradapter = new ViewPagerAdapter(fm);
        // Set the View Pager Adapter into ViewPager
        mPager.setAdapter(viewpageradapter);

        // Capture tab button clicks
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {

            @Override
            public void onTabSelected(Tab tab, FragmentTransaction ft) {
                // Pass the position on tab click to ViewPager
                mPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(Tab tab, FragmentTransaction ft) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTabReselected(Tab tab, FragmentTransaction ft) {
                // TODO Auto-generated method stub
            }
        };

        // TODO - Proper category setup

        // Create first Tab
        mTab = mActionBar.newTab().setText("US Constitution").setTabListener(tabListener);
        mActionBar.addTab(mTab);

        // Create second Tab
        mTab = mActionBar.newTab().setText("Similar Documents").setTabListener(tabListener);
        mActionBar.addTab(mTab);

        // Create third Tab
        mTab = mActionBar.newTab().setText("More Information").setTabListener(tabListener);
        mActionBar.addTab(mTab);
    }

    class MyTabsListener implements ActionBar.TabListener {
        public Fragment fragment;

        public MyTabsListener(Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            // TODO Auto-generated method stub
            if (fragment == null) {
                Log.v("App", "fragment is null");
            }

            if (ft == null) {
                Log.v("App", "fragment TRANSACTION is null");
            }

//            ft.replace(R.id.fragment_container, fragment);
        }

        @Override
        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            // TODO Auto-generated method stub
//            Toast.makeText(TabActivity.appContext, "Reselected!", Toast.LENGTH_LONG).show();

        }

//		if (Globals.currentTheme == Globals.THEME_DARK) {
//			ThemeManager.restartWithTheme(this, ThemeManager.DARK);
//		} else {
//			ThemeManager.restartWithTheme(this, ThemeManager.LIGHT);
//		}
//
//		if (isABSSupport()) {
//
//			setActionBar(getSupportActionBar());
//
//			getSupportActionBar().setDisplayShowTitleEnabled(true);
//			getSupportActionBar().setNavigationMode(
//					ActionBar.NAVIGATION_MODE_STANDARD
//							| ActionBar.NAVIGATION_MODE_TABS);
//
//			addTab(USConstitutionFragment.class, "Constitution");
//			addTab(MoreInformationFragment.class, "More Info.");
//			addTab(SimilarDocumentsFragment.class, "Similar Documents");
//		} else {
//			replaceFragment(android.R.id.content,
//					USConstitutionFragment.getInstance());
//		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			if (getSupportFragmentManager().getBackStackEntryCount() != 0)
				onBackPressed();
            break;
        case R.id.action_item_about:
            showAboutDialog();
            break;
        case R.id.action_item_settings:

			break;
		}

		return super.onOptionsItemSelected(item);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void showAboutDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog aboutDialog;
        builder.setTitle("About US Constitution");
        builder.setMessage(getString(R.string.about_application));
        builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
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

        // Declare the number of ViewPager pages
        final int PAGE_COUNT = 3;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            switch (arg0) {

                // Open FragmentTab1.java
                case 0:
                    ListItemFragment fragmenttab1 = new ListItemFragment(1);
                    return fragmenttab1;

                // Open FragmentTab2.java
                case 1:
                    ListItemFragment fragmenttab2 = new ListItemFragment(2);
                    return fragmenttab2;

                // Open FragmentTab3.java
                case 2:
                    ListItemFragment fragmenttab3 = new ListItemFragment(3);
                    return fragmenttab3;
            }
            return null;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return PAGE_COUNT;
        }

    }

    //	public void replaceFragment(int resId, Fragment fragment) {
//		replaceFragment(resId, fragment, null);
//	}
//
//	public void replaceFragment(int resId, Fragment fragment,
//			String backStackName) {
//		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//		ft.replace(resId, fragment);
//		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//		if (backStackName != null) {
//			ft.addToBackStack(backStackName);
//		}
//		ft.commit();
//	}
//
//	public void showInformation(View v) {
//		getSupportActionBar().removeAllTabs();
//		replaceFragment(android.R.id.content, new ViewFragment(), "view");
//	}
//
//	public static void showTabs() {
//
//	}
//
//	private final class FragmentListener implements TabListener {
//		private final Class<? extends Fragment> clazz;
//		private Fragment fragment;
//
//		public FragmentListener(Class<? extends Fragment> clazz) {
//			this.clazz = clazz;
//		}
//
//		@Override
//		public void onTabReselected(Tab tab, FragmentTransaction ft) {
//
//		}
//
//		@Override
//		public void onTabSelected(Tab tab, FragmentTransaction ft) {
//			if (fragment == null) {
//				try {
//					fragment = clazz.newInstance();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			ft.replace(android.R.id.content, fragment);
//			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//		}
//
//		@Override
//		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
//
//		}
//	}
//
//	private void addTab(Class<? extends Fragment> clazz, String title) {
//		Tab tab = getSupportActionBar().newTab();
//		tab.setText(title);
//		tab.setTabListener(new FragmentListener(clazz));
//		getSupportActionBar().addTab(tab);
//	}
//
//	public static ActionBar getThisActionBar() {
//		return actionBar;
//	}
//
//	public static void setActionBar(ActionBar bar) {
//		Main.actionBar = bar;
//	}
}