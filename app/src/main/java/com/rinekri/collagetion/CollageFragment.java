package com.rinekri.collagetion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.rinekri.model.InstagramPost;
import com.rinekri.model.InstagramPostsFactory;
import com.rinekri.network.NetworkConnector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CollageFragment extends ListFragment {
	public static final String EXTRA_INSTAGRAM_ID = "com.rinelri.instagram_id";
	private static final String KEY_POSTS_COUNTER = "checkedPostsCounter";
	private static final String TAG = "CollageFragment";

	private String mInstagramId;
	private ImageButton mBackImageButton;
	private TextView mSelectedPostsCounterEditText;
	private Button mCollageButton;
	private int checkedPostsCounter = 0;
	private ArrayList<InstagramPost> mPosts;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstagramId = (String) getActivity().getIntent().getSerializableExtra(EXTRA_INSTAGRAM_ID);
		if (mInstagramId != null) {
			Log.d(TAG, "ID at CollageFragment: "+mInstagramId);
			mPosts = InstagramPostsFactory.getFactory(getContext(), mInstagramId).getSortedForLikesInstagramPosts();
		}

		if (savedInstanceState != null) {
			checkedPostsCounter = savedInstanceState.getInt(KEY_POSTS_COUNTER);
		}

        PostAdapter adapter = new PostAdapter(mPosts);
        setListAdapter(adapter);
	}

	@Override
	public void onResume() {
		super.onResume();
		InstagramPostsFactory.getFactory(getContext(), mInstagramId).resetCurrentCombination();
	}

	@Override
	public void onSaveInstanceState (Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(KEY_POSTS_COUNTER, checkedPostsCounter);
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
		mSelectedPostsCounterEditText = (TextView) v.findViewById(R.id.counter_text_view);
		mSelectedPostsCounterEditText.setText(Integer.toString(checkedPostsCounter));


		final ListView listView = (ListView) v.findViewById(android.R.id.list);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		
		mCollageButton = (Button) v.findViewById(R.id.collage_button);
		mCollageButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SparseBooleanArray checkedPostsPositions = positionsOnlyCheckedItems(listView.getCheckedItemPositions());
				ArrayList<String> checkedPostsIDs = returnIdsCheckedItems(checkedPostsPositions);
				Intent intent = new Intent(getActivity(), PublishActivity.class);
				intent.putExtra(PublishFragment.EXTRA_IMAGES_IDS, checkedPostsIDs);
				startActivity(intent);
			}
		});

		setCollageButton(4);

		return v;
	}
	
	public class PostAdapter extends ArrayAdapter<InstagramPost> {

		public PostAdapter(ArrayList<InstagramPost> crimes) {
			super(getActivity(), 0, crimes);

		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_post, null);

			InstagramPost instaPost = getItem(position);

			ImageView instaPostImageView = (ImageView) convertView.findViewById(R.id.insta_post_image_imageVIew);
			NetworkConnector imageReturner = new NetworkConnector(getContext());
			Bitmap image = imageReturner.getBitmapFromURL(instaPost.getPostImageURL());
			instaPostImageView.setImageBitmap(image);

			ImageView instaPostCheckImageView = (ImageView) convertView.findViewById(R.id.insta_post_check_true_image_imageView);

			if (getListView().isItemChecked(position)) {
				instaPostCheckImageView.setVisibility(View.VISIBLE);
			} else {
				instaPostCheckImageView.setVisibility(View.GONE);
			}

			TextView instaPostDate = (TextView) convertView.findViewById(R.id.insta_post_date_textView);

			Date date = instaPost.getPostDate();
			if (date != null) {
				instaPostDate.setText(convertDateToString(instaPost.getPostDate()));
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

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		ImageView checkMark = (ImageView) v.findViewById(R.id.insta_post_check_true_image_imageView);
		checkMark.setVisibility(View.GONE);
		Boolean itemIsChecked = l.isItemChecked(position);
		Log.d(TAG, "Checkstate of " + position + " is " + itemIsChecked);
		Log.d(TAG, "Raw is " + id);

		if (itemIsChecked) {
			checkMark.setVisibility(View.VISIBLE);
			checkedPostsCounter++;

		} else {
			checkMark.setVisibility(View.GONE);
			checkedPostsCounter--;
		}
		mSelectedPostsCounterEditText.setText(Integer.toString(checkedPostsCounter));

		setCollageButton(4);

		Log.e(TAG, "Posts counter: " + checkedPostsCounter);
	}

	private String convertDateToString(Date setdate) {
		return new SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(setdate);
	}

	private void setCollageButton(int minPosts) {
		if (minPosts == 0) {
			mCollageButton.setVisibility(View.VISIBLE);
		} else {
			if ((checkedPostsCounter >= minPosts) && (mCollageButton.getVisibility() == View.GONE)) {
				mCollageButton.setVisibility(View.VISIBLE);
			} else if ((checkedPostsCounter < minPosts) && (mCollageButton.getVisibility() == View.VISIBLE)) {
				mCollageButton.setVisibility(View.GONE);
			}
		}
	}

	private SparseBooleanArray positionsOnlyCheckedItems(SparseBooleanArray checkedItemPositions) {
		Log.d(TAG, "Positions of checked posts (before): " + checkedItemPositions.toString());
		Log.d(TAG, "Size: " + checkedItemPositions.size());
		ArrayList<Integer> uncheckedKey = new ArrayList<Integer>();
		for (int i = 0; i < checkedItemPositions.size(); i++) {
			int currentKey = checkedItemPositions.keyAt(i);
			boolean keyIsChecked = checkedItemPositions.get(currentKey);
			if (!keyIsChecked) {
				uncheckedKey.add(currentKey);
			}
		}
		if (uncheckedKey.size() > 0) {
			Log.d(TAG, "Deleting positions :" + uncheckedKey.toString());
			for (int i = 0; i < uncheckedKey.size(); i++) {
				int deletingKey = uncheckedKey.get(i);
				checkedItemPositions.delete(deletingKey);
			}
		}
		Log.d(TAG, "Positions of checked posts (after): " + checkedItemPositions.toString());
		Log.d(TAG, "Size: " + checkedItemPositions.size());

		return checkedItemPositions;
	}

	private ArrayList<String> returnIdsCheckedItems(SparseBooleanArray imgsPositions) {

		ArrayList<String> ids = new ArrayList<String>();

		for (int i = 0; i < imgsPositions.size(); i++) {
			int position = imgsPositions.keyAt(i);
			InstagramPost currPost = (InstagramPost) getListView().getItemAtPosition(position);
			String ID = currPost.getPostID();
			ids.add(ID);
		}
		Log.d(TAG, "Finded IDs: " + ids.toString());
		return ids;
	}
}
