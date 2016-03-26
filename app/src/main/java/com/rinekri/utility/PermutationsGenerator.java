package com.rinekri.utility;

import android.util.Log;

import java.util.HashSet;
import java.util.concurrent.Executor;

public class PermutationsGenerator {
    private static final String TAG = "PermutationsGenerator";
    private static final int ELEMENTS = 4;

    private static HashSet<String> combinations;
    private static int combinationsSize;


    public HashSet<String> getCombinations(int size) {
        combinationsSize = size;
        combinations = new HashSet<String>();

        GenerateExecutor executor = new GenerateExecutor();
        executor.execute(new RunnableForGenerateExecutor());

        while (true) {
          if (combinations.size() > 0) {
              return combinations;
          }
        }
    }


    private class GenerateExecutor implements Executor {

        public void execute(Runnable r) {
            new Thread(r).start();
        }
    }

    private class RunnableForGenerateExecutor implements Runnable {

        @Override
        public void run() {
            StringBuilder numbers = new StringBuilder();

            for (int i = 0; i < combinationsSize; i++) {
                numbers.append(i);
            }
            generate(numbers.toString(), 0, combinationsSize, combinations);
            Log.d(TAG,"Combinations count: "+combinations.size() );
        }
    }


    public static void generate(String str, int k, int n, HashSet<String> resultSet){
        for(int i = k; i < n; i++){

            String temp = modifyString(str, i, k);

            char[] arr = temp.toCharArray();

            StringBuilder result = new StringBuilder();
            for (int g = 0; g < ELEMENTS; g++) {
                result.append(arr[g]);
            }
            Log.d(TAG,"Combination:"+result.toString());
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
