package com.rinekri.collagetion;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class PostFragment extends Fragment {
	public static final String TAG = "PostFragment";
	private static final String BITMAP_NAME = "PostCollage";
	
	private ImageButton mBackImageButton;
	private Button mPostButton;
	private RelativeLayout mCommonCollageLayout;
	private ImageView mBlockOneImageView;
	private BitmapCollageWorker mBitmapWorker;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_post, container, false);
		Toolbar toolbar = (Toolbar) v.findViewById(R.id.fragment_post_toolbar);
		toolbar.setTitle("");
		((PostActivity) getActivity()).setSupportActionBar(toolbar);
		
		mCommonCollageLayout = (RelativeLayout) v.findViewById(R.id.common_collage);

		mBlockOneImageView = (ImageView) v.findViewById(R.id.collage_block_one);
		mBackImageButton = (ImageButton) v.findViewById(R.id.back_image_button);
		mBackImageButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (NavUtils.getParentActivityName(getActivity()) != null) {
					NavUtils.navigateUpFromSameTask(getActivity());
				}
			}
		});
		
		mPostButton = (Button) v.findViewById(R.id.post_button);
		mPostButton.setOnClickListener(new View.OnClickListener() {
			
			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
			public void onClick(View v) {
				Toast loadToast = Toast.makeText(getContext(), R.string.toast_load, Toast.LENGTH_SHORT);
				loadToast.show();
				
				mBitmapWorker = new BitmapCollageWorker(getContext(), BITMAP_NAME, BitmapCollageWorker.JPEG_FORMAT);	
				Bitmap collage = mBitmapWorker.createBitmap(mCommonCollageLayout);
				boolean saved = mBitmapWorker.saveBitmap(collage, 70);
				if (saved) {
					loadToast.cancel();
					String dialogTitle = getContext().getResources().getString(R.string.impicit_intent_title);
					startActivity(Intent.createChooser(mBitmapWorker.generateCollageIntent(), dialogTitle));
				}
			}
		});
		
		return v;
	}
	
}
