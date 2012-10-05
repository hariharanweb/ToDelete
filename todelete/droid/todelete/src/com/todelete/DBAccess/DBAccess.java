package com.todelete.DBAccess;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAccess extends SQLiteOpenHelper {

	private String DB_PATH = "/data/data/com.todelete/databases/";
	private static String DB_NAME = "livedep.db";
	private Context context;

	
	
	public DBAccess(Context context) {
		super(context, DB_NAME, null, 1);
		this.context = context;
		loadOrCreateDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {
			// database does't exist yet.
		}
		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}

	private void copyDataBase() throws IOException {
		InputStream input = context.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream output = new FileOutputStream(outFileName);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = input.read(buffer)) > 0) {
			output.write(buffer, 0, length);
		}
		output.flush();
		output.close();
		input.close();

	}

	private void createDataBase() throws IOException {
		boolean dbExist = checkDataBase();
		if (dbExist) {
			// do nothing - database already exists
		} else {
			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are going to be able to overwrite that
			// database with our database.
			this.getReadableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	private void loadOrCreateDatabase() throws Error {
		try {
			createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}
	}
	
	public Cursor getStations(){
		SQLiteDatabase readableDatabase = this.getWritableDatabase();
		return readableDatabase.query("Stations", new String[] {"name","code","latitude","longitude"}, null, null, 
				null, null, null);		
	}
}
