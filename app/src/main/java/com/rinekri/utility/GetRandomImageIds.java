package com.rinekri.utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class Myclass
{
    private static ArrayList<String> sInstagramImgsCombination = new ArrayList<String>();
    private static HashSet<ArrayList<String>> sInstagramImgsCombinations = new HashSet<ArrayList<String>>();
    static Set<String> resultSet = new HashSet<String>();

    public static void main(String[] args){

        sInstagramImgsCombination.add("1136456667756342109_2261843945");
        sInstagramImgsCombination.add("1148012811326957685_2261843945");
        sInstagramImgsCombination.add("1159398661465942227_2261843945");
        sInstagramImgsCombination.add("1172335632467208654_2261843945");

        printCombinations(sInstagramImgsCombination, 0, sInstagramImgsCombination.size(), sInstagramImgsCombinations);

//        int i = 0;
//        for(ArrayList<String> combination : sInstagramImgsCombinations){
//            i++;
//            System.out.println("Combination "+i+" :");
//
//            for(String id : combination) {
//                System.out.println("Id: " +id);
//            }
//            System.out.println();
//        }

    }

    public static void printCombinations(ArrayList<String> str, int k, int n, HashSet<ArrayList<String>> resultSet){
        for(int i = k; i < n; i++){
            ArrayList<String> temp = modifyString(str, i, k);
            resultSet.add(temp);
            printCombinations(temp, k+1, n, resultSet);
        }
    }

    public static ArrayList<String> modifyString(ArrayList<String> str, int x, int y){
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