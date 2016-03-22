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
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NetworkConnector {
	public static final String TAG = "NetworkConnector";

    private Context mCOntext;
    private static OkHttpClient client = new OkHttpClient();

    public NetworkConnector(Context c) {
        mCOntext = c;
    }

    public String getStringResponce(String url) {

        Request request = new Request.Builder()
                .url(url)
                .build();

        String stringResponce = null;
        try {
            Call call = client.newCall(request);
            Response response = call.execute();
            stringResponce = response.body().string();

            if (stringResponce != null) {
                Log.e(TAG, "Response length: " + stringResponce.length());
//                Log.e(TAG, "Last part of response "+stringResponce.substring(stringResponce.length() - 500, stringResponce.length()));
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return stringResponce;
    }
}
