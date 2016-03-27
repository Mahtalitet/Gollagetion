package com.rinekri.model;

import android.content.Context;

import com.rinekri.json.InstagramJSONWorker;

import java.util.ArrayList;
import java.util.Collections;

public class InstagramPostsFactory {
    public static final String TAG = "InstagramPostsFactory";

    private static InstagramPostsFactory sInstagramPostsFactory;
    private static String sUserID;

    private Context mAppContext;
    private ArrayList<InstagramPost> mInstagramPosts;
    private InstagramJSONWorker JSONworker;

    private InstagramPostsFactory(Context c) {
        mAppContext = c;
        JSONworker = new InstagramJSONWorker(mAppContext);
    }

    public static InstagramPostsFactory getFactory(Context c) {

        if (sInstagramPostsFactory == null) {
            sInstagramPostsFactory = new InstagramPostsFactory(c);
        }
        return sInstagramPostsFactory;
    }

    public ArrayList<InstagramPost> getInstagramPosts(String id) {
        if ((sUserID == null) || (!sUserID.equals(id))) {
            sUserID = id;
            mInstagramPosts = JSONworker.getPosts(sUserID);
        }
        return mInstagramPosts;
    }

    public boolean getInstagramPostsStatus(String id) {
        if (sUserID == null) {
            return false;
        }

        if (sUserID.equals(id)) {
            return true;
        }

        return false;
    }


    public ArrayList<InstagramPost> getSortedForLikesInstagramPosts(ArrayList<InstagramPost> unsortedPosts) {
        Collections.sort(unsortedPosts);
        mInstagramPosts = unsortedPosts;
        return mInstagramPosts;
    }

    public InstagramPost getInstagramPost(String imageID) {
        for(InstagramPost post : mInstagramPosts) {
            if (post.getPostID().equals(imageID)) return post;
        }
        return null;
    }
}
