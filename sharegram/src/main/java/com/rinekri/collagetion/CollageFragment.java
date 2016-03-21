package com.rinekri.collagetion;

import java.util.ArrayList;

import com.rinekri.network.InstagramJSONWorker;

import android.content.Intent;
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
import android.widget.TextView;

public class CollageFragment extends ListFragment {
	private static final String TAG = "CollageFragment";
	public static final String EXTRA_INSTAGRAM_ID = "com.rinelri.instagram_id";
	
	private String mInstagramId;
	private ImageButton mBackImageButton;
	private TextView mCounterEditText;
	private Button mCollageButton;
	private int counter = 0;
	private ArrayList<String> mPosts;;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstagramId = (String) getActivity().getIntent().getSerializableExtra(EXTRA_INSTAGRAM_ID);
		if (mInstagramId != null) {
			Log.d(TAG, mInstagramId);
//			InstagramJSONWorker worker = new InstagramJSONWorker(getContext());
//			worker.getPosts(mInstagramId);
		}
		mPosts = new ArrayList<String>();
		mPosts.add("1post");
		mPosts.add("2post");
		mPosts.add("3post");
		mPosts.add("4post");
		mPosts.add("5post");
		mPosts.add("6post");
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
	
	public class PostAdapter extends ArrayAdapter<String> {

		public PostAdapter(ArrayList<String> crimes) {
			super(getActivity(), 0, crimes);

		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_post, null);
			}
			
			String c = mPosts.get(position);
			Log.d(TAG, c);
			
			return convertView;
		}
	}
	
}
