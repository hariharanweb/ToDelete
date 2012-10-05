package com.todelete;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.calatrava.bridge.Launcher;

@SuppressLint("NewApi")
public class Todelete extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);		
		
		
		Launcher.launchKernel("com.todelete", this, getApplication(),
			new Runnable() {
				public void run() {
					Intent intent = new Intent(Todelete.this,StationSelectActivity.class);
					startActivity(intent);
				}
			}
		);

	}



}
