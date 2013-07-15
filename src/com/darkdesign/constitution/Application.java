package com.darkdesign.constitution;

import com.WazaBe.HoloEverywhere.LayoutInflater;
import com.WazaBe.HoloEverywhere.Settings;
import com.WazaBe.HoloEverywhere.ThemeManager;

public class Application extends com.WazaBe.HoloEverywhere.app.Application {
	public Application() {
		LayoutInflater.putToMap("com.darkdesign.constitution.widget",
				"WidgetContainer", "OtherButton");
		Settings.setUseThemeManager(true);
		ThemeManager.THEME_DEFAULT |= ThemeManager.DARK;
	}
}
