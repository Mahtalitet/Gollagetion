package com.rinekri.collagetion;

import java.util.Date;
import java.util.UUID;

public class InstagramPosts {
	private UUID mPostID;
	private String mPostTitle;
	private Date mPostDate;
	private String mPostImageURL;
	private Long mPostLikeCounts;
	
	
	public InstagramPosts(String postTitle, Date postDate, String postImageURL, Long postLikeCounts) {
		mPostID = UUID.randomUUID();
		
		mPostTitle = postTitle;
		mPostDate = postDate;
		mPostImageURL = postImageURL;
		mPostLikeCounts = postLikeCounts;
	}
	
	public UUID getPostID() {
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
	
	public Long getPostLikeCounts() {
		return mPostLikeCounts;
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
	
	public void setPostLikeCounts(Long postLikeCounts) {
		mPostLikeCounts = postLikeCounts;
	}

}
