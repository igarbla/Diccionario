package com.txorua.diccionario;

/*
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
*/
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;

public class DictOpenHelper extends SQLiteOpenHelper {
	
	static final String TAG = "DictOpenHelper";
	static final String DB_NAME = "rae.db";
	static final int DB_VERSION = 1;
	static final String TABLE = "palabras";
	static final String C_PALABRA = "palabra";
	static final String C_DEFINICION = "definicion";
	static final String DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
	static final String CREATE = "create table " + TABLE + " (" + C_PALABRA + " text, " + C_DEFINICION + " blob)";
	static final String DELETE = "drop table " + TABLE;
	Context context;
	
	public DictOpenHelper(Context context) {				
		//super(context, DB_NAME, null, DB_VERSION);
		//super(context, context.getExternalFilesDir(null).getAbsolutePath() + "/" + DB_NAME, null, DB_VERSION);
		super(context,  DB_PATH + DB_NAME, null, DB_VERSION);
		this.context = context;
		Log.d(TAG, "constructor");
		//Log.w(TAG, context.getExternalFilesDir(null).getAbsolutePath());
		Log.w(TAG, Environment.getExternalStorageDirectory().getAbsolutePath());
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE);
		Log.d(TAG, "onCreate");
		/*try {
			copyDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DELETE);
		onCreate(db);
		Log.d(TAG, "onUpgrade");
		/*try {
			copyDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
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
	
	/*
	private void copyDataBase() throws IOException{
		 
		//Open your local db as the input stream
		InputStream myInput = context.getAssets().open(DB_NAME);
		 
		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;
		 
		//Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);
		 
		//transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer))>0){
		myOutput.write(buffer, 0, length);
		}
		 
		//Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
		Log.d(TAG, "copyDataBase() terminado");
	}*/
}
