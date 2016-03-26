package com.rinekri.thread;

public class GenerateExecutorThread {

    private static GenerateExecutorThread mExecutor;

    private static Thread thread;

    private GenerateExecutorThread(Runnable runnable) {

        if ((thread != null) && (thread.isAlive())) {
            thread.interrupt();
        }
        thread = new Thread(runnable);
        thread.start();
    }


    public static GenerateExecutorThread getExecutor(Runnable runnable) {
        if (mExecutor !=null) {

        }
        mExecutor = new GenerateExecutorThread(runnable);

        return mExecutor;
    }
}
