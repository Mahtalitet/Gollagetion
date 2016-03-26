package com.rinekri.utility;

import android.util.Log;

import java.util.HashSet;

public class PermutationsGenerator {
    private static final String TAG = "PermutationsGenerator";
    private static final int ELEMENTS = 4;

    public static String[] getCombinations(int size) {

        StringBuilder numbers = new StringBuilder();
        for (int k = 0; k < size; k++) {
            numbers.append(k);
        }
        Log.d(TAG,"Initial string:" + numbers.toString());

        HashSet<String> sInstagramImgsCombinations = new HashSet<String>();

        generate(numbers.toString(), 0, size, sInstagramImgsCombinations);

        String[] finalCombinations = new String[sInstagramImgsCombinations.size()];
        sInstagramImgsCombinations.toArray(finalCombinations);

//        int i = 0;
//        for(String combination : finalCombinations){
//            i++;
//            Log.d(TAG, "Combination "+i+" :"+combination);
//        }
        Log.d(TAG, "Array size "+finalCombinations.length);

        return finalCombinations;
    }


    public static void generate(String str, int k, int n, HashSet<String> resultSet){
        for(int i = k; i < n; i++){

            String temp = modifyString(str, i, k);

            char[] arr = temp.toCharArray();

            StringBuilder result = new StringBuilder();
            for (int g = 0; g < ELEMENTS; g++) {
                result.append(arr[g]);
            }
            resultSet.add(result.toString());
            generate(temp, k + 1, n, resultSet);
        }
    }

    public static String modifyString(String str, int x, int y){
        char[] arr = str.toCharArray();

        char t =  arr[x];
        arr[x] = arr[y];
        arr[y] = t;

        return new String(arr);
    }

}
