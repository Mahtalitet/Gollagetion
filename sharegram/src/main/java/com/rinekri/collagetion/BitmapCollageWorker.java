package com.rinekri.collagetion;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.ViewGroup;

public class BitmapCollageWorker {
	public static final String TAG = "BitmapCollageWorker";
	public static final String COLLAGE_FOLDER = "Collage";
	public static final Bitmap.CompressFormat JPEG_FORMAT = Bitmap.CompressFormat.JPEG;
	public static final Bitmap.CompressFormat PNG_FORMAT = Bitmap.CompressFormat.PNG;
	@SuppressLint("NewApi")
	public static final Bitmap.CompressFormat WEBP_FORMAT = Bitmap.CompressFormat.WEBP;
	
	private Context mContext;
	private String mBitmapName;
	private Bitmap.CompressFormat mBitmapFormat;
	private File mCollageDirectory;
	

	public BitmapCollageWorker(Context c, String bName, Bitmap.CompressFormat bFormat) {
		mContext = c;
		mBitmapFormat = bFormat;
	
		StringBuilder fullBitmapName = new StringBuilder();
		fullBitmapName.append(bName);
		fullBitmapName.append(".");
		fullBitmapName.append(bFormat.toString());
		mBitmapName = fullBitmapName.toString();

		DirectoryReturner dirReturner = new DirectoryReturner(c);
		mCollageDirectory = dirReturner.returnFolderDirectory(COLLAGE_FOLDER, mBitmapName);
	}
	
	
	public Bitmap createBitmap(ViewGroup view) {
		view.setDrawingCacheEnabled(true);
		int color = mContext.getResources().getColor(R.color.sharegram_window_background);
		view.setDrawingCacheBackgroundColor(color);
		view.buildDrawingCache();
		
		Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
		view.setDrawingCacheEnabled(false);
		
		return bitmap;
	}
	

	public boolean saveBitmap(Bitmap bitmap, int bitmapQuality) {
		ByteArrayOutputStream bytesBitmap = new ByteArrayOutputStream();
		bitmap.compress(mBitmapFormat, bitmapQuality, bytesBitmap);
		
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(mCollageDirectory, false);
			
			if (out != null) {
		        out.write(bytesBitmap.toByteArray());;
//		        Log.d(TAG, "Bitmap saved");
		        out.close();
			}

		} catch(IOException ex) {
			ex.printStackTrace();
			return false;
//	        Log.d(TAG, "Doesn't have a destination to save the bitmap.");		
		}	
		return true;
	}
	
	public Intent generateCollageIntent() {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("image/*");
		shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(mCollageDirectory));
		shareIntent.putExtra(Intent.EXTRA_TEXT, "#appkode #zapilika");
		
		return shareIntent;
	}

}
