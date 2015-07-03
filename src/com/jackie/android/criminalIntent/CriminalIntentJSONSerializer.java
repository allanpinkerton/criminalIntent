package com.jackie.android.criminalIntent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;
import android.util.Log;

public class CriminalIntentJSONSerializer {
	private static final String FILE_NOT_FOUND = "file not found";

	private Context mContext;
	private String mFileName;
	
	public CriminalIntentJSONSerializer(Context c, String f) {
		mContext = c;
		mFileName = f;
	}
	
	public void saveCrimes(ArrayList<Crime> crimes) throws JSONException, IOException{ 
		// build array in JSON
		JSONArray array = new JSONArray();
		for (Crime c : crimes) {
			array.put(c.toJSON());
		}
		
		// write to disk
		Writer writer = null;
		try {
			OutputStream out = mContext.openFileOutput(mFileName, mContext.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	
	public ArrayList<Crime> loadCrimes() throws JSONException, IOException {
		ArrayList<Crime> crimes = new ArrayList<Crime>();
		BufferedReader reader = null;
		try {
			// Open and read the file into a StringBuilder
			InputStream in = mContext.openFileInput(mFileName);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				jsonString.append(line);
			}
			//Parse the JSON
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
			
			for (int i=0; i < array.length(); i++) {
				crimes.add(new Crime(array.getJSONObject(i)));
			}
		} catch(FileNotFoundException e) {
			Log.d(FILE_NOT_FOUND, "The file is not found");
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return crimes;
	}
}
