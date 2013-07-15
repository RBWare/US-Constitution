package com.darkdesign.constitution;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.WazaBe.HoloEverywhere.ThemeManager;
import com.WazaBe.HoloEverywhere.app.Activity;
import com.WazaBe.HoloEverywhere.app.Dialog;
import com.WazaBe.HoloEverywhere.widget.Switch;

public class SettingsDialog extends Dialog {

	private Button buttonSave;
	private Button buttonCancel;

	private Switch switchLightTheme;
	private Switch switchSplashScreen;

	private boolean lightTheme;
	private Activity _activity;

	public SettingsDialog(Activity activity, Context context) {
		super(context);
		_activity = activity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.settings_dialog);

		setTitle("Settings");

		switchLightTheme = (Switch) findViewById(R.id.settings_dialog_switch_theme);
		switchSplashScreen = (Switch) findViewById(R.id.settings_dialog_switch_splash);

		buttonSave = (Button) findViewById(R.id.settings_dialog_button_save);
		buttonCancel = (Button) findViewById(R.id.settings_dialog_button_cancel);

		if (Globals.currentTheme == Globals.THEME_DARK)
			switchLightTheme.setChecked(false);
		else
			switchLightTheme.setChecked(true);

		switchSplashScreen.setChecked(Globals.showSplash);

		buttonSave.setOnClickListener(new clickListener());
		buttonCancel.setOnClickListener(new clickListener());

		super.onCreate(savedInstanceState);
	}

	private class clickListener implements android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.settings_dialog_button_save:

				if (switchLightTheme.isChecked())
					lightTheme = true;
				else
					lightTheme = false;

				saveSettings();
				break;

			case R.id.settings_dialog_button_cancel:
			default:
				// Nothing
				break;
			}

			SettingsDialog.this.dismiss();
		}
	};

	private void saveSettings() {

		Globals.showSplash = switchSplashScreen.isChecked();
		if (lightTheme) {
			Globals.currentTheme = Globals.THEME_LIGHT;
			ThemeManager.restartWithTheme(_activity, ThemeManager.LIGHT);
		} else {
			Globals.currentTheme = Globals.THEME_DARK;
			ThemeManager.restartWithTheme(_activity, ThemeManager.DARK);
		}

		SharedPreferences prefs = getContext().getSharedPreferences(
				Globals.preferencesFile, 0);

		prefs.edit().putBoolean("splash", Globals.showSplash).commit();
		prefs.edit().putBoolean("lightTheme", lightTheme).commit();

		Log.d("Settings", "Saved");

		// TODO - Switch themes
	}
}