package com.darkdesign.constitution.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import com.darkdesign.constitution.utils.Globals;
import com.darkdesign.constitution.R;

public class SettingsDialog extends AlertDialog {

	private Button buttonSave;
	private Button buttonCancel;

	private CheckBox switchLightTheme;
	private CheckBox switchSplashScreen;

	private boolean lightTheme;

	public SettingsDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_dialog);

		setTitle("Settings");

		switchLightTheme = (CheckBox) findViewById(R.id.settings_dialog_switch_theme);
		switchSplashScreen = (CheckBox) findViewById(R.id.settings_dialog_switch_splash);

		buttonSave = (Button) findViewById(R.id.settings_dialog_button_save);
		buttonCancel = (Button) findViewById(R.id.settings_dialog_button_cancel);

        setButton(Dialog.BUTTON_POSITIVE, "Save", new clickListener());
        setButton(Dialog.BUTTON_NEGATIVE, "Cancel", new clickListener());

//		if (Globals.currentTheme == Globals.THEME_DARK)
//			switchLightTheme.setChecked(false);
//		else
//			switchLightTheme.setChecked(true);
//
//		switchSplashScreen.setChecked(Globals.showSplash);


	}

    private class clickListener implements OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which){
            switch (which){
                case Dialog.BUTTON_POSITIVE:
                    saveSettings();
                    break;
                case Dialog.BUTTON_NEGATIVE:
                    // TODO - Cancel
                    break;
                default:
                    // Nothing
                    break;
            }
            SettingsDialog.this.dismiss(); // TODO - this isn't working
        }
    };
//	private class clickListener implements OnClickListener {

//		@Override
//		public void onClick(View v) {
//			switch (v.getId()) {
//
//			case R.id.settings_dialog_button_save:
//
//				if (switchLightTheme.isChecked())
//					lightTheme = true;
//				else
//					lightTheme = false;
//
//				saveSettings();
//				break;
//
//			case R.id.settings_dialog_button_cancel:
//			default:
//				// Nothing
//				break;
//			}
//
//			SettingsDialog.this.dismiss();
//		}
//	};

	private void saveSettings() {

		Globals.showSplash = switchSplashScreen.isChecked();
//		if (lightTheme) {
//			Globals.currentTheme = Globals.THEME_LIGHT;
//			ThemeManager.restartWithTheme(_activity, ThemeManager.LIGHT);
//		} else {
//			Globals.currentTheme = Globals.THEME_DARK;
//			ThemeManager.restartWithTheme(_activity, ThemeManager.DARK);
////		}

		SharedPreferences prefs = getContext().getSharedPreferences(
				Globals.preferencesFile, 0);

		prefs.edit().putBoolean("splash", Globals.showSplash).commit();
		prefs.edit().putBoolean("lightTheme", lightTheme).commit();

		Log.d("Settings", "Saved");

		// TODO - Switch themes
	}
}