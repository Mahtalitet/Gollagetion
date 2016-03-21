//package com.rinekri.network;
//
//import java.io.IOException;
//import java.net.URL;
//
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
//public class NetworkConnector {
//	OkHttpClient client;
//	
//	public String getRequest(URL url) {
//		client = new OkHttpClient();
//		
//	    Request request = new Request.Builder()
//	        .url(url)
//	        .build();
//	    
//	    String stringRequest = null;
//	    try {
//	    	Response response = client.newCall(request).execute();
//	        stringRequest = response.body().string();
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//	    
//	    return stringRequest;
//	}
//}
