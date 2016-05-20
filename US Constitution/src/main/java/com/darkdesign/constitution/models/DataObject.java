package com.darkdesign.constitution.models;

import android.os.Parcel;
import android.os.Parcelable;

public class DataObject implements Parcelable {

	private int id;
    private int category;
	private String title;
	private String subtitle;
	private String googleUrl;
	private String wikipediaUrl;
	private String officialUrl;
    private String htmlText;

    // Stupid empty constructor (thanks a lot parcelable)
    public DataObject() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory() { return category; }

    public void setCategory(int value) { this.category = value; }

	public String getTitle() {
		return title;
	}

	public void setTitle(String value) {
		this.title = value;
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

    public String getHtmlText() {
        return htmlText;
    }

    public void setHtmlText(String htmlText) {
        this.htmlText = htmlText;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(getId());
        out.writeInt(getCategory());
        out.writeString(getTitle());
        out.writeString(getSubtitle());
        out.writeString(getGoogleUrl());
        out.writeString(getWikipediaUrl());
        out.writeString(getOfficialUrl());
    }

    protected DataObject(Parcel in) {

        this.id = in.readInt();
        this.category = in.readInt();
        this.title = in.readString();
        this.subtitle = in.readString();
        this.googleUrl = in.readString();
        this.wikipediaUrl = in.readString();
        this.officialUrl = in.readString();

    }

    public static final Parcelable.Creator<DataObject> CREATOR = new Parcelable.Creator<DataObject>() {
        public DataObject createFromParcel(Parcel source) {
            return new DataObject(source);
        }

        public DataObject[] newArray(int size) {
            return new DataObject[size];
        }
    };
}