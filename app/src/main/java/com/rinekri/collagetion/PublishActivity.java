package com.rinekri.collagetion;

import android.support.v4.app.Fragment;

public class PublishActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new PublishFragment();
	}
}
