package com.masa.add.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.masa.add.R;

public class LevelLevelLevelActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level_level_level);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_level_level_level, menu);
		return true;
	}

}
