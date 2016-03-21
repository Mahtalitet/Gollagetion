package com.rinekri.network;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkConnector {
	public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=utf-8");
	public static final String TAG = "NetworkConnector";

	OkHttpClient client;

	public InputStream getByteResponce(String url) {
		client = new OkHttpClient();

	    Request request = new Request.Builder()
	        .url(url)
	        .build();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {

			}

			@Override
			public void onResponse(Call call, final Response response) throws IOException {
					String responseData = response.body().string();
					Log.d(TAG, responseData);
			}
		});


	    InputStream byteResponce = null;
	    try {
			Call call = client.newCall(request);
			Response response = client.newCall(request).execute();
	    	while(call.isExecuted()) {
				String body = response.body().toString();
//				Log.d(TAG, body);
			}

			byteResponce = response.body().byteStream();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return byteResponce;
	}
}
