package com.rinekri.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rinekri.collagetion.R;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import okhttp3.HttpUrl;

public class InstagramJSONWorker {
	private static final String TAG = "InstagramJSONWorker";
	
	private static final String URL_MAIN = "https://api.instagram.com/v1/users/";
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
//			StringBuilder getIDurl = new StringBuilder()
//					.append(URL_MAIN)
//					.append("search?q=")
//			        .append(mInstagramNick)
//					.append("&")
//					.append(URL_CLIENT_ID);

			StringBuilder getPOSTSurl = new StringBuilder()
					.append(URL_MAIN)
					.append("2261843945")
					.append("/media/recent")
					.append("?")
					.append(URL_CLIENT_ID);

			NetworkConnector connector = new NetworkConnector(mContext);
			try {
//				connector.getByteResponce(getIDurl.toString());
				connector.getByteResponce(getPOSTSurl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}

//			HttpURLConnection urlConnection = null;
//			BufferedReader reader = null;
//			String resultJson;
//
//			try {
//				URL url = new URL(getIDurl.toString());
//
//				urlConnection = (HttpURLConnection) url.openConnection();
//				urlConnection.setRequestMethod("GET");
//				urlConnection.connect();
//
//				InputStream inputStream = urlConnection.getInputStream();
//				StringBuffer buffer = new StringBuffer();
//
//				reader = new BufferedReader(new InputStreamReader(inputStream));
//
//				String line;
//				while ((line = reader.readLine()) != null) {
//					buffer.append(line);
//					Log.d(TAG, buffer.toString());
//				}
//
//				resultJson = buffer.toString();
//
//				Log.d(TAG,resultJson.toString());
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}


//			Log.d(TAG, getIDurl.toString());
//
//			NetworkConnector connector = new NetworkConnector();
//			try {
//				connector.getByteResponce(getIDurl.toString());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			BufferedReader reader = new BufferedReader(new InputStreamReader(byteResponce));
//
//			String line = null;
//			try {
//				while ((line = reader.readLine()) != null) {
//					Log.d(TAG, "> " + line);
//                }
//			} catch (IOException e) {
//				e.printStackTrace();
//			}


//	        String jsonStr = sh.makeServiceCall(getIDurl.toString(), ServiceHandler.GET);
//

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
