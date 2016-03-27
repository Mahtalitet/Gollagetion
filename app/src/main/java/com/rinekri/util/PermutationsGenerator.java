package com.rinekri.util;

import android.util.Log;

import java.util.HashSet;
import java.util.concurrent.Executor;

public class PermutationsGenerator {
    private static final String TAG = "PermutationsGenerator";
    private static final int ELEMENTS = 4;

    private static Thread sThread;
    private HashSet<String> mCombinations;
    private int mCombinationsSize;

    public HashSet<String> getCombinations(int size) {
        mCombinationsSize = size;
        mCombinations = new HashSet<String>();

        GenerateExecutor mExecutor = new GenerateExecutor();
        mExecutor.execute(new RunnableForGenerateExecutor());

       while (true) {
            Log.e(TAG, "Size of comnbinations"+mCombinations.size());
            if (mCombinations.size() >= 24) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return mCombinations;
            }
        }
    }

    private class GenerateExecutor implements Executor {

        public void execute(Runnable r) {
            if (sThread != null) {
                sThread.interrupt();
                Log.e(TAG, "Back generation of mCombinations was interrupted!");
            }
            Log.e(TAG, "New generation of mCombinations!");
            sThread = new Thread(r);
            sThread.start();
        }
    }

    private class RunnableForGenerateExecutor implements Runnable {

        @Override
        public void run() {
            StringBuilder numbers = new StringBuilder();

            for (int i = 0; i < mCombinationsSize; i++) {
                numbers.append(i);
            }
            generate(numbers.toString(), 0, mCombinationsSize, mCombinations);
            Log.d(TAG, "Combinations count: " + mCombinations.size());
        }
    }

    private static void generate(String str, int k, int n, HashSet<String> resultSet){
        for(int i = k; i < n; i++){

            String temp = modifyString(str, i, k);

            char[] arr = temp.toCharArray();

            StringBuilder result = new StringBuilder();
            for (int g = 0; g < ELEMENTS; g++) {
                result.append(arr[g]);
            }
//            Log.d(TAG,"Combination:"+result.toString());
            resultSet.add(result.toString());
            generate(temp, k + 1, n, resultSet);
        }
    }

    private static String modifyString(String str, int x, int y){
        char[] arr = str.toCharArray();

        char t =  arr[x];
        arr[x] = arr[y];
        arr[y] = t;

        return new String(arr);
    }

}
