package com.darkdesign.constitution;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.WazaBe.HoloEverywhere.ThemeManager;
import com.WazaBe.HoloEverywhere.app.Fragment;
import com.WazaBe.HoloEverywhere.sherlock.SActivity;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.view.MenuItem;
import com.darkdesign.constitution.fragments.MoreInformationFragment;
import com.darkdesign.constitution.fragments.SimilarDocumentsFragment;
import com.darkdesign.constitution.fragments.USConstitutionFragment;
import com.darkdesign.constitution.fragments.ViewFragment;

public class Main extends SActivity {

	private static ActionBar actionBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setForceThemeApply(true);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content);

		if (Globals.currentTheme == Globals.THEME_DARK) {
			ThemeManager.restartWithTheme(this, ThemeManager.DARK);
		} else {
			ThemeManager.restartWithTheme(this, ThemeManager.LIGHT);
		}

		if (isABSSupport()) {

			setActionBar(getSupportActionBar());

			getSupportActionBar().setDisplayShowTitleEnabled(true);
			getSupportActionBar().setNavigationMode(
					ActionBar.NAVIGATION_MODE_STANDARD
							| ActionBar.NAVIGATION_MODE_TABS);

			addTab(USConstitutionFragment.class, "Constitution");
			addTab(MoreInformationFragment.class, "More Info.");
			addTab(SimilarDocumentsFragment.class, "Similar Documents");
		} else {
			replaceFragment(android.R.id.content,
					USConstitutionFragment.getInstance());
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			if (getSupportFragmentManager().getBackStackEntryCount() != 0)
				onBackPressed();
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	public void replaceFragment(int resId, Fragment fragment) {
		replaceFragment(resId, fragment, null);
	}

	public void replaceFragment(int resId, Fragment fragment,
			String backStackName) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(resId, fragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		if (backStackName != null) {
			ft.addToBackStack(backStackName);
		}
		ft.commit();
	}

	public void showInformation(View v) {
		getSupportActionBar().removeAllTabs();
		replaceFragment(android.R.id.content, new ViewFragment(), "view");
	}

	public static void showTabs() {

	}

	private final class FragmentListener implements TabListener {
		private final Class<? extends Fragment> clazz;
		private Fragment fragment;

		public FragmentListener(Class<? extends Fragment> clazz) {
			this.clazz = clazz;
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {

		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			if (fragment == null) {
				try {
					fragment = clazz.newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			ft.replace(android.R.id.content, fragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {

		}
	}

	private void addTab(Class<? extends Fragment> clazz, String title) {
		Tab tab = getSupportActionBar().newTab();
		tab.setText(title);
		tab.setTabListener(new FragmentListener(clazz));
		getSupportActionBar().addTab(tab);
	}

	public static ActionBar getThisActionBar() {
		return actionBar;
	}

	public static void setActionBar(ActionBar bar) {
		Main.actionBar = bar;
	}
}