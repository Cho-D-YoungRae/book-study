package com.example.ch34;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ch34_02_ExecutorsDemo2 {

    public static void main(String[] args) throws IOException {
        Runnable task1 = () -> {
            System.out.println(Thread.currentThread().getName() + ": task1");
        };

        Runnable task2 = () -> {
            System.out.println(Thread.currentThread().getName() + ": task2");
        };

        ExecutorService exr = Executors.newFixedThreadPool(2);
        exr.submit(task1);
        exr.submit(task2);
        exr.submit(() -> {
            System.out.println(Thread.currentThread().getName() + ": Hello");
        });

        exr.shutdown();
    }
}
