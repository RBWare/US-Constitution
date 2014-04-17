package com.darkdesign.constitution;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListItemFragment extends ListFragment {

	private static ListItemFragment instance;
    private ListView mListView;
    private int mTabIndex;

	public static int mCurrentSelection = 0;
	public static boolean mSettingsLoaded;

	private EditText search;

	public static ListItemFragment getInstance(int tabIndex) {
		if (instance == null) {
			return new ListItemFragment(tabIndex);
		}
		return instance;
	}

	public ListItemFragment(int tabIndex) {
		instance = this;
        mTabIndex = tabIndex;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.list_item_fragment, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

        mListView = (ListView)view.findViewById(R.id.list_fragment_listview);

        ArrayList<Integer> totalItems = new ArrayList<Integer>();
        for (int i = 0; i < Globals.data.size(); i++)
            if (Globals.data.get(i).getCategory() == mTabIndex)
                totalItems.add(i);
        mListView.setAdapter(new MyCustomAdapter(getActivity(), R.layout.row, totalItems));
        mListView.setOnItemClickListener(listClickListener);

	}

	private OnItemClickListener listClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long id) {

			Globals.currentListItemSelection = (int) id;
			Globals.currentListTypeSelection = Globals.LIST_TYPE_CONSTITUTION;

//			FragmentTransaction ft = getSupportFragmentManager()
//					.beginTransaction();
//			ft.replace(android.R.id.content, new ViewFragment());
//			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//			if ("view" != null) {
//				ft.addToBackStack("view");
//			}
//			ft.commit();
		}
	};

//	public void onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu,
//			MenuInflater inflater) {
//
//		menu.add(0, 0, 0, "Settings")
//				.setIcon(android.R.drawable.ic_menu_preferences)
//				.setShowAsAction(
//						MenuItem.SHOW_AS_ACTION_IF_ROOM
//								| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
//		menu.add(0, 1, 0, "About")
//				.setIcon(android.R.drawable.ic_menu_info_details)
//				.setShowAsAction(
//						MenuItem.SHOW_AS_ACTION_IF_ROOM
//								| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
//		//
//		//
//		// menu.add(0, 1, 1, "Search")
//		// .setIcon(R.drawable.ic_action_search)
//		// .setActionView(R.layout.action_search)
//		// .setShowAsAction(
//		// MenuItem.SHOW_AS_ACTION_IF_ROOM
//		// | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
//		super.onCreateOptionsMenu(menu, inflater);
//	}

//	@Override
//	public boolean onOptionsItemSelected(
//			com.actionbarsherlock.view.MenuItem item) {
//
//		switch (item.getItemId()) {
//		case 0: // Settings
//
//			SettingsDialog settingsDialog = new SettingsDialog(
//					getActivity(), getActivity());
//			settingsDialog.show();
//			break;
//		case 1: // About
//			final Dialog aboutDialog = new Dialog(getActivity());
//			aboutDialog.setContentView(R.layout.about_dialog);
//			aboutDialog.setTitle("About");
//			aboutDialog.show();
//
//			android.widget.LinearLayout dismissLayout = (android.widget.LinearLayout) aboutDialog
//					.findViewById(R.id.about_dialog_layout);
//			dismissLayout.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					aboutDialog.dismiss();
//				}
//			});
//			break;
//
//		default:
//			// Nothing
//			break;
//		}
//		return true;
//	}

	public class MyCustomAdapter extends ArrayAdapter<String> {

        private Context mContext;

		public MyCustomAdapter(Context context, int textViewResourceId, ArrayList objects) {
			super(context, textViewResourceId, objects);
            mContext = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.row, parent, false);

			TextView textviewTitle = (TextView) convertView.findViewById(R.id.label);

			String title = "";
			if (Globals.data.get(position).getTitle().contains(":")) {
				title = Globals.data
						.get(position)
						.getTitle()
						.substring(
								0,
								Globals.data.get(position)
										.getTitle().indexOf(":"));
			} else {
				title = Globals.data.get(position).getTitle();
			}

			textviewTitle.setText(title);

			TextView textviewSubtitle = (TextView) convertView.findViewById(R.id.title);
			textviewSubtitle.setText(Globals.data.get(position)
					.getSubtitle());

			return convertView;

		}
	}
}
