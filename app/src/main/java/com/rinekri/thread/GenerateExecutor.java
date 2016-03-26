package com.rinekri.thread;


import java.util.concurrent.Executor;

public class GenerateExecutor implements Executor {

    public void execute(Runnable r) {
        GenerateExecutorThread.getExecutor(r);
    }

}
