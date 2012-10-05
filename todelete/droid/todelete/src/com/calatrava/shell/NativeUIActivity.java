package com.calatrava.shell;

import java.util.Map;

import android.annotation.SuppressLint;
import android.util.Log;

import com.calatrava.bridge.RegisteredActivity;
import com.calatrava.bridge.RhinoService;

@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
public abstract class NativeUIActivity extends RegisteredActivity {

	private RhinoService rhinoService;

	@Override
	protected void onRhinoConnected(RhinoService rhino) {
		rhinoService = rhino;
		rhinoService.getNativeJSController().addListener(this);
	}

	@Override
	protected String getPageName() {
		return "native";
	}

	@Override
	public String getFieldValue(String field) {
		return null;
	}

	@Override
	public void render(String json) {

	}

	protected void executeJS(String function, String[] args) {
		rhinoService.callJsFunction(function,args);
		NativeJSContainer nativeJSController = rhinoService.getNativeJSController();
//		String value = (String) nativeJSController.getValue("test");
//		Log.d(NativeUIActivity.class.getSimpleName(), "Value added "+value);				
	}
	
	public abstract void resultAvailable(Map<String,Object> returnValues);
	
	@Override
	public void onDestroy() {
		rhinoService.getNativeJSController().removeListener(this);
		super.onDestroy();
	}
	
}
