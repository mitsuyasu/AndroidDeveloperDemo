package com.masa.add;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.masa.add.adapter.DemoArrayAdapter;
import com.masa.add.adapter.DemoListViewItem;

public class MainActivity extends ListActivity {
		public static final String CATEGORY_SAMPLE_CODE = "com.masa.add.category.SAMPLE_CODE";
		public static final String EXTRA_KEY = "com.masa.add.Path";

		BaseAdapter mAdapter;
		List<DemoListViewItem> mList;
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        Intent intent = getIntent();
	        String path = intent.getStringExtra(EXTRA_KEY);
	        
	        if (path == null) {
	            path = "";
	        }

	        mList = getData(path);
	        mAdapter = new DemoArrayAdapter(this, mList);
	        setListAdapter(mAdapter);
	        
	        ListView listView = getListView();
	        listView.setTextFilterEnabled(true);
	        listView.setBackgroundColor(Color.BLACK);
	        listView.setDivider(new ColorDrawable(Color.GRAY));
	        listView.setDividerHeight(1);
	    }

	    protected List<DemoListViewItem> getData(String prefix) {
	        List<DemoListViewItem> myData = new ArrayList<DemoListViewItem>();

	        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
	        mainIntent.addCategory(CATEGORY_SAMPLE_CODE);

	        PackageManager pm = getPackageManager();
	        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, PackageManager.GET_ACTIVITIES|PackageManager.GET_META_DATA);

	        if (null == list)
	            return myData;

	        String[] prefixPath;
	        
	        if (prefix.equals("")) {
	            prefixPath = null;
	        } else {
	            prefixPath = prefix.split("/");
	        }
	        
	        Map<String, Boolean> entries = new HashMap<String, Boolean>();

	        PackageInfo packageInfo = null;
			try {
				packageInfo  = pm.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES
						| PackageManager.GET_META_DATA);

			} catch (NameNotFoundException e) {
				finish();
			}
			int appMinVersion = packageInfo.applicationInfo.targetSdkVersion;
	        
	        for (ResolveInfo info: list) {
	            CharSequence labelSeq = info.loadLabel(pm);
	            String label = labelSeq != null
	                    ? labelSeq.toString()
	                    : info.activityInfo.name;
	            Bundle metaData = info.activityInfo.metaData;
	            
	            int minVersion = 0;
	            if (metaData != null && metaData.getBoolean(getString(R.string.isLaunchableActivity), false)) {
	            	minVersion = metaData.getInt(getString(R.string.minVersion), appMinVersion);
	            }
	            
	            if (prefix.length() == 0 || label.startsWith(prefix)) {
	                
	                String[] labelPath = label.split("/");

	                String nextLabel = prefixPath == null ? labelPath[0] : labelPath[prefixPath.length];

	                
	                if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
	                    addItem(myData, nextLabel, activityIntent(
	                            info.activityInfo.applicationInfo.packageName,
	                            info.activityInfo.name), minVersion);
	                } else {
	                    if (entries.get(nextLabel) == null) {
	                        addItem(myData, nextLabel, browseIntent(prefix.equals("") ? nextLabel : prefix + "/" + nextLabel), 0);
	                        entries.put(nextLabel, true);
	                    }
	                }
	            }
	        }

	        //
	        
	        return myData;
	    }

	    private final static Comparator<DemoListViewItem> sDisplayNameComparator = new Comparator<DemoListViewItem>() {
	        private final Collator   collator = Collator.getInstance();

	        public int compare(DemoListViewItem Demo1, DemoListViewItem Demo2) {
	            return collator.compare(Demo1.getTitle(), Demo2.getTitle());
	        }
	    };

	    protected Intent activityIntent(String pkg, String componentName) {
	        Intent result = new Intent();
	        result.setClassName(pkg, componentName);
	        return result;
	    }
	    
	    protected Intent browseIntent(String path) {
	        Intent result = new Intent();
	        result.setClass(this, MainActivity.class);
	        result.putExtra(EXTRA_KEY, path);
	        return result;
	    }

	    protected void addItem(List<DemoListViewItem> data, String name, Intent intent, int minVersion) {
	        Demo temp = new Demo(name, intent, minVersion);
	        data.add(temp);
	    }

	    @Override
	    protected void onListItemClick(ListView l, View v, int position, long id) {
	        Demo Demo = (Demo) l.getItemAtPosition(position);

	        Intent intent = Demo.intent;
	        startActivity(intent);
	    }
	    
	    
	    private final class Demo implements DemoListViewItem {

			public final String title;
			public final int minVersion;
			public final Intent intent;

			public Demo(String title, Intent intent, int minVersion) {
				this.intent = intent;
				this.title = title;
				this.minVersion = minVersion;
			}

			@Override
			public boolean isEnabled() {
				return android.os.Build.VERSION.SDK_INT >= minVersion;
			}

			@Override
			public String getDisabledText() {
				String itemDisabledText = getString(R.string.list_item_disabled);
				return String.format(itemDisabledText, minVersion);
			}

			@Override
			public String getTitle() {
				return title;
			}

		}

	}
