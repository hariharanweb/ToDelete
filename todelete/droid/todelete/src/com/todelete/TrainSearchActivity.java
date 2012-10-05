package com.todelete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.calatrava.shell.NativeUIActivity;

public class TrainSearchActivity extends NativeUIActivity {

	private String stationName;
	private String directionType;

	@Override
	protected void onCreate(Bundle availableData) {
		super.onCreate(availableData);
		setContentView(R.layout.trainsearch);
		stationName = getIntent().getStringExtra("STATION_NAME");
		directionType = getIntent().getStringExtra("DIRECTION_TYPE");

		final ListView listView = (ListView) findViewById(R.id.listView);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				HashMap<String, String> itemAtPosition = (HashMap<String, String>) listView.getItemAtPosition(position);
				String href = itemAtPosition.get("href");
				Log.d(TrainSearchActivity.class.getSimpleName(), "Href :"
						+ href);
				Intent intent = new Intent(TrainSearchActivity.this,
						TrackTrainActivity.class);
				intent.putExtra("link", href);
				startActivity(intent);
			}

		});
	}

	@Override
	public void resultAvailable(Map<String, Object> returnValues) {
		NativeArray trains = (NativeArray) returnValues.get("trains");

		final ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

		for (int i = 0; i < trains.size(); i++) {
			NativeObject train = (NativeObject) trains.get(i);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("due", (String) train.get("due"));
			map.put("destination", (String) train.get("destination"));
			map.put("on-time-label", (String) train.get("on-time-label"));
			map.put("on-time",
					train.containsKey("mins-late") ? (String) train
							.get("mins-late") : (String) train.get("on-time"));
			map.put("href", (String) train.get("href"));
			list.add(map);
		}

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				String[] from = new String[] { "due", "destination",
						"on-time-label", "on-time" };
				int[] to = new int[] { R.id.due, R.id.destination,
						R.id.on_time_label, R.id.on_time };

				ListView listView = (ListView) findViewById(R.id.listView);
				listView.setAdapter(new SimpleAdapter(TrainSearchActivity.this,
						list, R.layout.train_row, from, to));
			}
		});

		Log.d(TrainSearchActivity.class.getSimpleName(),
				"Toast:" + trains.toString());
	}

	public void fetchTrains(View view) {
		String[] args = new String[] { stationName, directionType };
		executeJS("fetchTrains", args);
	}
}
