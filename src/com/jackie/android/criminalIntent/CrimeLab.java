	package com.jackie.android.criminalIntent;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

public class CrimeLab {
	private static final String TAG = "CrimeLab";
	private static final String FILENAME = "crimes.json";
	private static CrimeLab sCrimeLab;
	private Context mAppContext;
	
	private ArrayList<Crime> mCrimes;
	private CriminalIntentJSONSerializer mSerializer;

	
	private CrimeLab(Context appContext) {
		mAppContext = appContext;
		mCrimes = new ArrayList<Crime>();
		mSerializer = new CriminalIntentJSONSerializer(mAppContext, FILENAME);
		
		try {
			mCrimes = mSerializer.loadCrimes();
		} catch (Exception e){
			mCrimes = new ArrayList<Crime>();
			Log.e(TAG, "Error loading crimes: " + e);
		}
	}
	
	public void addCrime(Crime c) {
		mCrimes.add(c);
	}
	
	public void deleteCrime(Crime c) {
		mCrimes.remove(c);
	}
	
	public static CrimeLab get(Context c) {
		if (sCrimeLab == null) {
			sCrimeLab = new CrimeLab(c.getApplicationContext());
		}
		return sCrimeLab;
	}

	public ArrayList<Crime> getCrimes() {
		return mCrimes;
	}
	
	public Crime getCrime(UUID id) {
		for (Crime c : mCrimes) {
			if (c.getId().equals(id))
				return c;
		}
		return null;
	}
	
	public boolean saveCrimes() {
		try {
			mSerializer.saveCrimes(mCrimes);
			Log.d(TAG, "Crimes saved to file");
			return true;
		} catch (Exception e) {
			Log.e(TAG, "Error saving to file");
			return false;
		}
	}
}
