package com.rinekri.collagetion;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.rinekri.network.InstagramJSONWorker;
import com.rinekri.network.NetworkConnector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class CollageFragment extends ListFragment {
	private static final String TAG = "CollageFragment";
	public static final String EXTRA_INSTAGRAM_ID = "com.rinelri.instagram_id";
	
	private String mInstagramId;
	private ImageButton mBackImageButton;
	private TextView mCounterEditText;
	private Button mCollageButton;
	private int counter = 0;
	private ArrayList<InstagramPost> mPosts;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstagramId = (String) getActivity().getIntent().getSerializableExtra(EXTRA_INSTAGRAM_ID);
		if (mInstagramId != null) {
			Log.d(TAG, "ID at CollageFragment: "+mInstagramId);
			InstagramJSONWorker worker = new InstagramJSONWorker(getContext());
			mPosts = worker.getPosts(mInstagramId);
		}


        PostAdapter adapter = new PostAdapter(mPosts);
        setListAdapter(adapter);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_collage, container, false);
		Toolbar toolbar = (Toolbar) v.findViewById(R.id.fragment_collage_toolbar);
		toolbar.setTitle("");
		((CollageActivity) getActivity()).setSupportActionBar(toolbar);
		
		mBackImageButton = (ImageButton) v.findViewById(R.id.back_image_button);
		mBackImageButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (NavUtils.getParentActivityName(getActivity()) != null) {
					NavUtils.navigateUpFromSameTask(getActivity());
				}
			}
		});
		mCounterEditText = (TextView) v.findViewById(R.id.counter_text_view);
		mCounterEditText.setText(Integer.toString(counter));
		
		mCollageButton = (Button) v.findViewById(R.id.collage_button);
		mCollageButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), PostActivity.class);
				startActivity(i);
			}
		});
		
//		mCollageButton.setVisibility(View.VISIBLE);
		return v;
	}
	
	public class PostAdapter extends ArrayAdapter<InstagramPost> {

		public PostAdapter(ArrayList<InstagramPost> crimes) {
			super(getActivity(), 0, crimes);

		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_post, null);
			}
			
			InstagramPost instaPost = getItem(position);

			ImageView instaPostImageView = (ImageView) convertView.findViewById(R.id.insta_post_image_imageVIew);
			NetworkConnector imageReturner = new NetworkConnector(getContext());
			Bitmap image = imageReturner.getBitmapFromURL(instaPost.getPostImageURL());
			instaPostImageView.setImageBitmap(image);

			TextView instaPostDate = (TextView) convertView.findViewById(R.id.insta_post_date_textView);

			Date date = instaPost.getPostDate();
			if (date != null) {
				instaPostDate.setText(stringDate(instaPost.getPostDate()));
			} else {
				instaPostDate.setText("");
			}

			TextView instaPostTitle = (TextView) convertView.findViewById(R.id.insta_post_title_textView);

			String title = instaPost.getPostTitle();
			if (title != null) {
				instaPostTitle.setText(title);
			} else {
				instaPostTitle.setText(R.string.message_bad_title);
			}

			TextView instaPostLikes = (TextView) convertView.findViewById(R.id.insta_post_like_count_textView);
			instaPostLikes.setText(Integer.toString(instaPost.getPostLikeCounts()));

			return convertView;
		}
	}

	private String stringDate(Date setdate) {
		return new SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(setdate);
	}
	
}
