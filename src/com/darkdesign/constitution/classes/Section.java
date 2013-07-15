package com.darkdesign.constitution.classes;


public class Section {

	private String title;
	private String additionalInformation;
	private String notes;
	private String text;

	public Section() {
		setTitle("");
		setAdditionalInformation("");
		setNotes("");
		setText("");
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

	public String getText() {
		return text;
	}

	public void setText(String value) {
		this.text = value;
	}

}