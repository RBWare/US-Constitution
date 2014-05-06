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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.darkdesign.constitution.utils.Globals;
import com.darkdesign.constitution.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

import com.darkdesign.constitution.utils.sql.DatabaseHelper;

public class SplashActivity extends Activity {

    private static String DB_PATH = "/data/data/com.darkdesign.constitution/databases/";
    private static String DB_NAME ="constitution.db";// Database name

    private static int SPLASH_DISPLAY_LENGTH = 2500;

    // ===========================================================
    // "Constructors"
    // ===========================================================

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        try{
            createDataBase();
        } catch(IOException e){
            Log.e(getLocalClassName(), e.toString());
        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.setupSectionData();

        if (Globals.showSplash == false) {
            Intent i = new Intent(this, ListActivity.class);
            startActivity(i);
            SplashActivity.this.finish();
        } else {
            SPLASH_DISPLAY_LENGTH = 2500;

            setContentView(R.layout.splash);

			/*
			 * New Handler to start the Menu-Activity and close this
			 * SplashActivity-Screen after some seconds.
			 */
            new Handler().postDelayed(new Runnable() {
                // @Override
                public void run() {

					/* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(SplashActivity.this, ListActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
    }

    private void retreiveSettings() {
        SharedPreferences prefs = getSharedPreferences(Globals.preferencesFile,
                0);

        Globals.showSplash = prefs.getBoolean("splash", true);

        boolean temp = prefs.getBoolean("lightTheme", false);
        if (temp)
            Globals.currentTheme = Globals.THEME_LIGHT;
        else
            Globals.currentTheme = Globals.THEME_DARK;
    }

    public void createDataBase() throws IOException
    {
        //If database not exists copy it from the assets

        boolean mDataBaseExist = checkDataBase();
        if(!mDataBaseExist)
        {
            try
            {
                //Copy the database from assests
                copyDataBase();
                Log.e("App", "createDatabase database created");
            }
            catch (IOException mIOException)
            {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private boolean checkDataBase()
    {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() throws IOException {
        InputStream mInput = getApplicationContext().getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;

        File outputDirectory = new File(DB_PATH);
        if (!outputDirectory.exists())
            outputDirectory.mkdirs();

        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }
}