package com.rinekri.model;

import android.content.Context;

import com.rinekri.json.InstagramJSONWorker;

import java.util.ArrayList;

public class InstagramPostsFactory {
    public static final String TAG = "InstagramPostsFactory";

    private static InstagramPostsFactory sInstagramPostsFactory;
    private Context mAppContext;

    private ArrayList<InstagramPost> mInstagramPosts;
    private InstagramJSONWorker JSONworker;

    private InstagramPostsFactory(Context c, String userID) {

        JSONworker = new InstagramJSONWorker(c);
        mInstagramPosts = JSONworker.getPosts(userID);

        mAppContext = c;
    }

    public static InstagramPostsFactory getFactory(Context c, String userID) {
        if (sInstagramPostsFactory == null) {
            sInstagramPostsFactory = new InstagramPostsFactory(c, userID);
        }
        return sInstagramPostsFactory;
    }

    public ArrayList<InstagramPost> getInstagramPosts() {
        return mInstagramPosts;
    }

    public InstagramPost getInstagramPost(String postID) {
        for(InstagramPost post : mInstagramPosts) {
            if (post.getPostID().equals(postID)) return post;
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
