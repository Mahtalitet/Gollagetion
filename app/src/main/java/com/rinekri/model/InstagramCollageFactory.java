package com.rinekri.model;

import android.content.Context;
import android.util.Log;

import com.rinekri.util.PermutationsGenerator;

import java.util.HashSet;

public class InstagramCollageFactory {
    public static final String TAG = "InstagramCollageFactory";
    private static InstagramCollageFactory sInstagramCollageFactory;

    private Context mAppContext;
    private HashSet<String> sInstagramImgsCombinations;
    private int sCurrentInstagramImgsCombinationSize;
    private int sCurrentInstagramImgsCombination;

    private InstagramCollageFactory(Context c) {
        mAppContext = c;
    }

    public static InstagramCollageFactory getFactory(Context c) {

        if (sInstagramCollageFactory == null) {
            sInstagramCollageFactory = new InstagramCollageFactory(c);
        }
        return sInstagramCollageFactory;
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

            if (sCurrentInstagramImgsCombination != (sInstagramImgsCombinations.size()-1)) {
                sCurrentInstagramImgsCombination++;
            } else {
                resetCurrentCombination();
            }

        }

        Log.e(TAG, "Current instagram combination " + sCurrentInstagramImgsCombination);


        return parseCharToNumbers(getCombination().toCharArray());
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


        return parseCharToNumbers(getCombination().toCharArray());
    }

    public void resetCurrentCombination() {
        sCurrentInstagramImgsCombination = 0;
    }

    private void generateCombinations(int size) {
        PermutationsGenerator permutationsGenerator = new PermutationsGenerator();
        sInstagramImgsCombinations = permutationsGenerator.getCombinations(size);
    }

    private int[] parseCharToNumbers(char[] character) {

        int[] numbers = new int[character.length];

        for (int i = 0; i < character.length; i++) {
            numbers[i] = Character.getNumericValue(character[i]);
        }
        return numbers;
    }

    private String getCombination() {

        int i = 0;
        for (String s : sInstagramImgsCombinations) {
            if (sCurrentInstagramImgsCombination == i) {
                return s;
            }
            i++;
        }
        return null;
    }
}
