package com.darkdesign.constitution.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import com.WazaBe.HoloEverywhere.LayoutInflater;
import com.WazaBe.HoloEverywhere.app.Dialog;
import com.WazaBe.HoloEverywhere.sherlock.SListFragment;
import com.WazaBe.HoloEverywhere.widget.EditText;
import com.WazaBe.HoloEverywhere.widget.TextView;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.darkdesign.constitution.Globals;
import com.darkdesign.constitution.Main;
import com.darkdesign.constitution.R;
import com.darkdesign.constitution.SettingsDialog;
import com.darkdesign.constitution.classes.DataObject;

public class MoreInformationFragment extends SListFragment {

	private static MoreInformationFragment instance;

	public static int mCurrentSelection = 0;
	public static boolean mSettingsLoaded;

	private EditText search;

	public static MoreInformationFragment getInstance() {
		if (instance == null) {
			return new MoreInformationFragment();
		}
		return instance;
	}

	public MoreInformationFragment() {
		instance = this;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		setHasOptionsMenu(true);

		if (isABSSupport()) {
			Main.getThisActionBar().setDisplayHomeAsUpEnabled(false);
			Main.getThisActionBar().setDisplayShowTitleEnabled(true);
			Main.getThisActionBar().setDisplayUseLogoEnabled(true);
		}

		return inflater.inflate(R.layout.main);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		if (isABSSupport()) {

			String[] itemSize = new String[Globals.moreInformationData.size()];

			setListAdapter(new MyCustomAdapter(getActivity(), R.layout.row,
					itemSize));
			getListView().setOnItemClickListener(listClickListener);

		}
	}

	private OnItemClickListener listClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long id) {

			Globals.currentListItemSelection = (int) id;
			Globals.currentListTypeSelection = Globals.LIST_TYPE_MORE_INFO;

			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			ft.replace(android.R.id.content, new ViewFragment());
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			if ("view" != null) {
				ft.addToBackStack("view");
			}
			ft.commit();
		}
	};

	public void onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu,
			MenuInflater inflater) {

		menu.add(0, 0, 0, "Settings")
				.setIcon(android.R.drawable.ic_menu_preferences)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_IF_ROOM
								| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		menu.add(0, 1, 0, "About")
				.setIcon(android.R.drawable.ic_menu_info_details)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_IF_ROOM
								| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		//
		//
		// menu.add(0, 1, 1, "Search")
		// .setIcon(R.drawable.ic_action_search)
		// .setActionView(R.layout.action_search)
		// .setShowAsAction(
		// MenuItem.SHOW_AS_ACTION_IF_ROOM
		// | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {

		switch (item.getItemId()) {
		case 0: // Settings

			SettingsDialog settingsDialog = new SettingsDialog(
					getSherlockActivity(), getActivity());
			settingsDialog.show();
			break;
		case 1: // About
			final Dialog aboutDialog = new Dialog(getActivity());
			aboutDialog.setContentView(R.layout.about_dialog);
			aboutDialog.setTitle("About");
			aboutDialog.show();

			android.widget.LinearLayout dismissLayout = (android.widget.LinearLayout) aboutDialog
					.findViewById(R.id.about_dialog_layout);
			dismissLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					aboutDialog.dismiss();
				}
			});
			break;

		default:
			// Nothing
			break;
		}
		return true;
	}

	public class MyCustomAdapter extends ArrayAdapter<String> {

		private DataObject thisObject;

		public MyCustomAdapter(Context context, int textViewResourceId,
				String[] objects) {
			super(context, textViewResourceId, objects);
			// TODO Auto-generated constructor stub

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.row, parent, false);

			TextView textviewTitle = (TextView) row.findViewById(R.id.label);
			textviewTitle.setText(Globals.moreInformationData.get(position)
					.getTitle());

			TextView textviewSubtitle = (TextView) row.findViewById(R.id.title);
			textviewSubtitle.setText(Globals.moreInformationData.get(position)
					.getSubtitle());

			return row;
		}
	}
}
