package com.rinekri.json;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.rinekri.model.InstagramPost;
import com.rinekri.collagetion.R;
import com.rinekri.network.NetworkConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class InstagramJSONWorker {
	private static final String TAG = "InstagramJSONWorker";
	
	private static final String URL_MAIN = "https://api.instagram.com/v1/users/";
	private static final String URL_CLIENT_ID ="client_id=9734d32bcee14651829e7b2bed26b4c3";

	private Context mContext;
	private String mInstagramNick;
	private String mInstagramID;
	
	public InstagramJSONWorker(Context c) {
		mContext = c;
	}

	public String getId (String nick) {
		mInstagramNick = nick;

		AsyncTask<Void, Void, String> requestID = new GetInstagramID().execute();
		
		String instagramId = null;
		
		try {
			instagramId = requestID.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		return instagramId;
	}

	private class GetInstagramID extends AsyncTask<Void, Void, String> {
		private static final String TAG_DATA = "data";
		private static final String TAG_USERNAME = "username";
		private static final String TAG_ID = "id";
		
		private AlertDialog alert;

		protected void onPreExecute() {
			AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
			alertBuilder.setMessage(R.string.dialog_search_id_title);
			alertBuilder.setCancelable(false);
			alert = alertBuilder.create();
			alert.show();
		}
		   
		protected String doInBackground(Void... arg0) {

			StringBuilder getIDurl = new StringBuilder()
					.append(URL_MAIN)
					.append("search?q=")
			        .append(mInstagramNick)
					.append("&")
					.append(URL_CLIENT_ID);

			NetworkConnector connector = new NetworkConnector(mContext);

			String jsonStr = connector.getStringResponce(getIDurl.toString());

	        if (jsonStr != null) {
	            try {
	                JSONObject jsonObj = new JSONObject(jsonStr);

	                JSONArray usersData = jsonObj.getJSONArray(TAG_DATA);

	                for (int i = 0; i < usersData.length(); i++) {
	                	JSONObject user = usersData.getJSONObject(i);

	                	String nick = user.getString(TAG_USERNAME);
	                	Log.e(TAG, "nick: "+nick);
	                    if (nick.equals(mInstagramNick)) {
	                    	String id = user.getString(TAG_ID);
	                    	Log.e(TAG, "ID: "+id);
	                        return id;
	                    }
	                }
	            } catch (JSONException e) {
	            	e.printStackTrace();
		        	Log.i(TAG, "Didn't find something");
	            }
	        } else {
	        	   Log.e(TAG, "Couldn't get any data from the url.");
	        }

			return null;
		}
	        
		protected void onPostExecute(String result) {
			if (alert != null) alert.dismiss();
		}
	}

	public ArrayList<InstagramPost> getPosts (String id) {
		mInstagramID = id;

		AsyncTask<Void, Void, ArrayList<InstagramPost>> requestPosts = new GetInstagramPosts().execute();

		ArrayList<InstagramPost> instagramPostList = null;

		try {
			instagramPostList = requestPosts.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		return instagramPostList;
	}
	
	private class GetInstagramPosts extends AsyncTask<Void, Void, ArrayList<InstagramPost>> {
		private static final String TAG_DATA = "data";
		private static final String TAG_PAGINATION = "pagination";
		private static final String TAG_PAGINATION_NEXT_URL = "next_url";
		private static final String TAG_META = "meta";
		private static final String TAG_META_CODE = "code";
		private static final String TAG_LIKES = "likes";
		private static final String TAG_LIKES_COUNT = "count";
		private static final String TAG_IMAGES = "images";
		private static final String TAG_IMAGES_RESOLUTION = "low_resolution";
		private static final String TAG_IMAGES_URL = "url";
		private static final String TAG_CAPTION = "caption";
		private static final String TAG_CAPTION_DATE = "created_time";
		private static final String TAG_CAPTION_TEXT = "text";
		private static final String TAG_ID = "id";

		private AlertDialog alert;

		protected void onPreExecute() {
			AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
			alertBuilder.setMessage(R.string.dialog_get_posts_title);
			alertBuilder.setCancelable(false);
			alert = alertBuilder.create();
			alert.show();
		}

		protected ArrayList<InstagramPost> doInBackground(Void... arg0) {

			boolean haveRequest = true;
			boolean firstRequest = true;

			StringBuilder postsURL = null;
			NetworkConnector connector = new NetworkConnector(mContext);

			ArrayList<InstagramPost> instaPosts = new ArrayList<InstagramPost>();

			int pageCounter = 0;
			while (haveRequest) {

				if (firstRequest) {
					postsURL = new StringBuilder()
							.append(URL_MAIN)
							.append(mInstagramID)
							.append("/media/recent")
							.append("?")
							.append(URL_CLIENT_ID);
					firstRequest = false;
				}

				String allStringData = connector.getStringResponce(postsURL.toString());
				try {
					JSONObject allDataJSONObj = new JSONObject(allStringData);

					JSONObject metaJSONObj = allDataJSONObj.getJSONObject(TAG_META);
					String metaCode = metaJSONObj.getString(TAG_META_CODE);

					if (metaCode.equals("200")) {
						Log.e(TAG, "page"+pageCounter);

						try {
							JSONObject paginationJSONObj = allDataJSONObj.getJSONObject(TAG_PAGINATION);
							String nextURL = paginationJSONObj.getString(TAG_PAGINATION_NEXT_URL);
							postsURL = postsURL.delete(0, postsURL.length()).append(nextURL);

						} catch(JSONException e) {
							e.printStackTrace();
							Log.i(TAG, "Don't have pages or posts.");
							haveRequest = false;
						}

						JSONArray postsDataArr = allDataJSONObj.getJSONArray(TAG_DATA);

						for (int i = 0; i < postsDataArr.length(); i++) {
							Log.d(TAG, "IMAGE "+i);
							JSONObject postJSONObj = postsDataArr.getJSONObject(i);

							JSONObject likesJSONObj = postJSONObj.getJSONObject(TAG_LIKES);
							String likesCount = likesJSONObj.getString(TAG_LIKES_COUNT);
							Log.d(TAG, "Likes: "+likesCount);

							JSONObject imageJSONObj = postJSONObj.getJSONObject(TAG_IMAGES);
							JSONObject imageLowJSONObj = imageJSONObj.getJSONObject(TAG_IMAGES_RESOLUTION);
							String imageURL = imageLowJSONObj.getString(TAG_IMAGES_URL);
							Log.d(TAG, "URL: "+imageURL);

							String id = postJSONObj.getString(TAG_ID);
							Log.d(TAG, "ID: "+id);

							InstagramPost instaPost;
							String captionTitle;
							String captionTime;
							try {
								JSONObject captionJSONObj = postJSONObj.getJSONObject(TAG_CAPTION);
								captionTitle = captionJSONObj.getString(TAG_CAPTION_TEXT);
								captionTime = captionJSONObj.getString(TAG_CAPTION_DATE);
								instaPost = new InstagramPost(id,captionTitle, new Date(Long.parseLong(captionTime)*1000), imageURL, Integer.parseInt(likesCount));
								Log.d(TAG, "Time: "+captionTime);
								Log.d(TAG, "Title: "+captionTitle);
							} catch(JSONException ex) {
								Log.d(TAG, "Didn't find time and title for the post.");
								instaPost = new InstagramPost(id, imageURL, Integer.parseInt(likesCount));
							}

							if (instaPost!= null) instaPosts.add(instaPost);

						}


					} else if (metaCode.equals("400")) {
						haveRequest = false;
						Log.d(TAG, "Don't have access to this account.");
					} else {
						haveRequest = false;
						Log.d(TAG, "Was something else.");

					}

				} catch (JSONException e) {
					e.printStackTrace();
					Log.i(TAG, "Didn't find something.");
				}
				pageCounter++;
			}

	        return instaPosts;
		}

		protected void onPostExecute(ArrayList<InstagramPost> result) {
			if (alert != null) alert.dismiss();
		}
	}

}
