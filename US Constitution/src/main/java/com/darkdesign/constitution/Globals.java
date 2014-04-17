package com.darkdesign.constitution;

import java.util.ArrayList;

import com.darkdesign.constitution.models.DataObject;

public class Globals {

	public static final String preferencesFile = "settings";

	public static boolean showSplash = true;
	public static boolean showsNavigationTabs;

	public static int currentListItemSelection;
	public static int currentListTypeSelection;
	public static int currentTheme;

	public static final int THEME_DARK = 0;
	public static final int THEME_LIGHT = 1;

	public static final int LIST_TYPE_CONSTITUTION = 0;
	public static final int LIST_TYPE_MORE_INFO = 1;
	public static final int LIST_TYPE_SIMILAR_DOCUMENTS = 2;

	public static ArrayList<DataObject> data = new ArrayList<DataObject>();
}