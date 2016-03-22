package com.rinekri.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkConnector {
	public static final String TAG = "NetworkConnector";

    private Context mContext;
    private static OkHttpClient client = new OkHttpClient();
    private String bitmapURL;

    public NetworkConnector(Context c) {
        mContext = c;
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
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return stringResponce;
    }

    public String getStatusResponce(String url) {

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



    public Bitmap getBitmapFromURL(String url) {
        bitmapURL = url;
        AsyncTask<Void, Void, Bitmap> requestBitmap = new GetBitmap().execute();

        Bitmap postImage = null;

        try {
            postImage = requestBitmap.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return postImage;
    }


    private class GetBitmap extends AsyncTask<Void, Void, Bitmap> {

        protected Bitmap doInBackground(Void... arg0) {
            Bitmap photo = null;
            try {
                URL url  = new URL(bitmapURL);
                 photo = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return photo;
        }
    }
}
