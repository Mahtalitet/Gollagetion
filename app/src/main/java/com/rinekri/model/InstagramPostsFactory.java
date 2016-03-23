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
        mInstagramPosts = JSONworker.getPosts(sUserID);
    }

    public static InstagramPostsFactory getFactory(Context c, String id) {

        if (((sInstagramPostsFactory == null) && (sUserID == null)) || ((sInstagramPostsFactory != null) && (!sUserID.equals(id))) ) {
            sUserID = id;
            sInstagramPostsFactory = new InstagramPostsFactory(c);
        }
        return sInstagramPostsFactory;
    }

    public ArrayList<InstagramPost> getInstagramPosts() {
        return mInstagramPosts;
    }

    public ArrayList<InstagramPost> getSortedForLikesInstagramPosts() {
        Collections.sort(mInstagramPosts);
        return mInstagramPosts;
    }

    public InstagramPost getInstagramPost(String imageID) {
        for(InstagramPost post : mInstagramPosts) {
            if (post.getPostID().equals(imageID)) return post;
        }
        return null;
    }

    public void addInstagramPost(InstagramPost post) {
        mInstagramPosts.add(post);
    }

    public void deleteInstagramPost(InstagramPost post) {
        mInstagramPosts.remove(post);
    }
}
