package com.txorua.diccionario;

import java.io.File;

import android.os.Bundle;
import android.os.AsyncTask;
import android.os.Environment;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	static final String TAG = "MainActivity";
	EditText editText;
	Button button;
	TextView textView;
	DictOpenHelper myOpenHelper;
	
	boolean mExternalStorageAvailable = false;
	boolean mExternalStorageWriteable = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		editText = (EditText) findViewById(R.id.editText1);
		button = (Button) findViewById(R.id.button1);
		textView = (TextView) findViewById(R.id.textView1);
		
		button.setOnClickListener(this);
		
		
		String state = Environment.getExternalStorageState();

		// ????
		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		    Log.w(TAG, "Tengo acceso de escritura a la SD");
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    // We can only read the media
		    mExternalStorageAvailable = true;
		    mExternalStorageWriteable = false;
		    Log.w(TAG, "Tengo acceso de lectura a la SD");
		} else {
		    // Something else is wrong. It may be one of many other states, but all we need
		    //  to know is we can neither read nor write
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		    Log.w(TAG, "No tengo acceso a la SD");
		}
		
		/*File dbFile = new File(Environment.getExternalStorageDirectory(), DB_NAME);
		dbFile.delete();
		*/
		
		myOpenHelper = new DictOpenHelper(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void onClick(View v) {
		String query = editText.getText().toString();
		if(mExternalStorageAvailable) {
			Log.d(TAG, Boolean.valueOf(mExternalStorageAvailable).toString());
		    new QueryTask().execute(query);
		} else {
			Log.d(TAG, Boolean.valueOf(mExternalStorageAvailable).toString());
			textView.setText("Sin acceso a la base de datos.");
		}
	}
	
	class QueryTask extends AsyncTask<String, Integer, String> {
		
		@Override
		protected String doInBackground(String...words) {
			return myOpenHelper.getPalabra(words[0]).getDefinicion();
		}
		
		@Override 
		protected void onProgressUpdate(Integer...values) {
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(String result) {
			textView.setText(result);
		}
	}

}
