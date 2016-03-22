package com.rinekri.collagetion;

import java.util.Date;

public class InstagramPosts {
	private String mPostID;
	private String mPostTitle;
	private Date mPostDate;
	private String mPostImageURL;
	private int mPostLikeCounts;

	public InstagramPosts(String postID, String postTitle, Date postDate, String postImageURL, int postLikeCounts) {
		mPostID = postID;
		mPostTitle = postTitle;
		mPostDate = postDate;
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

}
