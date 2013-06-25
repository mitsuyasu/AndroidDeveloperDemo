package com.masa.add.activity;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.masa.add.R;

public class GallerySquareActivity extends Activity {

	private static final int REQUEST_GALLERY = 0;
	private ImageView mImageiew;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery_square);
		mImageiew = (ImageView) findViewById(R.id.image);
		
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
	
	public void onClick(View view){
		Bitmap src = ((BitmapDrawable) mImageiew.getDrawable()).getBitmap();
		if(src != null){
			mImageiew.setImageDrawable(null);
			mImageiew.setImageBitmap(getSquareBitmap(src));
		}
	}

	/**
	 * 画像を中央寄せの正方形で切りだす
	 * @param b 元の画像
	 * @return bitmap 正方形に切り取られた画像
	 */
	public Bitmap getSquareBitmap(Bitmap b){
		int width = b.getWidth();
		int height = b.getHeight();
		int max = Math.max(width, height);
		int min = Math.min(width, height);
		boolean direction = width < height;
		Rect src = new Rect(0, 0, min, min);
		int diff = (max - min)/2;
		if(direction){
			src.top += diff;
			src.bottom += diff;
		}else{
			src.left += diff;
			src.right += diff;
		}
		Bitmap bitmap = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Rect dst = new Rect(0, 0, min, min);
		canvas.drawBitmap(b, src, dst, null);
		return bitmap;
	}
	
}
