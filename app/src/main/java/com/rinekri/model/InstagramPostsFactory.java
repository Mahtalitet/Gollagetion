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
    private static String[] sInstagramImgsCombinations;
    private static int sCurrentInstagramImgsCombinationSize;
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

    public int[] getCombinationImages(int size) {

        if ((sInstagramImgsCombinations == null)) {
            sCurrentInstagramImgsCombinationSize = size;
            generateCombinations(sCurrentInstagramImgsCombinationSize);
            resetCurrentCombination();

        } else if ((sInstagramImgsCombinations != null) && (sCurrentInstagramImgsCombinationSize != size)) {
            sCurrentInstagramImgsCombinationSize = size;
            generateCombinations(sCurrentInstagramImgsCombinationSize);
            resetCurrentCombination();

        } else if ((sInstagramImgsCombinations != null) && (sCurrentInstagramImgsCombinationSize == size)) {

            if (sCurrentInstagramImgsCombination != (sInstagramImgsCombinations.length-1)) {
                sCurrentInstagramImgsCombination++;
            } else {
                resetCurrentCombination();
            }

        }

        Log.e(TAG, "Current instagram combination " +sCurrentInstagramImgsCombination);

        return parseCharToNumbers(sInstagramImgsCombinations[sCurrentInstagramImgsCombination].toCharArray());
    }

    public int[] getFirstCombinationImages(int size) {

        if ((sInstagramImgsCombinations == null)) {
            sCurrentInstagramImgsCombinationSize = size;
            generateCombinations(sCurrentInstagramImgsCombinationSize);

        } else if ((sInstagramImgsCombinations != null) && (sCurrentInstagramImgsCombinationSize != size)) {
            sCurrentInstagramImgsCombinationSize = size;
            generateCombinations(sCurrentInstagramImgsCombinationSize);

        }

        resetCurrentCombination();


        return parseCharToNumbers(sInstagramImgsCombinations[sCurrentInstagramImgsCombination].toCharArray());
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

    private void generateCombinations(int size) {
        sInstagramImgsCombinations = PermutationsGenerator.getCombinations(size);
    }

    private int[] parseCharToNumbers(char[] character) {

        int[] numbers = new int[character.length];

        for (int i = 0; i < character.length; i++) {
            numbers[i] = Character.getNumericValue(character[i]);
        }
        return numbers;
    }

}
