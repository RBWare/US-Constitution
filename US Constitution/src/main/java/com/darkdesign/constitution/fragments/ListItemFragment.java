package com.darkdesign.constitution.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.darkdesign.constitution.activities.DetailsActivity;
import com.darkdesign.constitution.utils.Globals;
import com.darkdesign.constitution.R;

import java.util.ArrayList;

public class ListItemFragment extends ListFragment {

	private static ListItemFragment instance;
    private ListView mListView;

	public static int mCurrentSelection = 0;
	public static boolean mSettingsLoaded;

	private EditText search;

	public static ListItemFragment getInstance(int tabIndex) {
		if (instance == null) {
			return new ListItemFragment();
		}
		return instance;
	}

    public ListItemFragment(){
        instance = this;
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.list_item_fragment, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

        mListView = (ListView)view.findViewById(R.id.list_fragment_listview);


        Bundle bundle = this.getArguments();
        int index = 0;
        if(bundle != null){
            index = bundle.getInt("tabIndex", 0);
        }

        ArrayList<Integer> totalItems = new ArrayList<Integer>();
        for (int i = 0; i < Globals.data.size(); i++)
            if (Globals.data.get(i).getCategory() == index)
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

            // TODO - Add to bundle, not some random global variable

            Intent i = new Intent(getActivity(), DetailsActivity.class);
            i.putExtra("itemTypeCount", adapter.getCount()); // TODO
            i.putExtra("itemPosition", position);
            startActivity(i);
		}
	};

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
