package com.rinekri.model;

import android.content.Context;
import android.util.Log;

import com.rinekri.json.InstagramJSONWorker;

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
            sCurrentInstagramImgsCombination = 0;
            Log.e(TAG, "First initialization of get IDs: " + sInstagramImgsCombination.toString());
            Log.d(TAG, "All combinations for the first initialization:");
            returnLog();

        } else if ((sInstagramImgsCombination != null) && (sInstagramImgsCombination.size() != postsIds.size())) {
            sInstagramImgsCombination = postsIds;
            generateCombinations();
            sCurrentInstagramImgsCombination = 0;
            Log.e(TAG, "Another initialization of get IDs: "+sInstagramImgsCombination.toString());
            Log.d(TAG, "All combinations for the another initialization: ");
            returnLog();

        } else if ((sInstagramImgsCombination != null) && (sInstagramImgsCombination.size() == postsIds.size())) {

            if (sCurrentInstagramImgsCombination != (sInstagramImgsCombinations.length-1)) {
                sCurrentInstagramImgsCombination++;
            } else {
                sCurrentInstagramImgsCombination = 0;
            }

            for (int i = 0; i < sInstagramImgsCombination.size(); i++) {
                String id = sInstagramImgsCombination.get(i);
                if (!id.equals(postsIds.get(i))) {
                    sInstagramImgsCombination = postsIds;
                    generateCombinations();
                    sCurrentInstagramImgsCombination = 0;
                    Log.e(TAG, "New initialization of IDs: " + sInstagramImgsCombination.toString());
                    Log.d(TAG, "All combinations for the first initialization:");
                    returnLog();
                    break;
                }
            }
        }
        Log.e(TAG, "Current instagram combination "+sCurrentInstagramImgsCombination);

        return sInstagramImgsCombinations[sCurrentInstagramImgsCombination];
    }

    public void addInstagramPost(InstagramPost post) {
        mInstagramPosts.add(post);
    }

    public void deleteInstagramPost(InstagramPost post) {
        mInstagramPosts.remove(post);
    }

    private void generateCombinations() {
        if (sInstagramImgsCombination.size() == 4) {
            HashSet<ArrayList<String>> combinations = new HashSet<ArrayList<String>>();
            getCombinations(sInstagramImgsCombination, 0, sInstagramImgsCombination.size(), combinations);
            Log.e(TAG, "Generated IDs at HasSet: " + combinations.toString());
            sInstagramImgsCombinations = new ArrayList[combinations.size()];
            combinations.toArray(sInstagramImgsCombinations);

        } else if (sInstagramImgsCombination.size() > 4) {

        }
    }

    private static void getCombinations(ArrayList<String> str, int k, int n, HashSet<ArrayList<String>> resultSet){
        for(int i = k; i < n; i++){
            ArrayList<String> temp = modifyString(str, i, k);
            resultSet.add(temp);
            getCombinations(temp, k + 1, n, resultSet);
        }
    }

    private static ArrayList<String> modifyString(ArrayList<String> str, int x, int y){
        String[] arr = new String[str.size()];
        str.toArray(arr);
        String t =  arr[x];
        arr[x] = arr[y];
        arr[y] = t;
        ArrayList<String> s = new ArrayList<String>();
        for(String id : arr) {
            s.add(id);
        }
        return s;
    }

    private void returnLog() {
        int i = 0;
        for(ArrayList<String> combination : sInstagramImgsCombinations) {
            Log.d(TAG, "Combination "+i++);

            for (String id : combination ) {
                Log.d(TAG, "ID: "+id);
            }
        }
    }
}
