package com.rinekri.utility;

import java.util.ArrayList;
import java.util.HashSet;

public class PermutationsGenerator {
    private static final int ELEMENTS = 4;

    public static void getCombinations(ArrayList<String> str, int k, int n, HashSet<ArrayList<String>> resultSet){
        for(int i = k; i < n; i++){
            ArrayList<String> temp = modifyString(str, i, k);
            ArrayList<String> result = new ArrayList<String>();
            String[] arr = new String[temp.size()];
            temp.toArray(arr);
            for (int g = 0; g < ELEMENTS; g++) {
                result.add(arr[g]);
            }
            resultSet.add(result);
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

}
