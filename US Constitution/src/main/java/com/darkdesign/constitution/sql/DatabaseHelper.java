package com.darkdesign.constitution.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.darkdesign.constitution.models.DataObject;
import com.darkdesign.constitution.models.ListObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

//    private static String DATABASE_PATH = "/data/data/com.darkdesign.constitution/databases/";
    public static final String DATABASE_NAME ="constitution.db";// Database name

    private static final String DB_MAIN_ROW_ID = "id";
    private static final String DB_MAIN_ROW_CATEGORY = "category";
    private static final String DB_MAIN_ROW_TITLE = "title";
    private static final String DB_MAIN_ROW_SUBTITLE = "subtitle";
    private static final String DB_MAIN_ROW_WIKI_URL = "wiki_url";
    private static final String DB_MAIN_ROW_GOOGLE_URL = "google_url";
    private static final String DB_MAIN_ROW_OFFICIAL_URL = "official_url";
    private static final String DB_MAIN_ROW_HTML = "html_text";
    private static final String DB_MAIN_ROW_RAW = "raw_text";

    private static final String DB_CATEGORIES_ID = "id";
    private static final String DB_CATEGORIES_NAME = "name";

    private static final String QUERY_STRING_ALL_DATA = "SELECT * FROM main";
    private static final String QUERY_STRING_SEARCH_RESULTS = "SELECT * FROM main WHERE CONTAINS((raw_text, tags), '%s')";
    private static final String QUERY_STRING_ENTRY_LIST = "SELECT * FROM main WHERE category='%s'";
    private static final String QUERY_STRING_ITEM_ID = "SELECT * FROM main WHERE id='%s'";
    private static final String QUERY_STRING_ITEM_HTML_DATA = "SELECT html_text FROM main WHERE id='%s'";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) { }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "HERRO");
        onCreate(db);
    }

    public int getEntryCount() throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY_STRING_ALL_DATA, null);
        int totalEntries = cursor.getCount();
        cursor.close();
        return totalEntries;
    }

    public ArrayList<ListObject> getSearchRequestList(String query) throws SQLException {

        ArrayList<ListObject> searchResultsList = new ArrayList<>();

        String dbQueryString = QUERY_STRING_SEARCH_RESULTS.replace("%s", query);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(dbQueryString, null);
        if (cursor.moveToFirst()) {
            do {
                ListObject dbSearchObject = new ListObject();
                dbSearchObject.setId(cursor.getInt(cursor.getColumnIndex(DB_MAIN_ROW_ID)));
                dbSearchObject.setCategoryId(cursor.getInt(cursor.getColumnIndex(DB_MAIN_ROW_CATEGORY)));
                dbSearchObject.setTitle(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_TITLE)));
                dbSearchObject.setSubTitle(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_SUBTITLE)));
                searchResultsList.add(dbSearchObject);
            } while (cursor.moveToNext());
        }
        db.close();
        return searchResultsList;

    }

    public ArrayList<String> getCategories() throws SQLException{

        ArrayList<String> categoryList = new ArrayList<>();

        String selectQuery = "SELECT * FROM categories";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String[] data = null;
        if (cursor.moveToFirst()) {
            do {
                categoryList.add(cursor.getString(cursor.getColumnIndex(DB_CATEGORIES_NAME)));
            } while (cursor.moveToNext());
        }
        db.close();

        return categoryList;
    }

    public String getEntryHtmlData(int rowNumber){
        String htmlData = "";

        String dbQueryString = QUERY_STRING_ITEM_ID.replace("%s", String.valueOf(rowNumber));

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(dbQueryString, null);
        if (cursor.moveToFirst()) {
            do {
                htmlData = cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_HTML));
                break;
            } while (cursor.moveToNext());
        }
        db.close();
        return htmlData;
    }

    public ArrayList<String> getAllEntryHtmlData(){

        ArrayList<String> htmlData = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY_STRING_ALL_DATA, null);
        if (cursor.moveToFirst()) {
            do {
                htmlData.add(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_HTML)));
            } while (cursor.moveToNext());
        }
        db.close();
        return htmlData;
    }

    public ArrayList<ListObject> getEntryListByCategoryId(int categoryId){

        ArrayList<ListObject> entryList = new ArrayList<>();

        String dbQueryString = QUERY_STRING_ENTRY_LIST.replace("%s", String.valueOf(categoryId));

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(dbQueryString, null);
        if (cursor.moveToFirst()) {
            do {
                ListObject dbSearchObject = new ListObject();
                dbSearchObject.setId(cursor.getInt(cursor.getColumnIndex(DB_MAIN_ROW_ID)));
                dbSearchObject.setCategoryId(cursor.getInt(cursor.getColumnIndex(DB_MAIN_ROW_CATEGORY)));
                dbSearchObject.setTitle(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_TITLE)));
                dbSearchObject.setSubTitle(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_SUBTITLE)));
                entryList.add(dbSearchObject);
            } while (cursor.moveToNext());
        }
        db.close();
        return entryList;
    }

    public ListObject getListEntryById(int itemId){

        ListObject dbSearchObject = new ListObject();
        String dbQueryString = QUERY_STRING_ITEM_ID.replace("%s", String.valueOf(itemId));

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(dbQueryString, null);
        if (cursor.moveToFirst()) {
            do {
                dbSearchObject.setId(cursor.getInt(cursor.getColumnIndex(DB_MAIN_ROW_ID)));
                dbSearchObject.setTitle(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_TITLE)));
                dbSearchObject.setSubTitle(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_SUBTITLE)));
                dbSearchObject.setCategoryId(cursor.getInt(cursor.getColumnIndex(DB_MAIN_ROW_CATEGORY)));
                break;
            } while (cursor.moveToNext());
        }
        db.close();
        return dbSearchObject;
    }

    public ArrayList<DataObject> getFullEntriesInCategoryByItemId(int itemId){

        ListObject itemToSearchBy = getListEntryById(itemId);

        ArrayList<DataObject> entryList = new ArrayList<>();

        String dbQueryString = QUERY_STRING_ENTRY_LIST.replace("%s", String.valueOf(itemToSearchBy.getCategoryId()));

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(dbQueryString, null);
        if (cursor.moveToFirst()) {
            do {
                DataObject dbSearchObject = new DataObject();
                dbSearchObject.setId(cursor.getInt(cursor.getColumnIndex(DB_MAIN_ROW_ID)));
                dbSearchObject.setCategory(cursor.getInt(cursor.getColumnIndex(DB_MAIN_ROW_CATEGORY)));
                dbSearchObject.setTitle(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_TITLE)));
                dbSearchObject.setSubtitle(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_SUBTITLE)));
                dbSearchObject.setGoogleUrl(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_GOOGLE_URL)));
                dbSearchObject.setWikipediaUrl(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_WIKI_URL)));
                dbSearchObject.setOfficialUrl(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_OFFICIAL_URL)));
                entryList.add(dbSearchObject);
            } while (cursor.moveToNext());
        }
        db.close();
        return entryList;
    }

    public DataObject getFullEntryInCategoryByItemId(int itemId){

        DataObject dataObject = new DataObject();
        String dbQueryString = QUERY_STRING_ITEM_ID.replace("%s", String.valueOf(itemId));

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(dbQueryString, null);
        if (cursor.moveToFirst()) {
            do {
                DataObject dbSearchObject = new DataObject();
                dbSearchObject.setId(cursor.getInt(cursor.getColumnIndex(DB_MAIN_ROW_ID)));
                dbSearchObject.setCategory(cursor.getInt(cursor.getColumnIndex(DB_MAIN_ROW_CATEGORY)));
                dbSearchObject.setTitle(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_TITLE)));
                dbSearchObject.setSubtitle(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_SUBTITLE)));
                dbSearchObject.setGoogleUrl(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_GOOGLE_URL)));
                dbSearchObject.setWikipediaUrl(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_WIKI_URL)));
                dbSearchObject.setOfficialUrl(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_OFFICIAL_URL)));
                dataObject = dbSearchObject;
            } while (cursor.moveToNext());
        }
        db.close();
        return dataObject;
    }

    public String getHTMLDataForEntry(int itemId){

        String dbQueryString = QUERY_STRING_ITEM_HTML_DATA.replace("%s", String.valueOf(itemId));
        String htmlText = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(dbQueryString, null);
        if (cursor.moveToFirst()) {
            do {

                htmlText = cursor.getString(cursor.getPosition());

            } while (cursor.moveToNext());
        }
        db.close();
        return htmlText;
    }
}