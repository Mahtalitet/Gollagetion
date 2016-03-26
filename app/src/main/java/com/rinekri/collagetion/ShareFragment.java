package com.rinekri.collagetion;

import com.rinekri.model.InstagramUserFactory;
import com.rinekri.network.NetworkConnector;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ShareFragment extends Fragment {
	private static final String TAG = "ShareFragment";
	private static final String KEY_ID = "id";
	
	private Button mSearchButton;
	private EditText mInstaIDEditText;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_share, container, false);
		Toolbar toolbar = (Toolbar) v.findViewById(R.id.fragment_share_toolbar);
		toolbar.setTitle("");
		((ShareActivity) getActivity()).setSupportActionBar(toolbar);
		
		mInstaIDEditText = (EditText) v.findViewById(R.id.instagram_id);
		
		mSearchButton = (Button) v.findViewById(R.id.search_post_button);
		mSearchButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if ((mInstaIDEditText.getText() != null) && (mInstaIDEditText.length() != 0)) {
					Toast toastOffline = Toast.makeText(getContext(), R.string.toast_network_offline, Toast.LENGTH_SHORT);
					if (NetworkConnector.isConnection(getContext())) {
						toastOffline.cancel();
						String instaNick = mInstaIDEditText.getText().toString();
						
						if (instaNick.contains(" ")) {
							mInstaIDEditText.setError(getContext().getResources().getString(R.string.toast_space_nick));
						} else {
							Log.d(TAG, "Entered nick: "+instaNick);
							String findedInstagramID = InstagramUserFactory.getFactory(getContext()).getID(instaNick);
							if (findedInstagramID != null) {
								Log.d(TAG, "Finded ID: "+findedInstagramID);
								Intent i = new Intent(getActivity(), CollageActivity.class);
								i.putExtra(CollageFragment.EXTRA_INSTAGRAM_ID, findedInstagramID);
								startActivity(i);
							} else {
								mInstaIDEditText.setError(getContext().getResources().getString(R.string.toast_bad_nick));
							}
						}
					} else {
						toastOffline.show();
					}
				} else {
					mInstaIDEditText.setError(getContext().getResources().getString(R.string.toast_null_nick));
				}
			}
		});
		return v;
	}
}
