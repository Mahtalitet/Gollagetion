package com.rinekri.network;

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rinekri.collagetion.R;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class InstagramJSONWorker {
	private static final String TAG = "InstagramJSONWorker";
	
	private static final String URL_MAIN = "api.instagram.com/v1/users";
	private static final String URL_SEARCH = "search"; 
	private static final String URL_MEDIA = "/media/recent";
	private static final String URL_CLIENT_ID ="client_id=9734d32bcee14651829e7b2bed26b4c3";
	
	private String mInstagramNick;
	private String mInstagramID;
	private Context mContext;
	
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
	
//	public void getPosts (String id) {
//		mInstagramID = id;
//		AsyncTask<Void, Void, String> requestPosts = new GetInstagramPosts().execute();
//		
//	}
	
	
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
//			HttpUrl getIDurl = new HttpUrl.Builder()
//					.scheme("https")
//					.host(URL_MAIN)
//					.addPathSegment(URL_SEARCH)
//					.addQueryParameter("q", mInstagramNick)
//					.addQueryParameter("&", URL_CLIENT_ID)
//					.build();
			
//			Log.d(TAG, getIDurl.toString());
			
			
	 
//	        String jsonStr = sh.makeServiceCall(getIDurl.toString(), ServiceHandler.GET);
//	 
//	        Log.d(TAG, "> " + jsonStr);
//	 
//	        if (jsonStr != null) {
//	            try {
//	                JSONObject jsonObj = new JSONObject(jsonStr);
//	                     
//	                JSONArray usersData = jsonObj.getJSONArray(TAG_DATA);
//	 
//	                for (int i = 0; i < usersData.length(); i++) {
//	                	JSONObject c = usersData.getJSONObject(i);
//	                         
//	                	String nick = c.getString(TAG_USERNAME);
//	                	Log.e(TAG, "nick: "+nick);
//	                    if (nick.equals(mInstagramNick)) {
//	                    	String id = c.getString(TAG_ID);
//	                    	Log.e(TAG, "ID: "+id);
//	                        return id;
//	                    }
//	                }
//	            } catch (JSONException e) {
//	            	e.printStackTrace();
//		        	Log.i(TAG, "Didn't find something");
//	            }
//	        } else {
//	        	   Log.e(TAG, "Couldn't get any data from the url.");
//	        }
	 
	        return null;
		}
	        
		protected void onPostExecute(String result) {
			if (alert != null) alert.dismiss();
		}
	}
	
//	private class GetInstagramPosts extends AsyncTask<Void, Void, String> {
//		private static final String TAG_DATA = "data";
//		private static final String TAG_PAGINATION = "pagination";
//		private static final String TAG_PAGINATION_NEXT_URL = "next_url";
//		private static final String TAG_LIKES = "likes";
//		private static final String TAG_LIKES_COUNT = "count";
//		private static final String TAG_IMAGES = "images";
//		private static final String TAG_IMAGES_STANDART = "standard_resolution";
//		private static final String TAG_IMAGES_STANDART_URL = "url";
//		private static final String TAG_CAPTION = "caption";
//		private static final String TAG_CAPTION_DATE = "created_time";
//		private static final String TAG_CAPTION_TEXT = "text";
//		
//		private AlertDialog alert;
//		
//		protected void onPreExecute() {
//			AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
//			alertBuilder.setMessage(R.string.dialog_get_posts_title);
//			alertBuilder.setCancelable(false);
//			alert = alertBuilder.create();
//			alert.show();
//		}
//		   
//		protected String doInBackground(Void... arg0) {
//			
//			StringBuilder getPostsurl = new StringBuilder();
//			getPostsurl.append(URL_MAIN);
//			getPostsurl.append(mInstagramID);
//			getPostsurl.append(URL_MEDIA);
//			getPostsurl.append("?");
//			getPostsurl.append(URL_CLIENT_ID);
//			   
//	        ServiceHandler sh = new ServiceHandler();
//	 
//	        String jsonStr = sh.makeServiceCall(getPostsurl.toString(), ServiceHandler.GET);
//	 
//	        Log.i(TAG, "> " + jsonStr);
//	 
//	        if (jsonStr != null) {
//	            try {
//	                JSONObject jsonObj = new JSONObject(jsonStr);
//	                     
//	                JSONArray usersData = jsonObj.getJSONArray(TAG_DATA);
//	 
//	                for (int i = 0; i < usersData.length(); i++) {
//	                	JSONObject c = usersData.getJSONObject(i);
//	                         
//	                	String nick = c.getString(TAG_USERNAME);
//	                	Log.e(TAG, "nick: "+nick);
//	                    if (nick.equals(mInstagramNick)) {
//	                    	String id = c.getString(TAG_ID);
//	                    	Log.e(TAG, "ID: "+id);
//	                        return id;
//	                    }
//	                }
//	            } catch (JSONException e) {
//	            	e.printStackTrace();
//		        	Log.i(TAG, "Didn't find something");
//	            }
//	        } else {
//	        	   Log.e(TAG, "Couldn't get any data from the url.");
//	        }
//	 
//	        return null;
//		}
//	        
//		protected void onPostExecute(String result) {
//			if (alert != null) alert.dismiss();
//		}
//	}
//	
}
