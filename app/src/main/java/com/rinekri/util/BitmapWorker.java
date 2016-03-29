package com.rinekri.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.ViewGroup;

import com.rinekri.collagetion.R;

public class BitmapWorker {
	public static final String TAG = "BitmapWorker";
	public static final String IMAGE_CACHE_FOLDER = "Image_cache";
	public static final String COLLAGE_FOLDER = "Collage";


	public static final Bitmap.CompressFormat JPEG_FORMAT = Bitmap.CompressFormat.JPEG;
	public static final Bitmap.CompressFormat PNG_FORMAT = Bitmap.CompressFormat.PNG;
	@SuppressLint("NewApi")
	public static final Bitmap.CompressFormat WEBP_FORMAT = Bitmap.CompressFormat.WEBP;
	
	private Context mContext;
	private String mBitmapName;
	private Bitmap.CompressFormat mBitmapFormat;
	private File mBitmapDirectory;
	private File mDirectory;


	public BitmapWorker(Context c, String bName, Bitmap.CompressFormat bFormat) {
		mContext = c;
		mBitmapFormat = bFormat;
	
		StringBuilder fullBitmapName = new StringBuilder();
		fullBitmapName.append(bName);
		fullBitmapName.append(".");
		fullBitmapName.append(bFormat.toString());
		mBitmapName = fullBitmapName.toString();

		DirectoryReturner dirReturner = new DirectoryReturner(c);
		mBitmapDirectory = dirReturner.returnFileDirectory(COLLAGE_FOLDER, mBitmapName);
	}
	
	
	public Bitmap createBitmapFromView(ViewGroup view) {
		view.setDrawingCacheEnabled(true);
		int color = mContext.getResources().getColor(R.color.sharegram_primary);
		view.setDrawingCacheBackgroundColor(color);
		view.buildDrawingCache();
		
		Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
		view.setDrawingCacheEnabled(false);
		
		return bitmap;
	}
	

	public boolean saveBitmap(Bitmap bitmap, int bitmapQuality) {
		mBitmapDirectory.delete();
		ByteArrayOutputStream bytesBitmap = new ByteArrayOutputStream();
		bitmap.compress(mBitmapFormat, bitmapQuality, bytesBitmap);
		
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(mBitmapDirectory, false);
			
			if (out != null) {
		        out.write(bytesBitmap.toByteArray());
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

	public boolean saveBitmapHighQuality(Bitmap bitmap) {
		return saveBitmap(bitmap, 100);
	}

	public Bitmap loadBitmapFromFile() {
		return BitmapFactory.decodeFile(mBitmapDirectory.toString());
	}


	public Intent generateCollageIntent() {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("image/*");
		shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(mBitmapDirectory));
		shareIntent.putExtra(Intent.EXTRA_TEXT, "#appkode #zapilika");
		
		return shareIntent;
	}

	public void deleteFilesFromDirectory() {

		if (mBitmapDirectory.exists()) {
			String deleteCmd = "rm -r " + mBitmapDirectory;
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec(deleteCmd);
			} catch (IOException e) { }
		}
	}
}
