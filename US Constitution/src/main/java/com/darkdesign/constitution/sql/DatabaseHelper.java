package com.darkdesign.constitution.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.darkdesign.constitution.Globals;
import com.darkdesign.constitution.models.DataObject;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "constitution.db";

    private static final String DB_MAIN_ROW_ID = "id";
    private static final String DB_MAIN_ROW_CATEGORY = "category";
    private static final String DB_MAIN_ROW_TITLE = "title";
    private static final String DB_MAIN_ROW_SUBTITLE = "subtitle";
    private static final String DB_MAIN_ROW_EXTRA_INFO = "extra_info";
    private static final String DB_MAIN_ROW_NOTES = "notes";
    private static final String DB_MAIN_ROW_WIKI_URL = "wiki_url";
    private static final String DB_MAIN_ROW_GOOGLE_URL = "google_url";
    private static final String DB_MAIN_ROW_OFFICIAL_URL = "official_url";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        // TODO Auto-generated constructor stub
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + "HERRO");
        onCreate(db);
    }

    public String geData() {
        // TODO Auto-generated method stub
//        String[]columns=new String[]{ KEY_ROWID,KEY_NAME, KEY_HOTNESS};
//        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
//        String result ="";
//        int iRow=c.getColumnIndex(KEY_ROWID);
//        int iName=c.getColumnIndex(KEY_NAME);
//        int iHotness=c.getColumnIndex(KEY_HOTNESS);

//        for(c.moveToFirst(); !c.isAfterLast();c.moveToNext()){
//            result=result + c.getString(iRow)+" "+c.getString(iName)+" "+c.getString(iHotness)+"\n";
//        }
        return "";
    }

    public boolean setupSectionData() throws SQLException{

        String selectQuery = "SELECT * FROM main";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String[] data = null;
        if (cursor.moveToFirst()) {
            do {
                DataObject dataEntry = new DataObject();
                dataEntry.setCategory(
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_CATEGORY))));
                dataEntry.setTitle(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_TITLE)));
                dataEntry.setSubtitle(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_SUBTITLE)));
                dataEntry.setAdditionalInformation(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_EXTRA_INFO)));
                dataEntry.setNotes(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_NOTES)));
                dataEntry.setWikipediaUrl(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_WIKI_URL)));
                dataEntry.setGoogleUrl(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_GOOGLE_URL)));
                dataEntry.setOfficialUrl(cursor.getString(cursor.getColumnIndex(DB_MAIN_ROW_OFFICIAL_URL)));

                Globals.data.add(dataEntry);
                // get  the  data into array,or class variable
            } while (cursor.moveToNext());
        }
        db.close();

        return true;
    }
}