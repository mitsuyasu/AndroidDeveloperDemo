package com.masa.add.activity;

import com.masa.add.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SampleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sample);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sample, menu);
		return true;
	}
	
	public boolean b(){
		return true;
	}

}
