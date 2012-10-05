package com.todelete;


import java.util.ArrayList;
import java.util.List;

import com.todelete.DBAccess.DBAccess;

import android.content.Context;
import android.database.Cursor;

public class Stations extends ArrayList<Station> {
	
	private static final long serialVersionUID = 7043793678873998614L;
	ArrayList<String> stationNames = new ArrayList<String>();	
	public Stations(Context context) {
		
		loadStationsFromDb(context);
		for (Station station : this) {
			stationNames.add(station.getName());
		}
		
	}

	private void loadStationsFromDb(Context context) {
		DBAccess dbAccess = new DBAccess(context);
		Cursor sc = dbAccess.getStations();
		try {

			if (sc.moveToFirst()) {
				do {
					Station station = new Station(sc.getString(sc
							.getColumnIndex("Code")), sc.getString(sc
							.getColumnIndex("Name")), sc.getDouble(sc
							.getColumnIndex("Latitude")), sc.getDouble(sc
							.getColumnIndex("Longitude")));

					add(station);
				} while (sc.moveToNext());
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			sc.close();
		}
	}

	public ArrayList<String> partialNameSearch(String string) {
		ArrayList<String> results = new ArrayList<String>();
		for (String station : this.all()) {
			if (station.toUpperCase().startsWith(string.toUpperCase()))
				results.add(station);
		}
		return results;

	}
	
	public String[] all(){
		List<String> subList = stationNames.subList(0, 100);
		return (String[])subList.toArray(new String[subList.size()]);
	}
}
