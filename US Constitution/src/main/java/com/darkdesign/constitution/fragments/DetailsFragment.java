package com.darkdesign.constitution.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.darkdesign.constitution.R;
import com.darkdesign.constitution.models.DataObject;

public class DetailsFragment extends Fragment {

	private static DetailsFragment instance;

	public static DetailsFragment getInstance() {
		if (instance == null) {
			return new DetailsFragment();
		}
		return instance;
	}

	public DetailsFragment() {
		instance = this;
	}

//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//
//		setHasOptionsMenu(true);
//
////		if (isABSSupport()) {
////			ListActivity.getThisActionBar().setDisplayHomeAsUpEnabled(true);
////		}
//
//		return inflater.inflate(R.layout.view_information);
//	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

	}

	@Override
	public void onResume() {
		generateLayout();
		super.onResume();
	}

//	@Override
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//
////		if (ThemeManager.getTheme(getSherlockActivity()) == ThemeManager.DARK) {
////
////			menu.add(0, 0, 0, "Official Page")
////					.setIcon(R.drawable.actionitem_dark_browser)
////					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
////			menu.add(0, 1, 0, "Google Search")
////					.setIcon(R.drawable.actionitem_dark_google)
////					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
////			menu.add(0, 2, 0, "Wikipedia Page")
////					.setIcon(R.drawable.actionitem_dark_wikipedia)
////					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
////
////		} else {
////			menu.add(0, 0, 0, "Official Page")
////					.setIcon(R.drawable.actionitem_light_browser)
////					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
////			menu.add(0, 1, 0, "Google Search")
////					.setIcon(R.drawable.actionitem_light_google)
////					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
////			menu.add(0, 2, 0, "Wikipedia Page")
////					.setIcon(R.drawable.actionitem_light_wikipedia)
////					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
////		}
//		super.onCreateOptionsMenu(menu, inflater);
//	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//
//		Log.e("Item", "Id: " + item.getItemId());
//
//		if (item.getItemId() == android.R.id.home) {
//			if (getFragmentManager().getBackStackEntryCount() != 0)
//				getActivity().onBackPressed();
//		} else {
//
//			Intent i = new Intent(Intent.ACTION_VIEW);
//			switch (Globals.currentListTypeSelection) {
//			case Globals.LIST_TYPE_CONSTITUTION:
//				switch (item.getItemId()) {
//				case R.id.menu_options_official_website: // Browser
//				case 0:
//					i.setData(Uri.parse(Globals.constitutionData.get(
//							Globals.currentListItemSelection).getOfficialUrl()));
//
//					break;
//				case R.id.menu_options_google: // Google Search
//				case 1:
//					i.setData(Uri.parse(Globals.constitutionData.get(
//							Globals.currentListItemSelection).getGoogleUrl()));
//					break;
//				case R.id.menu_options_wikipedia: // Wikipedia
//				case 2:
//					i.setData(Uri
//							.parse(Globals.constitutionData.get(
//									Globals.currentListItemSelection)
//									.getWikipediaUrl()));
//					break;
//				default:
//					// Nothing
//					break;
//				}
//				break;
//			case Globals.LIST_TYPE_MORE_INFO:
//				switch (item.getItemId()) {
//				case R.id.menu_options_official_website: // Browser
//				case 0:
//					i.setData(Uri.parse(Globals.moreInformationData.get(
//							Globals.currentListItemSelection).getOfficialUrl()));
//
//					break;
//				case R.id.menu_options_google: // Google Search
//				case 1:
//					i.setData(Uri.parse(Globals.moreInformationData.get(
//							Globals.currentListItemSelection).getGoogleUrl()));
//					break;
//				case R.id.menu_options_wikipedia: // Wikipedia
//				case 2:
//					i.setData(Uri
//							.parse(Globals.moreInformationData.get(
//									Globals.currentListItemSelection)
//									.getWikipediaUrl()));
//					break;
//				default:
//					// Nothing
//					break;
//				}
//				break;
//			case Globals.LIST_TYPE_SIMILAR_DOCUMENTS:
//				switch (item.getItemId()) {
//				case R.id.menu_options_official_website: // Browser
//				case 0:
//					i.setData(Uri.parse(Globals.similarDocumentsData.get(
//							Globals.currentListItemSelection).getOfficialUrl()));
//
//					break;
//				case R.id.menu_options_google: // Google Search
//				case 1:
//					i.setData(Uri.parse(Globals.similarDocumentsData.get(
//							Globals.currentListItemSelection).getGoogleUrl()));
//					break;
//				case R.id.menu_options_wikipedia: // Wikipedia
//				case 2:
//					i.setData(Uri
//							.parse(Globals.similarDocumentsData.get(
//									Globals.currentListItemSelection)
//									.getWikipediaUrl()));
//					break;
//				default:
//					// Nothing
//					break;
//				}
//				break;
//			default:
//				// Nothing
//				break;
//			}
//
//			startActivity(i);
//		}
//
//		return super.onOptionsItemSelected(item);
//	}

	// This will create the entire layout for all given
	// articles/ammendments/etc.
	private void generateLayout() {

		DataObject thisObject = new DataObject();
		LinearLayout informationLayout = new LinearLayout(getActivity());
		informationLayout.setOrientation(LinearLayout.VERTICAL);

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		layoutParams.setMargins(4, 4, 4, 4);
		informationLayout.setLayoutParams(layoutParams);

//		switch (Globals.currentListTypeSelection) {
//		case Globals.LIST_TYPE_CONSTITUTION:
//			thisObject = Globals.constitutionData
//					.get(Globals.currentListItemSelection);
//			break;
//		case Globals.LIST_TYPE_MORE_INFO:
//			thisObject = Globals.moreInformationData
//					.get(Globals.currentListItemSelection);
//			break;
//		case Globals.LIST_TYPE_SIMILAR_DOCUMENTS:
//			thisObject = Globals.similarDocumentsData
//					.get(Globals.currentListItemSelection);
//			break;
//		default:
//			// Nothing
//			break;
//		}

		// Setup Layout Parameters
		LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams notesLayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams extraLayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		titleLayoutParams.setMargins(8, 14, 8, 4);
		notesLayoutParams.setMargins(8, 4, 8, 4);
		extraLayoutParams.setMargins(8, 4, 8, 4);

		// TODO - Finish setting these up and adding them to the linear layout
		TextView textviewTitle = new TextView(getActivity());
		textviewTitle.setText(thisObject.getTitle());
		textviewTitle.setTextSize(22);
		textviewTitle.setLayoutParams(titleLayoutParams);
		informationLayout.addView(textviewTitle);

		// Does this exist?
		if (!"".equals(thisObject.getAdditionalInformation())) {
			TextView textviewExtraInfo = new TextView(getActivity());
			textviewExtraInfo.setText(thisObject.getAdditionalInformation());
			textviewExtraInfo.setTextSize(14);
			textviewExtraInfo.setLayoutParams(extraLayoutParams);
			informationLayout.addView(textviewExtraInfo);
		}

		// Does this exist?
		if (!"".equals(thisObject.getNotes())) {
			TextView textviewNotes = new TextView(getActivity());
			textviewNotes.setText(thisObject.getNotes());
			textviewNotes.setTextSize(12);
			textviewNotes.setTypeface(null, Typeface.ITALIC);
			textviewNotes.setLayoutParams(notesLayoutParams);
			informationLayout.addView(textviewNotes);
		}

		if (!thisObject.getSection().isEmpty()) {
			for (int i = 0; i < thisObject.getSection().size(); i++) {

				// Title
				if (!"".equals(thisObject.getSection().get(i).getTitle())) {
					TextView textviewSectionTitle = new TextView(getActivity());
					textviewSectionTitle.setText(thisObject.getSection().get(i)
							.getTitle());
					textviewSectionTitle.setTextSize(18);
					textviewSectionTitle.setLayoutParams(titleLayoutParams);
					informationLayout.addView(textviewSectionTitle);
				}

				// Extra Info
				if (!"".equals(thisObject.getSection().get(i)
						.getAdditionalInformation())) {
					TextView textviewSectionInfo = new TextView(getActivity());
					textviewSectionInfo.setText(thisObject.getSection().get(i)
							.getAdditionalInformation());
					textviewSectionInfo.setTextSize(14);
					textviewSectionInfo.setLayoutParams(extraLayoutParams);
					informationLayout.addView(textviewSectionInfo);
				}

				// Notes
				if (!"".equals(thisObject.getSection().get(i).getNotes())) {
					TextView textviewSectionNotes = new TextView(getActivity());
					textviewSectionNotes.setText(thisObject.getSection().get(i)
							.getNotes());
					textviewSectionNotes.setTextSize(12);
					textviewSectionNotes.setTypeface(null, Typeface.ITALIC);
					textviewSectionNotes.setLayoutParams(notesLayoutParams);
					informationLayout.addView(textviewSectionNotes);
				}

				// Body Text
				TextView textviewSectionText = new TextView(getActivity());
				textviewSectionText.setText(thisObject.getSection().get(i)
						.getText());
				textviewSectionText.setTextSize(14);
				textviewSectionText.setLayoutParams(notesLayoutParams);
				informationLayout.addView(textviewSectionText);
			}
		}

		ScrollView mainLayout = (ScrollView) getView().findViewById(
				R.id.information);
		mainLayout.removeAllViews();
		mainLayout.addView(informationLayout);
		// setContentView(mActivityScrollView);
	}
}
