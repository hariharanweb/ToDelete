package com.calatrava.shell;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;

public class NativeJSContainer {
	
	private HashMap<String, Object> returnValues;
	private ArrayList<NativeUIActivity> listeners;

	public NativeJSContainer() {
		returnValues = new HashMap<String, Object>();
		listeners = new ArrayList<NativeUIActivity>();
	}
	
	public void addValue(String key,Object value){
		Log.d(NativeJSContainer.class.getSimpleName(), "Someone added key "+key+" with value "+value+" of class "+value.getClass());
		returnValues.put(key, value);
		for (NativeUIActivity activity : listeners) {
			activity.resultAvailable(returnValues);
		}
	}
	
	public Object getValue(String key){
		return returnValues.get(key);
	}

	public void addListener(NativeUIActivity nativeUIActivity) {
		listeners.add(nativeUIActivity);		
	}

	public void removeListener(NativeUIActivity nativeUIActivity) {
		listeners.remove(nativeUIActivity);
	}
}
