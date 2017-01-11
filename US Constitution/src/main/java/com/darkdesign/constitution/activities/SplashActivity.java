/* Original Author: Ryan Bailey
 * 
 * Licensed under the GPL License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.fsf.org/licensing/licenses/gpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.darkdesign.constitution.activities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.darkdesign.constitution.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

import com.darkdesign.constitution.sql.DatabaseHelper;


public class SplashActivity extends Activity {
	
	private final String LOG_TAG = "SplashActivity";

    private final int DEFAULT_SPLASH_DISPLAY_LENGTH = 2500;
    private int mSplashDisplayLength;

    private String mDatabasePath;
    private boolean mIsContentDisplayed = false;
    private DatabaseHelper mDbHelper;


    private static final String CSS_REPLACE_KEY = "%CSS";

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mDatabasePath = getDatabasePath(DatabaseHelper.DATABASE_NAME).getPath();


        if (databaseExists() && htmlExists()){
            Log.v(LOG_TAG, "No file setup needed");
            startSplashTimer();
        } else {
            setContentView(R.layout.splash);

            mDbHelper = new DatabaseHelper(this);
            mIsContentDisplayed = true;
            Log.v(LOG_TAG, "Some files not found, setup starting");
            new SetupAsyncTask(this).execute();
        }
    }
    
    private void startSplashTimer(){
        // If we show splash, default delay, if not, 0 for handler delay time in ms
        mSplashDisplayLength = isDisplaySplash() ? DEFAULT_SPLASH_DISPLAY_LENGTH : 0;

        if (mSplashDisplayLength != 0 && !mIsContentDisplayed)
            setContentView(R.layout.splash);

        /*
         * Handler to start the main activity and close this
         * splash screen after some seconds.
         */
        new Handler().postDelayed(new Runnable() {
            // @Override
            public void run() {

                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, ListActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, mSplashDisplayLength);
    }

    private boolean isDisplaySplash() {
        SharedPreferences prefs = getSharedPreferences(getString(R.string.settings_shared_prefs),
                Context.MODE_PRIVATE);

        return prefs.getBoolean(getString(R.string.settings_pref_splash_enabled), true);
    }



    private void setupDatabase() throws IOException {

        if(!databaseExists()) {
            try  {
                //Copy the database from assets
                copyDatabase();
                Log.i(LOG_TAG, "Database created");
            }
            catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private boolean databaseExists() {

        File dbFile = new File(mDatabasePath);

        if (!dbFile.getParentFile().exists())
            dbFile.getParentFile().mkdir();

        return dbFile.exists();
    }

    private boolean htmlExists() {
        File htmlFile = new File(getFilesDir().getPath() + "/www");
        if (htmlFile.exists()){
            if (htmlFile.listFiles().length > 0)
                return true;
        } else {
            htmlFile.mkdir();
            return false;
        }

        return false;
    }

    private void setupSharedPrefs(){
        SharedPreferences prefs = getSharedPreferences(getString(R.string.settings_shared_prefs), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(getString(R.string.settings_pref_splash_enabled), true).commit();
        editor.putBoolean(getString(R.string.settings_pref_theme_light), false).commit();

        Log.i(LOG_TAG, "SharedPrefs setup completed.");
    }

    private void copyDatabase() throws IOException {
        InputStream mInput = getApplicationContext().getAssets().open(DatabaseHelper.DATABASE_NAME);

        OutputStream mOutput = new FileOutputStream(mDatabasePath);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();

        Log.d("Splash", "Database copy completed.");
    }

    private void setupHtmlFiles(Context context){

        // keep one instance of the HTML string! save both files right away!

        // We will continually reuse these until we finish with this method
        String lightVariantCss = readAssetFile("style_light.css");
        String darkVariantCss = readAssetFile("style_dark.css");

        int entryCount = mDbHelper.getEntryCount();
        for (int i = 0; i < entryCount; i++){
            String htmlData = mDbHelper.getHTMLDataForEntry(i);

            String currentVariantHtml = htmlData.replace(CSS_REPLACE_KEY, lightVariantCss);
            String fileName = i + "_light.html";
            writeTextToFile(fileName, currentVariantHtml);

            currentVariantHtml = htmlData.replace(CSS_REPLACE_KEY, darkVariantCss);
            fileName = i + "_dark.html";
            writeTextToFile(fileName, currentVariantHtml);
        }

        Log.v(LOG_TAG, "HTML setup completed.");
    }

    private boolean writeTextToFile(String fileName, String fileText) {
        
        try {
            File root = new File(getFilesDir().getPath() + "/www/");
            
            if (!root.exists())
                root.mkdirs();

            File fileToWrite = new File(root, fileName);
            FileWriter writer = new FileWriter(fileToWrite);
        
            writer.append(fileText);
            writer.flush();
            writer.close();

            Log.v(LOG_TAG, "File written: " + fileName);
            return true;
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException creating HTML file");
            return false;
        }
    }

    private String readAssetFile(String fileName) {
        String fileData = "";

        try {
            InputStream stream = getAssets().open(fileName);

            int size = stream.available();
            byte[] buffer = new byte[size];
            
            stream.read(buffer);
            stream.close();
            fileData = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
        }

        return fileData;
    }

    private class SetupAsyncTask extends AsyncTask<Void, Integer, Void>{

        private Context mContext;
        private ProgressDialog mProgressDialog;

        public SetupAsyncTask(Context context){
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            setupSharedPrefs();
            try {
                setupDatabase();
            } catch(IOException e){
                Log.e(LOG_TAG, "Database setup failed!");
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            setupHtmlFiles(mContext);
//            mProgressDialog.dismiss();
            startSplashTimer();
        }
    }
}