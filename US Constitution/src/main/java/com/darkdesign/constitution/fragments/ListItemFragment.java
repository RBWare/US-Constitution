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
import com.darkdesign.constitution.models.ListObject;
import com.darkdesign.constitution.R;
import com.darkdesign.constitution.sql.DatabaseHelper;

import java.util.ArrayList;

public class ListItemFragment extends ListFragment {

	private static ListItemFragment instance;
    private ListView mListView;

	public static int mCurrentSelection = 0;
	public static boolean mSettingsLoaded;
	private int mTabIndex;

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

		return inflater.inflate(R.layout.fragment_list, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

        mListView = (ListView)view.findViewById(R.id.list_fragment_listview);


        Bundle bundle = this.getArguments();
        if(bundle != null){
			mTabIndex = bundle.getInt("tabIndex", 0);
        }

		DatabaseHelper dbHelper = new DatabaseHelper(getActivity());

		ArrayList<ListObject> listItems  = dbHelper.getEntryListByCategoryId(mTabIndex);
        mListView.setAdapter(new ListEntryAdapter(getActivity(), R.layout.listview_row, listItems));
        mListView.setOnItemClickListener(listClickListener);
	}

	private OnItemClickListener listClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long id) {

            Intent i = new Intent(getActivity(), DetailsActivity.class);
            i.putExtra("itemId", (int)(v.getTag()));
            startActivity(i);
		}
	};

	public class ListEntryAdapter extends ArrayAdapter<String> {

        private Context mContext;
		private ArrayList<ListObject> mListObjects;

		public ListEntryAdapter(Context context, int textViewResourceId, ArrayList objects) {
			super(context, textViewResourceId, objects);
            mContext = context;
			mListObjects = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.listview_row, parent, false);

			TextView textviewTitle = (TextView) convertView.findViewById(R.id.textview_main_text);
			TextView textviewSubtitle = (TextView) convertView.findViewById(R.id.textview_sub_title);

			textviewTitle.setText(mListObjects.get(position).getTitle());
			textviewSubtitle.setText(mListObjects.get(position).getSubTitle());
			convertView.setTag(mListObjects.get(position).getId());

			return convertView;

		}
	}
}
