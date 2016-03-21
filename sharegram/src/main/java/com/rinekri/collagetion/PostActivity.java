package com.rinekri.collagetion;

import android.support.v4.app.Fragment;

public class PostActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new PostFragment();
	}
}
