package com.rinekri.model;

import android.content.Context;
import android.util.Log;

import com.rinekri.json.InstagramJSONWorker;
import com.rinekri.utility.PermutationsGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class InstagramPostsFactory {
    public static final String TAG = "InstagramPostsFactory";

    private static InstagramPostsFactory sInstagramPostsFactory;
    private static String sUserID;
    private static ArrayList<String> sInstagramImgsCombination;
    private static ArrayList<String>[] sInstagramImgsCombinations;
    private static int sCurrentInstagramImgsCombination;

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

    public static InstagramPostsFactory getFactory(Context c) {

        if (sInstagramPostsFactory == null) {
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


    public ArrayList<String> getCombinationImages(ArrayList<String> postsIds) {

        if ((sInstagramImgsCombination == null)) {
            sInstagramImgsCombination = postsIds;
            generateCombinations();
            resetCurrentCombination();

        } else if ((sInstagramImgsCombination != null) && (sInstagramImgsCombination.size() != postsIds.size())) {
            sInstagramImgsCombination = postsIds;
            generateCombinations();
            resetCurrentCombination();

        } else if ((sInstagramImgsCombination != null) && (sInstagramImgsCombination.size() == postsIds.size())) {

            if (sCurrentInstagramImgsCombination != (sInstagramImgsCombinations.length-1)) {
                sCurrentInstagramImgsCombination++;
            } else {
                resetCurrentCombination();
            }

            for (int i = 0; i < sInstagramImgsCombination.size(); i++) {
                String id = sInstagramImgsCombination.get(i);
                if (!id.equals(postsIds.get(i))) {
                    sInstagramImgsCombination = postsIds;
                    generateCombinations();
                    resetCurrentCombination();
                    break;
                }
            }
        }
        Log.e(TAG, "Current instagram combination " + sCurrentInstagramImgsCombination);

        return sInstagramImgsCombinations[sCurrentInstagramImgsCombination];
    }

    public void resetCurrentCombination() {
        sCurrentInstagramImgsCombination = 0;
    }

    public void addInstagramPost(InstagramPost post) {
        mInstagramPosts.add(post);
    }

    public void deleteInstagramPost(InstagramPost post) {
        mInstagramPosts.remove(post);
    }

    private void generateCombinations() {
        HashSet<ArrayList<String>> combinations = new HashSet<ArrayList<String>>();
        PermutationsGenerator.getCombinations(sInstagramImgsCombination, 0, sInstagramImgsCombination.size(), combinations);
        sInstagramImgsCombinations = new ArrayList[combinations.size()];
        combinations.toArray(sInstagramImgsCombinations);
    }
}
