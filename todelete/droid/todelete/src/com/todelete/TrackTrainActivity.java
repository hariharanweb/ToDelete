package com.todelete;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.calatrava.shell.NativeUIActivity;

public class TrackTrainActivity extends NativeUIActivity {
	ListView listView;
	private String link;

	@Override
	protected void onCreate(Bundle availableData) {
		super.onCreate(availableData);
		setContentView(R.layout.track_train	);
		listView = (ListView) findViewById(R.id.trackTrain);
		link = "www.thetrainline.com"+getIntent().getStringExtra("link");
	}

	@Override
	public void resultAvailable(Map<String, Object> returnValues) {
		NativeArray stations = (NativeArray) returnValues.get("stations");

		final ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

		for (int i = 0; i < stations.size(); i++) {
			NativeObject station = (NativeObject) stations.get(i);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("depart", (String) station.get("depart"));
			map.put("station", (String) station.get("station"));
			map.put("on-time-label", (String) station.get("on-time-label"));
			map.put("on-time", (String) station.get("on-time"));
			list.add(map);
		}

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				String[] from = new String[] { "depart", "station",
						"on-time-label", "on-time" };
				int[] to = new int[] { R.id.departTime, R.id.stationName,
						R.id.onTimeLabel, R.id.onTime };

				listView.setAdapter(new SimpleAdapter(TrackTrainActivity.this,
						list, R.layout.station_row, from, to));
			}
		});

		Log.d(TrainSearchActivity.class.getSimpleName(),
				"Toast:" + stations.toString());
	}

	public void fetchStations(View views) {
		 String[] args;
		try {
			args = new String[] { URLEncoder.encode(link,"UTF-8") };
			executeJS("fetchStations", args);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

}
