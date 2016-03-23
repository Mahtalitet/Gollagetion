package com.rinekri.model;

import java.util.Date;

public class InstagramPost implements Comparable<InstagramPost> {
	private String mPostID;
	private String mPostTitle;
	private Date mPostDate;
	private String mPostImageURL;
	private int mPostLikeCounts;

	public InstagramPost(String postID, String postTitle, Date postDate, String postImageURL, int postLikeCounts) {
		mPostID = postID;
		mPostTitle = postTitle;
		mPostDate = postDate;
		mPostImageURL = postImageURL;
		mPostLikeCounts = postLikeCounts;
	}

	public InstagramPost(String postID, String postImageURL, int postLikeCounts) {
		mPostID = postID;
		mPostTitle = null;
		mPostDate = null;
		mPostImageURL = postImageURL;
		mPostLikeCounts = postLikeCounts;
	}
	
	public String getPostID() {
		return mPostID;
	} 
	
	public String getPostTitle() {
		return mPostTitle;
	}
	
	public Date getPostDate() {
		return mPostDate;
	}
	
	public String getPostImageURL() {
		return mPostImageURL;
	}
	
	public int getPostLikeCounts() {
		return mPostLikeCounts;
	}

	public void setmPostID(String postID) {
		mPostID = postID;
	}
	
	public void setPostTitle(String postTitle) {
		mPostTitle = postTitle;
	}
	
	public void setPostDate(Date postDate) {
		mPostDate = postDate;
	}
	
	public void setPostImageURL(String postImageURL) {
		mPostImageURL = postImageURL;
	}
	
	public void setPostLikeCounts(int postLikeCounts) {
		mPostLikeCounts = postLikeCounts;
	}

	@Override
	public int compareTo(InstagramPost another) {
		if (mPostLikeCounts > another.getPostLikeCounts()) {
			return -1;
		} else if(mPostLikeCounts < another.getPostLikeCounts()) {
			return 1;
		}
		return 0;
	}
}
