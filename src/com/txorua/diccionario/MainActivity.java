package com.txorua.diccionario;

import android.os.Bundle;
import android.os.AsyncTask;
import android.app.Activity;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		editText = (EditText) findViewById(R.id.editText1);
		button = (Button) findViewById(R.id.button1);
		textView = (TextView) findViewById(R.id.textView1);
		
		button.setOnClickListener(this);
		
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
		new QueryTask().execute(query);
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
