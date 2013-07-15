package com.darkdesign.constitution.classes;

import java.util.ArrayList;

public class DataObject {

	private String title;
	private String subtitle;
	private String additionalInformation;
	private String notes;
	private String googleUrl;
	private String wikipediaUrl;
	private String officialUrl;
	private ArrayList<Section> section;

	public DataObject() {
		setTitle("");
		setSubtitle("");
		setAdditionalInformation("");
		setNotes("");
		setGoogleUrl("");
		setWikipediaUrl("");
		setOfficialUrl("");
		setSection(new ArrayList<Section>());
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String value) {
		this.title = value;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String value) {
		this.additionalInformation = value;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String value) {
		this.notes = value;
	}

	public String getGoogleUrl() {
		return googleUrl;
	}

	public void setGoogleUrl(String value) {
		this.googleUrl = value;
	}

	public String getWikipediaUrl() {
		return wikipediaUrl;
	}

	public void setWikipediaUrl(String value) {
		this.wikipediaUrl = value;
	}

	public ArrayList<Section> getSection() {
		return section;
	}

	public void setSection(ArrayList<Section> value) {
		this.section = value;
	}

	public String getOfficialUrl() {
		return officialUrl;
	}

	public void setOfficialUrl(String value) {
		this.officialUrl = value;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String value) {
		this.subtitle = value;
	}
}