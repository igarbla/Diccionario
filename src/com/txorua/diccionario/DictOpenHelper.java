package com.txorua.diccionario;

import java.io.File;

import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;

public class DictOpenHelper extends SQLiteOpenHelper {
	
	static final String TAG = "DictOpenHelper";
	static final String DB_NAME = "rae.db";
	static final int DB_VERSION = 5;
	static final String TABLE = "palabras";
	static final String C_PALABRA = "palabra";
	static final String C_DEFINICION = "definicion";
	Context context;
	
	public DictOpenHelper(Context context) {				
		//super(context, DB_NAME, null, DB_VERSION);
		//super(context, context.getExternalFilesDir(null).getAbsolutePath() + "/" + DB_NAME, null, DB_VERSION);
		super(context, Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + DB_NAME, null, DB_VERSION);
		this.context = context;
		Log.d(TAG, "constructor");
		//Log.w(TAG, context.getExternalFilesDir(null).getAbsolutePath());
		Log.w(TAG, Environment.getExternalStorageDirectory().getAbsolutePath());
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + TABLE + " (" + C_PALABRA + " text, " + C_DEFINICION + " blob)";
		db.execSQL(sql);
		/*ContentValues values = new ContentValues();
		values.put(C_PALABRA, "abacal");
		values.put(C_DEFINICION, "Bla bla bla ...");
		db.insertOrThrow(TABLE, null, values);
		values.clear();
		values.put(C_PALABRA, "abaloriol");
		values.put(C_DEFINICION, "Ble ble ble ...");
		db.insertOrThrow(TABLE, null, values);*/
		Log.d(TAG, "onCreate");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "drop table " + TABLE;
		db.execSQL(sql);
		onCreate(db);
		Log.d(TAG, "onUpgrade");
	}
	
	Palabra getPalabra(String query) {
        Palabra palabra = new Palabra();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.query(TABLE, new String[] { C_PALABRA, C_DEFINICION }, C_PALABRA + "=?", new String[] { query }, null, null, null, null);
			if (cursor != null && cursor.moveToFirst()) {			    
			    palabra.setPalabra(cursor.getString(0));
			    palabra.setDefinicion(cursor.getString(1));
			} else {
				palabra.setDefinicion("Palabra no encontrada");
			}
		    cursor.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // return contact
        return palabra;
    }
}
