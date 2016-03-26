package com.rinekri.utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class GetRandomImagesIds {
    private static final int ELEMENTS = 4;
    static Set<String> resultSet = new HashSet<String>();

    public static void main(String[] args){
        ArrayList<String> sInstagramImgsCombination = new ArrayList<String>();

        sInstagramImgsCombination.add("1136456667756342109_2261843945");
        sInstagramImgsCombination.add("1148012811326957685_2261843945");
        sInstagramImgsCombination.add("1159398661465942227_2261843945");
        sInstagramImgsCombination.add("1172335632467208654_2261843945");
//        sInstagramImgsCombination.add("1142029029092469260_2261843945");
//        sInstagramImgsCombination.add("1136484808893770298_2261843945");

        StringBuilder numbers = new StringBuilder();

        for (int k = 0; k < sInstagramImgsCombination.size(); k++) {
            numbers.append(k);
        }
                System.out.println("Initial string:"+numbers.toString());

        HashSet<String> sInstagramImgsCombinations = new HashSet<String>();
        printCombinations(numbers.toString(), 0, sInstagramImgsCombination.size(), sInstagramImgsCombinations);


        String[] finalCombinations = new String[sInstagramImgsCombinations.size()];
        sInstagramImgsCombinations.toArray(finalCombinations);


        int i = 0;
        for(String combination : finalCombinations){
            i++;
            System.out.println("Combination "+i+" :"+combination);
            char[] chare = combination.toCharArray();
            for(char c: chare) {
                System.out.println("Char"+c);
            }

            System.out.println();
        }




    }

    public static void printCombinations(String str, int k, int n, HashSet<String> resultSet){
        for(int i = k; i < n; i++){

            String temp = modifyString(str, i, k);

            char[] arr = temp.toCharArray();

            StringBuilder result = new StringBuilder();
            for (int g = 0; g < ELEMENTS; g++) {
                result.append(arr[g]);
            }
            resultSet.add(result.toString());
            printCombinations(temp, k+1, n, resultSet);
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