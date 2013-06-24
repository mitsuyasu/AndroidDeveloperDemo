package com.masa.add.activity;

import com.masa.add.R;
import com.masa.add.R.layout;
import com.masa.add.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

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
