package com.masa.add.activity;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.masa.add.R;

public class GalleryActivity extends Activity {

	private static final int REQUEST_GALLERY = 0;
	private ImageView mImageiew;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		mImageiew = (ImageView) findViewById(R.id.image);
		// ギャラリー呼び出し
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, REQUEST_GALLERY);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
			InputStream in = null;
			try {
				in = getContentResolver().openInputStream(data.getData());
				Bitmap img = BitmapFactory.decodeStream(in);
				// 選択した画像を表示
				mImageiew.setImageBitmap(img);
			} catch (Exception e) {

			} finally{
				try {
					in.close();
				} catch (IOException e) {
					
				} catch (NullPointerException e) {
					
				}
			}
		}
	}

}
