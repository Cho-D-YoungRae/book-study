package com.example.ch34;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ch34_01_ExecutorsDemo {

    public static void main(String[] args) throws IOException {
        Runnable task = () -> {
            int n1 = 10;
            int n2 = 20;
            System.out.println(Thread.currentThread().getName() + ": " + (n1 + n2));
        };

        ExecutorService exr = Executors.newSingleThreadExecutor();
        exr.submit(task);

        System.out.println("[" + Thread.currentThread().getName() + "] End");
        exr.shutdown();
    }
}
