package com.example.ch34;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Ch34_03_CallableDemo {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Callable<Integer> task = () -> {
            int sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += i;
            }
            return sum;
        };

        ExecutorService exr = Executors.newSingleThreadExecutor();

        Future<Integer> fur = exr.submit(task);
        System.out.println("result: " + fur.get());

        exr.shutdown();
    }
}
