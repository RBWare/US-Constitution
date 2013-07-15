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

package com.darkdesign.constitution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.darkdesign.constitution.classes.DataObject;
import com.darkdesign.constitution.classes.Section;

public class Splash extends Activity {

	private static int SPLASH_DISPLAY_LENGTH = 2500;

	// ===========================================================
	// "Constructors"
	// ===========================================================

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		retreiveSettings();
		setupApplicationData();

		// loadSettings();
		if (Globals.showSplash == false) {
			Intent i = new Intent(Splash.this, Main.class);
			startActivity(i);
			Splash.this.finish();
			// SPLASH_DISPLAY_LENGTH = 1;
		} else {
			SPLASH_DISPLAY_LENGTH = 2500;

			setContentView(R.layout.splash);

			/*
			 * New Handler to start the Menu-Activity and close this
			 * Splash-Screen after some seconds.
			 */
			new Handler().postDelayed(new Runnable() {
				// @Override
				public void run() {

					/* Create an Intent that will start the Menu-Activity. */
					Intent mainIntent = new Intent(Splash.this, Main.class);
					Splash.this.startActivity(mainIntent);
					Splash.this.finish();
				}
			}, SPLASH_DISPLAY_LENGTH);
		}
	}

	private void setupApplicationData() {
		SAXBuilder builder = new SAXBuilder();
		Document document = null;

		// Constitution Data First

		try {
			Globals.constitutionData.clear();

			String[] files = getAssets().list("us_constitution");

			for (int i = 0; i < files.length; i++) {
				InputStream raw = getAssets().open(
						"us_constitution/" + files[i]);

				Reader is = new BufferedReader(new InputStreamReader(raw,
						"UTF-8"));
				document = builder.build(is);

				Element rootNode = document.getRootElement();

				if (!rootNode.getChildren().isEmpty()) {
					DataObject newData = new DataObject();

					if (rootNode.getChild("title") != null)
						newData.setTitle(rootNode.getChild("title").getText());

					if (rootNode.getChild("subtitle") != null)
						newData.setSubtitle(rootNode.getChild("subtitle")
								.getText());

					if (rootNode.getChild("extraInfo") != null)
						newData.setAdditionalInformation(rootNode.getChild(
								"extraInfo").getText());

					if (rootNode.getChild("notes") != null)
						newData.setNotes(rootNode.getChild("notes").getText());

					if (rootNode.getChild("mainInfo") != null) {
						List<Element> sectionNodes = rootNode.getChild(
								"mainInfo").getChildren("section");

						if (!sectionNodes.isEmpty()) {
							for (int x = 0; x < sectionNodes.size(); x++) {
								Section newSection = new Section();
								if (sectionNodes.get(x).getChild("title") != null)
									newSection.setTitle(sectionNodes.get(x)
											.getChild("title").getText());

								if (sectionNodes.get(x).getChild("text") != null)
									newSection.setText(sectionNodes.get(x)
											.getChild("text").getText());

								if (sectionNodes.get(x).getChild("notes") != null)
									newSection.setNotes(sectionNodes.get(x)
											.getChild("notes").getText());

								if (sectionNodes.get(x).getChild("extraInfo") != null)
									newSection
											.setAdditionalInformation(sectionNodes
													.get(x)
													.getChild("extraInfo")
													.getText());

								newData.getSection().add(newSection);
							}
						}
					}

					if (rootNode.getChild("google") != null)
						newData.setGoogleUrl(rootNode.getChild("google")
								.getText());

					if (rootNode.getChild("wikipedia") != null)
						newData.setWikipediaUrl(rootNode.getChild("wikipedia")
								.getText());

					if (rootNode.getChild("official") != null)
						newData.setOfficialUrl(rootNode.getChild("official")
								.getText());

					Globals.constitutionData.add(newData);
				}
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// More Information Data
		try {
			Globals.moreInformationData.clear();

			String[] files = getAssets().list("more_information");

			for (int i = 0; i < files.length; i++) {
				InputStream raw = getAssets().open(
						"more_information/" + files[i]);

				Reader is = new BufferedReader(new InputStreamReader(raw,
						"UTF-8"));
				document = builder.build(is);

				Element rootNode = document.getRootElement();

				if (!rootNode.getChildren().isEmpty()) {
					DataObject newData = new DataObject();

					if (rootNode.getChild("title") != null)
						newData.setTitle(rootNode.getChild("title").getText());

					if (rootNode.getChild("subtitle") != null)
						newData.setSubtitle(rootNode.getChild("subtitle")
								.getText());

					if (rootNode.getChild("extraInfo") != null)
						newData.setAdditionalInformation(rootNode.getChild(
								"extraInfo").getText());

					if (rootNode.getChild("notes") != null)
						newData.setNotes(rootNode.getChild("notes").getText());

					if (rootNode.getChild("mainInfo") != null) {
						List<Element> sectionNodes = rootNode.getChild(
								"mainInfo").getChildren("section");

						if (!sectionNodes.isEmpty()) {
							for (int x = 0; x < sectionNodes.size(); x++) {
								Section newSection = new Section();
								if (sectionNodes.get(x).getChild("title") != null)
									newSection.setTitle(sectionNodes.get(x)
											.getChild("title").getText());

								if (sectionNodes.get(x).getChild("text") != null)
									newSection.setText(sectionNodes.get(x)
											.getChild("text").getText());

								if (sectionNodes.get(x).getChild("notes") != null)
									newSection.setNotes(sectionNodes.get(x)
											.getChild("notes").getText());

								if (sectionNodes.get(x).getChild("extraInfo") != null)
									newSection
											.setAdditionalInformation(sectionNodes
													.get(x)
													.getChild("extraInfo")
													.getText());

								newData.getSection().add(newSection);
							}
						}
					}

					if (rootNode.getChild("google") != null)
						newData.setGoogleUrl(rootNode.getChild("google")
								.getText());

					if (rootNode.getChild("wikipedia") != null)
						newData.setWikipediaUrl(rootNode.getChild("wikipedia")
								.getText());

					if (rootNode.getChild("official") != null)
						newData.setOfficialUrl(rootNode.getChild("official")
								.getText());

					Globals.moreInformationData.add(newData);
				}
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Similar Documents Data
		try {
			Globals.similarDocumentsData.clear();

			String[] files = getAssets().list("similar_documents");

			for (int i = 0; i < files.length; i++) {
				InputStream raw = getAssets().open(
						"similar_documents/" + files[i]);

				Reader is = new BufferedReader(new InputStreamReader(raw,
						"UTF-8"));
				document = builder.build(is);

				Element rootNode = document.getRootElement();

				if (!rootNode.getChildren().isEmpty()) {
					DataObject newData = new DataObject();

					if (rootNode.getChild("title") != null)
						newData.setTitle(rootNode.getChild("title").getText());

					if (rootNode.getChild("subtitle") != null)
						newData.setSubtitle(rootNode.getChild("subtitle")
								.getText());

					if (rootNode.getChild("extraInfo") != null)
						newData.setAdditionalInformation(rootNode.getChild(
								"extraInfo").getText());

					if (rootNode.getChild("notes") != null)
						newData.setNotes(rootNode.getChild("notes").getText());

					if (rootNode.getChild("mainInfo") != null) {
						List<Element> sectionNodes = rootNode.getChild(
								"mainInfo").getChildren("section");

						if (!sectionNodes.isEmpty()) {
							for (int x = 0; x < sectionNodes.size(); x++) {
								Section newSection = new Section();
								if (sectionNodes.get(x).getChild("title") != null)
									newSection.setTitle(sectionNodes.get(x)
											.getChild("title").getText());

								if (sectionNodes.get(x).getChild("text") != null)
									newSection.setText(sectionNodes.get(x)
											.getChild("text").getText());

								if (sectionNodes.get(x).getChild("notes") != null)
									newSection.setNotes(sectionNodes.get(x)
											.getChild("notes").getText());

								if (sectionNodes.get(x).getChild("extraInfo") != null)
									newSection
											.setAdditionalInformation(sectionNodes
													.get(x)
													.getChild("extraInfo")
													.getText());

								newData.getSection().add(newSection);
							}
						}
					}

					if (rootNode.getChild("google") != null)
						newData.setGoogleUrl(rootNode.getChild("google")
								.getText());

					if (rootNode.getChild("wikipedia") != null)
						newData.setWikipediaUrl(rootNode.getChild("wikipedia")
								.getText());

					if (rootNode.getChild("official") != null)
						newData.setOfficialUrl(rootNode.getChild("official")
								.getText());

					Globals.similarDocumentsData.add(newData);
				}
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
}