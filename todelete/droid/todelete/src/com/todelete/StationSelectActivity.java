package com.todelete;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class StationSelectActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle availableData) {
		super.onCreate(availableData);
		setContentView(R.layout.activity_station_search);
		Stations stations = new Stations(this);

		AutoCompleteTextView station = (AutoCompleteTextView) findViewById(R.id.station);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, stations.all());
		station.setAdapter(adapter);
	}
	
	public void searchTrainsForStation(View view) {
		Intent intent = new Intent(this, TrainSearchActivity.class);

		TextView stationView = (TextView) findViewById(R.id.station);
		RadioGroup directionView = (RadioGroup) findViewById(R.id.directionGroup);

		String stationName = stationView.getText().toString();
		int checkedDirectionButtonId = directionView.getCheckedRadioButtonId();
		String directionType = ((RadioButton) findViewById(checkedDirectionButtonId))
				.getText().toString();
		intent.putExtra("STATION_NAME", stationName);
		intent.putExtra("DIRECTION_TYPE", directionType);
		startActivity(intent);
	}
}
