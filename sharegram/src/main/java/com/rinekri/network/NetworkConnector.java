package com.rinekri.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.rinekri.collagetion.DirectoryReturner;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ContentHandler;
import java.net.URL;
import java.util.Map;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NetworkConnector {
	public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=utf-8");
	public static final String TAG = "NetworkConnector";

    private Context mCOntext;

    public NetworkConnector(Context c) {
        mCOntext = c;
    }


//	private final Gson gson = new Gson();

	public void getByteResponce(String url) throws IOException {

//        DirectoryReturner directory = new DirectoryReturner(mCOntext);
//        File dir = directory.returnFolderDirectory("Cache", "CacheFile");
//
//        OkHttpClient client = new OkHttpClient.Builder().cache(new Cache(dir, 1000)).build();
        OkHttpClient client = new OkHttpClient.Builder().build();
	    Request request = new Request.Builder()
	        .url(url)
	        .build();

        Log.i(TAG, "initial Request: "+request.toString());
        try {
            Call call = client.newCall(request);
            Response response = call.execute();
            ResponseBody responseBody = response.body();
            Log.e(TAG, "ResponceBody size: "+responseBody.contentLength());
            byte[] byteResp = responseBody.bytes();
            ByteArrayInputStream byteInputSteam = new ByteArrayInputStream(byteResp);
            InputStreamReader inputStreamReader = new InputStreamReader(byteInputSteam);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            StringBuffer buffer = new StringBuffer();

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
                Log.i(TAG, "Line length: " + line.length());
                Log.i(TAG, "Line: "+ line.toString());
            }

            Log.v(TAG, "Buffer length: "+ buffer.length());
            Log.e(TAG, buffer.substring(buffer.length() - 500, buffer.length()));
            String finishString = buffer.toString();
            Log.v(TAG, "FinalString length: "+ finishString.length());
        } catch (IOException ex) {
            ex.printStackTrace();
        }


//		call.enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//
//			}
//
//			@Override
//			public void onResponse(Call call, final Response response) throws IOException {
//                String responseData = response.body().string();
//                Log.d(TAG, "FirstResponce: "+responseData);
//			}
//		});


//		Request request = new Request.Builder()
//				.url("https://api.github.com/gists/c2a7c39532239ff261be")
//				.build();
//		Response response = client.newCall(request).execute();
//		if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//
//		Gist gist = gson.fromJson(response.body().charStream(), Gist.class);
//		for (Map.Entry<String, GistFile> entry : gist.files.entrySet()) {
//			Log.d(TAG, entry.getKey());
//			Log.d(TAG, entry.getValue().content);
//		}

//
//
//	    InputStream byteResponce = null;
//	    try {
//			Call call = client.newCall(request);
//			Response response = client.newCall(request).execute();
//	    	while(call.isExecuted()) {
//				String body = response.body().toString();
////				Log.d(TAG, body);
//			}
//
//			byteResponce = response.body().byteStream();
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }

//	    return byteResponce;
	}

//	static class Gist {
//		Map<String, GistFile> files;
//	}
//
//	static class GistFile {
//		String content;
//	}

}
