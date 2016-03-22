package com.rinekri.network;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.rinekri.collagetion.InstagramPosts;
import com.rinekri.collagetion.R;

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

	public void getPosts (String id) {
		mInstagramID = id;
		AsyncTask<Void, Void, String> requestPosts = new GetInstagramPosts().execute();

	}
	
	private class GetInstagramPosts extends AsyncTask<Void, Void, String> {
		private static final String TAG_DATA = "data";
		private static final String TAG_PAGINATION = "pagination";
		private static final String TAG_PAGINATION_NEXT_URL = "next_url";
		private static final String TAG_LIKES = "likes";
		private static final String TAG_LIKES_COUNT = "count";
		private static final String TAG_IMAGES = "images";
		private static final String TAG_IMAGES_STANDART = "standard_resolution";
		private static final String TAG_IMAGES_STANDART_URL = "url";
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

		protected String doInBackground(Void... arg0) {

		StringBuilder getPOSTSurl = new StringBuilder()
				.append(URL_MAIN)
				.append(mInstagramID)
				.append("/media/recent")
				.append("?")
				.append(URL_CLIENT_ID);

			NetworkConnector connector = new NetworkConnector(mContext);

			String allStringData = connector.getStringResponce(getPOSTSurl.toString());

	        if (allStringData != null) {
	            try {
	                JSONObject allDataJSONObj = new JSONObject(allStringData);

					JSONObject paginationJSONObj = allDataJSONObj.getJSONObject(TAG_PAGINATION);
	                JSONArray postsDataArr = allDataJSONObj.getJSONArray(TAG_DATA);

					ArrayList<InstagramPosts> instaPosts = new ArrayList<InstagramPosts>();

	                for (int i = 0; i < postsDataArr.length(); i++) {
						Log.d(TAG, "IMAGE "+i);
	                	JSONObject postJSONObj = postsDataArr.getJSONObject(i);

						JSONObject likesJSONObj = postJSONObj.getJSONObject(TAG_LIKES);
						String likesCount = likesJSONObj.getString(TAG_LIKES_COUNT);
						Log.e(TAG, "Likes: "+likesCount);

						JSONObject imageJSONObj = postJSONObj.getJSONObject(TAG_IMAGES);
						JSONObject imageStandartJSONObj = imageJSONObj.getJSONObject(TAG_IMAGES_STANDART);
						String imageURL = imageStandartJSONObj.getString(TAG_IMAGES_STANDART_URL);
						Log.e(TAG, "URL: "+imageURL);

						JSONObject captionJSONObj = postJSONObj.getJSONObject(TAG_CAPTION);
						String captionTime = captionJSONObj.getString(TAG_CAPTION_DATE);
						String captionTitle = captionJSONObj.getString(TAG_CAPTION_TEXT);
						Log.e(TAG, "Time: "+captionTime);
						Log.e(TAG, "Title: "+captionTitle);

						String id = postJSONObj.getString(TAG_ID);
						Log.e(TAG, "ID: "+id);

						InstagramPosts instaPost = new InstagramPosts(id,captionTitle, new Date(Integer.getInteger(captionTime)*1000), imageURL, Integer.getInteger(likesCount));
						instaPosts.add(instaPost);
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

}
