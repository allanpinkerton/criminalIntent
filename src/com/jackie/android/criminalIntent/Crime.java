package com.jackie.android.criminalIntent;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class Crime {
	private static final String JSONId = "id";
	private static final String JSONTitle = "title";
	private static final String JSONSolved = "solved";
	private static final String JSONDate = "date";
	private static final String JSONPhoto = "photo";
	private static final String JSONSuspect = "suspect";
	
	private UUID mId;
	private String mTitle;
	private Date mDate;
	private boolean mSolved;
	private Photo mPhoto;
	private String mSuspect;
	
	public UUID getId() {
		return mId;
	}
	
	public Crime() {
		mId = UUID.randomUUID();
		mDate = new Date();
	}
	
	public Crime(JSONObject json) throws JSONException {
		mId = UUID.fromString(json.getString(JSONId));
		
		if (json.has(JSONTitle)) {
			mTitle = json.getString(JSONTitle);
		}
		mSolved = json.getBoolean(JSONSolved);
		mDate = new Date(json.getLong(JSONDate));
		if(json.has(JSONPhoto)) {
			mPhoto = new Photo(json.getJSONObject(JSONPhoto));
		}
		if(json.has(JSONSuspect)) {
			mSuspect = json.getString(JSONSuspect);
		}
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSONId, mId.toString());
		json.put(JSONTitle, mTitle);
		json.put(JSONSolved, mSolved);
		json.put(JSONDate, mDate.getTime());
		if(mPhoto != null) {
			json.put(JSONPhoto, mPhoto.toJSON());
		}
		json.put(JSONSuspect, mSuspect);
		
		return json;
	}
	
	@Override
	public String toString() {
		return mTitle;
	}

	public boolean isSolved() {
		return mSolved;
	}

	public void setSolved(boolean solved) {
		mSolved = solved;
	}

	public Date getDate() {
		return mDate;
	}

	public void setDate(Date date) {
		mDate = date;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public Photo getPhoto() {
		return mPhoto;
	}

	public void setPhoto(Photo photo) {
		mPhoto = photo;
	}

	public String getSuspect() {
		return mSuspect;
	}

	public void setSuspect(String suspect) {
		mSuspect = suspect;
	}
	

}
